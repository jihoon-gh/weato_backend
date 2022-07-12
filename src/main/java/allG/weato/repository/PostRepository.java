package allG.weato.repository;

import allG.weato.domain.Comment;
import allG.weato.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
      Post findPostById(Long id);
     Post findPostByTitle(String title);


//    @Query("delete from Comment c where c.id = :commentId")
//    void deleteCommentById(@Param("commentId") Long commentId);


}
