package allG.weato.domainTest.member.dto.retrieve;

import allG.weato.domainTest.enums.TagType;
import allG.weato.domainTest.member.entities.Member;
import allG.weato.domainTest.newsletter.dto.retrieve.NewsletterResponseDto;
import allG.weato.domainTest.newsletter.entities.BookMark;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MemberBookmarkNewslettersDto {

    private Integer min;
    private Integer max;
    private Integer current;
    private List<NewsletterResponseDto> result;

    public MemberBookmarkNewslettersDto(Member member, Pageable pageable){

       List<NewsletterResponseDto> newsletters = member.getBookMarkList()
                .stream()
                .sorted(Comparator.comparing(BookMark::getLocalDateTime))
                .map(n -> new NewsletterResponseDto(n.getNewsletter()))
                .collect(Collectors.toList());
        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), newsletters.size());
        Page<NewsletterResponseDto> page = new PageImpl<>(newsletters.subList(start, end), pageable, newsletters.size());

        int lastPage = page.getTotalPages();
        result=page.getContent();
        current=pageable.getPageNumber()+1;
        min = 1+current/10*10;
        max =10+current/10*10;
        if(max>=lastPage) max = lastPage;
        if(max==0) max++;
    }

    public MemberBookmarkNewslettersDto(Member member,Pageable pageable, TagType tagType){

        List<NewsletterResponseDto> newsletters = member.getBookMarkList()
                .stream()
                .filter(n->n.getNewsletter().getTagType()==tagType)
                .sorted(Comparator.comparing(BookMark::getLocalDateTime))
                .map(n->new NewsletterResponseDto(n.getNewsletter()))
                .collect(Collectors.toList());

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), newsletters.size());
        Page<NewsletterResponseDto> page = new PageImpl<>(newsletters.subList(start, end), pageable, newsletters.size());

        int lastPage = page.getTotalPages();
        result=page.getContent();
        current=pageable.getPageNumber()+1;
        min = 1+current/10*10;
        max =10+current/10*10;
        if(max>=lastPage) max = lastPage;
        if(max==0) max++;
    }
}
