package allG.weato.domain;


import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long Id;

    private String name;

    private String email;

    private String password;

    private Boolean additional_info;

    private Boolean receiveChecker;

    private String newsletterEmail;

    private LocalDateTime createAt;

    @OneToMany(mappedBy = "member")
    private List<Post> postList = new ArrayList<>();
    //컬렉션은 필드에서 초기화를 하자. 이게 null 문제에서 안전하다.
    //hibernate가 entity를 persist 하는 순간 변환되니까.

    @OneToMany(mappedBy = "member")
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<Like> likeList=new ArrayList<>();


    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "book_mark_id")
    private BookMark bookMark;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="profile_id")
    private Profile profile;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="level_id")
    private Level level;

    public Member(){}

    public Member(String name, String email, String password,LocalDateTime createAt){
        this.name=name;
        this.email=email;
        this.password=password;
        this.createAt = createAt;
    }

    public void changePassword(String password){
        this.password=password;
    }

    public void changeReceivingStatus(){
        if(receiveChecker){
            receiveChecker=false;
            newsletterEmail=null;
        }
        else {
            receiveChecker=true;
            this.changeNewsletterEmail(this.email);
            //으악
        }
    }
    public void changeNewsletterEmail(String newsletterEmail){
        this.newsletterEmail=newsletterEmail;
    }

}
