package project.study.authority.admin.authority;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.study.authority.admin.dto.RequestDeleteRoomDto;
import project.study.authority.admin.dto.SearchRoomDto;
import project.study.domain.Room;

import java.util.List;

public interface RoomInfoAuthority {

    Page<SearchRoomDto> searchRoomList(int pageNumber, String word);
    void deleteRoom(RequestDeleteRoomDto dto);
}
