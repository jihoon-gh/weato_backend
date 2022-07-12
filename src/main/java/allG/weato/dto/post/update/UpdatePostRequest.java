package allG.weato.dto.post.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePostRequest {
    private String title;
    private String content;
    private LocalDateTime localDateTime;
}
