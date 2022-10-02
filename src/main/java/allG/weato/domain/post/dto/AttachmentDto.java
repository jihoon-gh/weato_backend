package allG.weato.domain.post.dto;

import allG.weato.domain.post.entities.Attachment;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AttachmentDto {
    private String imgUrl;

    public AttachmentDto(Attachment attachment) {
        this.imgUrl = attachment.getImgUrl();
    }
}
