package allG.weato.dto.comment.create;

import allG.weato.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateCommentResponse {

    private String author;
    private String content;
    private LocalDateTime createdAt;

    public CreateCommentResponse(Comment comment) {
        author = comment.getMember().getName();
        content = comment.getContent();
        createdAt = comment.getCreatedAt();
    }
}
