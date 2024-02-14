package project.study.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.study.domain.Member;
import project.study.domain.Room;
import project.study.authority.member.dto.RequestCreateRoomDto;
import project.study.repository.JoinRoomRepository;
import project.study.repository.RoomRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {

    private final RoomRepository roomRepository;
    private final JoinRoomRepository joinRoomRepository;

    @Transactional
    public Long createRoom(RequestCreateRoomDto data, Member member) {
        Room room = roomRepository.createRoom(data);
        roomRepository.createRoomImage(data, room);
        roomRepository.createTags(data, room);
        roomRepository.createPassword(data, room);
        joinRoomRepository.createJoinRoom(room, member);
        return room.getRoomId();
    }

    public void validRoomData(RequestCreateRoomDto data) {
        roomRepository.validRoomTitle(data.getTitle());
        roomRepository.validRoomIntro(data.getIntro());
        roomRepository.validRoomMaxPerson(data.getMax());
        roomRepository.validTagList(data.getTags());
    }

}
