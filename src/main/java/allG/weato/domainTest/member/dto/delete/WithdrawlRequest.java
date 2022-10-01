package allG.weato.domainTest.member.dto.delete;

import allG.weato.domainTest.enums.Withdrawal;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
@Getter
@AllArgsConstructor
public class WithdrawlRequest {

    @NotNull
    private Withdrawal withdrawal;
}
