package allG.weato.domain;

import allG.weato.domain.comment.entities.Comment;
import allG.weato.domain.member.entities.Member;
import allG.weato.domain.post.entities.Post;
import allG.weato.domain.post.entities.PostLike;
import allG.weato.domain.post.entities.Scrap;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PostTest {

    @DisplayName("게시글 내용 변경 테스트")
    @Test
    public void changeContentTest(){

        //given
        Post post = new Post();

        //when
        post.changeContent("test");

        //then
        assertThat(post.getContent()).isEqualTo("test");

    }

    @DisplayName("제목 변경 테스트")
    @Test
    public void changeTitleTest(){

        //given
        Post post = new Post();
        String title = "test";

        //when
        post.changeTitle(title);

        //then
        assertThat(post.getTitle()).isEqualTo("test");
    }

    @DisplayName("게시글 작성자 확인 테스트")
    @Test
    public void setOwnerTest(){

        //given
        Post post = new Post();
        Member member = post.getMember();

        //when
        Member member1 = new Member();
        post.setOwner(member1);

        //then
        assertThat(member1).isNotEqualTo(member);
    }

    @DisplayName("게시글 댓글 추가 테스트")
    @Test
    public void addCommentTest(){

        //given
        Post post = new Post();
        Comment comment = new Comment();

        //when
        post.addComment(comment);

        //then
        assertThat(post.getCommentList().size()).isEqualTo(1);
    }

    @DisplayName("댓글 삭제 테스트")
    @Test
    public void deleteCommentTest(){

        //given
        Post post = new Post();
        Comment comment = new Comment();
        post.addComment(comment);
        //when

        //then
    }

    @DisplayName("게시글 좋아요 테스트")
    @Test
    public void addPostLikeTest(){

        //given
        Post post = new Post();
        PostLike postLike = new PostLike();

        //when
        post.addPostLike(postLike);

        //then
        assertThat(post.getLikeCount()).isEqualTo(1);
        assertThat(post.getPostLikeList().size()).isEqualTo(1);
    }

    @DisplayName("좋아요 취소 테스트")
    @Test
    public void deletePostLike(){

        //given
        Post post = new Post();
        PostLike postLike = new PostLike();
        post.addPostLike(postLike);

        //when
        post.deletePostLike(postLike);

        //then
        assertThat(post.getPostLikeList().size()).isEqualTo(0);
    }

    @DisplayName("조회수 증가 테스트")
    @Test
    public void addViewsTest(){

        //given
        Post post = new Post();
        int views1 = post.getViews();

        //when
        post.addViews();

        //then
        assertThat(views1).isEqualTo(0);
        assertThat(post.getViews()).isEqualTo(1);
    }

    @DisplayName("스크랩 더하기 테스트")
    @Test
    public void addScrapTest(){

        //given
        Post post = new Post();
        Scrap scrap = new Scrap();

        //when
        post.addScrap(scrap);

        //then
        assertThat(post.getScrapCount()).isEqualTo(1);
    }

    @DisplayName("스크랩 취소 테스트")
    @Test
    public void deleteScrapTest(){

        //given
        Post post = new Post();
        Scrap scrap = new Scrap();
        post.addScrap(scrap);
        int scrapCount = post.getScrapCount();

        //when
        post.deleteScrap(scrap);

        //then
        assertThat(scrapCount).isEqualTo(1);
        assertThat(post.getScrapCount()).isEqualTo(0);

    }

}
