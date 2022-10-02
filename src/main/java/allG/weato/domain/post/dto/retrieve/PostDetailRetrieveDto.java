package allG.weato.domain.post.dto.retrieve;

import allG.weato.domain.enums.BoardType;
import allG.weato.domain.enums.SymptomDegree;
import allG.weato.domain.enums.TagType;
import allG.weato.domain.member.entities.Member;
import allG.weato.domain.post.entities.Post;
import allG.weato.domain.comment.commentDto.retrieve.CommentRetrieveDto;
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

    private Integer authorLevel;

    private Integer medicalHistory;

    private SymptomDegree symptomDegree;
    private String title;
    private String content;

    private BoardType boardType;

    private TagType tagType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    private Integer likeCount;
    private List<CommentRetrieveDto> comments;

    private Integer views;

    private Integer scrapCount;

    private Boolean likeChecker=false;
    private Boolean scrapChecker=false;

    private Boolean isAuthor=false;

    public PostDetailRetrieveDto(Post post, Member member) {
        id = post.getId();
        title = post.getTitle();
        content = post.getContent();
        createdAt = post.getCreatedAt();
        boardType=post.getBoardType();
        tagType=post.getTagType();
        likeCount = post.getLikeCount();
        author=post.getMember().getNickname();
        authorLevel=post.getMember().getLevel().getLevel();
        medicalHistory=  post.getMember().getAdditionalInfo().getMedicalHistory();
        symptomDegree = post.getMember().getAdditionalInfo().getSymptomDegree();
        views=post.getViews();
        comments = post.getCommentList().stream()
                .filter(comment -> comment.getParent()==null)
                .map( c -> new CommentRetrieveDto(c,member,true)).collect(Collectors.toList());
        scrapCount=post.getScrapCount();
        if(member.getPostLikeChecker().contains(post.getId())){
            likeChecker=true;
        }
        if(member.getScrapChecker().contains(post.getId())){
            scrapChecker=true;
        }
        if(post.getMember().getId()==member.getId()){
            isAuthor=true;
        }
    }
}
