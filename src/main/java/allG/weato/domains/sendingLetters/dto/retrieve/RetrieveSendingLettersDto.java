package allG.weato.domains.sendingLetters.dto.retrieve;

import allG.weato.domains.enums.TagType;
import allG.weato.domains.sendingLetters.entities.SendingLetter;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class RetrieveSendingLettersDto {

    private Long id;

    private TagType tagType;
    private String author;
    private String title;
    private String content;
    private LocalDateTime localDateTime;

    public RetrieveSendingLettersDto(SendingLetter sendingLetter){
        id = sendingLetter.getId();
        author=sendingLetter.getAuthor();
        title=sendingLetter.getTitle();
        content=sendingLetter.getContent();
        tagType=sendingLetter.getTagType();
        localDateTime=sendingLetter.getLocalDateTime();
    }
}
