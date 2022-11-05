package allG.weato.domain;

import allG.weato.domain.comment.entities.Comment;
import allG.weato.domain.comment.entities.CommentLike;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CommentTest {

    @Test
    @DisplayName("댓글 내용 변경")
    public void changeCommentContent(){

        //given
        Comment comment = new Comment();

        //when
        String str = "test";
        comment.changeContent(str);

        //then
        assertThat(comment.getContent()).isEqualTo("test");
    }

    @Test
    @DisplayName("댓글 좋아요 테스트")
    public void commentLikeTest(){

        //given
        Comment comment = new Comment();
        CommentLike cl = new CommentLike();

        //when
        comment.addLike(cl);

        //then
        assertThat(comment.getLikeCount()).isEqualTo(1);
    }

}
