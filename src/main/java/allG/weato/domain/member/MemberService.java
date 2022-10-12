package allG.weato.domain.member;

import allG.weato.domain.comment.entities.Comment;
import allG.weato.domain.enums.TagType;
import allG.weato.domain.enums.Withdrawal;
import allG.weato.domain.member.dto.create.CreateMemberRequest;
import allG.weato.domain.member.dto.update.UpdateProfileRequestDto;
import allG.weato.domain.member.entities.Member;
import allG.weato.validation.CommonErrorCode;
import allG.weato.validation.RestException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member findById(Long id){
      return memberRepository.findById(id).orElseThrow(()->new RestException(CommonErrorCode.RESOURCE_NOT_FOUND));
    }

    public Member findMemberForScrap(Long id){
        return memberRepository.findMemberByIdWithScrap(id);
    }

    public Member findMemberForBookMark(Long id){
        return memberRepository.findMemberByIdWithBookmark(id);
    }
    public Member findMemberForProfile(Long id){
        return memberRepository.findMemberByIdForProfile(id);
    }

    public Member findByEmail(String email){
      return memberRepository.findByEmail(email).orElseThrow(()-> new RestException(CommonErrorCode.RESOURCE_NOT_FOUND));
    }

    @Transactional
    public void save(Member member){
        memberRepository.save(member);
    }

    public boolean validateNickname(String nickname){
        Member member = memberRepository.findMemberByNickname(nickname);
        if(member==null){
            return false; //중복이 없다 -> FALSE
        }
        return true; //중복이 존재환다 -> TRUE
    }

    @Transactional
    public void addComment(Member member, Comment comment) {
        member.addComment(comment);
    }

    @Transactional
    public void createMemberDetail(Member member, CreateMemberRequest request) {
        member.changeNickname(request.getNickname());
        member.changeNewsletterEmail(request.getNewsletterEmail());
        if(!member.getTagTypeList().isEmpty()) member.getTagTypeList().clear();
        if(request.getDrug()) member.getTagTypeList().add(TagType.DRUG);
        if(request.getCleaning()) member.getTagTypeList().add(TagType.CLEANING);
        if(request.getFood()) member.getTagTypeList().add(TagType.FOOD);
        if(request.getEnvironment()) member.getTagTypeList().add(TagType.ENVIRONMENT);
        if(request.getSleep()) member.getTagTypeList().add(TagType.SLEEP);
        if(request.getEtc()) member.getTagTypeList().add(TagType.OTHERWISE);
        memberRepository.save(member);
    }

    @Transactional
    public void updateProfile(Member member, UpdateProfileRequestDto request) {
        member.getProfile().changeImgurl(request.getImageUrl());
        member.changeNickname(request.getNickname());
        member.getAdditionalInfo().setMedicalHistory(request.getMedicalHistory());
        member.getAdditionalInfo().setIsFamilyHistory(request.getIsFamilyHistory());
        member.getAdditionalInfo().setIsRecurrence(request.getIsRecurrence());
        member.getAdditionalInfo().setSymptomDegree(request.getSymptomDegree());
        member.getAdditionalInfo().changeManagement(request);
        member.changeTagTypesByUpdate(request);
        memberRepository.save(member);
    }


    @Transactional
    public void validateEmail(Member member) {
        member.changeEmailValidation();
        int newNum =  (int)(Math.random() * (99999 - 10000 + 1)) + 10000;
        member.changeAuthNum(newNum);
    }

    @Transactional
    public void emailValidation(@NotNull Member member, int num) {
        member.changeAuthNum(num);
    }

    @Transactional
    public void deleteMember(Member member, Withdrawal withdrawal) {
        member.deleteMember(withdrawal);
    }
}
