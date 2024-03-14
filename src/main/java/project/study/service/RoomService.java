package project.study.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.study.authority.member.dto.RequestEditRoomDto;
import project.study.authority.member.dto.RequestNoticeDto;
import project.study.authority.member.dto.ResponseRoomListDto;
import project.study.chat.dto.ResponseChatHistory;
import project.study.constant.WebConst;
import project.study.domain.JoinRoom;
import project.study.domain.Member;
import project.study.domain.Room;
import project.study.authority.member.dto.RequestCreateRoomDto;
import project.study.domain.RoomNotice;
import project.study.dto.abstractentity.ResponseDto;
import project.study.dto.room.*;
import project.study.enums.AuthorityMemberEnum;
import project.study.exceptions.roomcreate.CreateExceedRoomException;
import project.study.exceptions.roomjoin.IllegalRoomException;
import project.study.jpaRepository.JoinRoomJpaRepository;
import project.study.jpaRepository.RoomNoticeJpaRepository;
import project.study.repository.JoinRoomRepository;
import project.study.repository.RoomRepository;
import project.study.repository.TagRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {

    private final RoomRepository roomRepository;
    private final JoinRoomRepository joinRoomRepository;
    private final JoinRoomJpaRepository joinRoomJpaRepository;
    private final RoomNoticeJpaRepository roomNoticeJpaRepository;
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


    public List<ResponseRoomListDto> getMyRoomList(Member member) {
        List<JoinRoom> joinRoomList = member.getJoinRoomList();

        List<ResponseRoomListDto> temp = new ArrayList<>();
        for (JoinRoom joinRoom : joinRoomList) {
            temp.add(joinRoom.getResponseRoomListDto());
        }

        return temp;
    }


    public List<ResponseRoomListDto> searchRoomList(Long memberId, String word, Pageable pageable) {
        List<Room> roomInfo = joinRoomRepository.search(word, pageable);

        List<ResponseRoomListDto> temp = new ArrayList<>();
        for (Room room : roomInfo) {
            temp.add(room.getResponseRoomListDto(memberId));
        }
        return temp;
    }

    public void validMaxCreateRoom(Member member) {
        if (member.isExceedJoinRoom(AuthorityMemberEnum.방장)) {
            throw new CreateExceedRoomException(new ResponseDto(WebConst.ERROR, "방 생성 최대개수를 초과하였습니다."));
        }
    }

    public ResponsePrivateRoomInfoDto getResponsePrivateRoomInfoDto(Room room) {
        return ResponsePrivateRoomInfoDto.builder()
                .image(room.getStoreImage())
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
    public Optional<Room> findById(Long roomId) {
        if (roomId == null) return Optional.empty();
        return roomRepository.findById(roomId);
    }

    public void deleteRoom(Room room) {
        roomRepository.moveToDeleteRoom(room);
    }

    public List<ResponseRoomMemberList> getResponseRoomMemberList(Room room, Member member) {
        return roomRepository.getResponseRoomMemberList(room, member);
    }

    public List<ResponseChatHistory> findByChatHistory(Room room) {
        List<ResponseChatHistory> byChatHistory = roomRepository.findByChatHistory(room);
        byChatHistory.sort(Comparator.comparing(ResponseChatHistory::getTime));
        return byChatHistory;
    }

    public ResponseRoomInfo getRoomNotice(Room room, Member member) {
        JoinRoom joinRoom = joinRoomJpaRepository.findByMemberAndRoom(member, room).get();
        return ResponseRoomInfo.builder()
                .roomTitle(room.getRoomTitle())
                .isPublic(room.isPublic())
                .isManager(joinRoom.isManager())
                .build();
    }

    public ResponseRoomNotice getNotice(Room room) {
        RoomNotice roomNotice = room.getRoomNotice();
        if (roomNotice == null) return null;

        return roomNotice.buildResponseRoomNotice();
    }

    public ResponseEditRoomForm getEditRoomForm(Room room) {
        ResponseEditRoomForm form = roomRepository.getResponseEditRoomForm(room);
        form.setTagList(tagRepository.findAllByRoomId(room.getRoomId()));
        return form;
    }

    public void editRoom(Room room, RequestEditRoomDto data) {
        room.setRoomTitle(data.getTitle());
        room.setRoomIntro(data.getIntro());
        room.setRoomPublic(data.getRoomPublic());
        room.setRoomLimit(data.getMax());

        roomRepository.editRoomImage(room, data);
        roomRepository.editRoomPassword(room, data);
        roomRepository.editTag(room, data);
    }

    public RoomNotice saveRoomNotice(Room room, RequestNoticeDto data) {
        RoomNotice saveRoomNotice = RoomNotice.builder()
            .room(room)
            .roomNoticeContent(data.getNotice())
            .roomNoticeDate(LocalDateTime.now())
            .build();

        return roomNoticeJpaRepository.save(saveRoomNotice);
    }

    public void deleteRoomNotice(Room room) {
        RoomNotice roomNotice = room.getRoomNotice();
        roomNoticeJpaRepository.delete(roomNotice);
    }

    public void deleteJoinRoom(JoinRoom joinRoom) {
        joinRoomJpaRepository.delete(joinRoom);
    }
}
