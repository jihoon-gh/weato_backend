package allG.weato.config.oauth.controller;


import allG.weato.config.oauth.authToken.AuthToken;
import allG.weato.config.oauth.authToken.AuthTokenProvider;
import allG.weato.config.oauth.model.ApiResponse;
import allG.weato.config.oauth.model.AuthReqModel;
import allG.weato.config.oauth.properties.AppProperties;
import allG.weato.config.oauth.util.CookieUtil;
import allG.weato.config.oauth.util.HeaderUtil;
import allG.weato.domains.enums.Role;
import allG.weato.domains.member.MemberRefreshTokenRepository;
import allG.weato.domains.member.entities.MemberPrincipal;
import allG.weato.domains.member.entities.MemberRefreshToken;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AppProperties appProperties;
    private final AuthTokenProvider authTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final MemberRefreshTokenRepository memberRefreshTokenRepository;
    private final static long  THREE_DAYS_MSEC = 259200000;
    private final static String REFRESH_TOKEN = "refresh_token";



    @PostMapping("/login")
    public ApiResponse login (HttpServletRequest request, HttpServletResponse response, @RequestBody AuthReqModel authReqModel){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authReqModel.getId(),
                        authReqModel.getPassword()
                )
        );

        String userId = authReqModel.getId();
        System.out.println("userId = " + userId);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Date now = new Date();
        AuthToken accessToken = authTokenProvider.createAuthToken(
                userId,((MemberPrincipal)authentication.getPrincipal()).getRole().getCode(),
                new Date(now.getTime()+appProperties.getAuth().getTokenExpiry())
        );

        long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();
        AuthToken refreshToken = authTokenProvider.createAuthToken(
                appProperties.getAuth().getTokenSecret(),
                new Date(now.getTime()+refreshTokenExpiry)
        );

        MemberRefreshToken memberRefreshToken = memberRefreshTokenRepository.findByUserId(userId);
        if(memberRefreshToken==null){
            memberRefreshToken = new MemberRefreshToken(userId,refreshToken.getToken());
            memberRefreshTokenRepository.saveAndFlush(memberRefreshToken);
        }
        else{
            memberRefreshToken.setRefreshToken(refreshToken.getToken());
        }

        int cookieMaxAge= (int) refreshTokenExpiry/60;
        CookieUtil.deleteCookie(request,response,REFRESH_TOKEN);
        CookieUtil.addCookie(response, REFRESH_TOKEN,refreshToken.getToken(),cookieMaxAge);

        return ApiResponse.success("token",accessToken.getToken());
    }

    @GetMapping("/refresh")
    public ApiResponse refreshToken(HttpServletRequest request,HttpServletResponse response){
        String accessToken = HeaderUtil.getAccessToken(request);
        AuthToken authToken = authTokenProvider.convertAuthToken(accessToken);
        if(!authToken.validate()){
            return ApiResponse.invalidAccessToken();
        }

        //check token is expired or not
        Claims claims = authToken.getExpiredTokenClaims();
        if(claims==null){
            return ApiResponse.notExpiredTokenYet();
        }

        String userId = claims.getSubject();
        Role role = Role.of(claims.get("role",String.class));

        //refreshToken
        String refreshToken = CookieUtil.getCookie(request, REFRESH_TOKEN)
                .map(Cookie::getValue)
                .orElse((null));
        AuthToken authRefreshToken = authTokenProvider.convertAuthToken(refreshToken);

        if(authRefreshToken.validate()){
            return ApiResponse.invalidRefreshToken();
        }

        MemberRefreshToken memberRefreshToken = memberRefreshTokenRepository.findByUserIdAndRefreshToken(userId,refreshToken);
        if(memberRefreshToken ==null){
            return ApiResponse.invalidRefreshToken();
        }

        Date now = new Date();
        AuthToken newAccessToken = authTokenProvider.createAuthToken(
                userId,role.getCode(),new Date(now.getTime()+ appProperties.getAuth().getTokenExpiry())
        );
        long validTime = authRefreshToken.getTokenClaims().getExpiration().getTime()- now.getTime();
        if(validTime<=THREE_DAYS_MSEC){
            long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();
            authRefreshToken=authTokenProvider.createAuthToken(appProperties.getAuth().getTokenSecret(),new Date(now.getTime()+refreshTokenExpiry));

            memberRefreshToken.setRefreshToken(authRefreshToken.getToken());
            int cookieMaxAge = (int) refreshTokenExpiry / 60;
            CookieUtil.deleteCookie(request,response,REFRESH_TOKEN);
            CookieUtil.addCookie(response,REFRESH_TOKEN,authRefreshToken.getToken(),cookieMaxAge);
        }
        return ApiResponse.success("token",newAccessToken.getToken());
    }

}
