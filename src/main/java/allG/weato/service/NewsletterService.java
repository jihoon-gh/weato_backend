package allG.weato.service;

import allG.weato.domain.Newsletter;
import allG.weato.repository.NewsletterRepository;
import lombok.RequiredArgsConstructor;
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



}
