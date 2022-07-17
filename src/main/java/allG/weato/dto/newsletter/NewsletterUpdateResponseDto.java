package allG.weato.dto.newsletter;

import allG.weato.domain.Newsletter;
import allG.weato.domain.enums.TagType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@NoArgsConstructor
public class NewsletterUpdateResponseDto {
    private String title;
    private String content;
    private TagType tagType;
    private LocalDateTime createdAt;
    private int likeCount=0;

    public NewsletterUpdateResponseDto(Newsletter newsletter)
    {
        title=newsletter.getTitle();
        content = newsletter.getContent();
        tagType=newsletter.getTagType();
        createdAt=LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        likeCount=newsletter.getLikeCount();
    }
}