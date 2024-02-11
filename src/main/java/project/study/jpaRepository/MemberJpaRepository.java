package project.study.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.study.domain.Member;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    boolean existsByMemberNickname(String nickName);


    /***
     * SELECT * FROM MEMBER M
     * JOIN PHONE P ON (M.MEMBER_ID = P.MEMBER_ID)
     * JOIN BASIC B ON (M.MEMBER_ID = B.MEMBER_ID)
     * JOIN SOCIAL S ON (M.MEMBER_ID = S.MEMBER_ID)
     * WHERE M.MEMBER_NAME = '1' AND P.PHONE = '2'
     */
    Optional<Member> findByMemberNameAndMemberPhone(String name, String phone);
}
