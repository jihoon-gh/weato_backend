package allG.weato.domains.comment;

import allG.weato.domains.comment.entities.Comment;
import allG.weato.domains.comment.entities.CommentLike;
import allG.weato.domains.member.entities.Member;
import allG.weato.domains.post.entities.Post;
import allG.weato.validation.CommonErrorCode;
import allG.weato.validation.RestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final EntityManager em;


    @Transactional
    public void save(Comment comment){
        commentRepository.save(comment);
    }
    public Comment findCommentById(Long id){
        return commentRepository.findCommentById(id).orElseGet(()->{
            throw new RestException(CommonErrorCode.RESOURCE_NOT_FOUND);
        });
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
        comment.deleteLike(commentLike);
        commentRepository.deleteCommentLikeById(commentLike.getId());
    }

    @Transactional
    public void addCommentLike(Member member, Comment comment, CommentLike commentLike){
        member.addCommentLike(commentLike);
        comment.addLike(commentLike);
        em.persist(commentLike);
    }

    @Transactional
    public void addReComment(Comment parent, Comment comment){
        comment.changeParent(parent);
        commentRepository.save(comment);
    }
}

