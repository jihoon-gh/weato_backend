package allG.weato.domainTest.comment.commentDto.update;

import allG.weato.domainTest.comment.entities.Comment;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class UpdatedCommentDto {

    private Long id;
    private HttpStatus httpStatus;

    public UpdatedCommentDto(Comment comment){
        id = comment.getId();
        httpStatus=HttpStatus.OK;
    }
}



