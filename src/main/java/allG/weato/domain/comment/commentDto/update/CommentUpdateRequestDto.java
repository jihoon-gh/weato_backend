package allG.weato.domain.comment.commentDto.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentUpdateRequestDto {

    @NotEmpty
    private String content;
}
