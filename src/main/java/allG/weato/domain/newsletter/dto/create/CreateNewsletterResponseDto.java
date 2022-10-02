package allG.weato.domain.newsletter.dto.create;

import allG.weato.domain.newsletter.entities.Newsletter;
import allG.weato.domain.newsletter.dto.retrieve.NewsletterResponseDto;
import org.springframework.http.HttpStatus;

public class CreateNewsletterResponseDto extends NewsletterResponseDto {

    private Long id;

    private HttpStatus httpStatus;
    public CreateNewsletterResponseDto(Newsletter newsletter) {
        id = newsletter.getId();
        httpStatus=HttpStatus.CREATED;
    }
}
