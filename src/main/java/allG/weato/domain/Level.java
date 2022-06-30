package allG.weato.domain;

import lombok.Getter;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Level {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "level_id")
    private Long id;

    private int level;

    private int exp;


    public void levelUp(){
        level++;
    }
    public void expPlus(int exp) {
        this.exp += exp;
    }


}

