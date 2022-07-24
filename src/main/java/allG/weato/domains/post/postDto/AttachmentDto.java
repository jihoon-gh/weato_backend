package allG.weato.domains.post.postDto;

import allG.weato.domains.post.entities.Attachment;
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
