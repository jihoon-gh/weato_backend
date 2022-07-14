package allG.weato.validation;

import allG.weato.dto.error.Error500;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RestException extends RuntimeException {
    private final ErrorCode errorCode;
}
