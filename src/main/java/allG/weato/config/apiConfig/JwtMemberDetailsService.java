package allG.weato.config.apiConfig;

import allG.weato.domain.Member;
import allG.weato.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JwtMemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member entityMember = memberRepository.findByEmail(username).get();
        if(entityMember==null) throw new UsernameNotFoundException("Could not find Member with email : " + username);
        else return new JwtMemberDetails(entityMember);

    }
}
