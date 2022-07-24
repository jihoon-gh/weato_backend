package allG.weato.domains.post.postDto.retrieve;

import allG.weato.domains.post.entities.Post;
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

    private int views;

    private int likecounter;

    public PostDto(Post post) {
        id = post.getId();
        title = post.getTitle();
        createdAt = post.getCreateAt();
        author = post.getMember().getName();
        commentsCounter = post.getCommentList().size();
        views= post.getViews();
        likecounter=post.getLikeCount();
    }
}
