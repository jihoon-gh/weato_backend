package allG.weato.repository;

import allG.weato.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment findCommentById(Long id);


    @Modifying
    void deleteCommentById(Long id);


    @Transactional
    @Modifying
    @Query("delete from CommentLike cl where cl.id = :commentLikeId")
    void deleteCommentLikeById(@Param("commentLikeId") Long commentLikeId);

}
