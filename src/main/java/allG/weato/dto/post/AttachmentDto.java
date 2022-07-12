package allG.weato.dto.post;

import allG.weato.domain.Attachment;
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
