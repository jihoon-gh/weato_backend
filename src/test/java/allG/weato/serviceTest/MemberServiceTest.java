package allG.weato.serviceTest;

import allG.weato.domains.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
@SpringBootTest
public class MemberServiceTest {

    @Autowired private MemberService memberService;

    @PersistenceContext
    EntityManager em;

    //멤버는 근데 세션 멤버로 일케 들어가는데 이거 굳이,,?


}
