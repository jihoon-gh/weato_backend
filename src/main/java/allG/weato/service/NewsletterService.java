package allG.weato.service;

import allG.weato.domain.Newsletter;
import allG.weato.domain.Tag;
import allG.weato.domain.enums.TagType;
import allG.weato.dto.newsletter.NewsletterUpdateRequestDto;
import allG.weato.repository.NewsletterRepository;
import allG.weato.validation.CommonErrorCode;
import allG.weato.validation.RestException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsletterService {

    private final NewsletterRepository newsletterRepository;


    public Newsletter findOneById(Long id){
        Optional<Newsletter> findNewsletter = newsletterRepository.findById(id);
        if(findNewsletter.isEmpty()) throw new RuntimeException("존재하는 않는 페이지");
        else {
            return findNewsletter.get();
        }
    }

    public Page<Newsletter> findPage(Integer num){
        PageRequest pageRequest = PageRequest.of(num,8,Sort.by(Sort.Direction.DESC,"createAt"));
        Page<Newsletter> result = newsletterRepository.findAll(pageRequest);
        return result;
    }

    public Page<Newsletter> findPageByTag(TagType tagType, Integer num){
        PageRequest pageRequest = PageRequest.of(num,8, Sort.by(Sort.Direction.DESC,"createAt"));
        Page<Newsletter> pages = newsletterRepository.findNewslettersByTagType(tagType,pageRequest);
        return pages;
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
        if(counter!=0) newsletter.changeCreateAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
    }


    @Transactional
    @Modifying
    public void deleteNewsletter(Newsletter newsletter){
        if(newsletter==null) throw new RestException(CommonErrorCode.RESOURCE_NOT_FOUND);
        newsletterRepository.delete(newsletter);
    }
}
