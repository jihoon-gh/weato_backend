package allG.weato.serviceTest;

import allG.weato.domain.comment.entities.Comment;
import allG.weato.domain.member.entities.Member;
import allG.weato.domain.post.entities.Post;
import allG.weato.domain.post.entities.PostLike;
import allG.weato.domain.member.MemberService;
import allG.weato.domain.post.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PostServiceTest {
    @Autowired private PostService postService;
    @Autowired private MemberService memberService;

    @Test
    @DisplayName("게시글 생성")
    public void createPost(){
        //given
        Post post = new Post();
        postService.save(post);

        //when
        Long findId = post.getId();
        //then
        Post findPost= postService.findPostById(findId);
        assertThat(post).isSameAs(findPost);
    }


    @Test
    @DisplayName("게시글 수정")
    public void postUpdateTest(){
        //given
        Post post = new Post();
        postService.save(post);
        //when
        Post findPost = postService.findPostById(post.getId());
//        postService.updatePost(findPost, "hello","this is test");
        //then
        assertThat(findPost.getContent()).isEqualTo("this is test");
        assertThat(findPost.getTitle()).isEqualTo("hello");
    }

    @Test
    @DisplayName("게시글 조회")
    public void postFindSaveTest(){
        //given
        Post post = new Post();
        post.changeContent("this is america");
        post.changeTitle("gambino");
        postService.save(post);
        //when

        Post findPost2 = postService.findPostById(post.getId());
        //then
        assertThat(findPost2).isSameAs(post);
    }
    
    @Test
    @DisplayName("게시글 삭제")
    public void deleteTest(){
        //given
        Member member = new Member();
        Post post = new Post();
        post.setOwner(member);
        post.changeContent("for test");
        memberService.save(member);
        postService.save(post);
        Long testId = post.getId();
        //when
        postService.deletePost(post);
        //then
        assertThat(postService.findAll()).isEmpty();
        assertThat(postService.findPostById(testId)).isNull();
        assertThat(member.getPostList().size()).isEqualTo(0);
    }
    @Test
    @DisplayName("댓글 생성")
    public void createCommentTest(){
        //given
          Post post = new Post();
          Comment comment = new Comment();
          post.addComment(comment);
          postService.save(post);
          //when
          Post findPost = postService.findPostById(post.getId());
          //then
          assertThat(findPost.getCommentList().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("좋아요 확인")
    public void likeTest(){

        //given
        Post post = new Post();
        post.changeTitle("test1");
        PostLike postLike = new PostLike();
        Member member = new Member();
        member.addPostLike(postLike);
        post.addPostLike(postLike);
        postService.save(post);
        memberService.save(member);
        //when

        //then

    }

    @Test
    @DisplayName("좋아요 삭제 구현")
    public void deleteLikeTest(){
        //given
        Post post = new Post();
        PostLike postLike = new PostLike();
        Member member = new Member();
        post.addPostLike(postLike);
        member.addPostLike(postLike);
        System.out.println("post.getLikeCount() = " + post.getLikeCount());
        System.out.println("member.getCommentLikeList().size() = " + member.getCommentLikeList().size());
        postService.save(post);
        memberService.save(member);
        Long postId = post.getId();
        Long memberId = member.getId();

        //when
        Post findPost = postService.findPostById(postId);
        Member findMember = memberService.findById(memberId);
        List<PostLike> likes = findPost.getPostLikeList();
        Long id=0L;
        for (PostLike like : likes) {
            if(like.getMember()==findMember){
                post.deletePostLike(like);
                id=like.getId();
                break;
            }
        }
        if(id!=0L) postService.deleteLike(findMember,findPost,postLike);
        //then
        System.out.println("member.getCommentLikeList().size() = " + member.getCommentLikeList().size());
        assertThat(post.getLikeCount()).isEqualTo(post.getPostLikeList().size());
        assertThat(findMember.getPostLikeList().size()).isEqualTo(0);
        //controller 단에서 이미 존재하는 postLike.member.id with postLike.post.id인지 검사하자
        //검사해서 없으면 좋아요를 하는 걸루다가 하자
    }

    @DisplayName("추천글 테스트 - 좋아요 n개 이상 글에서")
    @Test
    public void postsRecommendTest(){
        //given
        List<Post> candidatePosts = postService.findCandidatesOfRecommendPost(1);

        //when
        Collections.shuffle(candidatePosts);
        List<Post> recommendPosts = candidatePosts.subList(0,2);

        //then
        assertThat(recommendPosts.size()).isEqualTo(2);
    }

  

}
