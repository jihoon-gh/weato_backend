package allG.weato.domains.post.dto.retrieve;

import allG.weato.domains.enums.BoardType;
import allG.weato.domains.enums.TagType;
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

    private Long id;
    private String author;

    private int authorLevel;
    private String title;
    private String content;

    private BoardType boardType;

    private TagType tagType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    private int likeCount;
    private List<CommentRetrieveDto> comments;

    private int views;

    private int scrapCount;

    private boolean likeChecker=false;
    private boolean scrapChecker=false;

    public PostDetailRetrieveDto(Post post, Member member) {
        id = post.getId();
        title = post.getTitle();
        content = post.getContent();
        createdAt = post.getCreatedAt();
        boardType=post.getBoardType();
        tagType=post.getTagType();
        likeCount = post.getLikeCount();
        author=post.getMember().getNickname();
        authorLevel=post.getMember().getMemberLevel().getLevel();
        views=post.getViews();
        comments = post.getCommentList().stream()
                .filter(comment -> comment.getParent()==null)
                .map( c -> new CommentRetrieveDto(c,true)).collect(Collectors.toList());
        scrapCount=post.getScrapCount();
        if(member.getPostLikeChecker().contains(post.getId())){
            likeChecker=true;
        }
        if(member.getScrapChecker().contains(post.getId())){
            scrapChecker=true;
        }
    }
}
