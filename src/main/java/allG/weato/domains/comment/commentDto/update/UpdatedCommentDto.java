package allG.weato.domains.comment.commentDto.update;

import allG.weato.domains.comment.entities.Comment;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@NoArgsConstructor
public class UpdatedCommentDto {

    String content;
    LocalDateTime updatedAt;
    public UpdatedCommentDto(Comment comment){
        content=comment.getContent();
        updatedAt=LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }
}



