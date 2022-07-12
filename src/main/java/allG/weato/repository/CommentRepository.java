package allG.weato.repository;

import allG.weato.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment findCommentById(Long id);

    void deleteCommentById(Long id);


}
