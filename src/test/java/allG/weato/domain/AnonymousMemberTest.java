package allG.weato.domain;

import allG.weato.domain.anonymousMember.entities.AnonymousMember;
import allG.weato.domain.anonymousMember.dtos.create.CreateAnonymousMemberRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AnonymousMemberTest {

    @DisplayName("이름 변경 테스트")
    @Test
    public void changeNameTest(){

        //given
        AnonymousMember am = new AnonymousMember();
        String name = "Test";

        //when
        am.changeName(name);

        //then
        assertThat(am.getName()).isEqualTo("Test");
    }

    @DisplayName("뉴스레터 수신 메일 변경 테스트")
    @Test
    public void changeNewsletterEmail(){

        //given
        AnonymousMember am = new AnonymousMember();
        String email = "test";

        //when
        am.changeNewsletterEmail(email);

        //then
        assertThat(am.getNewsletterEmail()).isEqualTo("test");
    }

    @DisplayName("dto를 이용한 생성자 작동 테스트 ")
    @Test
    public void createUserTest(){

        //given
        CreateAnonymousMemberRequest request = new CreateAnonymousMemberRequest("test","test",true,true,true,false,false,false);

        //when
        AnonymousMember am = new AnonymousMember(request);

        //then
        assertThat(am.getNewsletterEmail()).isEqualTo("test");
        assertThat(am.getName()).isEqualTo("test");
        assertThat(am.getTagTypeList().size()).isEqualTo(3);
    }
}
