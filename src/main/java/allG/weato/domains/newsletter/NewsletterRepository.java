package allG.weato.domains.newsletter;

import allG.weato.domains.newsletter.entities.Newsletter;
import allG.weato.domains.enums.TagType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsletterRepository  extends JpaRepository<Newsletter,Long> {


    Page<Newsletter> findNewslettersByTagType(TagType tagType, Pageable pageable);

    Page<Newsletter> findAll(Pageable pageable);



}
