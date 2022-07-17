package allG.weato.controller;

import allG.weato.domain.Newsletter;
import allG.weato.domain.enums.TagType;
import allG.weato.dto.newsletter.CreateNewsletterDto;
import allG.weato.dto.newsletter.NewsletterResponseDto;
import allG.weato.dto.newsletter.NewsletterUpdateRequestDto;
import allG.weato.dto.newsletter.NewsletterUpdateResponseDto;
import allG.weato.service.NewsletterService;
import allG.weato.validation.CommonErrorCode;
import allG.weato.validation.RestException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class NewsletterController {

    private final NewsletterService newsletterService;

    @GetMapping("/api/newsletters") //전체조회. 어차피 페이징 구현해야함 8개씩 구현
    public List<NewsletterResponseDto> findNewsletters(@RequestParam("page") Integer page){

        List<Newsletter> newsletters = newsletterService.findPage(page).getContent();
        if(newsletters.isEmpty()) throw new RestException(CommonErrorCode.RESOURCE_NOT_FOUND);
        else{
            List<NewsletterResponseDto> result = newsletters
                    .stream()
                    .map(n -> new NewsletterResponseDto(n)).collect(toList());
            return result;
        }
    }

    @GetMapping("/api/newsletters/{tagType}") //얘도 마찬가지임 8개씩 페이징
    public List<NewsletterResponseDto> findByTagType(@PathVariable("tagType")TagType tagType, @RequestParam("page") Integer page){
        Page<Newsletter> finds = newsletterService.findPageByTag(tagType,page);
        List<Newsletter> newsletters = finds.getContent();
        if(newsletters.isEmpty()) throw new RestException(CommonErrorCode.RESOURCE_NOT_FOUND);
        List<NewsletterResponseDto> result = newsletters
                .stream()
                .map(n -> new NewsletterResponseDto(n)).collect(toList());
        return result;
    }

    @GetMapping("/api/newsletters/{id}") //단건 조회
    public NewsletterResponseDto findNewsletter(@PathVariable("id") Long id){
        Newsletter findOne = newsletterService.findOneById(id);
        return new NewsletterResponseDto(findOne);

    }

    @PostMapping("/api/newsletters")
    public NewsletterResponseDto postNewsletter(@RequestBody @Valid CreateNewsletterDto request){
        Newsletter newsletter = new Newsletter(request.getTitle(),request.getContent(),request.getTagType());
        Long id = newsletter.getId();
        newsletterService.save(newsletter);
        Newsletter findOne = newsletterService.findOneById(id);
       if(findOne==null) throw new RestException(CommonErrorCode.INTERNAL_SERVER_ERROR);
       else return new NewsletterResponseDto(findOne);
    }




    @PatchMapping("/api/newsletters/{id}") //업데이트
    public NewsletterUpdateResponseDto updateNewsletter(@PathVariable("id") Long id, @RequestBody @Valid NewsletterUpdateRequestDto request){
        Newsletter findOne = newsletterService.findOneById(id);
        newsletterService.updateNewsletter(findOne,request);
        return new NewsletterUpdateResponseDto(findOne);
    }

    @DeleteMapping("api/newsletters/{id}") //삭제
    public HttpStatus deleteNewsletter(@PathVariable("id") Long id){
        Newsletter findNewsletter = newsletterService.findOneById(id);
        if(findNewsletter==null) throw new RestException(CommonErrorCode.RESOURCE_NOT_FOUND);
        newsletterService.deleteNewsletter(findNewsletter);
        return  HttpStatus.NO_CONTENT;
    }
}
