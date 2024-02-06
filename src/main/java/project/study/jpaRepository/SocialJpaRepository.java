package project.study.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.study.domain.Social;

public interface SocialJpaRepository extends JpaRepository<Social, Long> {
}
