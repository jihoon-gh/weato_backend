package allG.weato.domain.sendingletter.dto.update;

import allG.weato.domain.sendingletter.entities.SendingLetter;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
