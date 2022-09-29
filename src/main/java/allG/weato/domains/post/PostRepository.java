package allG.weato.domains.post;

import allG.weato.domains.enums.TagType;
import allG.weato.domains.post.entities.Post;
import allG.weato.domains.enums.BoardType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

      Optional<Post> findPostById(Long id);

      @Query("select p from Post p where p.member.id = :id")
      Page<Post> findPostsByMemberId(@Param("id") Long id,Pageable pageable);
      @Query("select p from Post p left join fetch p.commentList left join fetch p.member where p.id = :id")
      Optional<Post> findPostFecthJoin(@Param("id") Long id);

      @Query("select p from Post p left join fetch p.postLikeList where p.id = :id")
      Post findPostByIdWithLikes(@Param("id")Long id);

       @Query("select p from Post p left join fetch p.scrapList where p.id = :id")
      Post findPostByIdWithScrap(@Param("id")Long id);

      Post findPostByTitle(String title);

      @EntityGraph(attributePaths = {"commentList"})
      Page<Post> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title,String content, Pageable pageable);

      @Override
      @EntityGraph(attributePaths = {"commentList","member"})
      List<Post> findAll();

      @EntityGraph(attributePaths = {"commentList","member"})
      Page<Post> findAll(Pageable pageable);

      @EntityGraph(attributePaths = {"commentList","member"})
      Page<Post> findPostsByTagType(Pageable pageable,TagType tagType);

      @EntityGraph(attributePaths = {"commentList","member"})
      Page<Post> findPostsByBoardType(Pageable pageable, BoardType boardType);

      @EntityGraph(attributePaths = {"commentList","member"})
      Page<Post> findPostsByBoardTypeAndTagType(Pageable pageable, BoardType boardType,TagType tagType);

      @Modifying
      @Transactional
      @Query("delete from PostLike pl where pl.id = :postLikeId")
      void deletePostLikeById(@Param("postLikeId") Long postLikeId);

      @Modifying
      @Transactional
      @Query("delete from Scrap s where s.id = :scrapId")
      void deleteScrapById(@Param("scrapId")Long scrapId);

      @Query("select p from Post p where p.createdAt >= :now order by p.likeCount desc")
      Page<Post> sortPostsByLikeCount(@Param("now")LocalDateTime now,Pageable pageable);

      @Query("select p from Post p where p.createdAt >= :now order by p.scrapCount desc")
      Page<Post> sortPostByScrapCount(@Param("now") LocalDateTime now, Pageable pageable);

      @Query("select p from Post p where p.likeCount >=  :std")
      List<Post> findRecommendPosts(@Param("std")Integer std);

//    @Query("delete from Comment c where c.id = :commentId")
//    void deleteCommentById(@Param("commentId") Long commentId);



}
