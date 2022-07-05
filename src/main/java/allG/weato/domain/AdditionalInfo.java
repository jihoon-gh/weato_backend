package allG.weato.domain;

import allG.weato.domain.enums.SymptomDegree;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class AdditionalInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "additional_info_id")
    private Long id;

    private Boolean isRecurrence;

    private Boolean isFamilyHistory;

    private SymptomDegree symptomDegree;


}
