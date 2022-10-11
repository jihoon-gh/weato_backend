package allG.weato.domain.comment;

import allG.weato.domain.comment.entities.Comment;
import allG.weato.domain.comment.entities.CommentLike;
import allG.weato.domain.member.entities.Member;
import allG.weato.domain.post.entities.Post;
import allG.weato.validation.CommonErrorCode;
import allG.weato.validation.RestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;

    @PersistenceContext
    private final EntityManager em;


    @Transactional
    public void save(Comment comment){
        commentRepository.save(comment);
    }
    public Comment findCommentById(Long id){
        return commentRepository.findCommentById(id).orElseThrow(()->
            new RestException(CommonErrorCode.RESOURCE_NOT_FOUND)
        );
    }

    @Transactional
    public void updateComment(Comment comment,String content){

        comment.changeContent(content);
    }

    @Transactional
    public void deleteComment(Comment comment){
        Post post = comment.getPost();
        Member member = comment.getMember();
        member.deleteComment(comment);
        post.deleteComment(comment);
        commentRepository.deleteCommentById(comment.getId());
    }

    @Transactional
    public void deleteCommentLike(Member member, Comment comment, CommentLike commentLike){
        member.deleteCommentLike(commentLike);
        member.deleteCommentLikeChecker(comment.getId());
        comment.deleteLike(commentLike);
        commentRepository.deleteCommentLikeById(commentLike.getId());
    }

    @Transactional
    public void addCommentLike(Member member, Comment comment, CommentLike commentLike){
        member.addCommentLike(commentLike);
        member.addCommentLikeChecker(comment.getId());
        comment.addLike(commentLike);
        em.persist(commentLike);
    }

    @Transactional
    public void addReComment(Comment parent, Comment comment){
        comment.changeParent(parent);
        commentRepository.save(comment);
    }
}

