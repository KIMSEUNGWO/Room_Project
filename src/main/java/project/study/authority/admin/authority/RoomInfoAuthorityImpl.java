package project.study.authority.admin.authority;

import org.springframework.stereotype.Component;
import project.study.authority.admin.dto.SearchRoomDto;
import project.study.domain.Room;

import java.util.List;

@Component
public class RoomInfoAuthorityImpl implements RoomInfoAuthority{
    @Override
    public List<Room> searchRoom(SearchRoomDto data) {
        System.out.println("searchRoom 실행");
        return null;
    }

    @Override
    public List<Room> findAllByRoom() {
        System.out.println("findAllByRoom 실행");
        return null;
    }

    @Override
    public void deleteByRoomId(Long roomId) {
        System.out.println("deleteByRoomId 실행");
    }
}
