package project.study.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.study.domain.Member;

import java.util.Optional;


public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    boolean existsByMemberNickname(String nickName);
    Optional<Member> findByMemberNameAndPhone(String name, String phone);
    Optional<Member> findByMemberNickname(String nickname);
}
