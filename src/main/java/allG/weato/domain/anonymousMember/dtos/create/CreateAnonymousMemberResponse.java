package allG.weato.domain.anonymousMember.dtos.create;

import allG.weato.domain.anonymousMember.entities.AnonymousMember;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CreateAnonymousMemberResponse {

    private Long id;
    private HttpStatus httpStatus;

    public CreateAnonymousMemberResponse(AnonymousMember am){
        id = am.getId();
        httpStatus = HttpStatus.CREATED;
    }
}
