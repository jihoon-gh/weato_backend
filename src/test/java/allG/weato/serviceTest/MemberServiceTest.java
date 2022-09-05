package allG.weato.serviceTest;

import allG.weato.domains.member.MemberService;
import allG.weato.domains.member.entities.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
@SpringBootTest
public class MemberServiceTest {

    @Autowired private MemberService memberService;

//    @Test
//    @DisplayName("회원 탈퇴(삭제) 테스트")
//    public void deleteMemberTest(){
//        //given
//        Member member = new Member(null,"abcd",null,null,null,null,null);
//        memberService.save(member);
//
//        //when
//        Member member1 =memberService.getMember("abcd");
//        System.out.println("member1 = " + member1);
//        memberService.deleteMember(member1);
//
//        //then
//        Member member2=memberService.getMember("abcd");
//        Assertions.assertThat(member2).isNull();
//    }


}
