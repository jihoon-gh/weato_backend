package allG.weato.domains.post.postDto.update;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UpdatePostResponse {
    private Long id;
    private LocalDateTime localDateTime;
}
