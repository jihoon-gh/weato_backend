package allG.weato.dto.post.retrieve;

import allG.weato.domain.Post;
import allG.weato.dto.comment.CommentDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class PostDetail {
    String Author;
    String title;
    String content;
    LocalDateTime createdAt;
    int likeCount;
    List<CommentDto> comments;

    int views;

    public PostDetail(Post post) {
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreateAt();
        this.likeCount = post.getLikeCount();
        this.Author=post.getMember().getName();
        this.views=post.getViews();
        this.comments = post.getCommentList().stream()
                .map( c -> new CommentDto(c)).collect(Collectors.toList());
    }
}
