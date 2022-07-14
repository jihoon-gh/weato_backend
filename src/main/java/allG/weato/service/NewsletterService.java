package allG.weato.service;

import allG.weato.domain.Newsletter;
import allG.weato.domain.Tag;
import allG.weato.domain.enums.TagType;
import allG.weato.repository.NewsletterRepository;
import allG.weato.validation.CommonErrorCode;
import allG.weato.validation.RestException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsletterService {

    private final NewsletterRepository newsletterRepository;

    public List<Newsletter> findAll(){
        return newsletterRepository.findAll();
    }

    public Newsletter findOneById(Long id){
        Optional<Newsletter> findNewsletter = newsletterRepository.findById(id);
        if(findNewsletter.isEmpty()) throw new RuntimeException("존재하는 않는 페이지");
        else {
            return findNewsletter.get();
        }
    }

    public List<Newsletter> findNewsLetterByTag(Tag tag){
        List<Newsletter> finds = newsletterRepository.findNewslettersByTag(tag);
        return finds;
    }

    @Transactional
    public void addNewsletter(Newsletter newsletter){
        if(newsletter==null) throw new RestException(CommonErrorCode.RESOURCE_NOT_FOUND);
        newsletterRepository.save(newsletter);
    }


    @Transactional
    @Modifying
    public void deleteNewsletter(Newsletter newsletter){
        if(newsletter==null) throw new RestException(CommonErrorCode.RESOURCE_NOT_FOUND);
        newsletterRepository.delete(newsletter);
    }
}
