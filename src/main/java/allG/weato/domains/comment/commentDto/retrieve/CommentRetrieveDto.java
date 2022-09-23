package allG.weato.domains.comment.commentDto.retrieve;

import allG.weato.domains.comment.entities.Comment;
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

    private int authorLevel;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    private int likeCounter;

    private List<CommentRetrieveDto> children = new ArrayList<>();

    public CommentRetrieveDto(Comment comment){
        id=comment.getId();
        author=comment.getMember().getNickname();
        authorLevel=comment.getMember().getLevel().getLevel();
        content=comment.getContent();
        createdAt=comment.getCreatedAt();
        likeCounter=comment.getLikeCount();
    }

    public CommentRetrieveDto(Comment comment, boolean bool){
        id=comment.getId();
        author=comment.getMember().getNickname();
        authorLevel=comment.getMember().getLevel().getLevel();
        content=comment.getContent();
        createdAt=comment.getCreatedAt();
        likeCounter=comment.getLikeCount();
        children=comment.getChildren()
                .stream()
                .map(c->new CommentRetrieveDto(c))
                .collect(Collectors.toList());
    }


}
