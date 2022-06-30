package allG.weato.service;

import allG.weato.domain.Member;
import allG.weato.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member findById(Long id){
      return memberRepository.findById(id).get();
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
        List<Member> findMembersByEmail = memberRepository.findByEmail(member.getEmail());
        if (findMembersByEmail.size()!=0&&findMembersByName.size()!=0){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

    }
}
