package allG.weato.domains.post.dto.create;

import allG.weato.domains.post.entities.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostScrapDto {

    private Long id;
    private int count;

    public PostScrapDto(Post post){
        id=post.getId();
        count=post.getScrapCount();
    }
}
