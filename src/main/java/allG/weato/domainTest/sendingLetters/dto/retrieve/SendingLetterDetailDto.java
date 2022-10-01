package allG.weato.domainTest.sendingLetters.dto.retrieve;

import allG.weato.domainTest.sendingLetters.entities.SendingLetter;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SendingLetterDetailDto {

    private Long id;
    private String title;
    private String author;
    private String content;
    private LocalDateTime localDateTime;

    public SendingLetterDetailDto(SendingLetter sl){
        id=sl.getId();
        title= sl.getTitle();
        author=sl.getAuthor();
        content=sl.getContent();
        localDateTime=sl.getLocalDateTime();
    }
}
