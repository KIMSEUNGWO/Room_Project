package project.study.repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import project.study.authority.member.dto.ResponseRoomListDto;
import project.study.domain.*;
import project.study.enums.AuthorityMemberEnum;
import project.study.enums.PublicEnum;
import project.study.jpaRepository.JoinRoomJpaRepository;

import java.util.List;

@Repository
@Slf4j
public class JoinRoomRepository {

    private final JoinRoomJpaRepository joinRoomJpaRepository;
    private final JPAQueryFactory query;

    public JoinRoomRepository(JoinRoomJpaRepository joinRoomJpaRepository, EntityManager em) {
        this.joinRoomJpaRepository = joinRoomJpaRepository;
        this.query = new JPAQueryFactory(em);
    }

    public void createJoinRoom(Room room, Member member) {
        JoinRoom saveJoinRoom = JoinRoom.builder()
            .room(room)
            .member(member)
            .authorityEnum(AuthorityMemberEnum.방장)
            .build();
        joinRoomJpaRepository.save(saveJoinRoom);
    }

    public List<JoinRoom> findAllByMember(Member member) {
        return joinRoomJpaRepository.findAllByMember(member);
    }

    public int countByRoom(Room room) {
        return joinRoomJpaRepository.countByRoom(room);
    }

    public List<ResponseRoomListDto> getRoomInfo(Member member) {
        QJoinRoom j = QJoinRoom.joinRoom;
        QRoom r = QRoom.room;
        QRoomImage ri = QRoomImage.roomImage;

        return query
            .select(Projections.fields(ResponseRoomListDto.class,
                r.roomId.as("roomId"),
                ri.roomImageStoreName.as("roomImage"),
                r.roomTitle.as("roomTitle"),
                r.roomIntro.as("roomIntro"),
                r.roomPublic.eq(PublicEnum.PUBLIC).as("roomPublic"),
                j.member.eq(member).as("roomJoin"),
                ExpressionUtils.as(JPAExpressions
                    .select(j.count().stringValue().concat("/").concat(r.roomLimit.stringValue()))
                    .from(j)
                    .where(j.room.eq(r)), "roomMaxPerson")
            ))
            .from(j)
            .join(r).on(j.room.eq(r))
            .join(ri).on(r.eq(ri.room))
            .where(j.member.eq(member))
            .fetch();
    }
}
