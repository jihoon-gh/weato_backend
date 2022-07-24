package allG.weato.domains.member;

import allG.weato.domains.member.entities.MemberRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRefreshTokenRepository extends JpaRepository<MemberRefreshToken,Long> {

    MemberRefreshToken findByUserId(String userId);
    MemberRefreshToken findByUserIdAndRefreshToken(String userId, String refreshToken);


}
