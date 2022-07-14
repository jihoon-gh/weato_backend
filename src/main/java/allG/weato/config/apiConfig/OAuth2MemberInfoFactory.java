package allG.weato.config.apiConfig;

import javax.print.DocFlavor;
import java.util.Map;

public class OAuth2MemberInfoFactory {
    public static OAuth2MemberInfo getOAuth2MemberInfo(ProviderType providerType, Map<String, Object> attributes){
        switch (providerType){
            case NAVER : return new NaverOAuth2MemberInfo(attributes);
            default: throw new IllegalArgumentException("Invalid ProviderType");
        }
    }
}
