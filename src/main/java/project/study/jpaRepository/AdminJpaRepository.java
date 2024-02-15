package project.study.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.study.domain.Basic;
import project.study.domain.Member;

import java.util.List;

public interface AdminJpaRepository extends JpaRepository<Member, Long> {
}
