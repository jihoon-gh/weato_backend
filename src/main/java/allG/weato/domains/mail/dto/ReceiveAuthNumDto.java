package allG.weato.domains.mail.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class ReceiveAuthNumDto {
    @NotNull
    Integer num;
}
