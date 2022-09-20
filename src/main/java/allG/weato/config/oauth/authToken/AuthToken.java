package allG.weato.config.oauth.authToken;

import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestHeader;

import java.security.Key;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class AuthToken {

    @Getter
    private final String token;
    private final Key key;

    private static final String AUTHORITIES_KEY = "role";

    AuthToken(String id, Date expiry, Key key){
        this.key=key;
        this.token=createAuthToken(id,expiry);
        System.out.println("token = " + token);
    }

    AuthToken(String id, String role, Date expiry, Key key){
        this.key=key;
        this.token=createAuthToken(id,role,expiry);
        System.out.println("token = " + token);
    }

    private String createAuthToken(String id,Date expiry){
        return Jwts.builder()
                .setSubject(id)
                .signWith(key,SignatureAlgorithm.HS256)
                .setExpiration(expiry)
                .compact();
    }

    private String createAuthToken(String id, String role, Date expiry ){
        return Jwts.builder()
                .setSubject(id)
                .claim(AUTHORITIES_KEY,role)
                .signWith(key,SignatureAlgorithm.HS256)
                .setExpiration(expiry)
                .compact();
    }

    public boolean validate(){
        return this.getTokenClaims()!=null;
    }

    public Claims getTokenClaims(){
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }catch (SecurityException e){
            log.info("Invalid JWT Signature");
        }catch (MalformedJwtException e){
            log.info("INVALID JWT TOKEN");
        }catch (UnsupportedJwtException e){
            log.info("Unsupported JWT Token");
        }catch(IllegalArgumentException e){
            log.info("JWT token compact of handler are invalid");
        }
        return null;
    }

    public Claims getExpiredTokenClaims(){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }catch(ExpiredJwtException e){
            log.info("Expired JWT Token");
            return e.getClaims();
        }
        return null;
    }

}
