package allG.weato.domains.post.dto.update;

import allG.weato.domains.post.entities.Post;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class UpdatePostResponse {

    private Long id;

    private HttpStatus httpStatus;

    public UpdatePostResponse(Post post){
        id=post.getId();
        httpStatus=HttpStatus.OK;
    }

}


