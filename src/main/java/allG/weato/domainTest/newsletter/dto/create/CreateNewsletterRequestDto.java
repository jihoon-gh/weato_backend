package allG.weato.domainTest.newsletter.dto.create;

import allG.weato.domainTest.enums.TagType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateNewsletterRequestDto {

    @NotEmpty
    String title;

    @NotEmpty
    String content;

    @NotNull
    TagType tagType;
}

