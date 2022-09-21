package allG.weato.domains.member.entities;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class MemberLevel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "level_id")
    private Long id;

    private int level;

    private int exp;

    public MemberLevel(){
        level=1;
        exp=0;
    }
    public void levelUp(){
        level++;
    }
    public void addExp(int exp) {
        this.exp += exp;
        levelChecker();
    }

    public void levelChecker(){
        if(level==1&&exp>=25){
            addExp(-25);
            levelUp();
        }
        if(level==2&&exp>=80){
            addExp(-80);
            levelUp();
        }
        if(level==3&&exp>=150){
            addExp(-150);
            levelUp();
        }
    }
    public int getRequiredExp(){
        int requiredExp=0;
        if(level==1) {
            requiredExp=25;
        }
        if(level==2) {
            requiredExp=80;
        }
        if(level==3){
            requiredExp=150;
        }
        return requiredExp;
    }


}

