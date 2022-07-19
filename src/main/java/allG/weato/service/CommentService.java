package allG.weato.service;

import allG.weato.domain.Comment;
import allG.weato.domain.CommentLike;
import allG.weato.domain.Member;
import allG.weato.domain.Post;
import allG.weato.repository.CommentRepository;
import allG.weato.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
    public void updateComment(Long id,String content){
        Comment comment= commentRepository.findCommentById(id);
        if(comment==null) throw new RuntimeException("존재하지 않는 댓글입니다.");
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
}

