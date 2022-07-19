//package allG.weato.config.apiConfig;
//
//import io.jsonwebtoken.Claims;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.io.Serializable;
//
//@Component
//public class JwtTokenUtil implements Serializable {
//    private static final long serialVersionUID =  -2550185165626007488L;
//    private static final long JWT_TOKEN_VALIDITY = 5*24*60*60;
//
//    @Value("${jwt.secret}")
//    private String secret;
//
//
//    //토큰에서 사용자 이름 꺼내기
//    public String getNameFromToken(String token){
//        return getClaimFromToken(token, Claims::getSubject);
//    }
//}
