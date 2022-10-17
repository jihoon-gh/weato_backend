package allG.weato.domain.anonymousMember.dtos.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateAnonymousMemberRequest {

    @NotEmpty
    private String name;
    @NotEmpty
    private String newsletterEmail;

    @NotNull private Boolean tagDrug;
    @NotNull private Boolean tagSleep;
    @NotNull private Boolean tagCleaning;
    @NotNull private Boolean tagFood;

    @NotNull private Boolean tagEnvironment;
    @NotNull private Boolean otherwise;
}
