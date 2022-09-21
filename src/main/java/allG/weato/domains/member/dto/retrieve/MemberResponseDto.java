package allG.weato.domains.member.dto.retrieve;

import allG.weato.domains.member.entities.Member;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberResponseDto {
    private Long id;

    private Integer level;

    private Integer currentExp;

    private Integer requiredExp;
    private String name;
    private String email;
    private String gender;
    private String birthYear;
    private LocalDateTime createdAT;

    private Boolean newMemberChecker ;

    public MemberResponseDto(Member member) {
        id = member.getId();
        name = member.getName();
        level=member.getMemberLevel().getLevel();
        currentExp=member.getMemberLevel().getExp();
        requiredExp=member.getMemberLevel().getRequiredExp();
        email=member.getEmail();
        gender= member.getGender();
        birthYear=member.getBirthyear();
        createdAT=member.getCreateAt();
        if(member.getTagTypeList().isEmpty()){
            newMemberChecker=true;
        }else{
            newMemberChecker=false;
        }
    }
}
