package allG.weato.repository;

import allG.weato.domain.Member;
import org.hibernate.annotations.NamedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> { //타입 ,pk타입.
    List<Member> findByName(String name);

    Optional<Member> findByEmail(String email);

//    @Query(name = "select m from Member m")
//    List<Member> findAllByEmail();



}
