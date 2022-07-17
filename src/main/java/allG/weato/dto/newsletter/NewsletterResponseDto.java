package allG.weato.dto.newsletter;

import allG.weato.domain.Newsletter;
import allG.weato.domain.enums.TagType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class NewsletterResponseDto {
    private String title;
    private TagType tagType;
    private String content;
    private LocalDateTime createAt;

    public NewsletterResponseDto(Newsletter newsletter){
        title=newsletter.getTitle();
        content=newsletter.getContent();
        tagType=newsletter.getTagType();
        createAt=newsletter.getCreatedAt();
    }
}