package project.study.repository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import project.study.authority.admin.dto.*;
import project.study.domain.QJoinRoom;
import project.study.domain.QRoom;
import project.study.enums.MemberStatusEnum;

import java.util.List;

import static project.study.domain.QBasic.*;
import static project.study.domain.QJoinRoom.*;
import static project.study.domain.QMember.*;
import static project.study.domain.QPhone.*;
import static project.study.domain.QRoom.*;
import static project.study.domain.QSocial.*;

@Repository
public class AdminRepository {

    private final JPAQueryFactory queryFactory;

    public AdminRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<AdminMembersDto> findAllByMember() {
        return queryFactory
            .select(new QAdminMembersDto(
                Expressions.stringTemplate("{0}", accountExpression).as("memberAccount"),
                member.memberName,
                member.memberNickname,
                phone1.phone,
                Expressions.stringTemplate("TO_CHAR({0}, {1})", member.memberCreateDate, "YYYY-MM-DD"),
                member.memberNotifyCount,
                social.socialType,
                member.memberStatus))
            .from(member)
            .leftJoin(member.basic, basic)
            .leftJoin(member.phone, phone1)
            .leftJoin(member.social, social)
            .where(member.memberStatus.eq(MemberStatusEnum.정상).or(member.memberStatus.eq(MemberStatusEnum.이용정지)))
            .orderBy(member.memberCreateDate.desc())
            .fetch();
    }

    private StringExpression accountExpression = new CaseBuilder()
        .when(Expressions.stringPath(String.valueOf(basic.account)).isNull())
        .then(social.socialEmail)
        .otherwise(basic.account);

    public List<AdminExpireMembersDto> findAllByExpireMember(){
        return queryFactory
            .select(new QAdminExpireMembersDto(
                Expressions.stringTemplate("{0}", accountExpression).as("memberAccount"),
                member.memberName,
                member.memberNickname,
                phone1.phone,
                Expressions.stringTemplate("TO_CHAR({0}, {1})", member.memberCreateDate, "YYYY-MM-DD"),
                Expressions.stringTemplate("TO_CHAR({0}, {1})", member.memberExpireDate, "YYYY-MM-DD"),
                social.socialType,
                member.memberStatus))
            .from(member)
            .leftJoin(member.basic, basic)
            .leftJoin(member.phone, phone1)
            .leftJoin(member.social, social)
            .where(member.memberStatus.eq(MemberStatusEnum.탈퇴))
            .orderBy(member.memberExpireDate.desc())
            .fetch();
    }

//    public List<AdminRoomsDto> findAllByRoom(){
//        queryFactory
//            .select(new QAdminRoomsDto(
//                room.roomId,
//                room.roomTitle,
//                room.roomLimit,
//                member.memberName,
//                room.r
//
//            ))
//    }

}