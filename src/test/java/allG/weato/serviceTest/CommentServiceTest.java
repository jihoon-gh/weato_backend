package allG.weato.serviceTest;

import allG.weato.domain.Comment;
import allG.weato.domain.CommentLike;
import allG.weato.domain.Member;
import allG.weato.domain.Post;
import allG.weato.repository.CommentRepository;
import allG.weato.service.CommentService;
import allG.weato.service.PostService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@SpringBootTest
@Transactional
public class CommentServiceTest {

    @Autowired private CommentService commentService;

    @Autowired private CommentRepository commentRepository;

    @Autowired private PostService postService;

    @Test
    @DisplayName("댓글 생성 테스트")
    public void createTest(){

        //given
        Comment comment = new Comment();
        comment.changeContent("take the world");
        commentRepository.save(comment);
        //when
        Comment findComment =commentRepository.findCommentById(comment.getId());
        //then
        Assertions.assertThat(findComment.getContent()).isEqualTo("take the world");
    }

    @Test
    @DisplayName("댓글 조회 테스트")
    public void retrieveTest(){

        //given
        Post post = new Post();
        Comment comment1 = new Comment();
        Comment comment2 = new Comment();
        Comment comment3 = new Comment();
        post.addComment(comment1);
        post.addComment(comment2);
        post.addComment(comment3);
        postService.save(post);
        //when
        Long id = post.getId();
        Post findPost = postService.findPostById(id);
        List<Comment> comments = commentRepository.findAll();
        System.out.println("comments.size() = " + comments.size());
        //then
        Assertions.assertThat(findPost.getCommentList().size()).isEqualTo(3);
    }

    @Test
    @DisplayName("댓글 수정 테스트")
    public void updateTest(){

        //given

        //when

        //then
    }

    @Test
    @DisplayName("댓글 삭제 테스트")
    public void deleteTest(){

        //given

        //when

        //then
    }

    @Test
    @DisplayName("댓글 좋아요 테스트")
    public void commentLikeTest(){

    }

    @Test
    @DisplayName("댓글 좋아요 삭제 테스트")
    public void deleteCommentLikeTest(){

        //given
        Post post = new Post();
        Comment comment = new Comment();
        Member member = new Member();
        CommentLike commentLike = new CommentLike();
        post.addComment(comment);
        post.setOwner(member);
        commentService.addCommentLike(member,comment,commentLike);

        //when

        //then
    }
}
