package allG.weato.repository;

import allG.weato.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findPostById(Long id);
    Post findPostByTitle(String title);
}
