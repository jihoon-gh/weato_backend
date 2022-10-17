package allG.weato.domain.anonymousMember.dtos.update;

import allG.weato.domain.anonymousMember.entities.AnonymousMember;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class UpdateAnonymousMemberResponse {

    private Long id;
    private HttpStatus httpStatus;

    public UpdateAnonymousMemberResponse(AnonymousMember am){
        id = am.getId();
        httpStatus = HttpStatus.OK;
    }

}
