package allG.weato.domain.anonymousMember;

import allG.weato.domain.anonymousMember.entities.AnonymousMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnonymousMemberRepository extends JpaRepository<AnonymousMember, Long> {
}
