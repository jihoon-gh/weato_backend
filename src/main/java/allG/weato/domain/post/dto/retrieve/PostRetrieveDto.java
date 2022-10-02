package allG.weato.domain.post.dto.retrieve;

import allG.weato.domain.enums.BoardType;
import allG.weato.domain.enums.TagType;
import allG.weato.domain.post.entities.Post;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PostRetrieveDto {
    private Long id;

    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    private String author;

    private String content;

    private Integer commentsCounter;

    private Integer views;

    private Integer likeCounter;

    private Integer level;

    private BoardType boardType;

    private TagType tagType;

    public PostRetrieveDto(Post post) {
        id= post.getId();
        title = post.getTitle();
        createdAt = post.getCreatedAt();
        author = post.getMember().getNickname();
        level = post.getMember().getLevel().getLevel();
        commentsCounter = post.getCommentList().size();
        views= post.getViews();
        likeCounter=post.getLikeCount();
        boardType=post.getBoardType();
        tagType=post.getTagType();
    }

    public PostRetrieveDto(Post post, Integer std){
        id= post.getId();
        title = post.getTitle();
        content = post.getContent();
        createdAt = post.getCreatedAt();
        author = post.getMember().getNickname();
        level = post.getMember().getLevel().getLevel();
        commentsCounter = post.getCommentList().size();
        views= post.getViews();
        likeCounter=post.getLikeCount();
        boardType=post.getBoardType();
        tagType=post.getTagType();
    }
}
