package allG.weato.domain;

import allG.weato.domain.comment.entities.Comment;
import allG.weato.domain.comment.entities.CommentLike;
import allG.weato.domain.enums.Withdrawal;
import allG.weato.domain.member.dtos.update.UpdateProfileRequestDto;
import allG.weato.domain.member.entities.AdditionalInfo;
import allG.weato.domain.member.entities.Member;
import allG.weato.domain.member.entities.Profile;
import allG.weato.domain.newsletter.entities.BookMark;
import allG.weato.domain.newsletter.entities.NewsletterLike;
import allG.weato.domain.post.entities.Post;
import allG.weato.domain.post.entities.PostLike;
import allG.weato.domain.post.entities.Scrap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
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
    public void addCommentLikeTest(){

        //given
        Member member = new Member();
        CommentLike commentLike = new CommentLike();

        //when
        member.addCommentLike(commentLike);

        //then
        assertThat(member.getCommentLikeList().size()).isEqualTo(1);
    }

    @DisplayName("게시글 좋아요 테스트")
    @Test
    public void addPostLikeTest(){

        //given
        Member member = new Member();
        PostLike postLike1 =new PostLike();
        PostLike postLike2 =new PostLike();

        //when
        member.addPostLike(postLike1);
        member.addPostLike(postLike2);

        //then
        assertThat(member.getPostLikeList().size()).isEqualTo(2);
    }

    @DisplayName("태그 타입 변경")
    @Test
    public void updateTagTypeTest(){

        //given
        UpdateProfileRequestDto request = new UpdateProfileRequestDto();
        request.setTagCleaning(true);
        request.setTagDrug(true);
        request.setTagEnvironment(false);
        request.setTagFood(false);
        request.setTagSleep(false);
        request.setOtherwise(false);

        //when
        Member member = new Member();
        member.changeTagTypesByUpdate(request);

        //then
        assertThat(member.getTagTypeList().size()).isEqualTo(2);
    }

    @DisplayName("북마크 추가")
    @Test
    public void addBookMarkTest(){

        //given
        BookMark bookMark1 = new BookMark();
        BookMark bookMark2 = new BookMark();
        BookMark bookMark3 = new BookMark();
        Member member = new Member();

        //when
        member.addBookMark(bookMark1);
        member.addBookMark(bookMark2);
        member.addBookMark(bookMark3);

        //then
        assertThat(member.getBookMarkList().size()).isEqualTo(3);
    }

    @DisplayName("게시글 좋아요 취소")
    @Test
    public void deletePostLike(){

        //given
        Member member = new Member();
        PostLike postLike1 = new PostLike();
        PostLike postLike2 = new PostLike();
        PostLike postLike3 = new PostLike();
        member.addPostLike(postLike1);
        member.addPostLike(postLike2);
        member.addPostLike(postLike3);

        //when
        member.deletePostLike(postLike1);
        member.deletePostLike(postLike2);

        //then
        assertThat(member.getPostLikeList().size()).isEqualTo(1);
    }

    @DisplayName("댓글 좋아요 취소")
    @Test
    public void deleteCommentLike(){

        //given
        Member member = new Member();
        CommentLike commentLike1 = new CommentLike();
        CommentLike commentLike2 = new CommentLike();
        CommentLike commentLike3 = new CommentLike();
        member.addCommentLike(commentLike1);
        member.addCommentLike(commentLike2);
        member.addCommentLike(commentLike3);
        //when
        member.deleteCommentLike(commentLike2);
        //then
        assertThat(member.getCommentLikeList().size()).isEqualTo(2);
    }

    @DisplayName("게시글 삭제 test")
    @Test
    public void deletePostTest(){

        //given
        Post post1 = new Post();
        Post post2 = new Post();
        Member member = new Member();
        member.addPost(post1);
        member.addPost(post2);

        //when
        member.deletePost(post1);

        //then
        assertThat(member.getPostList().size()).isEqualTo(1);
    }

    @DisplayName("댓글 삭제 테스트")
    @Test
    public void deleteCommentTest(){

        //given
        Member member = new Member();
        Comment comment1 = new Comment();
        Comment comment2 = new Comment();
        Comment comment3 = new Comment();
        member.addComment(comment1);
        member.addComment(comment2);
        member.addComment(comment3);

        //when
        member.deleteComment(comment1);
        member.deleteComment(comment2);
        member.deleteComment(comment3);

        //then
        assertThat(member.getCommentList().size()).isEqualTo(0);
    }

    @DisplayName("스크랩 추가 테스트")
    @Test
    public void addScrapTest(){

        //given
        Member member = new Member();
        Scrap scrap = new Scrap();
        //when
        member.addScrap(scrap);
        //then
        assertThat(member.getScrapList().size()).isEqualTo(1);
    }

    @DisplayName("스크랩 취소 테스트")
    @Test
    public void deleteScrapTest(){

        //given
        Member member = new Member();
        Scrap scrap1 = new Scrap();
        Scrap scrap2= new Scrap();
        member.addScrap(scrap1);
        member.addScrap(scrap2);

        //when
        member.deleteScrap(scrap1);
        member.deleteScrap(scrap2);

        //then
        assertThat(member.getScrapList().size()).isEqualTo(0);
    }

    @DisplayName("뉴스레터 좋아요 테스트")
    @Test
    public void addNewsletterLikeTest(){

        //given
        Member member = new Member();
        NewsletterLike newsletterLike = new NewsletterLike();

        //when
        member.addNewsletterLike(newsletterLike);

        //then
        assertThat(member.getNewsletterLikeList().size()).isEqualTo(1);
    }

    @DisplayName("뉴스레터 좋아요 취소")
    @Test
    public void deleteNewsletterLikeTest(){

        //given
        Member member = new Member();
        NewsletterLike newsletterLike1 = new NewsletterLike();
        NewsletterLike newsletterLike2 = new NewsletterLike();
        NewsletterLike newsletterLike3 = new NewsletterLike();
        member.addNewsletterLike(newsletterLike1);
        member.addNewsletterLike(newsletterLike2);
        member.addNewsletterLike(newsletterLike3);

        //when
        member.deleteNewsletterLike(newsletterLike1);

        //then
        assertThat(member.getNewsletterLikeList().size()).isEqualTo(2);
    }

    @DisplayName("회원 탈퇴 후 db삭제 전 회원정보 가져오기")
    @Test
    public void memberWithdrawTest(){

        //given
        Member member = new Member();

        //when
        member.deleteMember(Withdrawal.NUM1);

        //then
        assertThat(member.getNickname()).isEqualTo("탈퇴회원");
        assertThat(member.getName()).isEqualTo("탈퇴회원");
        assertThat(member.getNewsletterEmail()).isNull();
    }

}
