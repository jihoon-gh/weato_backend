package allG.weato.serviceTest;

import allG.weato.domain.comment.entities.Comment;
import allG.weato.domain.comment.entities.CommentLike;
import allG.weato.domain.member.entities.Member;
import allG.weato.domain.post.entities.Post;
import allG.weato.domain.comment.CommentRepository;
import allG.weato.domain.comment.CommentService;
import allG.weato.domain.member.MemberService;
import allG.weato.domain.post.PostService;
import allG.weato.validation.CommonErrorCode;
import allG.weato.validation.RestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Rollback
public class CommentServiceTest {

    @Autowired private CommentService commentService;

    @Autowired private CommentRepository commentRepository;

    @Autowired private PostService postService;

    @Autowired private MemberService memberService;

    @Test
    @DisplayName("댓글 생성 테스트")
    public void createTest(){

        //given
        Comment comment = new Comment();
        comment.changeContent("take the world");
        commentRepository.save(comment);
        //when
        Comment findComment =commentRepository.findCommentById(comment.getId()).orElseGet(()->{
                throw new RestException(CommonErrorCode.RESOURCE_NOT_FOUND);});
        //then
        assertThat(findComment.getContent()).isEqualTo("take the world");
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
        assertThat(findPost.getCommentList().size()).isEqualTo(3);
    }

    @Test
    @DisplayName("댓글 수정 테스트")
    public void updateTest(){

        //given
        Post post = new Post();
        Comment comment = new Comment();
        comment.changeContent("this is test");
        post.addComment(comment);
        postService.save(post);
        //when
        Long id = comment.getId();
        Comment findComment = commentService.findCommentById(id);
        commentService.updateComment(findComment,"this is updated comment");
        //then
        assertThat(findComment.getContent()).isEqualTo("this is updated comment");
        assertThat(post.getCommentList().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("댓글 삭제 테스트")
    public void deleteTest(){

        //given
        Post post = new Post();
        Comment comment =  new Comment();
        Member member = new Member();
        member.addComment(comment);
        post.addComment(comment);
        memberService.save(member);
        postService.save(post);

        //when
        Long id = comment.getId();
        Comment findComment1 = commentService.findCommentById(id);
        commentService.deleteComment(findComment1);

        //then

        assertThat(post.getCommentList().size()).isEqualTo(0);
        assertThat(member.getCommentList().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("댓글 좋아요 테스트")
    public void commentLikeTest(){
        //given
        Comment comment = new Comment();
        Member member = new Member();
        memberService.save(member);

        //when
        CommentLike commentLike = new CommentLike();
        commentService.addCommentLike(member,comment,commentLike);

        //then
        assertThat(comment.getLikeCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("댓글 좋아요 삭제 테스트")
    public void deleteCommentLikeTest(){

        //given
        Comment comment = new Comment();
        Member member = new Member();
        CommentLike commentLike = new CommentLike();
        memberService.save(member);
        commentService.save(comment);
        commentService.addCommentLike(member,comment,commentLike);
        //when
        int sizeBeforeDelete = comment.getCommentLikeList().size();
        commentService.deleteCommentLike(member,comment,commentLike);
        //then
        int sizeAfterDelete = comment.getCommentLikeList().size();
        assertThat(sizeAfterDelete).isNotEqualTo(sizeBeforeDelete);
    }
}
