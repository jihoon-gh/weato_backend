package allG.weato.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postlike_id")
    private Long id;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;


    public void setOwnPost(Post post){
        this.post=post;
    }

    public void setOwner(Member member)
    {
        this.member=member;
    }

}
