package allG.weato.domain;

import allG.weato.domain.enums.TagType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Tag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TagType tagType;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Tag(TagType tagType){
        this.tagType=tagType;
    }

    public void setOwner(Member member){
        this.member=member;
    }

}
