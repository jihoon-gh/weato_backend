package allG.weato.domain.member;


import allG.weato.domain.enums.BoardType;
import allG.weato.domain.enums.TagType;
import allG.weato.domain.member.dto.*;
import allG.weato.domain.member.dto.create.CreateMemberRequest;
import allG.weato.domain.member.dto.create.CreateMemberResponse;
import allG.weato.domain.member.dto.delete.WithdrawlRequest;
import allG.weato.domain.member.dto.retrieve.MemberBookmarkNewslettersDto;
import allG.weato.domain.member.dto.retrieve.MemberResponseDto;
import allG.weato.domain.member.dto.retrieve.ProfileResponseDto;
import allG.weato.domain.member.dto.update.UpdateProfileRequestDto;
import allG.weato.domain.member.dto.update.UpdateProfileResponseDto;
import allG.weato.domain.member.entities.AdditionalInfo;
import allG.weato.domain.member.entities.Member;
import allG.weato.domain.post.PostService;
import allG.weato.domain.post.dto.retrieve.PostRetrieveDto;
import allG.weato.domain.post.entities.Post;
import allG.weato.dto.ResultForSearch;
import allG.weato.oauth2.JwtMemberDetails;
import allG.weato.validation.CommonErrorCode;
import allG.weato.validation.RestException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    private final PostService postService;

    @Operation(summary = "Current member", description = "현재 로그인된 멤버")
    @GetMapping("/members")
    public MemberResponseDto getCurrentMember() {

        JwtMemberDetails principal = (JwtMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email =  principal.getUsername();
        Member member = memberService.findByEmail(email);

        return new MemberResponseDto(member);
    }

    @Operation(summary = "Create member profile(profile)", description = "멤버 프로필 생성")
    @PostMapping("/members/{memberId}")
    public CreateMemberResponse createMemberCompletely(@PathVariable("memberId") Long memberId, @RequestBody @Valid CreateMemberRequest request){
        Member member = memberService.findById(memberId);
        memberService.createMemberDetail(member,request);
        return new CreateMemberResponse(member);
    }

    @GetMapping("/members/validation")
    public boolean validationCheckForNickname(@RequestParam("nickname") String nickname){
        return memberService.validateNickname(nickname);
    }

    @Operation(summary = "Retrieve specific member", description = "특정 멤버 조회")
    @GetMapping("/members/{memberId}")// my-page
    public MemberResponseDto showMember(@PathVariable("memberId") Long memberId){

        Member member = memberService.findById(memberId);
        return new MemberResponseDto(member);
    }
    @Operation(summary = "Retrieve profile of specific member", description = "특정 멤버 프로필 조회")
    @GetMapping("/members/{memberId}/profile")//프로필
    public ProfileResponseDto showMemberProfile(@PathVariable("memberId") Long memberId){

        Member member = memberService.findMemberForProfile(memberId);
        if(member.getProfile().getId()==null || member.getAdditionalInfo()==null){
            throw new RuntimeException("추가정보 혹은 프로필을 먼저 입력해주세요!");
        }
        return new ProfileResponseDto(member);
    }

    @Operation(summary = "Update member profile", description = "멤버 프로필 수정")
    @PatchMapping("/members/{memberId}/profile")
    public UpdateProfileResponseDto updateMemberProfile(@PathVariable("memberId") Long memberId, @RequestBody @Valid UpdateProfileRequestDto request){
        Member member = memberService.findMemberForProfile(memberId);
        memberService.updateProfile(member, request);
        return new UpdateProfileResponseDto(member);
    }

    @Operation(summary = "Create Additional-info of member", description = "멤버 추가정보 생성")
    @PostMapping("/members/{memberId}/additional-info")//추가정보
    public AdditionalInfoResponseDto createMemberAdditionalInfo(@PathVariable("memberId") Long memberId, @RequestBody @Valid AdditionalInfoRequestDto request){
        JwtMemberDetails principal = (JwtMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email =  principal.getUsername();
        Member member = memberService.findByEmail(email);

        if(member.getId()!=memberId) throw new RestException(CommonErrorCode.NOT_AUTHORIZATED);

        AdditionalInfo additionalInfo = new AdditionalInfo(request.getYears(), request.getRecurrence(),request.getFamilyHistory(),request.getSymptomDegree());
        additionalInfo.changeManagement(request);
        member.setAdditional_info(additionalInfo);
        memberService.save(member);
        return new AdditionalInfoResponseDto(additionalInfo);
    }

    @Operation(summary = "Retrieve post which is bookmarked", description = "북마크된 게시글 조회")
    @GetMapping("/members/{memberId}/bookmarks")
    public MemberBookmarkNewslettersDto showBookmarkByTagType(@PathVariable("memberId") Long memberId
            , @RequestParam(value = "tag",defaultValue = "all") String code
            , @RequestParam(value = "page",defaultValue = "1") Integer page){
        code=code.toUpperCase();
        TagType tagType = TagType.valueOf(code);
        Member member = memberService.findMemberForBookMark(memberId);
        PageRequest pageRequest = PageRequest.of(page-1,10, Sort.by(Sort.Direction.DESC,"createAt"));

        MemberBookmarkNewslettersDto memberBookmarkNewslettersDto;
        memberBookmarkNewslettersDto = new MemberBookmarkNewslettersDto(member,pageRequest, tagType);
        if(tagType==tagType.ALL) memberBookmarkNewslettersDto = new MemberBookmarkNewslettersDto(member,pageRequest);
        return memberBookmarkNewslettersDto;
    }

    @Operation(summary = "get scrap posts", description = "내가 스크랩한 게시글")
    @GetMapping("/members/{memberId}/scraps")
    public MemberScrapedPostDto showScrap(@PathVariable("memberId") Long memberId
            ,@RequestParam(value = "type",defaultValue = "all")String code
            ,@RequestParam(value = "page",defaultValue = "1")Integer page ){

        code=code.toUpperCase();
        BoardType boardType = BoardType.valueOf(code);
        Member member = memberService.findMemberForScrap(memberId);
        PageRequest pageRequest = PageRequest.of(page-1,10, Sort.by(Sort.Direction.DESC,"createAt"));

        MemberScrapedPostDto memberScrapedPostDto;
        memberScrapedPostDto = new MemberScrapedPostDto(member,pageRequest, boardType);
        if(boardType==BoardType.ALL) memberScrapedPostDto = new MemberScrapedPostDto(member,pageRequest);
        return memberScrapedPostDto;
    }


    @Operation(summary = "get owned posts", description = "작성글 조회")
    @GetMapping("/members/{memberId}/owned-posts")
    public ResultForSearch showOwnedPosts(@PathVariable("memberId")Long memberId,
                                          @RequestParam(value = "page",defaultValue = "1")Integer page){

        Member member = memberService.findById(memberId);
        Page<Post> postPage = postService.findMemberOwnedPosts(member,page);
        List<PostRetrieveDto> posts = postPage.stream()
                .map(p->new PostRetrieveDto(p))
                .collect(Collectors.toList());
        int lastPageNum = postPage.getTotalPages();
        int current = page;
        int min = 1+current/10*10;
        int max =10+current/10*10;
        if(max>=lastPageNum) max = lastPageNum;
        long totalNum = postPage.getTotalElements();
        return new ResultForSearch(posts,min,max,current,totalNum);
    }

    @Operation(summary = "delete member", description = "회원 탈퇴")
    @PatchMapping("/members/{memberId}/withdrawal")
    public HttpStatus deleteMember(@PathVariable("memberId")Long memberId,@RequestBody @Valid WithdrawlRequest request){
        JwtMemberDetails principal = (JwtMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email =  principal.getUsername();
        Member member = memberService.findByEmail(email);

        if(member.getId()!=memberId) throw new RestException(CommonErrorCode.NOT_AUTHORIZATED);
        memberService.deleteMember(member,request.getWithdrawal());

        return  HttpStatus.NO_CONTENT;
    }
}

