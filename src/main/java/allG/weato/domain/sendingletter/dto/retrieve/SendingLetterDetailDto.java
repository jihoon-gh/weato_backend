package allG.weato.domain.sendingletter.dto.retrieve;

import allG.weato.domain.sendingletter.entities.SendingLetter;
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
