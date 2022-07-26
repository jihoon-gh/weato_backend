package allG.weato.domains.member.entities;

import allG.weato.domains.comment.entities.Comment;
import allG.weato.domains.comment.entities.CommentLike;
import allG.weato.domains.enums.ProviderType;
import allG.weato.domains.enums.Role;
import allG.weato.domains.post.entities.Post;
import allG.weato.domains.post.entities.PostLike;
import allG.weato.domains.post.entities.Scrap;
import lombok.Builder;
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

    private String userId;

    private String email;

    private String password;

    private Boolean additional_info_checker;

    private Boolean receiveChecker;

    private String newsletterEmail;

    private LocalDateTime createAt;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    private String birthyear;

    private String gender;

    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    @OneToMany(mappedBy = "member")
    private List<Post> postList = new ArrayList<>();
    //컬렉션은 필드에서 초기화를 하자. 이게 null 문제에서 안전하다.
    //hibernate가 entity를 persist 하는 순간 변환되니까.

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<PostLike> postLikeList =new ArrayList<>();

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<CommentLike> commentLikeList = new ArrayList<>();


    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "book_mark_id")
    private BookMark bookMark;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="profile_id")
    private Profile profile;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="level_id")
    private Level level;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "additional_info_id")
    private AdditionalInfo additionalInfo;

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<Tag> tags = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "scrap_id")
    private Scrap scrap;

    //NoArgsConstructor
    public Member(){ initMember();}

    //Constructor with Args
    @Builder
    public Member(String name, String email,String gender,String birthyear, Role role,ProviderType providerType){
        initMember();
        this.name=name;
        this.email=email;
        this.role=role;
        this.gender=gender;
        this.birthyear=birthyear;
        this.providerType=providerType;
    }
    public Member update(String name)
    {
        this.name = name;
        return this;
    }
    public void changeName(String name){
        this.name=name;
    }


    //init function with constructor of OneToOne things
    public void initMember(){
        Level level = new Level();
        BookMark bookMark = new BookMark();
        this.bookMark=bookMark;
        this.level=level;
    }

//    public String getRole(Role role){
//        return role.getGrantedAuthority();
//    }

    public void changePassword(String password){
        this.password=password;
    }

    public void changeNewsletterEmail(String newsletterEmail){
        this.newsletterEmail=newsletterEmail;
    }

    public void setAdditional_info(AdditionalInfo additionalInfo){
        this.additionalInfo = additionalInfo;
        additional_info_checker=true;
    }

    public void setProfile(Profile profile){
        this.profile=profile;
    }

    public void addComment(Comment comment){
        commentList.add(comment);
        comment.setMember(this);
        level.addExp(3);
    }

    public void addCommentLike(CommentLike commentLike){
        commentLikeList.add(commentLike);
        commentLike.setOwner(this);
    }

    public void addPostLike(PostLike postLike){
        postLikeList.add(postLike);
        postLike.setOwner(this);
    }

    public void addTag(Tag tag){
        tags.add(tag);
        tag.setOwner(this);
    }

    public void setBookMark(BookMark bookMark){
        this.bookMark=bookMark;
    }

    public void deletePostLike(PostLike postLike) {
        postLikeList.remove(postLike);
        postLike.setOwner(null);
    }
    public void deleteCommentLike(CommentLike commentLike) {
        commentLikeList.remove(commentLike);
        commentLike.setOwner(null);
    }

    public void deletePost(Post post) {
       if(postList.contains(post))postList.remove(post);
       else throw new IllegalStateException("작성하지 않은 게시글을 삭제할 수 없습니다.");
    }

    public void deleteComment(Comment comment) {
        if(commentList.contains(comment)) commentList.remove(comment);
        else throw new IllegalStateException("작성하지 않은 댓글을 삭제할 수 없습니다.");
    }

    public void changeEmail(String email) {
        this.email=email;
    }

    //연관관계 편의 메소드

}