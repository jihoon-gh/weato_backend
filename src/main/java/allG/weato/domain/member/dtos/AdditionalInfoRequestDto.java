package allG.weato.domain.member.dtos;

import allG.weato.domain.enums.SymptomDegree;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdditionalInfoRequestDto {
    private int years;
    private Boolean recurrence;
    private Boolean familyHistory;

    private Boolean moisturizer;
    private Boolean steroid;
    private Boolean diet;
    private Boolean drug;
    private Boolean cleaning;
    private Boolean ointment;
    private Boolean laser;
    private Boolean orientalMedicine;
    private Boolean etc;

    private SymptomDegree symptomDegree;

}
