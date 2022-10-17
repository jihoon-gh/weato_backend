package allG.weato.domain.anonymousMember.dtos.update;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UpdateAnonymousMemberRequest {


    private String name;
    private String newsletterEmail;


    private Boolean tagDrug;
    private Boolean tagSleep;
    private Boolean tagCleaning;
    private Boolean tagFood;
    private Boolean tagEnvironment;
    private Boolean otherwise;
}
