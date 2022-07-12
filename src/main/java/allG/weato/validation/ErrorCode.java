package allG.weato.validation;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String name();
    HttpStatus getHttpStatus();
    String getMessage();

}
