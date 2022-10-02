package allG.weato.domain.member.dto.delete;

import allG.weato.domain.enums.Withdrawal;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
@Getter
@AllArgsConstructor
public class WithdrawlRequest {

    @NotNull
    private Withdrawal withdrawal;
}
