package allG.weato.config.apiConfig;

import allG.weato.domain.Member;
import allG.weato.domain.enums.Role;
import allG.weato.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2MemberService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        try{
            return process(userRequest,user);
        } catch(AuthenticationException ex){
            throw new OAuth2AuthenticationException(ex.getMessage());
        } catch (Exception ex){
            throw new InternalAuthenticationServiceException(ex.getMessage(),ex.getCause());
        }
    }

    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user){
        ProviderType providerType = ProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());
        OAuth2MemberInfo memberInfo = OAuth2MemberInfoFactory.getOAuth2MemberInfo(providerType,user.getAttributes());

        Member savedMember = memberRepository.findByEmail(memberInfo.getEmail()).get();
        if(savedMember!=null){
            if(providerType!=savedMember.getProviderType()){
                throw new IllegalStateException("잘못된 접근입니다.");
            }
            updateMember(savedMember, memberInfo);
        }
        else{
            savedMember=createMember(memberInfo,providerType);
        }
        return new JwtMemberDetails(savedMember,user.getAttributes());
    }

    //네이버에서 넘어온 사용자 정보를 통한 회원가입(Member 생성)
    private Member createMember(OAuth2MemberInfo memberInfo, ProviderType providerType){
        Member member = new Member(
                memberInfo.getName(),
                memberInfo.getEmail(),
                memberInfo.getGender(),
                memberInfo.getBirthyear(),
                Role.MEMBER);
        member.setProviderType(providerType);

        return memberRepository.save(member);
    }

    private Member updateMember(Member member, OAuth2MemberInfo memberInfo){
        if(memberInfo.getName()!=null && !member.getName().equals(memberInfo.getName())) member.changeName(memberInfo.getName());
        if(memberInfo.getEmail()!=null&& !member.getEmail().equals(memberInfo.getEmail())) member.changeEmail(memberInfo.getEmail());

        return member;
    }


}

