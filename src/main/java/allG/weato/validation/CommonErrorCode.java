package allG.weato.validation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode{

    INVALID_PARAMETER(HttpStatus.BAD_REQUEST,"invalied parmeter"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND,"resource not exist"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"internal server error"),

    NOT_AUTHORIZATED(HttpStatus.NOT_ACCEPTABLE,"no Authorization");
    ;

    private final HttpStatus httpStatus;
    private final String message;


}
