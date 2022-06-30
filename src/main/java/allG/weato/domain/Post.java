package allG.weato.domain;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Post {

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="post_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    private LocalDateTime createAt;

    @NotNull
    private String content;


    int likeCount ;
    @ManyToOne(fetch = FetchType.LAZY) //멤버 - 게시글 외래키의 주인 > post
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL) //persist의 전파.
    private List<Comment> commentList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY) //북마크 - 게시글 외래키의 주인 -> post
    @JoinColumn(name = "book_mark_id")
    private BookMark bookMark;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<Attachment> attachmentList = new ArrayList<>();

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<Like> likeList = new ArrayList<>();

    //연관관계 메소드
    public void setMember(Member member){
        this.member=member;
        member.getPostList().add(this);
    }

    public void setBookMark(BookMark bookMark){
        this.bookMark=bookMark;
        bookMark.getPostList().add(this);
    }


}
