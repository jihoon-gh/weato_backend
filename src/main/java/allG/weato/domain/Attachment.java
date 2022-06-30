package allG.weato.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Attachment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachment_id")
    private Long id;

    private String imgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    private Post post;

    public void setPost(Post post){
        this.post=post;
        post.getAttachmentList().add(this);
    }

}
