package allG.weato.service;

import allG.weato.domain.Comment;
import allG.weato.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment findCommentById(Long id){
        Comment comment = commentRepository.findCommentById(id);
        if(comment==null) throw new RuntimeException("존재하지 않는 댓글입니다.");
        return comment;
    }

    @Transactional
    @Modifying
    public void updateComment(Long id,String content){
        Comment comment= commentRepository.findCommentById(id);
        if(comment==null) throw new RuntimeException("존재하지 않는 댓글입니다.");
        comment.changeContent(content);
    }

    @Transactional
    @Modifying
    public void deleteComment(Long id){
        commentRepository.deleteCommentById(id);
    }

    @Transactional
    @Modifying
    public void delete(Comment comment){
        commentRepository.delete(comment);
    }
}

