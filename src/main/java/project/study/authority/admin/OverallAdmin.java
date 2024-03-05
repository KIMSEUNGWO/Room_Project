package project.study.authority.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import project.study.authority.admin.authority.*;
import project.study.authority.admin.dto.*;
import project.study.domain.Member;
import project.study.domain.Notify;
import project.study.domain.Room;
import project.study.enums.BanEnum;
import project.study.enums.NotifyStatus;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OverallAdmin implements ExpireMemberInfoAuthority, MemberInfoAuthority, RoomInfoAuthority {

    private final ExpireMemberInfoAuthority expireMemberInfoAuthority;
    private final MemberInfoAuthority memberInfoAuthority;
    private final RoomInfoAuthority roomInfoAuthority;

    @Override
    public Page<SearchExpireMemberDto> searchExpireMember(String word, int pageNumber) {
        return expireMemberInfoAuthority.searchExpireMember(word, pageNumber);
    }

    @Override
    public Page<SearchMemberDto> searchMember(String word, int pageNumber) {
        return memberInfoAuthority.searchMember(word, pageNumber);
    }

    @Override
    public Page<SearchMemberDto> SearchMemberOnlyFreeze(String word, int pageNumber) {
        return memberInfoAuthority.SearchMemberOnlyFreeze(word, pageNumber);
    }

    @Override
    public Page<SearchRoomDto> searchRoom(String word, int pageNumber) {
        return roomInfoAuthority.searchRoom(word, pageNumber);
    }

    @Override
    public void deleteJoinRoom(RequestDeleteRoomDto dto) {
        roomInfoAuthority.deleteJoinRoom(dto);
    }
}
