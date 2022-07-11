package allG.weato.dto;

import allG.weato.domain.Post;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PostDto {
    private Long id;
    private String title;
    private LocalDateTime createdAt;
    private String author;

    private int commentsCounter;

    public PostDto(Post post) {
        id = post.getId();
        title = post.getTitle();
        createdAt = post.getCreateAt();
        author = post.getMember().getName();
        commentsCounter = post.getCommentList().size();
    }
}
