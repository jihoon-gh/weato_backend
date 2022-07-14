package allG.weato.domain;

import allG.weato.domain.enums.BoardType;
import allG.weato.domain.enums.TagType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Getter
@NoArgsConstructor
public class Newsletter {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "newsletter_id")
    private Long id;

    private String title; //뉴스레터 제목
    private String content; //html 기반의 무언가..?....
    private LocalDateTime createdAt; //작성일시
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_mark_id")
    private BookMark bookMark;

    @Enumerated(EnumType.STRING)
    private TagType tagType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;




    public Newsletter(String title, String content, TagType tagType){
        this.title=title;
        this.content=content;
        createdAt=LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }

    public void setBookMark(BookMark bookMark) {
        this.bookMark=bookMark;
    }

    public void classifyByTag(Tag tag){
        this.tag=tag;
        tag.addNewsletter(this);
    }












}
