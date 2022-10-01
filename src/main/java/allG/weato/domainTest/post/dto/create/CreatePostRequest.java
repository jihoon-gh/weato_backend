package allG.weato.domainTest.post.dto.create;

import allG.weato.domainTest.enums.BoardType;
import allG.weato.domainTest.enums.TagType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CreatePostRequest {
    @NotEmpty
    String title;

    @NotEmpty
    String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime createAt;

    @NotNull
    BoardType boardType;

    @NotNull
    TagType tagType;
    List<String> imageUrls = new ArrayList<>();

}
