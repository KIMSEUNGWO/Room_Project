package project.study.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.study.constant.WebConst;
import project.study.domain.Member;
import project.study.domain.Room;
import project.study.enums.AuthorityMemberEnum;
import project.study.exceptions.authority.joinroom.ExceedJoinRoomException;
import project.study.repository.JoinRoomRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class JoinRoomService {

    private final JoinRoomRepository joinRoomRepository;

    public boolean exitsByMemberAndRoom(Member member, Room room) {
        return joinRoomRepository.exitsByMemberAndRoom(member, room);
    }

    public void validMaxJoinRoom(Member member, HttpServletResponse response) {
        int nowJoinRoomCount = joinRoomRepository.countByMemberAndAuthority(member, AuthorityMemberEnum.일반);
        if (nowJoinRoomCount >= WebConst.MAX_JOIN_ROOM_COUNT) {
            throw new ExceedJoinRoomException(response);
        }
    }
}
