package project.study.authority.admin.authority;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import project.study.authority.admin.dto.RequestDeleteRoomDto;
import project.study.authority.admin.dto.SearchRoomDto;
import project.study.service.AdminService;

@Component
@RequiredArgsConstructor
public class RoomInfoAuthorityImpl implements RoomInfoAuthority{

    private final AdminService adminService;

    @Override
    public Page<SearchRoomDto> searchRoom(String word, int pageNumber) {
        return adminService.searchRoom(word, pageNumber);
    }

    @Override
    public Page<SearchRoomDto> searchRoomList(int pageNumber, String word) {
        return adminService.searchRoomList(pageNumber, word);
    }

    @Override
    public void deleteJoinRoom(RequestDeleteRoomDto dto) {
        adminService.deleteRoom(dto);
    }

    @Override
    public void deleteRoom(RequestDeleteRoomDto dto) {
        adminService.roomDelete(dto);
    }

}
