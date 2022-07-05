package allG.weato.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

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


    public Newsletter(String title, String content){
        this.title=title;
        this.content=content;
        createdAt=LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }

    public void setBookMark(BookMark bookMark) {
        this.bookMark=bookMark;
    }












}
