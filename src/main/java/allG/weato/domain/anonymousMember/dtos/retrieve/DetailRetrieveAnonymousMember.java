package allG.weato.domain.anonymousMember.dtos.retrieve;

import allG.weato.domain.anonymousMember.entities.AnonymousMember;
import allG.weato.domain.enums.TagType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DetailRetrieveAnonymousMember {

    private Long id;
    private String name;
    private String newsletterEmail;
    private List<TagType> tagTypes;

    public DetailRetrieveAnonymousMember(AnonymousMember am){
        id = am.getId();
        name = am.getName();
        newsletterEmail = am.getNewsletterEmail();
        tagTypes = am.getTagTypeList();
    }

}
