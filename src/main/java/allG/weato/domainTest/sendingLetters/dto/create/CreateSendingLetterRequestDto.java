package allG.weato.domainTest.sendingLetters.dto.create;

import allG.weato.domainTest.enums.TagType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateSendingLetterRequestDto {

    @NotEmpty
    private String title;

    @NotEmpty
    private String author;

    @NotEmpty
    private String content;

    @NotNull
    private TagType tagType;
}

