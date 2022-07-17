package allG.weato.dto.newsletter;

import allG.weato.domain.enums.TagType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@NoArgsConstructor
public class NewsletterUpdateRequestDto {
    private String updatedTitle;
    private String updatedContent;
    private LocalDateTime updatedAt=LocalDateTime.now(ZoneId.of("Asia/Seoul"));
}
