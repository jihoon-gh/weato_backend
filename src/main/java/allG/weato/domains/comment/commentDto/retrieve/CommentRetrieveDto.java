package allG.weato.domains.comment.commentDto.retrieve;

import allG.weato.domains.comment.entities.Comment;
import allG.weato.domains.member.entities.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class CommentRetrieveDto {
    private Long id;
    private String author;

    private Integer authorLevel;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    private Integer likeCounter;

    private Boolean likeChecker=false;

    private List<CommentRetrieveDto> children = new ArrayList<>();

    private Boolean isAuthor=false;

    public CommentRetrieveDto(Comment comment, Member member){
        id=comment.getId();
        author=comment.getMember().getNickname();
        authorLevel=comment.getMember().getLevel().getLevel();
        content=comment.getContent();
        createdAt=comment.getCreatedAt();
        likeCounter=comment.getLikeCount();
        if(member.getCommentLikeChecker().contains(comment.getId())){
            likeChecker=true;
        }
        if(comment.getMember().getId()==member.getId()){
            isAuthor=true;
        }
    }

    public CommentRetrieveDto(Comment comment, Member member,boolean bool){
        id=comment.getId();
        author=comment.getMember().getNickname();
        authorLevel=comment.getMember().getLevel().getLevel();
        content=comment.getContent();
        createdAt=comment.getCreatedAt();
        likeCounter=comment.getLikeCount();
        children=comment.getChildren()
                .stream()
                .map(c->new CommentRetrieveDto(c,member))
                .collect(Collectors.toList());
        if(member.getCommentLikeChecker().contains(comment.getId())){
            likeChecker=true;
        }
        if(comment.getMember().getId()==member.getId()){
            isAuthor=true;
        }

    }


}
