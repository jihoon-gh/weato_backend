package allG.weato.domain.member.dto.create;

import lombok.Data;

import javax.validation.Valid;

@Data
public class CreateMemberRequest {

    @Valid
    private String nickname;

    @Valid
    private String newsletterEmail;

    private Boolean drug;
    private Boolean sleep;
    private Boolean cleaning;
    private Boolean food;
    private Boolean environment;
    private Boolean etc;

}
