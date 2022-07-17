package allG.weato.dto.newsletter;

import allG.weato.domain.enums.TagType;
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

