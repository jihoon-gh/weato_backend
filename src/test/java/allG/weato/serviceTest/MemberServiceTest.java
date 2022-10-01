package allG.weato.serviceTest;

import allG.weato.domainTest.enums.Withdrawal;
import allG.weato.domainTest.member.MemberService;
import allG.weato.domainTest.member.entities.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberServiceTest {

    @Autowired private MemberService memberService;
    @Test
    @DisplayName("회원 탈퇴(삭제) 테스트")
    public void deleteMemberTest() {
        //given
        Member member = new Member("지훈", "abcd@naver.com", null, null, null, null, null);
        memberService.save(member);
        Long id = member.getId();

        //when
        Member member1 = memberService.findById(id);
        System.out.println("member1 = " + member1);
        Withdrawal withdrawal=Withdrawal.NUM2;
        memberService.deleteMember(member1,withdrawal);

        //then
        Assertions.assertThat(member1.getName()).isEqualTo("탈퇴회원");
    }
}
