package project.study.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import project.study.authority.admin.dto.*;
import project.study.domain.Member;
import project.study.enums.MemberStatusEnum;

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

    public Page<SearchMemberDto> searchMembers(String word, Pageable pageable){

        BooleanBuilder builder = new BooleanBuilder();

        StringPath memberAccount = Expressions.stringPath(String.valueOf(accountExpression));
        StringPath memberName = Expressions.stringPath(String.valueOf(member.memberName));
        StringPath memberNickname = Expressions.stringPath(String.valueOf(member.memberNickname));
        StringPath memberPhone = Expressions.stringPath(String.valueOf(phone1.phone));

        builder.or(memberAccount.likeIgnoreCase("%" + word + "%"));
        builder.or(memberName.likeIgnoreCase("%" + word + "%"));
        builder.or(memberNickname.likeIgnoreCase("%" + word + "%"));
        builder.or(memberPhone.likeIgnoreCase("%" + word + "%"));

        Predicate predicate = builder.getValue();

        List<SearchMemberDto> content = queryFactory
            .select(new QSearchMemberDto(
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
            .where(predicate)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(member.memberCreateDate.desc())
            .fetch();

        JPAQuery<Member> count = queryFactory
            .select(member)
            .from(member)
            .leftJoin(member.basic, basic)
            .leftJoin(member.phone, phone1)
            .leftJoin(member.social, social)
            .where(member.memberStatus.eq(MemberStatusEnum.정상).or(member.memberStatus.eq(MemberStatusEnum.이용정지)))
            .where(predicate);

        return PageableExecutionUtils.getPage(content, pageable, () -> count.fetchCount());
    }

    public Page<SearchExpireMemberDto> searchExpireMembers(String word, Pageable pageable){

        BooleanBuilder builder = new BooleanBuilder();

        StringPath memberAccount = Expressions.stringPath(String.valueOf(accountExpression));
        StringPath memberName = Expressions.stringPath(String.valueOf(member.memberName));
        StringPath memberNickname = Expressions.stringPath(String.valueOf(member.memberNickname));
        StringPath memberPhone = Expressions.stringPath(String.valueOf(phone1.phone));

        builder.or(memberAccount.likeIgnoreCase("%" + word + "%"));
        builder.or(memberName.likeIgnoreCase("%" + word + "%"));
        builder.or(memberNickname.likeIgnoreCase("%" + word + "%"));
        builder.or(memberPhone.likeIgnoreCase("%" + word + "%"));

        Predicate predicate = builder.getValue();

        List<SearchExpireMemberDto> content = queryFactory
            .select(new QSearchExpireMemberDto(
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
            .where(predicate)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(member.memberExpireDate.desc())
            .fetch();

        JPAQuery<Member> count = queryFactory
            .select(member)
            .from(member)
            .leftJoin(member.basic, basic)
            .leftJoin(member.phone, phone1)
            .leftJoin(member.social, social)
            .where(member.memberStatus.eq(MemberStatusEnum.탈퇴))
            .where(predicate);

        return PageableExecutionUtils.getPage(content, pageable, () -> count.fetchCount());
    }

//    public Page<SearchRoomDto> searchRoom(String word, Pageable pageable){
//        queryFactory
//            .select()
//    }



    public List<AdminMembersDto> findAllByFreezeMember(){
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
            .where(member.memberStatus.eq(MemberStatusEnum.이용정지))
            .orderBy(member.memberCreateDate.desc())
            .fetch();
    }

    //    public List<AdminMembersDto> findAllByMember() {
//        return queryFactory
//            .select(new QAdminMembersDto(
//                Expressions.stringTemplate("{0}", accountExpression).as("memberAccount"),
//                member.memberName,
//                member.memberNickname,
//                phone1.phone,
//                Expressions.stringTemplate("TO_CHAR({0}, {1})", member.memberCreateDate, "YYYY-MM-DD"),
//                member.memberNotifyCount,
//                social.socialType,
//                member.memberStatus))
//            .from(member)
//            .leftJoin(member.basic, basic)
//            .leftJoin(member.phone, phone1)
//            .leftJoin(member.social, social)
//            .where(member.memberStatus.eq(MemberStatusEnum.정상).or(member.memberStatus.eq(MemberStatusEnum.이용정지)))
//            .orderBy(member.memberCreateDate.desc())
//            .fetch();
//    }

    private StringExpression accountExpression = new CaseBuilder()
        .when(Expressions.stringPath(String.valueOf(basic.account)).isNull())
        .then(social.socialEmail)
        .otherwise(basic.account);
}