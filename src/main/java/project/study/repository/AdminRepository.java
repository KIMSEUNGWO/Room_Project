package project.study.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Predicate;
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
import project.study.domain.Member;
import project.study.domain.QJoinRoom;
import project.study.domain.QRoom;
import project.study.domain.Room;
import project.study.enums.AuthorityMemberEnum;
import project.study.enums.MemberStatusEnum;

import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;
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
//                select(joinRoom.member.count())
//                    .from(joinRoom)
//                    .where(joinRoom.room.eq(joinRoom.room)),
//                Expressions.numberTemplate(Long.class, "{0}", room.roomLimit),
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



    public Page<SearchMemberDto> findAllByFreezeMember(String word, Pageable pageable){
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

    private StringExpression accountExpression = new CaseBuilder()
        .when(Expressions.stringPath(String.valueOf(basic.account)).isNull())
        .then(social.socialEmail)
        .otherwise(basic.account);

}