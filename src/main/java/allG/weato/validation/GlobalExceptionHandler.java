package allG.weato.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RestException.class)
    public ResponseEntity<Object> handleCustomException(RestException e) {
        ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }





    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus()).body(makeErrorResponse(errorCode));
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode){
        return ErrorResponse.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .build();
    }


}
