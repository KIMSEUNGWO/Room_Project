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
public class OverallAdmin implements BanAuthority, ExpireMemberInfoAuthority, MemberInfoAuthority, NotifyMemberInfoAuthority, RoomInfoAuthority {

    @Override
    public void notifyStatusChange(RequestNotifyStatusChangeDto dto) {

    }

    @Override
    public void notifyMemberFreeze(RequestNotifyMemberFreezeDto dto) {

    }

    @Override
    public Page<SearchExpireMemberDto> searchExpireMember(String word, Pageable pageable) {
        return null;
    }

    @Override
    public Page<SearchMemberDto> searchMember(String word, Pageable pageable) {
        return null;
    }

    @Override
    public Page<SearchMemberDto> SearchMemberOnlyFreeze(String word, Pageable pageable) {
        return null;
    }

    @Override
    public Page<SearchNotifyDto> searchNotify(String word, Pageable pageable) {
        return null;
    }

    @Override
    public Page<SearchNotifyDto> searchNotifyIncludeComplete(String word, Pageable pageable) {
        return null;
    }

    @Override
    public SearchNotifyReadMoreDto searchNotifyReadMore(Long notifyId) {
        return null;
    }

    @Override
    public SearchNotifyMemberInfoDto searchNotifyMemberInfo(Long notifyId) {
        return null;
    }

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
