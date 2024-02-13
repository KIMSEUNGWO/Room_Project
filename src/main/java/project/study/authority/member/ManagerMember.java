package project.study.authority.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.study.authority.member.authority.ManagerAuthority;
import project.study.authority.member.authority.MemberAuthority;
import project.study.authority.member.dto.*;
import project.study.domain.Member;
import project.study.jpaRepository.MemberJpaRepository;

@Component
@RequiredArgsConstructor
public class ManagerMember implements MemberAuthority, ManagerAuthority {

    private final MemberAuthority memberAuthority;
    private final ManagerAuthority managerAuthority;

    @Override
    public void editRoom(Member member, RequestEditRoomDto data) {
        managerAuthority.editRoom(member, data);
    }
    @Override
    public void managerEntrust(Member member, RequestEntrustDto data) {
        managerAuthority.managerEntrust(member, data);
    }
    @Override
    public void editNotice(Member member, RequestNoticeDto data) {
        managerAuthority.editNotice(member, data);
    }
    @Override
    public void kickMember(Member member, RequestKickDto data) {
        managerAuthority.kickMember(member, data);
    }
    @Override
    public void createRoom(Member member, RequestCreateRoomDto data) {
        memberAuthority.createRoom(member, data);
    }
    @Override
    public void notify(Member member, RequestNotifyDto data) {
        memberAuthority.notify(member, data);
    }
}
