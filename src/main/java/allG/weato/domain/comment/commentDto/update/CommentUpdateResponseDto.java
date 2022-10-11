package allG.weato.domain.comment.commentDto.update;

import allG.weato.domain.comment.entities.Comment;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class CommentUpdateResponseDto {

    private Long id;
    private HttpStatus httpStatus;

    public CommentUpdateResponseDto(Comment comment){
        id = comment.getId();
        httpStatus=HttpStatus.OK;
    }
}



