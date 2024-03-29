package allG.weato.domain.comment.commentDto.delete;

import allG.weato.domain.comment.entities.Comment;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeleteCommentLikeDto {
    private Long id;
    private int likeCount;

    public DeleteCommentLikeDto(Comment comment){
        id= comment.getId();
        likeCount=comment.getLikeCount();
    }
}
