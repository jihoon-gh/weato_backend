package allG.weato.domains.newsletter.dto.update;

import allG.weato.domains.newsletter.entities.Newsletter;
import allG.weato.domains.enums.TagType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.ZoneId;

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
