package allG.weato.serviceTest;

import allG.weato.domain.Comment;
import allG.weato.domain.Post;
import allG.weato.repository.CommentRepository;
import allG.weato.service.CommentService;
import allG.weato.service.PostService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CommentServiceTest {

    @Autowired private CommentService commentService;

    @Autowired private CommentRepository commentRepository;

    @Autowired private PostService postService;

    @Test
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
    public void updateTest(){

        //given

        //when

        //then
    }

    @Test
    public void deleteTest(){

        //given

        //when

        //then
    }
}
