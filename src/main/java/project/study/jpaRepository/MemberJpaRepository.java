package project.study.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.study.domain.Member;


public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    boolean existsByMemberNickname(String nickName);

//    @Query("SELECT m FROM MEMBER m " +
//            "JOIN PHONE p ON m.MEMBER_ID = p.MEMBER_ID " +
//            "JOIN BASIC b ON m.MEMBER_ID = b.MEMBER_ID " +
//            "JOIN SOCIAL s ON m.MEMBER_ID = s.MEMBER_ID " +
//            "WHERE m.MEMBER_NAME = :name AND p.PHONE = :phone")
//    Optional<Member> findByMemberNameAndMemberPhone(@Param("name") String name, @Param("phone") String phone);
}
