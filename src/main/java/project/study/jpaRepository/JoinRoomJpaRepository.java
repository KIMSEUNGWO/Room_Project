package project.study.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.study.domain.JoinRoom;
import project.study.domain.Member;
import project.study.domain.Room;

import java.util.List;
import java.util.Optional;

public interface JoinRoomJpaRepository extends JpaRepository<JoinRoom, Long> {
    Optional<JoinRoom> findByMemberAndRoom(Member member, Room room);
}
