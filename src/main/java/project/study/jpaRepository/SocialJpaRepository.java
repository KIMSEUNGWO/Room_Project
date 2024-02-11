package project.study.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.study.domain.Social;

import java.util.Optional;

public interface SocialJpaRepository extends JpaRepository<Social, Long> {

    Optional<Social> findBySocialTypeAndSocialEmail(String socialType, String socialEmail);
}
