package allG.weato.domain.member.dtos;

import allG.weato.domain.enums.ManagementType;
import allG.weato.domain.enums.SymptomDegree;
import allG.weato.domain.member.entities.AdditionalInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class AdditionalInfoResponseDto {
    private int medicalHistory;
    private Boolean familyHistory;
    private Boolean recurrence;
    private SymptomDegree symptomDegree;
    private List<ManagementType> managementTypes = new ArrayList<>();

    public AdditionalInfoResponseDto(AdditionalInfo additionalInfo){
        medicalHistory=additionalInfo.getMedicalHistory();
        familyHistory=additionalInfo.getIsFamilyHistory();
        recurrence=additionalInfo.getIsRecurrence();
        symptomDegree=additionalInfo.getSymptomDegree();
        managementTypes=additionalInfo.getManagementTypes();
    }
}
