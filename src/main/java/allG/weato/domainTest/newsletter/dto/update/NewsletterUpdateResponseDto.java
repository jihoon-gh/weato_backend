package allG.weato.domainTest.newsletter.dto.update;

import allG.weato.domainTest.newsletter.entities.Newsletter;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class NewsletterUpdateResponseDto {

    private Long id;
    private HttpStatus httpStatus;
    public NewsletterUpdateResponseDto(Newsletter newsletter)
    {
        id=newsletter.getId();
        httpStatus=HttpStatus.ACCEPTED;
    }
}
