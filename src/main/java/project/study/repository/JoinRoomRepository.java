package project.study.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import project.study.domain.JoinRoom;
import project.study.domain.Member;
import project.study.domain.Room;
import project.study.enums.AuthorityMemberEnum;
import project.study.jpaRepository.JoinRoomJpaRepository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class JoinRoomRepository {

    private final JoinRoomJpaRepository joinRoomJpaRepository;

    public void createJoinRoom(Room room, Member member) {
        JoinRoom saveJoinRoom = JoinRoom.builder()
            .room(room)
            .member(member)
            .authorityEnum(AuthorityMemberEnum.방장)
            .build();
        joinRoomJpaRepository.save(saveJoinRoom);
    }
}
