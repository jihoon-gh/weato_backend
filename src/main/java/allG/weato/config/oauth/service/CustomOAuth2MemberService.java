package allG.weato.config.oauth.service;

import allG.weato.config.oauth2.OAuth2ProviderMissmathchException;
import allG.weato.config.oauth2.OAuth2UserInfo;
import allG.weato.config.oauth2.OAuth2UserInfoFactory;
import allG.weato.domains.enums.ProviderType;
import allG.weato.domains.enums.Role;
import allG.weato.domains.member.MemberRepository;
import allG.weato.domains.member.entities.Member;
import allG.weato.domains.member.entities.MemberPrincipal;
import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.annotations.reflection.internal.XMLContext;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomOAuth2MemberService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);


        try {
            return this.process(userRequest,user);
        }catch (AuthenticationException ex){
            throw ex;
        }catch (Exception ex){
            ex.printStackTrace();
            throw new InternalAuthenticationServiceException(ex.getMessage(),ex.getCause());
        }
    }

    private OAuth2User process(OAuth2UserRequest request, OAuth2User user){
        ProviderType providerType = ProviderType.valueOf(request.getClientRegistration().getRegistrationId().toUpperCase());
        System.out.println("providerType.name() = " + providerType.name());
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType,user.getAttributes());

        Member savedMember = memberRepository.findByUserId(userInfo.getId());

        if(savedMember!=null){
            if(providerType!=savedMember.getProviderType()){
                throw new OAuth2ProviderMissmathchException("Looks like you're signed up with " + providerType +
                        " account. Please use your " + savedMember.getProviderType() + " account to login.");
            }
            updateMember(savedMember,userInfo);
        }else {
            savedMember=createMember(userInfo,providerType);
        }

        return MemberPrincipal.create(savedMember, user.getAttributes());
    }

    private Member createMember(OAuth2UserInfo userInfo, ProviderType providerType){

        System.out.println("userInfo.getId() = " + userInfo.getId());
        Member member = new Member(
                userInfo.getName(),
                userInfo.getEmail(),
                userInfo.getGender(),
                userInfo.getBirthyear(),
                Role.USER,
                providerType
        );

        return memberRepository.save(member);
    }

    private Member updateMember(Member member, OAuth2UserInfo userInfo){
        if (userInfo.getName() != null && !member.getName().equals(userInfo.getName())) {
            member.changeName(userInfo.getName());
        }
        return memberRepository.save(member);
    }
}
