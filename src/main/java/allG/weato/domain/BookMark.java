package allG.weato.domain;

import lombok.Getter;
import lombok.Setter;

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

    @OneToMany(mappedBy = "bookMark")
    private List<Post> postList = new ArrayList<>();

    @OneToOne(mappedBy = "bookMark",fetch = FetchType.LAZY)
    private Member member;

    public void setMember(Member member) {
        this.member = member;
        member.setBookMark(this);
    }

}
