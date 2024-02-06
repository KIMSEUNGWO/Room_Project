package project.study.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.study.domain.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {

}
