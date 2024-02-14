package project.study.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import project.study.authority.member.dto.ResponseMyRoomListDto;
import project.study.domain.*;
import project.study.enums.AuthorityMemberEnum;
import project.study.enums.PublicEnum;
import project.study.jpaRepository.JoinRoomJpaRepository;

import java.util.ArrayList;
import java.util.List;

import static project.study.domain.QJoinRoom.joinRoom;
import static project.study.domain.QRoom.room;
import static project.study.domain.QRoomImage.roomImage;

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

    public List<ResponseMyRoomListDto> getRoomInfo(Member member) {
//        SimpleExpression<Room> room1 = room.as("room1");
//        return query
//                .select(Projections.fields(ResponseMyRoomListDto.class,
//                                joinRoom.joinRoomId.as("roomId"),
//                                roomImage.roomImageStoreName.as("roomImage"),
//                                room.roomTitle.as("roomTitle"),
//                                room.roomIntro.as("roomIntro"),
//                                room.roomPublic.eq(PublicEnum.PUBLIC).as("roomPublic")
//
//
//                        ))
//                .from(joinRoom)
//                .join(joinRoom.room, )
//                .join(roomImage.room, room)
//                .where(joinRoom.member.eq(member))
//                .fetch();
        List<Tuple> fetch = query.select(room.roomId, roomImage.roomImageStoreName, room.roomTitle, room.roomIntro, room.roomPublic, room.roomLimit)
                .from(joinRoom)
                .join(room, joinRoom.room)
                .leftJoin(roomImage.room, room)
                .where(joinRoom.member.eq(member))
                .fetch();
        List<ResponseMyRoomListDto> temp = new ArrayList<>();
        for (Tuple tuple : fetch) {
            ResponseMyRoomListDto build = ResponseMyRoomListDto.builder()
                    .roomId(tuple.get(room.roomId))
                    .roomImage(tuple.get(roomImage.roomImageStoreName))
                    .roomTitle(tuple.get(room.roomTitle))
                    .roomIntro(tuple.get(room.roomIntro))
                    .roomPublic(tuple.get(room.roomPublic).isPublic())
                    .roomJoin(true)
                    .build();
            temp.add(build);
        }
        return temp;
    }
}
