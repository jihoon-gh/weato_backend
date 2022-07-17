package allG.weato.service;

import allG.weato.config.auth.dto.SessionMember;
import allG.weato.domain.Comment;
import allG.weato.domain.Member;
import allG.weato.domain.Post;
import allG.weato.domain.PostLike;
import allG.weato.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member findById(Long id){
      return memberRepository.findById(id).get();
    }

    public Member findByEmail(String email){
      Optional<Member> member  = memberRepository.findByEmail(email);
      Member findMember = member.get();
      return findMember;

    }

    public List<Member> findAll(){
        return memberRepository.findAll();
    }

    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    public void validateDuplicateMember(Member member){
        List<Member> findMembersByName = memberRepository.findByName(member.getName());
//        List<Member> findMembersByEmail = memberRepository.findAllByEmail();
        if (findMembersByName.size()!=0){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

    }

    @Transactional
    public void addComment(Member member, Comment comment) {
        member.addComment(comment);
    }

}
