package allG.weato.domain;


import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class CommentLike {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public void setOwnComment(Comment comment) {
        this.comment=comment;
    }

    public void setOwner(Member member){
        this.member=member;
    }
}
