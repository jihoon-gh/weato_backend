package allG.weato.domain.comment.commentDto.create;

import allG.weato.domain.comment.entities.Comment;
import lombok.Data;
import org.springframework.http.HttpStatus;

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
