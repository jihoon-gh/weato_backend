package allG.weato.oauth2.exception;

import com.nimbusds.oauth2.sdk.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class OAuthProviderMissMatchException extends RuntimeException{
    public OAuthProviderMissMatchException(String message) {
        super(message);
    }

    public OAuthProviderMissMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public OAuthProviderMissMatchException(Throwable cause) {
        super(cause);
    }

//    @ExceptionHandler(OAuthProviderMissMatchException.class)
//    public ResponseEntity<ErrorResponse> handleOAuthProviderMissMatchException(OAuthProviderMissMatchException e){
//        ErrorResponse errorResponse = new ErrorResponse();
//        HttpStatus status = HttpStatus.BAD_REQUEST;
//        errorResponse.setStatus(status.value());
//        errorResponse.setMessage(e.getMessage());
//
//        return new ResponseEntity<>(errorResponse, status);
//    }
}

