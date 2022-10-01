package allG.weato.domainTest.member;

import allG.weato.domainTest.member.entities.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> { //타입 ,pk타입.
    List<Member> findByName(String name);

    Optional<Member> findByEmail(String Email);
    Member findMemberByEmail(String Email);

    @Query("select m from Member m left join fetch m.bookMarkList where m.id = :id")
    Member findMemberByIdWithBookmark(@Param("id")Long id);

    @Query("select m from Member m left join fetch m.scrapList where m.id = :id")
    Member findMemberByIdWithScrap(@Param("id") Long id);

    @EntityGraph(attributePaths = {"profile","additionalInfo"})
    @Query("select m from Member m where m.id = :id")
    Member findMemberByIdForProfile(@Param("id") Long id);

    Member findByUserId(String userId);

    Member findMemberByNickname(String nickname);

//    @Query(name = "select m from Member m")
//    List<Member> findAllByEmail();



}
