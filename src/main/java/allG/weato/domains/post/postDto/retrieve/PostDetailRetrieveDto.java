package allG.weato.domains.post.postDto.retrieve;

import allG.weato.domains.member.entities.Level;
import allG.weato.domains.member.entities.Member;
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

    private boolean likeChecker;
    private boolean scrapChecker;

    public PostDetailRetrieveDto(Post post, Member member) {
        title = post.getTitle();
        content = post.getContent();
        createdAt = post.getCreatedAt();
        likeCount = post.getLikeCount();
        author=post.getMember().getName();
        authorLevel=post.getMember().getLevel().getLevel();
        views=post.getViews();
        comments = post.getCommentList().stream()
                .map( c -> new CommentRetrieveDto(c)).collect(Collectors.toList());
        scrapCount=post.getScrapCount();
        if(member.getPostLikeChecker().contains(post.getId())){
            likeChecker=true;
        }else{
            likeChecker=false;
        }

        if(member.getScrapChecker().contains(post.getId())){
            scrapChecker=true;
        }else {
            scrapChecker = false;
        }
    }
}
