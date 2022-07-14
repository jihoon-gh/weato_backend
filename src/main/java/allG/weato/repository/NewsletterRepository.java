package allG.weato.repository;

import allG.weato.domain.Newsletter;
import allG.weato.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsletterRepository  extends JpaRepository<Newsletter,Long> {

    public List<Newsletter> findNewslettersByTag(Tag tag);



}
