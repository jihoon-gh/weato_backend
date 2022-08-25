package allG.weato.domains.newsletter.newsletterDto.retrieve;

import allG.weato.domains.newsletter.entities.Newsletter;
import allG.weato.domains.enums.TagType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class NewsletterResponseDto {
    private Long id;
    private String title;
    private TagType tagType;

    private int views;

    private int likeCount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    private Integer bookmarkCount;
    public NewsletterResponseDto(Newsletter newsletter){
        id = newsletter.getId();
        title=newsletter.getTitle();
        tagType=newsletter.getTagType();
        createdAt=newsletter.getCreatedAt();
        views=newsletter.getViews();
        likeCount= newsletter.getLikeCount();
        bookmarkCount=newsletter.getBookMarkCount();
    }
}
