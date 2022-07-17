package allG.weato.serviceTest;

import allG.weato.domain.Comment;
import allG.weato.domain.Post;
import allG.weato.repository.PostRepository;
import allG.weato.service.PostService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class PostServiceTest {
    @Autowired private PostService postService;
    @PersistenceContext
    EntityManager em;

    @Test
    public void createPost(){
        //given
        Post post = new Post();
        postService.save(post);
        em.flush();
        //when
        Long findId = post.getId();
        //then
        Post findPost= postService.findPostById(findId);
        assertThat(post).isSameAs(findPost);
    }


    @Test
    public void PostUpdateTest(){
        //given
        Post post = new Post();
        postService.save(post);
        em.flush();
        //when
        Post findPost = postService.findPostById(post.getId());
        postService.updatePost(findPost, "hello","this is test");
        //then
        assertThat(findPost.getContent()).isEqualTo("this is test");
        assertThat(findPost.getTitle()).isEqualTo("hello");
    }

    @Test
    public void postFindSaveTest(){
        //given
        Post post = new Post();
        post.changeContent("this is america");
        post.changeTitle("gambino");
        postService.save(post);
        //when
        Post findPost1 = postService.findPostByTitle(post.getTitle());
        Post findPost2 = postService.findPostById(post.getId());
        //then
        assertThat(findPost1).isSameAs(post);
        assertThat(findPost2).isSameAs(post);
    }
    
    @Test
    public void deleteTest(){
        //given
        Post post = new Post();
        postService.save(post);
        Long testId =post.getId();
        System.out.println("testId = " + testId);
        em.flush();
        //when
        postService.DeletePost(post);
        //then
        assertThat(postService.findPostById(testId)).isNull();
    }
    @Test
    public void createCommentTest(){
        //given
          Post post = new Post();
          Comment comment = new Comment();
          post.addComment(comment);
          postService.save(post);
          em.flush();
          //when
          Post findPost = postService.findPostById(post.getId());
          //then
          assertThat(findPost.getCommentList().size()).isEqualTo(1);
  }
  

}
