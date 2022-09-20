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
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.Data;
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
@RequestMapping("/api")
public class NewsletterController {

    private final NewsletterService newsletterService;

    @Operation(summary = "get pages of newsletters", description = "뉴스레터 페이지")
    @GetMapping("/newsletters") //전체조회. 어차피 페이징 구현해야함 8개씩 구현
    public ResultForPaging findNewsletters(@RequestParam(value = "page",defaultValue = "1") Integer page){

        Page<Newsletter> finds = newsletterService.findPage(page-1);
        List<Newsletter> newsletters = finds.getContent();

        if(newsletters.isEmpty()) throw new RestException(CommonErrorCode.RESOURCE_NOT_FOUND);

        int lastPage = finds.getTotalPages();
        int current = finds.getNumber();
        int min = 1+current/10*10;
        int max =10+current/10*10;
        if(max>=lastPage) max = lastPage;
        List<NewsletterResponseDto> result = newsletters
                    .stream()
                    .map(n -> new NewsletterResponseDto(n)).collect(toList());

        return new ResultForPaging(result,min, max, current);
    }
    @Operation(summary = "get pages of newsletters with TagType", description = "태그 타입을 통한 뉴스레터 조회")
    @GetMapping("/newsletters/{tagType}") //얘도 마찬가지임 8개씩 페이징
    public ResultForPaging<Newsletter> findByTagType(@PathVariable("tagType")TagType tagType, @RequestParam("page") Integer page){
        Page<Newsletter> finds = newsletterService.findPageByTag(tagType,page);
        List<Newsletter> newsletters = finds.getContent();

        if(newsletters.isEmpty()) throw new RestException(CommonErrorCode.RESOURCE_NOT_FOUND);

        int lastPage = finds.getTotalPages();
        int current = finds.getNumber();
        int min = 1+current/10*10;
        int max =10+current/10*10;
        if(max>=lastPage) max = lastPage;
        List<NewsletterResponseDto> result = newsletters
                .stream()
                .map(n -> new NewsletterResponseDto(n)).collect(toList());
        return new ResultForPaging(result,min,max,current);
    }

    @Operation(summary = "get specific newsletter", description = "뉴스레터 단건조회")
    @GetMapping("/newsletters/{id}") //단건 조회
    public NewsletterResponseDto findNewsletter(@PathVariable("id") Long id){
        Newsletter findOne = newsletterService.findOneById(id);
        return new NewsletterResponseDto(findOne);

    }

    @Operation(summary = "create a newsletter - only admin is authorized", description = "뉴스레터 생성")
    @PostMapping("/newsletters")
    public NewsletterResponseDto postNewsletter(@RequestBody @Valid CreateNewsletterDto request){
        Newsletter newsletter = new Newsletter(request.getTitle(),request.getContent(),request.getTagType());
        Long id = newsletter.getId();
        newsletterService.save(newsletter);
        /*Newsletter findOne = newsletterService.findOneById(id); //이거 약간 테스트처럼 드가는데???
        if(findOne==null) throw new RestException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        else */ return new NewsletterResponseDto(newsletter);
    }
    @Operation(summary = "update specific newsletter - only admin is authorized", description = "뉴스레터 수정")
    @PatchMapping("/newsletters/{id}") //업데이트
    public NewsletterUpdateResponseDto updateNewsletter(@PathVariable("id") Long id, @RequestBody @Valid NewsletterUpdateRequestDto request){
        Newsletter findOne = newsletterService.findOneById(id);
        if(findOne==null) throw new RestException(CommonErrorCode.RESOURCE_NOT_FOUND);
        newsletterService.updateNewsletter(findOne,request);
        return new NewsletterUpdateResponseDto(findOne);
    }

    @Operation(summary = "delete specific newsletter - only admin is authorized", description = "뉴스레터 삭제")
    @DeleteMapping("/newsletters/{id}") //삭제
    public HttpStatus deleteNewsletter(@PathVariable("id") Long id){
        Newsletter findNewsletter = newsletterService.findOneById(id);
        if(findNewsletter==null) throw new RestException(CommonErrorCode.RESOURCE_NOT_FOUND);
        newsletterService.deleteNewsletter(findNewsletter);
        return  HttpStatus.NO_CONTENT;
    }

    @Data
    @AllArgsConstructor
    static class ResultForPaging<T>{
        private T data;
        private int min;
        private int max;
        private int current;
    }
}
