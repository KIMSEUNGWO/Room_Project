package project.study.authority.member.authority;

import org.springframework.stereotype.Component;
import project.study.authority.member.dto.RequestEditRoomDto;
import project.study.authority.member.dto.RequestEntrustDto;
import project.study.authority.member.dto.RequestKickDto;
import project.study.authority.member.dto.RequestNoticeDto;
import project.study.domain.Member;

@Component
public class ManagerAuthorityImpl implements ManagerAuthority{
    @Override
    public void editRoom(Member member, RequestEditRoomDto data) {
        System.out.println("editRoom 실행");
    }

    @Override
    public void managerEntrust(Member member, RequestEntrustDto data) {
        System.out.println("managerEntrust 실행");
    }

    @Override
    public void editNotice(Member member, RequestNoticeDto data) {
        System.out.println("editNotice 실행");
    }

    @Override
    public void kickMember(Member member, RequestKickDto data) {
        System.out.println("kickMember 실행");
    }
}