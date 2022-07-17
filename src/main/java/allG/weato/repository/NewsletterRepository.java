package allG.weato.repository;

import allG.weato.domain.Newsletter;
import allG.weato.domain.Tag;
import allG.weato.domain.enums.TagType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NewsletterRepository  extends JpaRepository<Newsletter,Long> {

//    @Query("select n from Newsletter n where n.tagType = :tagtype")
    public List<Newsletter> findNewslettersByTagType(TagType tagType);

    Page<Newsletter> findNewslettersByTagType(TagType tagType, Pageable pageable);

    Page<Newsletter> findAll(Pageable pageable);



}
