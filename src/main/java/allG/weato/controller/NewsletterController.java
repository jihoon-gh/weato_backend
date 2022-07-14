package allG.weato.controller;

import allG.weato.domain.Newsletter;
import allG.weato.service.NewsletterService;
import allG.weato.validation.CommonErrorCode;
import allG.weato.validation.RestException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NewsletterController {

    private NewsletterService newsletterService;

    @GetMapping("/api/newsletters")
    public List<Newsletter> findNewsletters(){
        List<Newsletter> newsletters = newsletterService.findAll();
        if(newsletters.isEmpty()) throw new RestException(CommonErrorCode.RESOURCE_NOT_FOUND);
        else return newsletters;
    }

    /*@GetMapping("/api/newsletters/{tagType}")


    @GetMapping("/api/newsletters/{id}")*/
}
