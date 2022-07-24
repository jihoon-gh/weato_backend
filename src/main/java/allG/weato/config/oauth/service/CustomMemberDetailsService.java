package allG.weato.config.oauth.service;

import allG.weato.domains.member.MemberRepository;
import allG.weato.domains.member.entities.Member;
import allG.weato.domains.member.entities.MemberPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.attribute.UserPrincipal;


@Service
@RequiredArgsConstructor
@Transactional
public class CustomMemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUserId(username);
        if(member==null){
            throw new UsernameNotFoundException("Can't find name of member");
        }
        System.out.println("ok");
        return MemberPrincipal.create(member);
    }
}
