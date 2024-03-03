package project.study.authority.admin.authority;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import project.study.authority.admin.dto.RequestDeleteRoomDto;
import project.study.authority.admin.dto.SearchRoomDto;
import project.study.domain.Room;

import java.util.List;

@Component
public class RoomInfoAuthorityImpl implements RoomInfoAuthority{

    @Override
    public Page<SearchRoomDto> searchRoom(String word, Pageable pageable) {
        return null;
    }

    @Override
    public void deleteJoinRoom(RequestDeleteRoomDto dto) {

    }

    @Override
    public void insertRoomDelete(RequestDeleteRoomDto dto) {

    }
}
