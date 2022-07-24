package allG.weato.domains.post.postDto.create;

import allG.weato.domains.enums.BoardType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CreatePostRequest {
    @NotEmpty
    String content;

    @NotEmpty
    String title;
    LocalDateTime createAt;

    BoardType boardType;
    List<String> imageUrls = new ArrayList<>();

}
