package allG.weato.domain.post.dto.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePostRequest {
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
}
