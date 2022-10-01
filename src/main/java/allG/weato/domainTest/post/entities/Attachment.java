package allG.weato.domainTest.post.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Attachment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachment_id")
    private Long id;

    private String imgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    @JsonIgnore
    private Post post;
    

    public Attachment(String imgUrl){ //기본 생성자는 @NoArgsCons.로 생성
        this.imgUrl=imgUrl;
    }
    
    public void setPost(Post post){
        this.post=post;
    }

}
