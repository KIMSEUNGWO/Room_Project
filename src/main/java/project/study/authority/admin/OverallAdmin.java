package project.study.authority.admin;

import lombok.RequiredArgsConstructor;
import project.study.authority.admin.authority.BanAuthority;
import project.study.authority.admin.authority.MemberInfoAuthority;
import project.study.authority.admin.authority.NotifyMemberInfoAuthority;
import project.study.authority.admin.authority.RoomInfoAuthority;
import project.study.authority.admin.dto.SearchMemberDto;
import project.study.authority.admin.dto.SearchNotifyDto;
import project.study.authority.admin.dto.SearchRoomDto;
import project.study.domain.Member;
import project.study.domain.Notify;
import project.study.domain.Room;
import project.study.enums.BanEnum;
import project.study.enums.NotifyStatus;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class OverallAdmin implements MemberInfoAuthority, NotifyMemberInfoAuthority, BanAuthority, RoomInfoAuthority {

    private final MemberInfoAuthority memberInfoAuthority;
    private final NotifyMemberInfoAuthority notifyMemberInfoAuthority;
    private final BanAuthority banAuthority;
    private final RoomInfoAuthority roomInfoAuthority;

    @Override
    public List<Room> searchRoom(SearchRoomDto data) {
        return roomInfoAuthority.searchRoom(data);
    }
    @Override
    public List<Room> findAllByRoom() {
        return roomInfoAuthority.findAllByRoom();
    }
    @Override
    public void deleteByRoomId(Long roomId) {
        roomInfoAuthority.deleteByRoomId(roomId);
    }
    @Override
    public List<Member> findAllByMember() {
        return memberInfoAuthority.findAllByMember();
    }
    @Override
    public List<Member> searchMember(SearchMemberDto data) {
        return memberInfoAuthority.searchMember(data);
    }
    @Override
    public Optional<Member> findByMemberId(Long memberId) {
        return memberInfoAuthority.findByMemberId(memberId);
    }
    @Override
    public void ban(Long memberId, BanEnum banEnum, String banReason) {
        banAuthority.ban(memberId, banEnum, banReason);
    }
    @Override
    public void banCancel(Long memberId) {
        banAuthority.banCancel(memberId);
    }
    @Override
    public List<Notify> searchNotify(SearchNotifyDto data) {
        return notifyMemberInfoAuthority.searchNotify(data);
    }
    @Override
    public List<Notify> findAllByNotify() {
        return notifyMemberInfoAuthority.findAllByNotify();
    }
    @Override
    public List<Notify> findAllByNotifyWhereNotifyStatus(NotifyStatus notifyStatus) {
        return notifyMemberInfoAuthority.findAllByNotifyWhereNotifyStatus(notifyStatus);
    }
    @Override
    public Optional<Member> findByNotifyMember(Long notifyId) {
        return notifyMemberInfoAuthority.findByNotifyMember(notifyId);
    }
    @Override
    public void notifyConfirm(Long notifyId) {
        notifyMemberInfoAuthority.notifyConfirm(notifyId);
    }
}
