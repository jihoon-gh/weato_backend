package allG.weato.domainTest.member.dto.create;

import allG.weato.domainTest.enums.TagType;
import allG.weato.domainTest.member.entities.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMemberResponse {

    private Long id ;
    private String birthyear;
    private String newsletterEmail;
    private List<TagType> tagTypes = new ArrayList<>();

    public CreateMemberResponse(Member member){
        id= member.getId();
        birthyear=member.getBirthyear();
        newsletterEmail= member.getNewsletterEmail();
        tagTypes=member.getTagTypeList();
    }
}
