package allG.weato.domain.member.entities;

import allG.weato.domain.enums.ManagementType;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@Getter
public class Management {

   List<ManagementType> managementTypes = new ArrayList<>();

}
