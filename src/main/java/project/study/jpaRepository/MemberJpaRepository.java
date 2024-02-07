package project.study.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.study.domain.Member;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    boolean existsByNickname(String nickName);
}
