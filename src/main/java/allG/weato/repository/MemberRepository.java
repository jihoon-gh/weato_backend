package allG.weato.repository;

import allG.weato.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> { //타입 ,pk타입.
    List<Member> findByName(String name);

    List<Member> findByEmail(String email);
}
