package allG.weato.domain.newsletter.dto.retrieve;

import allG.weato.domain.member.entities.Member;
import allG.weato.domain.newsletter.entities.Newsletter;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookmarkResponseDto {

    private Long id;
    private String name;
    private String title;
    private int bookmarkCount;

    public BookmarkResponseDto(Member member, Newsletter newsletter){
        id=newsletter.getId();
        name=member.getName();
        title= newsletter.getTitle();
        bookmarkCount=newsletter.getBookMarkList().size();
    }


}
