package allG.weato.domain.sendingletter;

import allG.weato.domain.sendingletter.entities.SendingLetter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface SendingLetterRepository extends JpaRepository<SendingLetter,Long> {

    @Query("select sl from SendingLetter sl where sl.localDateTime > :checker")
    Page<SendingLetter> findLettersOfThisWeek(@Param("checker")LocalDateTime checker, Pageable pageable);
}
