package allG.weato.domains.newsletter.dto.create;

import allG.weato.domains.newsletter.entities.Newsletter;
import allG.weato.domains.newsletter.dto.retrieve.NewsletterResponseDto;
import org.springframework.http.HttpStatus;

public class CreateNewsletterResponseDto extends NewsletterResponseDto {

    private Long id;

    private HttpStatus httpStatus;
    public CreateNewsletterResponseDto(Newsletter newsletter) {
        id = newsletter.getId();
        httpStatus=HttpStatus.CREATED;
    }
}
