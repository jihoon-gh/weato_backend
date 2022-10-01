package allG.weato.domainTest.post.entities;

import allG.weato.domainTest.member.entities.Member;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Getter
public class Scrap {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scrap_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

    public void initMember(Member member){
        this.member=member;
    }

    public void initPost(Post post){
        this.post=post;
    }

}
