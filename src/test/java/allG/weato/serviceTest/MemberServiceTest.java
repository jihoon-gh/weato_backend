package allG.weato.serviceTest;

import allG.weato.domains.member.MemberService;
import allG.weato.domains.member.entities.Member;
import allG.weato.validation.CommonErrorCode;
import allG.weato.validation.RestException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.*;

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
        memberService.deleteMember(member1);

        //then
//        Assertions.assertThat(memberService.findById(id)).;
    }
}
