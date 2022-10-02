package allG.weato.domain.post.entities;

import allG.weato.domain.member.entities.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
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
