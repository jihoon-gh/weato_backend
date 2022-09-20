package allG.weato.domains.comment.commentDto.update;

import allG.weato.domains.comment.entities.Comment;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.ZoneId;

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



