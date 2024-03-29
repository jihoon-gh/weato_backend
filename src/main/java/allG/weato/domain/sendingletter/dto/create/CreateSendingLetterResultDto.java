package allG.weato.domain.sendingletter.dto.create;

import allG.weato.domain.sendingletter.entities.SendingLetter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class CreateSendingLetterResultDto {

    private Long id;

    private HttpStatus httpStatus;

    public CreateSendingLetterResultDto(SendingLetter sl) {
        id=sl.getId();
        if(id!=null){
            httpStatus=HttpStatus.CREATED;
        }
    }
}
