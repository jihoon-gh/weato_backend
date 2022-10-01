package allG.weato.domainTest.post.dto.create;

import allG.weato.domainTest.post.entities.Post;
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
