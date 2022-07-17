package allG.weato.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddLikeDto {

    Long id;
    int likecount;

    public AddLikeDto(Long id, int likecount) {
        this.id = id;
        this.likecount = likecount;
    }
}
