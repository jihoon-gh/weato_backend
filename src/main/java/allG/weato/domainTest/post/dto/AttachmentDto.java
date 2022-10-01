package allG.weato.domainTest.post.dto;

import allG.weato.domainTest.post.entities.Attachment;
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
