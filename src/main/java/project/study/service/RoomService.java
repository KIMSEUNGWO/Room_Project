package project.study.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.study.authority.member.dto.ResponseMyRoomListDto;
import project.study.domain.Member;
import project.study.domain.Room;
import project.study.authority.member.dto.RequestCreateRoomDto;
import project.study.domain.Tag;
import project.study.repository.JoinRoomRepository;
import project.study.repository.RoomRepository;

import java.util.ArrayList;
import java.util.List;

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

    public List<ResponseMyRoomListDto> getMyRoomList(Member member) {
        List<ResponseMyRoomListDto> roomInfo = joinRoomRepository.getRoomInfo(member);
        for (ResponseMyRoomListDto myRoom : roomInfo) {
            System.out.println("responseMyRoomListDto = " + myRoom);
        }
//        List<ResponseMyRoomListDto> temp = new ArrayList<>();
//        for (JoinRoom joinRoom : joinRoomList) {
//            Room room = joinRoom.getRoom();
//            int nowPerson = joinRoomRepository.countByRoom(room);
//            ResponseMyRoomListDto build = ResponseMyRoomListDto.builder()
//                    .roomId(room.getRoomId())
//                    .roomImage(room.getRoomImage().getRoomImageStoreName())
//                    .roomTitle(room.getRoomTitle())
//                    .roomIntro(room.getRoomIntro())
//                    .roomPublic(room.getRoomPublic().isPublic())
//                    .roomJoin(true)
//                    .roomMaxPerson(String.format("%d/%d", nowPerson, room.getRoomLimit()))
//                    .tagList(convertTagList(room.getTags()))
//                    .build();
//
//            temp.add(build);
//        }
//        return temp;
        return roomInfo;
    }

    private List<String> convertTagList(List<Tag> tags) {
        List<String> temp = new ArrayList<>();
        for (Tag tag : tags) {
            temp.add(tag.getTagName());
        }
        return temp;
    }
}
