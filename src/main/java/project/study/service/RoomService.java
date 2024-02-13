package project.study.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.study.domain.Room;
import project.study.domain.RoomImage;
import project.study.dto.room.RequestCreateRoomDto;
import project.study.repository.RoomRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {

    private final RoomRepository roomRepository;

    @Transactional
    public Long createRoom(RequestCreateRoomDto data) {
        Room room = roomRepository.createRoom(data);
        roomRepository.createRoomImage(data, room);
        roomRepository.createTags(data, room);

        return room.getRoomId();
    }

    public void validRoomData(RequestCreateRoomDto data) {
        roomRepository.validRoomTitle(data.getTitle());
        roomRepository.validRoomIntro(data.getIntro());
        roomRepository.validRoomMaxPerson(data.getMax());
        roomRepository.validTagList(data.getTags());
    }

}
