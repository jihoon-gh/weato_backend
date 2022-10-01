package allG.weato.domainTest.member.entities;

import allG.weato.domainTest.enums.ManagementType;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@Getter
public class Management {

   List<ManagementType> managementTypes = new ArrayList<>();

}