package allG.weato.config.oauth.handler;


import allG.weato.config.oauth.authToken.AuthToken;
import allG.weato.config.oauth.authToken.AuthTokenProvider;
import allG.weato.config.oauth.properties.AppProperties;
import allG.weato.config.oauth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import allG.weato.config.oauth.util.CookieUtil;
import allG.weato.config.oauth2.OAuth2UserInfo;
import allG.weato.config.oauth2.OAuth2UserInfoFactory;
import allG.weato.domains.enums.ProviderType;
import allG.weato.domains.enums.Role;
import allG.weato.domains.member.MemberRefreshTokenRepository;
import allG.weato.domains.member.entities.MemberRefreshToken;
import jdk.dynalink.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import static allG.weato.config.oauth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository.REDIRECT_URI_PARAM_COOKIE_NAME;
import static allG.weato.config.oauth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository.REFRESH_TOKEN;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final AuthTokenProvider authTokenProvider;
    private final AppProperties appProperties;
    private final MemberRefreshTokenRepository memberRefreshTokenRepository;
    private final OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestBasedOnCookieRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        String targetUrl = determineTargetUrl(request,response,authentication);

        if(response.isCommitted()){
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return ;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,Authentication authentication){
        Optional<String> redirectUri = CookieUtil.getCookie(request,REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        if(redirectUri.isPresent()&&!isAuthorizedRedirectUri(redirectUri.get())){
            throw new IllegalArgumentException("Unauthorized Redirect URI");
        }

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        ProviderType providerType = ProviderType.valueOf(authToken.getAuthorizedClientRegistrationId().toUpperCase());

        OidcUser user = ((OidcUser) authentication.getPrincipal());
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType,user.getAttributes());
        Collection <? extends GrantedAuthority> authorities = ((OidcUser) authentication.getPrincipal()).getAuthorities();

        Role role = hasAuthority(authorities, Role.ADMIN.getCode()) ? Role.ADMIN : Role.USER;

        Date now = new Date();
        AuthToken accessToken = authTokenProvider.createAuthToken(
                userInfo.getId(),
                role.getCode(),
                new Date(now.getTime()+appProperties.getAuth().getTokenExpiry())
        );

        long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();

        AuthToken refreshToken = authTokenProvider.createAuthToken(
                appProperties.getAuth().getTokenSecret(),new Date(now.getTime()+refreshTokenExpiry)
        );

        MemberRefreshToken memberRefreshToken = memberRefreshTokenRepository.findByUserId(userInfo.getId());
        if(memberRefreshToken != null){
            memberRefreshToken.setRefreshToken(refreshToken.getToken());
        }else{
            memberRefreshToken = new MemberRefreshToken(userInfo.getId(), refreshToken.getToken());
            memberRefreshTokenRepository.saveAndFlush(memberRefreshToken);
        }

        int cookieMaxAge = (int) refreshTokenExpiry / 60;

        CookieUtil.deleteCookie(request,response,REFRESH_TOKEN);
        CookieUtil.addCookie(response,REFRESH_TOKEN,refreshToken.getToken(),cookieMaxAge);

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token",accessToken.getToken())
                .build().toUriString();

    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        authorizationRequestBasedOnCookieRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean hasAuthority(Collection<? extends GrantedAuthority> authorities,String authority){
        if(authorities==null){
            return false;
        }
        for (GrantedAuthority grantedAuthority : authorities){
            if(authority.equals(grantedAuthority.getAuthority())){
                return true;
            }
        }
        return false;
    }

    private boolean isAuthorizedRedirectUri(String uri){
        URI clientRedirectUri = URI.create(uri);

        return appProperties.getOAuth2().getAuthorizedRedirectUris()
                .stream()
                .anyMatch(authorizedRedirectUri ->{
                    URI authorizedURI = URI.create(authorizedRedirectUri);
                    if(authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())&&
                    authorizedURI.getPort()==clientRedirectUri.getPort()){
                        return true;
                    }
                    return false;
                });
    }
}
