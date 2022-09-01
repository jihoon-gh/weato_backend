package allG.weato.domains.comment;

import allG.weato.domains.comment.entities.Comment;
import allG.weato.domains.comment.entities.CommentLike;
import allG.weato.domains.member.entities.Member;
import allG.weato.domains.post.entities.Post;
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
        Comment comment = commentRepository.findCommentById(id);
        if(comment==null) throw new RuntimeException("존재하지 않는 댓글입니다.");
        return comment;
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

