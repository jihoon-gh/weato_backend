package allG.weato.domains.newsletter.newsletterDto;

import allG.weato.domains.enums.TagType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateNewsletterDto {
    String title;
    String content;
    TagType tagType;
}

