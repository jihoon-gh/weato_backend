package allG.weato.service;

import allG.weato.repository.NewsletterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsletterService {

    private final NewsletterRepository newsletterRepository;


}
