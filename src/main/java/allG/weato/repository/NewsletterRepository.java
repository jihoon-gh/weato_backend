package allG.weato.repository;

import allG.weato.domain.Newsletter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsletterRepository  extends JpaRepository<Newsletter,Long> {


}
