package allG.weato.domains.newsletter.dto.retrieve;

import allG.weato.domains.enums.TagType;
import allG.weato.domains.member.entities.Member;
import allG.weato.domains.newsletter.entities.Newsletter;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class NewsletterDetailResponseDto {

    private Long id;
    private String title;
    private TagType tagType;

    private int views;

    private String content;

    private int likeCount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    private int bookmarkCount;

    private Boolean bookmarkChecker;

    private Boolean likeChecker;

    public NewsletterDetailResponseDto(Newsletter newsletter){
        id= newsletter.getId();
        title= newsletter.getTitle();
        tagType=newsletter.getTagType();
        views=newsletter.getViews();
        content=newsletter.getContent();
        likeCount= newsletter.getLikeCount();
        createdAt=newsletter.getCreatedAt();
        bookmarkCount=newsletter.getBookMarkCount();
        bookmarkChecker=false;
        likeChecker=false;
    }
    public NewsletterDetailResponseDto(Newsletter newsletter,Member member){
        id= newsletter.getId();
        title= newsletter.getTitle();
        tagType=newsletter.getTagType();
        views=newsletter.getViews();
        content=newsletter.getContent();
        likeCount= newsletter.getLikeCount();
        createdAt=newsletter.getCreatedAt();
        bookmarkCount=newsletter.getBookMarkCount();
        if(member.getNewsletterLikeChecker().contains(newsletter.getId())){
            likeChecker=true;
        }
        if(member.getBookmarkChecker().contains(newsletter.getId())){
            bookmarkChecker=true;
        }
    }
}
