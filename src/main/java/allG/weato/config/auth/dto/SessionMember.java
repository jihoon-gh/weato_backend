package allG.weato.config.auth.dto;

import allG.weato.domain.Member;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionMember implements Serializable {
    private String name;
    private String email;
    private String gender;
    private String birthyear;

    public SessionMember(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.gender = member.getGender();
        this.birthyear = member.getBirthyear();
    }
}
