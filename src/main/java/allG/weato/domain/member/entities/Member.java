package allG.weato.domain.member.entities;

import allG.weato.domain.comment.entities.Comment;
import allG.weato.domain.comment.entities.CommentLike;
import allG.weato.domain.enums.ProviderType;
import allG.weato.domain.enums.Role;
import allG.weato.domain.enums.TagType;
import allG.weato.domain.enums.Withdrawal;
import allG.weato.domain.member.dtos.update.UpdateProfileRequestDto;
import allG.weato.domain.newsletter.entities.BookMark;
import allG.weato.domain.newsletter.entities.NewsletterLike;
import allG.weato.domain.post.entities.Post;
import allG.weato.domain.post.entities.PostLike;
import allG.weato.domain.post.entities.Scrap;
import allG.weato.validation.CommonErrorCode;
import allG.weato.validation.RestException;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String userId;
    private String name;

    private String nickname;

    private String email;

    private String password;

    private Boolean additional_info_checker;

    private Boolean receiveChecker;

    private String newsletterEmail;

    private LocalDateTime createAt;

    private Boolean emailValidation=false;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    private String birthyear;

    private String birthday;

    private String gender;

    private Integer authNum;

    private Boolean isWithdrawal;

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

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<NewsletterLike> newsletterLikeList = new ArrayList<>();


    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<BookMark> bookMarkList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="profile_id")
    private Profile profile;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="level_id")
    private Level level;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "additional_info_id")
    private AdditionalInfo additionalInfo;

    @ElementCollection
    private List<TagType> tagTypeList = new ArrayList<>();

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<Scrap> scrapList = new ArrayList<>();

    @ElementCollection
    private Set<Long> newsletterLikeChecker = new HashSet<>();

    @ElementCollection
    private Set<Long> postLikeChecker = new HashSet<>();

    @ElementCollection
    private Set<Long> scrapChecker = new HashSet<>();

    @ElementCollection
    private Set<Long> bookmarkChecker = new HashSet<>();

    @ElementCollection
    private Set<Long> commentLikeChecker = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Withdrawal withdrawalReason;
    public Member(){ initMember();}

    //Constructor with Args
    @Builder
    public Member(String name, String email,String gender,String birthyear, String birthday, Role role,ProviderType providerType){
        this.name=name;
        this.email=email;
        this.role=role;
        this.gender=gender;
        this.birthyear=birthyear;
        this.birthday=birthday;
        this.providerType=providerType;
        this.createAt=LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        this.additional_info_checker=false;
        this.receiveChecker=false;
        initMember();
    }
    public void changeNickname(String nickname)
    {
        this.nickname=nickname;

    }
    public void changeName(String name){
        this.name=name;
    }


    //init function with constructor of OneToOne things
    public void initMember(){
        Level level = new Level();
        initLevel(level);
    }

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

    public void addProfile(Profile profile){
        this.profile=profile;
    }

    public void addPost(Post post){
        postList.add(post);
        post.setOwner(this);
        level.addExp(10);
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

    public void changeTagTypesByUpdate(UpdateProfileRequestDto requestDto){
        tagTypeList.clear();
        if(requestDto.getTagCleaning()) tagTypeList.add(TagType.CLEANING);
        if(requestDto.getTagEnvironment()) tagTypeList.add(TagType.ENVIRONMENT);
        if(requestDto.getTagFood()) tagTypeList.add(TagType.FOOD);
        if(requestDto.getTagDrug()) tagTypeList.add(TagType.DRUG);
        if(requestDto.getTagSleep()) tagTypeList.add(TagType.SLEEP);
        if(requestDto.getOtherwise()) tagTypeList.add(TagType.OTHERWISE);
    }

    public void addBookMark(BookMark bookMark){
        bookMarkList.add(bookMark);
        bookMark.initMember(this);
    }

    public void initLevel(Level level){
        this.level = level;
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
       if(postList.contains(post)) {
           postList.remove(post);
           return ;
       }
       throw new RestException(CommonErrorCode.RESOURCE_NOT_FOUND);
    }

    public void deleteComment(Comment comment) {
        if(commentList.contains(comment)) {
            commentList.remove(comment);
            return ;
        }
        throw new RestException(CommonErrorCode.RESOURCE_NOT_FOUND);
    }

    public void deleteBookMark(BookMark bookMark){
        bookMarkList.remove(bookMark);
        bookMark.initMember(null);
    }

    public void addScrap(Scrap scrap){
        scrapList.add(scrap);
        scrap.initMember(this);
    }

    public void deleteScrap(Scrap scrap){
        scrapList.remove(scrap);
        scrap.initMember(null);
    }

    public void addNewsletterLike(NewsletterLike newsletterLike){
        newsletterLikeList.add(newsletterLike);
        newsletterLike.initMember(this);
    }

    public void deleteNewsletterLike(NewsletterLike newsletterLike){
        newsletterLikeList.remove(newsletterLike);
        newsletterLike.initMember(null);
    }

    public Integer getAuthNum() {
        return authNum;
    }

    public void changeEmailValidation(){
        if(emailValidation==null||emailValidation==false) emailValidation=true;
        else emailValidation=false;
    }

    public void changeAuthNum(int num) {
        authNum=num;
    }

    public void addBookmarkChecker(Long id){
        bookmarkChecker.add(id);
    }
    public void addNewsletterLikeChecker(Long id){
        newsletterLikeChecker.add(id);
    }
    public void addScrapChecker(Long id ){
        scrapChecker.add(id);
    }
    public void addPostLikeChecker(Long id ){
        postLikeChecker.add(id);
    }

    public void addCommentLikeChecker(Long id){
        commentLikeChecker.add(id);
    }
    public void deleteCommentLikeChecker(Long id){
        commentLikeChecker.remove(id);
    }
    public void deleteBookmarkChecker(Long id ){
        bookmarkChecker.remove(id);
    }
    public void deleteNewsletterLikeChecker(Long id){
        newsletterLikeChecker.remove(id);
    }
    public void deleteScrapChecker(Long id ){
        scrapChecker.remove(id);
    }
    public void deletePostLikeChecker(Long id ){
        postLikeChecker.remove(id);
    }

    public void deleteMember(Withdrawal withdrawal){
        isWithdrawal=true;
        withdrawalReason=withdrawal;
        changeNewsletterEmail(null);
    }
    public String getName() {
        if(isWithdrawal==null||isWithdrawal==false){
            return name;
        }
        return "탈퇴회원";
    }

    public String getNickname() {
        if(isWithdrawal==null||isWithdrawal==false){
            return nickname;
        }
        return "탈퇴회원";
    }
}


