package allG.weato.dto.create;

import allG.weato.domain.Attachment;
import allG.weato.domain.Post;
import allG.weato.domain.enums.BoardType;
import allG.weato.dto.AttachmentDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CreatePostResponse {

    String Author;
    String title;
    String content;
    LocalDateTime createdAt;
    BoardType boardType;
    List<AttachmentDto> attachments = new ArrayList<>();

    public CreatePostResponse(Post post){
        Author = post.getMember().getName();
        title=post.getTitle();
        content = post.getContent();
        createdAt = post.getCreateAt();
        boardType=post.getBoardType();
        attachments=post.getAttachmentList().stream()
                .map(a -> new AttachmentDto(a)).collect(Collectors.toList());
    }
}
