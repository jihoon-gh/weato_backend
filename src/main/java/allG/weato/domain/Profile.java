package allG.weato.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Profile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="profile_id")
    private Long id;

    private String introduction;

    private String imgUrl;


}
