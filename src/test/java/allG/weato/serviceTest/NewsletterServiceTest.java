package allG.weato.serviceTest;

import allG.weato.domain.Newsletter;
import allG.weato.dto.newsletter.NewsletterUpdateRequestDto;
import allG.weato.repository.NewsletterRepository;
import allG.weato.service.NewsletterService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class NewsletterServiceTest {
    @Autowired private NewsletterRepository newsletterRepository;
    @Autowired private NewsletterService newsletterService;


    @Test
    public void findOneTest(){
        //given
        Newsletter newsletter1 = new Newsletter();
        newsletter1.changeContent("this is for test");
        newsletterService.save(newsletter1);
        //when
        Long id = newsletter1.getId();
        System.out.println("newsletter.id = " + id);
        Newsletter findOne = newsletterService.findOneById(id);
        System.out.println("findOne.id = " + findOne.getId());
        //then
        assertThat(findOne.getContent()).isEqualTo(newsletter1.getContent());
    }

    @Test
    public void updateTest(){
        //given
        Newsletter newsletter1 = new Newsletter();
        newsletter1.changeTitle("asdgasdasdfasg");
        newsletterService.save(newsletter1);

        //when
        NewsletterUpdateRequestDto request = new NewsletterUpdateRequestDto();
        request.setUpdatedTitle("this is for update");
        request.setUpdatedContent("updated content");
        newsletterService.updateNewsletter(newsletter1,request);

        //then
        assertThat(newsletter1.getTitle()).isEqualTo("this is for update");
        assertThat(newsletter1.getContent()).isNotEqualTo(null);

    }

    @Test
    public void deleteTest(){
        //given
        Newsletter newsletter1 = new Newsletter();
        newsletterService.save(newsletter1);

        //when
        Long id = newsletter1.getId();
        newsletterService.deleteNewsletter(newsletter1);
        //then
        assertThat(newsletterService.findOneById(id)).isNull();
    }



}
