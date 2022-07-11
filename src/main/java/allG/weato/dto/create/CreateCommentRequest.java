package allG.weato.dto.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentRequest {
    private String author;
    private String content;
    private LocalDateTime createdAt;
}
