package allG.weato.domains.post.postDto.retrieve;

import allG.weato.domains.member.entities.Level;
import allG.weato.domains.post.entities.Post;
import allG.weato.domains.comment.commentDto.retrieve.CommentRetrieveDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class PostDetailRetrieveDto {
    private String author;

    private int authorLevel;
    private String title;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    private int likeCount;
    private List<CommentRetrieveDto> comments;

    private int views;

    private int scrapCount;

    public PostDetailRetrieveDto(Post post) {
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.likeCount = post.getLikeCount();
        this.author=post.getMember().getName();
        this.authorLevel=post.getMember().getLevel().getLevel();
        this.views=post.getViews();
        this.comments = post.getCommentList().stream()
                .map( c -> new CommentRetrieveDto(c)).collect(Collectors.toList());
        this.scrapCount=post.getScrapCount();
    }
}