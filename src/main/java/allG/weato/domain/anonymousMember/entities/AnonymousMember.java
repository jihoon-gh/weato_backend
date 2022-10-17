package allG.weato.domain.anonymousMember.entities;

import allG.weato.domain.anonymousMember.dtos.update.UpdateAnonymousMemberRequest;
import allG.weato.domain.enums.TagType;
import allG.weato.domain.anonymousMember.dtos.create.CreateAnonymousMemberRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class AnonymousMember {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String newsletterEmail;

    @ElementCollection
    private List<TagType> tagTypeList=new ArrayList<>();

    public AnonymousMember(CreateAnonymousMemberRequest requestDto){
        changeNewsletterEmail(requestDto.getNewsletterEmail());
        changeName(requestDto.getName());
        changeTagTypeList(requestDto);
    }
    public void updateAnonymousMember(UpdateAnonymousMemberRequest request){
        if(request.getName()!=null){
            changeName(request.getName());
        }
        if(request.getNewsletterEmail()!=null){
            changeNewsletterEmail(request.getNewsletterEmail());
        }
        tagTypeList.clear();
        if(request.getTagCleaning()) tagTypeList.add(TagType.CLEANING);
        if(request.getTagEnvironment()) tagTypeList.add(TagType.ENVIRONMENT);
        if(request.getTagFood()) tagTypeList.add(TagType.FOOD);
        if(request.getTagDrug()) tagTypeList.add(TagType.DRUG);
        if(request.getTagSleep()) tagTypeList.add(TagType.SLEEP);
        if(request.getOtherwise()) tagTypeList.add(TagType.OTHERWISE);

    }

    public void changeName(String name){
        this.name = name;
    }
    public void changeNewsletterEmail(String newsletterEmail){
        this.newsletterEmail = newsletterEmail;
    }
    public void changeTagTypeList(CreateAnonymousMemberRequest request){
        tagTypeList.clear();
        if(request.getTagCleaning()) tagTypeList.add(TagType.CLEANING);
        if(request.getTagEnvironment()) tagTypeList.add(TagType.ENVIRONMENT);
        if(request.getTagFood()) tagTypeList.add(TagType.FOOD);
        if(request.getTagDrug()) tagTypeList.add(TagType.DRUG);
        if(request.getTagSleep()) tagTypeList.add(TagType.SLEEP);
        if(request.getOtherwise()) tagTypeList.add(TagType.OTHERWISE);
    }
}
