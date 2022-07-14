package allG.weato.config.apiConfig;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

//@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private final Auth auth = new Auth();

    private final OAuth2 oAuth2 = new OAuth2();

    public static class Auth{
        private String tokenSecret;

        private Long tokenExpirationSec;

        public String getTokenSecret() {
            return tokenSecret;
        }

        public Long getTokenExpirationSec() {
            return tokenExpirationSec;
        }

        public void setTokenSecret(String tokenSecret) {
            this.tokenSecret = tokenSecret;
        }

        public void setTokenExpirationSec(Long tokenExpirationSec) {
            this.tokenExpirationSec = tokenExpirationSec;
        }
    }

    public static class OAuth2{
        private List<String> authorizedRedirectUris = new ArrayList<>();

        public List<String> getAuthorizedRedirectUris() {
            return authorizedRedirectUris;
        }
        public OAuth2 authorizedRedirectUris(List<String> authorizedRedirectUris) {
            this.authorizedRedirectUris = authorizedRedirectUris;
            return this;
        }
    }

    public Auth getAuth() {
        return auth;
    }

    public OAuth2 getOAuth2() {
        return oAuth2;
    }
}
