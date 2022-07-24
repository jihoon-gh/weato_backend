package allG.weato.domains.comment.commentDto.create;

import allG.weato.domains.comment.entities.Comment;
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
