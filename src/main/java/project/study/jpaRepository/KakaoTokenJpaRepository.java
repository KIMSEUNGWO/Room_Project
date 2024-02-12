package project.study.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.study.domain.KakaoToken;

public interface KakaoTokenJpaRepository extends JpaRepository<KakaoToken, Long> {
}
