package allG.weato.controller;


import allG.weato.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/hello")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("hello");
    }
    
    @GetMapping("/members/{memberId}")// my-page
    public void showMember(@PathVariable("memberId") Long memberId){
        
    }
    @GetMapping("/members/{memberId}/profile")//프로필
    public void showMemberProfile(@PathVariable("memberId") Long memberId){

    }
    @GetMapping("/members/{memberId}/additional-info")//추가정보
    public void showMemberAdditionalInfo(@PathVariable("memberId") Long memberId){

    }
    @PostMapping("/members/{memberId}/additional-info")//추가정보
    public void createMemberAdditionalInfo(@PathVariable("memberId") Long memberId, @RequestBody @Valid int a){
    }

    @GetMapping("/members/{memberId}/bookmark") //북마크
    public void showBookMark(@PathVariable("memberId") Long memberId){

    }




    
    
    
}
