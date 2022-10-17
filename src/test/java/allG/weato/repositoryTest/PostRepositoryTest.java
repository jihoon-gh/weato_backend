package allG.weato.repositoryTest;

import allG.weato.domain.post.PostRepository;
import allG.weato.domain.post.entities.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @DisplayName("id로 게시글 조회 테스트")
    @Test
    public void findPostByIdTest(){

        //given
        Post post = new Post();
        post.changeTitle("test");
        postRepository.save(post);
        Long id = post.getId();

        //when
        Post findPost = postRepository.findPostById(id).get();

        //then
        assertThat(findPost).isNotNull();
        assertThat(findPost.getTitle()).isEqualTo("test");
    }


}
