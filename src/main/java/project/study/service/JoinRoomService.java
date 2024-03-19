package project.study.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.study.authority.member.dto.RequestJoinRoomDto;
import project.study.domain.JoinRoom;
import project.study.domain.Member;
import project.study.domain.Room;
import project.study.exceptions.NotLoginMemberRestException;
import project.study.exceptions.authority.NotJoinRoomException;
import project.study.exceptions.authority.joinroom.ExceedJoinRoomException;
import project.study.repository.JoinRoomRepository;

import java.util.Optional;

import static project.study.enums.AuthorityMemberEnum.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class JoinRoomService {

    private final JoinRoomRepository joinRoomRepository;

    public boolean exitsByMemberAndRoom(Long memberId, Room room) {
        if (memberId == null) throw new NotLoginMemberRestException();
        return joinRoomRepository.exitsByMemberAndRoom(memberId, room);
    }

    public void validMaxJoinRoom(Member member, HttpServletResponse response) {
        if (member.isExceedJoinRoom(일반)) {
            throw new ExceedJoinRoomException(response);
        }
    }

    public synchronized JoinRoom joinRoom(RequestJoinRoomDto data) {
        JoinRoom saveJoinRoom = data.saveJoinRoom();
        return joinRoomRepository.save(saveJoinRoom);
    }

    public JoinRoom findByMemberAndRoom(Member member, Room room, HttpServletResponse response) {
        Optional<JoinRoom> findJoinRoom = joinRoomRepository.findByMemberAndRoom(member, room);
        return findJoinRoom.orElseThrow(() -> new NotJoinRoomException(response));
    }
    public Optional<JoinRoom> findByMemberAndRoom(Member member, Room room) {
        return joinRoomRepository.findByMemberAndRoom(member, room);
    }

    public void deleteJoinRoom(JoinRoom joinRoom) {
        joinRoomRepository.deleteJoinRoom(joinRoom);
    }
}
