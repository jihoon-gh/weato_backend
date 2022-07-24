package allG.weato.domains.comment.commentDto.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentRequest {
    private String content;
    private LocalDateTime createdAt;
}
