package project.study.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.study.authority.member.dto.ResponseRoomListDto;
import project.study.domain.Member;
import project.study.domain.Room;
import project.study.authority.member.dto.RequestCreateRoomDto;
import project.study.domain.Tag;
import project.study.repository.JoinRoomRepository;
import project.study.repository.RoomRepository;
import project.study.repository.TagRepository;

import java.util.ArrayList;
import java.util.List;

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

    private List<String> convertTagList(List<Tag> tags) {
        List<String> temp = new ArrayList<>();
        for (Tag tag : tags) {
            temp.add(tag.getTagName());
        }
        return temp;
    }
}
