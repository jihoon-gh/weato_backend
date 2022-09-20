package allG.weato.domains.member.dto.update;

import allG.weato.domains.enums.ManagementType;
import allG.weato.domains.enums.TagType;
import allG.weato.domains.member.entities.Member;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Data
public class UpdateProfileResponseDto {
      private Long id;
      private HttpStatus httpStatus;
//    private String imageUrl;
//    private String nickname;
//
//    private Integer medicalHistory;
//    private Boolean isRecurrence;
//    private Boolean isFamilyHistory;
//
//    private List<ManagementType> managementTypes = new ArrayList<>();
//    private List<TagType> tagTypes = new ArrayList<>();
    public UpdateProfileResponseDto(Member member){
          id= member.getId();
          httpStatus=HttpStatus.OK;
//        imageUrl=member.getProfile().getImageUrl();
//        nickname= member.getNickname();
//        medicalHistory=member.getAdditionalInfo().getMedicalHistory();
//        isRecurrence=member.getAdditionalInfo().getIsRecurrence();
//        isFamilyHistory=member.getAdditionalInfo().getIsFamilyHistory();
//        managementTypes=member.getAdditionalInfo().getManagementTypes();
//        tagTypes=member.getTagTypeList();
    }
}
