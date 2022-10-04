package allG.weato.domain.sendingletter.entities;

import allG.weato.domain.enums.TagType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Getter
@NoArgsConstructor
public class SendingLetter {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sending_letter_id")
    private Long id;

    private String title;

    private String author;

    @Column(columnDefinition = "TEXT")
    private String content;

    private TagType tagType;

    private LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

    public SendingLetter(String title, String author, String content, TagType tagType){
        this.title=title;
        this.author=author;
        this.content=content;
        this.tagType=tagType;
    }
    public void changeContent(String content){
        this.content=content;
        localDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }

    public void changeTitle(String title){
        this.title=title;
        localDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }

    public void changeLocalDateTimeByNow(){
        localDateTime=LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }

}
