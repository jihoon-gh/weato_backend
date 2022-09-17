package allG.weato.domains.mail.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class ReceiveAuthNumDto {
    @NotEmpty
    Integer num;
}
