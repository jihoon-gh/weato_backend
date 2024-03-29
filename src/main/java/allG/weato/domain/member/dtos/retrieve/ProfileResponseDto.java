package allG.weato.domain.member.dtos.retrieve;

import allG.weato.domain.enums.ManagementType;
import allG.weato.domain.enums.ProviderType;
import allG.weato.domain.enums.SymptomDegree;
import allG.weato.domain.enums.TagType;
import allG.weato.domain.member.entities.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ProfileResponseDto {

    private String name;
    private String newsletterEmail;
    private String imageUrl;
    private int level;
    private String nickname;
    private String birthyear;
    private String birthday;
    private ProviderType providerType;

    private Boolean isRecurrence;

    private Boolean isFamilyHistory;
    private Integer medicalHistory;
    private SymptomDegree symptomDegree;
    private List<ManagementType> managementTypeList=new ArrayList<>();

    private List<TagType> tags = new ArrayList<>();

    public ProfileResponseDto(Member member){
        name = member.getName();
        newsletterEmail= member.getNewsletterEmail();
        imageUrl=member.getProfile().getImageUrl();
        level=member.getLevel().getLevel();
        nickname=member.getNickname();
        birthyear=member.getBirthyear();
        birthday=member.getBirthday();
        providerType=member.getProviderType();
        isRecurrence=member.getAdditionalInfo().getIsRecurrence();
        isFamilyHistory=member.getAdditionalInfo().getIsFamilyHistory();
        medicalHistory=member.getAdditionalInfo().getMedicalHistory();
        symptomDegree=member.getAdditionalInfo().getSymptomDegree();
        managementTypeList=member.getAdditionalInfo().getManagementTypes();
        tags=member.getTagTypeList();
    }

}
