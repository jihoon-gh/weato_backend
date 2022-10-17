package allG.weato.serviceTest;

import allG.weato.domain.comment.entities.Comment;
import allG.weato.domain.enums.Withdrawal;
import allG.weato.domain.member.MemberService;
import allG.weato.domain.member.entities.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(member1.getName()).isEqualTo("탈퇴회원");
    }

    @DisplayName("이메일을 통한 멤버 조회")
    @Transactional
    @Test
    public void findByEmailTest(){

        //given
        Member member = new Member("지훈", "abcd@naver.com", null, null, null, null, null);
        memberService.save(member);

        //when
        Member findMember = memberService.findByEmail("abcd@naver.com");

        //then
        assertThat(findMember.getName()).isEqualTo("지훈");
    }

    @DisplayName("댓글 추가 테스트")
    @Transactional
    @Test
    public void addCommentTest(){

        //given
        Member member = new Member("지훈", "abcd@naver.com", null, null, null, null, null);
        Comment comment = new Comment();
        memberService.save(member);

        //when
        memberService.addComment(member,comment);

        //then
        assertThat(member.getCommentList().size()).isEqualTo(1);
    }

    @DisplayName("닉네임 중복 체크")
    @Transactional
    @Test
    public void validateNicknameTest(){

        //given
        Member member = new Member("지훈", "abcd@naver.com", null, null, null, null, null);
        member.changeNickname("test");
        memberService.save(member);

        //when
        String nickname = "test";

        //then
        assertThat(memberService.validateNickname(nickname)).isTrue();
    }

    @DisplayName("이메일 인증번호 변경")
    @Transactional
    @Test
    public void validateEmailTest(){

        //given
        Member member = new Member("지훈", "abcd@naver.com", null, null, null, null, null);
        Integer temp = member.getAuthNum();

        //when
        memberService.validateEmail(member);

        //then
        assertThat(member.getAuthNum()).isNotEqualTo(temp);
    }


}
