package allG.weato.domains.comment.commentDto.create;

import allG.weato.domains.comment.entities.Comment;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class CreateCommentResponse {

    private Long id;
    private Long parentId;
    private HttpStatus httpStatus;

    public CreateCommentResponse(Comment comment) {
       id=comment.getId();
       httpStatus=HttpStatus.OK;
    }

    public CreateCommentResponse(Comment comment, Comment parent){
        id= comment.getId();
        parentId=parent.getId();
        httpStatus=HttpStatus.OK;
    }
}
