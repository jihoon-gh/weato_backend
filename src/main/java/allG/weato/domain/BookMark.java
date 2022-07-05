package allG.weato.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.parameters.P;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class BookMark {

    //id
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_mark_id")
    private Long id;

    //수량
    private int count;

    @OneToOne(mappedBy = "bookMark",fetch = FetchType.LAZY)
    private Member member;

    @OneToMany(mappedBy = "bookMark")
    private List<Newsletter> newsletterList = new ArrayList<>();

    public void setMember(Member member) {
        this.member = member;
        member.setBookMark(this);
    }

    public void addNewsLetter(Newsletter newsletter){
        newsletterList.add(newsletter);
        newsletter.setBookMark(this);
        member.getLevel().addExp(7);
    }

}
