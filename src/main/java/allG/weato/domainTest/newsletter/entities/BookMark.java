package allG.weato.domainTest.newsletter.entities;

import allG.weato.domainTest.member.entities.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Getter
@NoArgsConstructor
public class BookMark {

    //id
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_mark_id")
    private Long id;


    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "newsletter_id")
    private Newsletter newsletter;

    private LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

    public void initMember(Member member){
        this.member=member;
    }

    public void initNewsletter(Newsletter newsletter){
        this.newsletter=newsletter;
    }

}
