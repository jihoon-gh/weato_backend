package allG.weato.domains.post.dto.retrieve;

import allG.weato.domains.enums.BoardType;
import allG.weato.domains.member.entities.MemberLevel;
import allG.weato.domains.post.entities.Post;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ScrapedPostDto {

    private Long id;
    private String title;
    private String name;
    private MemberLevel memberLevel;
    private BoardType boardType;
    private int likeCount;
    private int views;
    private int scrapedCount;


    public ScrapedPostDto(Post post){
        id = post.getId();
        title = post.getTitle();
        name = post.getMember().getNickname();
        memberLevel =post.getMember().getMemberLevel();
        boardType=post.getBoardType();
        likeCount= post.getLikeCount();
        views=post.getViews();
        scrapedCount=post.getScrapList().size();
    }
}
