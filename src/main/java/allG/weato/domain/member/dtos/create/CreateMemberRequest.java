package allG.weato.domain.member.dtos.create;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Data
public class CreateMemberRequest {

    @NotEmpty
    private String nickname;

    @NotEmpty
    private String newsletterEmail;

    private Boolean drug;
    private Boolean sleep;
    private Boolean cleaning;
    private Boolean food;
    private Boolean environment;
    private Boolean etc;

}
