package allG.weato.repositoryTest;

import allG.weato.domain.enums.ProviderType;
import allG.weato.domain.member.MemberRepository;
import allG.weato.domain.member.entities.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("이메일로 회원 찾기")
    @Test
    public void findMemberByEmailTest(){

        //given - 운영자 이메일(back4740@naver.com)을 통한 조회

        //when
        Member member = memberRepository.findMemberByEmail("back4740@naver.com");

        //then
        assertThat(member.getProviderType()).isEqualTo(ProviderType.NAVER);
    }

    @DisplayName("닉네임을 통한 회원 조회 테스트")
    @Transactional
    @Test
    public void findMemberByNicknameTest(){

        //given
        Member member = new Member();
        member.changeNickname("hypeboy");
        memberRepository.save(member);

        //when
        Member findMember = memberRepository.findMemberByNickname("hypeboy");

        //name
        assertThat(findMember).isNotNull();
    }


}
