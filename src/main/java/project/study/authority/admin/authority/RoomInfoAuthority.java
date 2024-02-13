package project.study.authority.admin.authority;

import project.study.authority.admin.dto.SearchRoomDto;
import project.study.domain.Room;

import java.util.List;

public interface RoomInfoAuthority {

    List<Room> searchRoom(SearchRoomDto data);
    List<Room> findAllByRoom();
    void deleteByRoomId(Long roomId);
}
