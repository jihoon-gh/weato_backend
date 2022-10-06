package allG.weato.domain;

import allG.weato.domain.comment.entities.Comment;
import allG.weato.domain.comment.entities.CommentLike;
import allG.weato.domain.member.entities.AdditionalInfo;
import allG.weato.domain.member.entities.Level;
import allG.weato.domain.member.entities.Member;
import allG.weato.domain.member.entities.Profile;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MemberTest {

    @DisplayName("닉네임 변경 테스트")
    @Test
    public void changeNickNameTest(){

        //given
        Member member = new Member();
        //when
        member.changeNickname("changedNickName");
        //then
        assertThat(member.getNickname()).isEqualTo("changedNickName");
    }

    @DisplayName("뉴스레터 수신 이메일 변경 테스트")
    @Test
    public void changeNewsletterEmail(){
        //given
        String email = "new email";
        Member member = new Member();

        //when
        member.changeNewsletterEmail(email);

        //then
        assertThat(member.getNewsletterEmail()).isEqualTo("new email");
    }

    @DisplayName("추가정보 테스트")
    @Test
    public void setAdditionalInfoTest(){
        //given
        AdditionalInfo additionalInfo = new AdditionalInfo();
        Member member = new Member();
        //when
        member.setAdditional_info(additionalInfo);
        //then
        assertThat(member.getAdditionalInfo()).isNotNull();
    }

    @DisplayName("profile 테스트")
    @Test
    public void addProfileTest(){

        //given
        Profile profile = new Profile();
        Member member = new Member();
        //when
        member.addProfile(profile);
        //then
        assertThat(member.getProfile()).isNotNull();
    }

    @DisplayName("댓글 추가 테스트")
    @Test
    public void addCommentTest(){

        //given
        Comment comment = new Comment();
        Member member = new Member();

        //when
        member.initMember();
        member.addComment(comment);

        //then
        assertThat(member.getCommentList().size()).isEqualTo(1);
    }

    @DisplayName("댓글 좋아요 테스트")
    @Test
    public void commentLikeTest(){

        //given
        Member member = new Member();
        CommentLike commentLike = new CommentLike();
        //when
        member.addCommentLike(commentLike);
        //then
        assertThat(member.getCommentLikeList().size()).isEqualTo(1);
    }
}
