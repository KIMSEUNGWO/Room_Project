package project.study.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import kotlin.reflect.jvm.internal.impl.resolve.constants.StringValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import project.study.authority.admin.dto.*;
import project.study.domain.*;
import project.study.enums.AuthorityMemberEnum;
import project.study.enums.MemberStatusEnum;
import project.study.enums.NotifyStatus;

import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;
import static org.hibernate.internal.util.NullnessHelper.coalesce;
import static project.study.domain.QBasic.*;
import static project.study.domain.QJoinRoom.*;
import static project.study.domain.QMember.*;
import static project.study.domain.QNotify.*;
import static project.study.domain.QNotifyImage.*;
import static project.study.domain.QPhone.*;
import static project.study.domain.QRoom.*;
import static project.study.domain.QSocial.*;

@Repository
public class AdminRepository {

    private final JPAQueryFactory queryFactory;

    public AdminRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Page<SearchMemberDto> searchMember(String word, Pageable pageable){

        BooleanBuilder builder = new BooleanBuilder();

        StringPath socialType = Expressions.stringPath(String.valueOf(social.socialType));

        builder.or(accountExpression.likeIgnoreCase("%" + word + "%"));
        builder.or(member.memberName.likeIgnoreCase("%" + word + "%"));
        builder.or(member.memberNickname.likeIgnoreCase("%" + word + "%"));
        builder.or(phone1.phone.likeIgnoreCase("%" + word + "%"));
        builder.or(socialType.likeIgnoreCase("%" + word + "%"));

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

    public Page<SearchExpireMemberDto> searchExpireMember(String word, Pageable pageable){

        BooleanBuilder builder = new BooleanBuilder();

        StringPath socialType = Expressions.stringPath(String.valueOf(social.socialType));

        builder.or(accountExpression.likeIgnoreCase("%" + word + "%"));
        builder.or(member.memberName.likeIgnoreCase("%" + word + "%"));
        builder.or(member.memberNickname.likeIgnoreCase("%" + word + "%"));
        builder.or(phone1.phone.likeIgnoreCase("%" + word + "%"));
        builder.or(socialType.likeIgnoreCase("%" + word + "%"));

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

    private Expression<String> getRoomMember(QRoom r) {
        return Expressions
            .asString(r.joinRoomList.size().stringValue())
            .concat("/")
            .concat(r.roomLimit.stringValue())
            .as("roomMember");
    }

    public Page<SearchRoomDto> searchRoom(String word, Pageable pageable){

        BooleanBuilder builder = new BooleanBuilder();

        StringPath roomPublic = Expressions.stringPath(String.valueOf(room.roomPublic));

        builder.or(room.roomId.like("%" + word + "%"));
        builder.or(room.roomTitle.likeIgnoreCase("%" + word + "%"));
        builder.or(roomPublic.likeIgnoreCase("%" + word + "%"));
        builder.or(member.memberName.likeIgnoreCase("%"+ word +"%"));

        Predicate predicate = builder.getValue();

        List<SearchRoomDto> content = queryFactory
            .select(new QSearchRoomDto(
                room.roomId,
                room.roomTitle,
                getRoomMember(room),
                member.memberName,
                Expressions.stringTemplate("TO_CHAR({0}, {1})", room.roomCreateDate, "YYYY-MM-DD"),
                room.roomPublic))
            .from(joinRoom)
            .leftJoin(joinRoom.room, room)
            .leftJoin(joinRoom.member, member)
            .where(joinRoom.authorityEnum.eq(AuthorityMemberEnum.방장))
            .where(predicate)
            .orderBy(room.roomCreateDate.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Room> count = queryFactory
            .select(room)
            .from(room)
            .leftJoin(room.joinRoomList, joinRoom);
        return PageableExecutionUtils.getPage(content, pageable, () -> count.fetchCount());
    }

    public Page<SearchNotifyDto> searchNotify(String word, Pageable pageable){

        QMember reporterMember = new QMember("reporterMember");
        QMember criminalMember = new QMember("criminalMember");

        QBasic reporterBasic = new QBasic("reporterBasic");
        QBasic criminalBasic = new QBasic("criminalBasic");

        QSocial reporterSocial = new QSocial("reporterSocial");
        QSocial criminalSocial = new QSocial("criminalSocial");


        StringExpression reporterAccountExpression = new CaseBuilder()
            .when(Expressions.stringPath(String.valueOf(reporterBasic.account)).isNull())
            .then(reporterSocial.socialEmail)
            .otherwise(reporterBasic.account);

        StringExpression criminalAccountExpression = new CaseBuilder()
            .when(Expressions.stringPath(String.valueOf(criminalBasic.account)).isNull())
            .then(criminalSocial.socialEmail)
            .otherwise(criminalBasic.account);

        StringPath notifyStatus = Expressions.stringPath(String.valueOf(notify.notifyStatus));

        BooleanBuilder builder = new BooleanBuilder();

        builder.or(reporterAccountExpression.likeIgnoreCase("%" + word + "%"));
        builder.or(criminalAccountExpression.likeIgnoreCase("%" + word + "%"));
        builder.or(room.roomId.like("%" + word + "%"));
        builder.or(notify.notifyReason.likeIgnoreCase("%" + word + "%"));
        builder.or(notifyStatus.likeIgnoreCase("%" + word + "%"));

        Predicate predicate = builder.getValue();

        List<SearchNotifyDto> content = queryFactory
            .select(new QSearchNotifyDto(
                Expressions.stringTemplate("{0}", reporterAccountExpression).as("reporterMemberAccount"),
                Expressions.stringTemplate("{0}", criminalAccountExpression).as("criminalMemberAccount"),
                room.roomId,
                notify.notifyReason,
                Expressions.stringTemplate("TO_CHAR({0}, {1})", notify.notifyDate, "YYYY-MM-DD"),
                notify.notifyId,
                notify.notifyContent,
                notify.notifyStatus
            ))
            .from(notify)
            .leftJoin(notify.room, room)
            .leftJoin(notify.reporter, reporterMember).on(reporterMember.eq(reporterMember))
            .leftJoin(notify.criminal, criminalMember).on(criminalMember.eq(criminalMember))
            .leftJoin(reporterMember.basic, reporterBasic)
            .leftJoin(reporterMember.social, reporterSocial)
            .leftJoin(criminalMember.basic, criminalBasic)
            .leftJoin(criminalMember.social, criminalSocial)
            .where(notify.notifyStatus.eq(NotifyStatus.처리중))
            .where(predicate)
            .orderBy(notify.notifyDate.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Notify> count = queryFactory
            .select(notify)
            .from(notify)
            .where(notify.notifyStatus.eq(NotifyStatus.처리중))
            .where(predicate);

        return PageableExecutionUtils.getPage(content, pageable, () -> count.fetchCount());
    }

    public Page<SearchMemberDto> SearchMemberOnlyFreeze(String word, Pageable pageable){
        BooleanBuilder builder = new BooleanBuilder();

        StringPath socialType = Expressions.stringPath(String.valueOf(social.socialType));

        builder.or(accountExpression.likeIgnoreCase("%" + word + "%"));
        builder.or(member.memberName.likeIgnoreCase("%" + word + "%"));
        builder.or(member.memberNickname.likeIgnoreCase("%" + word + "%"));
        builder.or(phone1.phone.likeIgnoreCase("%" + word + "%"));
        builder.or(socialType.likeIgnoreCase("%" + word + "%"));

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
            .where(member.memberStatus.eq(MemberStatusEnum.이용정지))
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
            .where(member.memberStatus.eq(MemberStatusEnum.이용정지))
            .where(predicate);

        return PageableExecutionUtils.getPage(content, pageable, () -> count.fetchCount());
    }

    public Page<SearchNotifyDto> searchNotifyIncludeComplete(String word, Pageable pageable){

        QMember reporterMember = new QMember("reporterMember");
        QMember criminalMember = new QMember("criminalMember");

        QBasic reporterBasic = new QBasic("reporterBasic");
        QBasic criminalBasic = new QBasic("criminalBasic");

        QSocial reporterSocial = new QSocial("reporterSocial");
        QSocial criminalSocial = new QSocial("criminalSocial");


        StringExpression reporterAccountExpression = new CaseBuilder()
            .when(Expressions.stringPath(String.valueOf(reporterBasic.account)).isNull())
            .then(reporterSocial.socialEmail)
            .otherwise(reporterBasic.account);

        StringExpression criminalAccountExpression = new CaseBuilder()
            .when(Expressions.stringPath(String.valueOf(criminalBasic.account)).isNull())
            .then(criminalSocial.socialEmail)
            .otherwise(criminalBasic.account);

        StringPath notifyStatus = Expressions.stringPath(String.valueOf(notify.notifyStatus));

        BooleanBuilder builder = new BooleanBuilder();

        builder.or(reporterAccountExpression.likeIgnoreCase("%" + word + "%"));
        builder.or(criminalAccountExpression.likeIgnoreCase("%" + word + "%"));
        builder.or(room.roomId.like("%" + word + "%"));
        builder.or(notify.notifyReason.likeIgnoreCase("%" + word + "%"));
        builder.or(notifyStatus.likeIgnoreCase("%" + word + "%"));

        Predicate predicate = builder.getValue();

        List<SearchNotifyDto> content = queryFactory
            .select(new QSearchNotifyDto(
                Expressions.stringTemplate("{0}", reporterAccountExpression).as("reporterMemberAccount"),
                Expressions.stringTemplate("{0}", criminalAccountExpression).as("criminalMemberAccount"),
                room.roomId,
                notify.notifyReason,
                Expressions.stringTemplate("TO_CHAR({0}, {1})", notify.notifyDate, "YYYY-MM-DD"),
                notify.notifyId,
                notify.notifyContent,
                notify.notifyStatus
            ))
            .from(notify)
            .leftJoin(notify.room, room)
            .leftJoin(notify.reporter, reporterMember).on(reporterMember.eq(reporterMember))
            .leftJoin(notify.criminal, criminalMember).on(criminalMember.eq(criminalMember))
            .leftJoin(reporterMember.basic, reporterBasic)
            .leftJoin(reporterMember.social, reporterSocial)
            .leftJoin(criminalMember.basic, criminalBasic)
            .leftJoin(criminalMember.social, criminalSocial)
            .where(notify.notifyStatus.eq(NotifyStatus.처리중).or(notify.notifyStatus.eq(NotifyStatus.처리완료)))
            .where(predicate)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(notify.notifyDate.desc())
            .fetch();

        JPAQuery<Notify> count = queryFactory
            .select(notify)
            .from(notify)
            .where(notify.notifyStatus.eq(NotifyStatus.처리중).or(notify.notifyStatus.eq(NotifyStatus.처리완료)))
            .where(predicate);

        return PageableExecutionUtils.getPage(content, pageable, () -> count.fetchCount());
    }

    private StringExpression accountExpression = new CaseBuilder()
        .when(Expressions.stringPath(String.valueOf(basic.account)).isNull())
        .then(social.socialEmail)
        .otherwise(basic.account);



    //    public Page<Notify> searchNotify(String word, Pageable pageable){
//
//        BooleanBuilder builder = new BooleanBuilder();
//
//        Predicate predicate = builder.getValue();
//
//        List<Notify> content = queryFactory
//            .select(notify)
//            .from(notify)
//            .where(notify.notifyStatus.eq(NotifyStatus.처리중))
//            .where(predicate)
//            .offset(pageable.getOffset())
//            .limit(pageable.getPageSize())
//            .fetch();
//
//        JPAQuery<Notify> count = queryFactory
//            .select(notify)
//            .from(notify)
//            .where(notify.notifyStatus.eq(NotifyStatus.처리중))
//            .where(predicate);
//
//        return PageableExecutionUtils.getPage(content, pageable, () -> count.fetchCount());
//    }
}