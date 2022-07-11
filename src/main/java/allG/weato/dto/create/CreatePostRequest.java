package allG.weato.dto.create;

import allG.weato.domain.enums.BoardType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Not;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
    @NotEmpty
    String author;
    LocalDateTime createAt;

    BoardType boardType;
    List<String> imageUrls = new ArrayList<>();

    public CreatePostRequest(String content, String title, String author, LocalDateTime createAt, BoardType boardType){
        this.content = content;
        this.title = title;
        this.author = author;
        this.createAt = createAt;
        this.boardType = boardType;
    }
}
