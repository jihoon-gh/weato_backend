package allG.weato.domain.post.dto.create;

import allG.weato.domain.post.entities.Post;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CreatePostResponse {
    private Long id;
    private HttpStatus httpStatus;

    public CreatePostResponse(Post post){
        id = post.getId();
        httpStatus=HttpStatus.CREATED;
    }
}
