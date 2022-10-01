package allG.weato.domainTest.sendingLetters.dto.create;

import allG.weato.domainTest.sendingLetters.entities.SendingLetter;
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
