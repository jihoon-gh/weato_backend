package allG.weato.domains.post.dto.create;

import allG.weato.domains.post.entities.Post;
import allG.weato.domains.enums.BoardType;
import allG.weato.domains.post.dto.AttachmentDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CreatePostResponse {
    private Long id;
    private HttpStatus httpStatus;

    public CreatePostResponse(Post post){
        id = post.getId();
        httpStatus=HttpStatus.CREATED;
    }
}
