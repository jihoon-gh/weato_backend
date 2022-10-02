package allG.weato.domain.newsletter;

import allG.weato.domain.newsletter.entities.BookMark;
import allG.weato.domain.member.entities.Member;
import allG.weato.domain.newsletter.entities.Newsletter;
import allG.weato.domain.enums.TagType;
import allG.weato.domain.newsletter.entities.NewsletterLike;
import allG.weato.domain.newsletter.dto.update.NewsletterUpdateRequestDto;
import allG.weato.validation.CommonErrorCode;
import allG.weato.validation.RestException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsletterService {

    private final NewsletterRepository newsletterRepository;


    public Newsletter findOneById(Long id){
       return newsletterRepository.findById(id).orElseGet(()->
       {throw new RestException(CommonErrorCode.RESOURCE_NOT_FOUND);});
    }
    public Newsletter findOneByIdWithLikes(Long id){
        return newsletterRepository.findNewsletterByIdWithLikes(id);
    }

    public Newsletter findOneByIdWithBookMarks(Long id){
        return newsletterRepository.findNewsletterByIdWithBookMaks(id);
    }



    public Page<Newsletter> findPage(Integer num){
        PageRequest pageRequest = PageRequest.of(num,8,Sort.by(Sort.Direction.DESC,"createdAt"));
        return newsletterRepository.findAll(pageRequest);

    }

    public Page<Newsletter> findPageByTag(TagType tagType, Integer num){
        PageRequest pageRequest = PageRequest.of(num,8, Sort.by(Sort.Direction.DESC,"createdAt"));
        return  newsletterRepository.findNewslettersByTagType(tagType,pageRequest);

    }

    public Page<Newsletter> searchNewslettersWithKeyword(Integer page, String keyword){
        PageRequest pageRequest = PageRequest.of(page,8,Sort.by(Sort.Direction.DESC,"createdAt"));

        return newsletterRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword,keyword,pageRequest);
    }

    @Transactional
    public void save(Newsletter newsletter){
        newsletterRepository.save(newsletter);
    }
    @Transactional
    public void addNewsletter(Newsletter newsletter){
        if(newsletter==null) throw new RestException(CommonErrorCode.RESOURCE_NOT_FOUND);
        newsletterRepository.save(newsletter);
    }

    @Transactional
    public void updateNewsletter(Newsletter newsletter, NewsletterUpdateRequestDto request){
       int counter=0;
        if(!request.getUpdatedTitle().isEmpty()) {
            newsletter.changeTitle(request.getUpdatedTitle());
            counter++;
        }
        if(!request.getUpdatedContent().isEmpty()){
            newsletter.changeContent(request.getUpdatedContent());
            counter++;
        }
        if(counter!=0) newsletter.changeCreatedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
    }


    @Transactional
    @Modifying
    public void deleteNewsletter(Newsletter newsletter){
        if(newsletter==null) throw new RestException(CommonErrorCode.RESOURCE_NOT_FOUND);
        newsletterRepository.delete(newsletter);
    }

    @Transactional
    public void addBookMark(Member member, Newsletter newsletter, BookMark bookMark) {
        member.addBookMark(bookMark);
        member.addBookmarkChecker(newsletter.getId());
        newsletter.addBookMark(bookMark);

    }

    @Transactional
    public void deleteBookMark(Member member,Newsletter newsletter,BookMark bookMark){
        member.deleteBookMark(bookMark);
        member.deleteBookmarkChecker(newsletter.getId());
        newsletter.deleteBookMark(bookMark);
        newsletterRepository.deleteBookMark(bookMark.getId());
    }

    @Transactional
    public void addNewsletterLike(Member member, Newsletter newsletter, NewsletterLike newsletterLike) {
        member.addNewsletterLike(newsletterLike);
        member.addNewsletterLikeChecker(newsletter.getId());
        newsletter.addNewsletterLike(newsletterLike);
    }

    @Transactional
    public void deleteNewsletterLike(Member member, Newsletter newsletter, NewsletterLike newsletterLike) {
        member.deleteNewsletterLike(newsletterLike);
        member.deleteNewsletterLikeChecker(newsletter.getId());
        newsletter.deleteNewsletterLike(newsletterLike);
        newsletterRepository.deleteNewsletterLike(newsletterLike.getId());
    }

    public List<Newsletter> retrieveHotTopicsOfThisWeek(){
        LocalDateTime std = LocalDateTime.now(ZoneId.of("Asia/Seoul")).minusDays(7);
        PageRequest pageRequest = PageRequest.of(0,8);
        return newsletterRepository.sortNewsletterByLikeCount(std,pageRequest).getContent();
    }

    public List<Newsletter> retrieveMostBookMarked(){
        LocalDateTime std = LocalDateTime.now(ZoneId.of("Asia/Seoul")).minusDays(7);
        PageRequest pageRequest = PageRequest.of(0,8);
        return newsletterRepository.sortNewsletterByBookMarkCount(std,pageRequest).getContent();
    }
}
