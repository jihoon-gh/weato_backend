package allG.weato.domainTest.newsletter.dto.create;

import allG.weato.domainTest.newsletter.entities.Newsletter;
import allG.weato.domainTest.newsletter.dto.retrieve.NewsletterResponseDto;
import org.springframework.http.HttpStatus;

public class CreateNewsletterResponseDto extends NewsletterResponseDto {

    private Long id;

    private HttpStatus httpStatus;
    public CreateNewsletterResponseDto(Newsletter newsletter) {
        id = newsletter.getId();
        httpStatus=HttpStatus.CREATED;
    }
}
