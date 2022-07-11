package allG.weato.dto;

import allG.weato.domain.Comment;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CommentDto {
    private String author;
    private String content;
    private LocalDateTime createdAt;

    public CommentDto(Comment comment){
        author=comment.getMember().getName();
        content=comment.getContent();
        createdAt=comment.getCreatedAt();
    }
}
