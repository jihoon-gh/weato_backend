package allG.weato.config.auth.dto;

import allG.weato.domain.member.entities.Member;
import allG.weato.domain.enums.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String gender;
    private String birthyear;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String gender, String birthyear) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.birthyear = birthyear;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
        return ofNaver("id",attributes);
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes){
        Map<String,Object> response = (Map<String, Object>)attributes.get("response");

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .gender((String) response.get("gender"))
                .birthyear((String) response.get("birthyear"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public Member toEntity(){
        return Member.builder()
                .name(name)
                .email(email)
                .gender(gender)
                .birthyear(birthyear)
                .role(Role.GUEST)
                .build();
    }



}
