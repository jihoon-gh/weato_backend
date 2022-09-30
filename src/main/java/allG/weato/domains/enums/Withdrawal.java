package allG.weato.domains.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Withdrawal {

    NUM1("이미 다 알고 있는 정보들이에요."),
    NUM2("좀 더 전문적인 사람의 이야기를 듣고 싶어요"),
    NUM3("증상 개선에 도움이 되지 않는 거 같아요"),
    NUM4("필요한 서비스는 아닌 것 같아요"),
    NUM5("커뮤니티의 다른 사람들이 불쾌하게 행동해요");

    private String reason;
}
