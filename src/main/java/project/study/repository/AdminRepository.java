package project.study.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import project.study.dto.admin.AdminMembersDto;
import project.study.dto.admin.QAdminMembersDto;
import java.util.List;

import static project.study.domain.QBasic.*;
import static project.study.domain.QMember.*;
import static project.study.domain.QPhone.*;
import static project.study.domain.QSocial.*;

@Repository
public class AdminRepository {

    private final JPAQueryFactory queryFactory;

    public AdminRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<AdminMembersDto> memberList() {
        return queryFactory
            .select(new QAdminMembersDto(
                basic.account,
                social.socialEmail,
                member.memberName,
                member.memberNickname,
                phone1.phone,
                member.memberCreateDate,
                member.memberNotifyCount,
                social.socialType,
                member.memberStatus))
            .from(member)
            .leftJoin(member.basic, basic)
            .leftJoin(member.phone, phone1)
            .leftJoin(member.social, social)
            .orderBy(member.memberCreateDate.desc())
            .fetch();
    }
}
