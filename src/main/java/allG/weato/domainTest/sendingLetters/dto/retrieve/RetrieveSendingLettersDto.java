package allG.weato.domainTest.sendingLetters.dto.retrieve;

import allG.weato.domainTest.enums.TagType;
import allG.weato.domainTest.sendingLetters.entities.SendingLetter;
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
