package allG.weato.dto.post.update;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UpdatePostResponse {
    private Long id;
    private LocalDateTime localDateTime;
}
