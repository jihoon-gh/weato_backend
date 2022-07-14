package allG.weato.config.apiConfig;

import allG.weato.validation.RestException;

import java.util.Map;

public class NaverOAuth2MemberInfo extends OAuth2MemberInfo{

    public NaverOAuth2MemberInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return super.getAttributes();
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getEmail() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        if(response==null) return null;
        return (String) response.get("email");
    }

    @Override
    public String getImageUrl() {
        return null;
    }

    @Override
    public String getFirstName() {
        return null;
    }

    @Override
    public String getLastName() {
        return null;
    }

    @Override
    public String getGender() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        if(response==null) return null;
        return (String) response.get("gender");
    }

    @Override
    public String getBirthyear() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        if(response==null) return null;
        return (String) response.get("birthyear");
    }
}
