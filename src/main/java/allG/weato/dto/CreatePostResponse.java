package allG.weato.dto;

import allG.weato.domain.Attachment;
import allG.weato.domain.enums.BoardType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class CreatePostResponse {

    String Author;
    String title;
    String content;
    LocalDateTime createdAt;
    BoardType boardType;
    List<Attachment> attachments = new ArrayList<>();
}
