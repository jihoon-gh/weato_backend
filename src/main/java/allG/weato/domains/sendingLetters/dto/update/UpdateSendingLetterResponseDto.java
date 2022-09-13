package allG.weato.domains.sendingLetters.dto.update;

import allG.weato.domains.sendingLetters.entities.SendingLetter;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UpdateSendingLetterResponseDto {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime localDateTime;

    public UpdateSendingLetterResponseDto(SendingLetter sl){
        id= sl.getId();
        title= sl.getTitle();
        content= sl.getContent();
        localDateTime=sl.getLocalDateTime();
    }
}
