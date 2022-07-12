package allG.weato.dto.comment.update;

import allG.weato.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.asm.Advice;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UpdatedCommentDto {

    String content;
    LocalDateTime updatedAt;
    public UpdatedCommentDto(Comment comment){
        content=comment.getContent();
    }
}



