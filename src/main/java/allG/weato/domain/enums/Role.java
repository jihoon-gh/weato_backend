package allG.weato.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

@Getter
@RequiredArgsConstructor
public enum Role {
    GUEST("ROLE_GUEST","손님"),
    MEMBER("ROLE_MEMBER","회원"),

    ADMIN("ROLE_MEMBER","관리자");

    private final String key;
    private final String title;
}
