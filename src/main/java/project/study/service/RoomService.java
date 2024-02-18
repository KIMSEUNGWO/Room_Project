package project.study.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.study.authority.member.dto.ResponseRoomListDto;
import project.study.constant.WebConst;
import project.study.domain.Member;
import project.study.domain.Room;
import project.study.authority.member.dto.RequestCreateRoomDto;
import project.study.dto.abstractentity.ResponseDto;
import project.study.dto.room.ResponsePrivateRoomInfoDto;
import project.study.enums.AuthorityMemberEnum;
import project.study.exceptions.roomcreate.CreateExceedRoomException;
import project.study.exceptions.roomjoin.IllegalRoomException;
import project.study.repository.JoinRoomRepository;
import project.study.repository.RoomRepository;
import project.study.repository.TagRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {

    private final RoomRepository roomRepository;
    private final JoinRoomRepository joinRoomRepository;
    private final TagRepository tagRepository;

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

    public List<ResponseRoomListDto> getMyRoomList(Member member) {
        List<ResponseRoomListDto> roomInfo = joinRoomRepository.getRoomInfo(member);
        for (ResponseRoomListDto data : roomInfo) {
            List<String> tagList = tagRepository.findAllByRoomId(data.getRoomId());
            data.setTagList(tagList);
        }
        return roomInfo;
    }


    public List<ResponseRoomListDto> searchRoomList(Member member, String word, Pageable pageable) {
        List<ResponseRoomListDto> roomInfo = joinRoomRepository.search(member, word, pageable);
        for (ResponseRoomListDto data : roomInfo) {
            List<String> tagList = tagRepository.findAllByRoomId(data.getRoomId());
            data.setTagList(tagList);
        }
        return roomInfo;
    }

    public void validMaxCreateRoom(Member member) {
        int nowCount = joinRoomRepository.countByMemberAndAuthority(member, AuthorityMemberEnum.방장);
        if (nowCount >= WebConst.MAX_CREATE_ROOM_COUNT) throw new CreateExceedRoomException(new ResponseDto(WebConst.ERROR, "방 생성 최대개수를 초과하였습니다."));
    }

    public ResponsePrivateRoomInfoDto getResponsePrivateRoomInfoDto(Room room) {
        return ResponsePrivateRoomInfoDto.builder()
                .image(room.getRoomImage().getRoomImageStoreName())
                .title(room.getRoomTitle())
                .intro(room.getRoomIntro())
                .build();
    }

    public Room findByRoom(String roomIdStr, HttpServletResponse response) {
        Long roomId = roomRepository.getNumberFormat(roomIdStr, response);
        Optional<Room> findRoom = roomRepository.findById(roomId);
        if (findRoom.isEmpty()) throw new IllegalRoomException(response, "방 정보를 찾을 수 없습니다.");
        return findRoom.get();
    }

    public void deleteRoom(Room room) {
//        roomRepository.deleteRoomImage(room.getRoomImage());
//        roomRepository.deleteRoomNotice(room.getRoomNotice());
//        roomRepository.deleteRoomPassword(room.getRoomPassword());
//        tagRepository.deleteTag(room.getTags());
//
//        roomRepository.deleteRoom(room);
    }
}
