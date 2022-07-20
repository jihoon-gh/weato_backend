package allG.weato.config.apiConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID =  -2550185165626007488L;
    private static final long JWT_TOKEN_VALIDITY = 5*24*60*60;

    @Value("${jwt.secret}")
    private String secret;


    //토큰에서 사용자 이름 꺼내기
    public String getNameFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);
    }
    //토큰에서 유효기간 가져오기
    public Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token,Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims,T> claimsResolver){
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    //토큰에 저장된 모든 정보 가져오기
    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token){
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date()); //지금 date 값이 expiration 이전이면 true!
    }

    public String generateToken(String name){
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims,name);
    }

    //while creating the token - (토큰에 정보를 넣고, 시크릿 키를 이용해서 토큰을 compact하게 만든다)
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   compaction of the JWT to a URL-safe string
    private String doGenerateToken(Map<String, Object> claims, String subject){

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY*1000))
                .signWith(SignatureAlgorithm.HS512,secret).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        final String name = getNameFromToken(token);
        return (name.equals(userDetails.getUsername())&&!isTokenExpired(token));
    }

}
