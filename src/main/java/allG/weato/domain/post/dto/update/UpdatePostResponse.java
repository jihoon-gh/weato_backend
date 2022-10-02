package allG.weato.domain.post.dto.update;

import allG.weato.domain.post.entities.Post;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class UpdatePostResponse {

    private Long id;

    private HttpStatus httpStatus;

    public UpdatePostResponse(Post post){
        id=post.getId();
        httpStatus=HttpStatus.OK;
    }

}


