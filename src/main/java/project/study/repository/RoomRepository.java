package project.study.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import project.study.domain.Room;
import project.study.domain.RoomImage;
import project.study.dto.room.RequestCreateRoomDto;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class RoomRepository {


    public void validRoomTitle(String title) {

    }

    public void validRoomIntro(String intro) {

    }

    public void validRoomMaxPerson(String max) {

    }

    public void validTagList(List<String> tags) {

    }

    public Room createRoom(RequestCreateRoomDto data) {
        return null;
    }

    public RoomImage createRoomImage(RequestCreateRoomDto data, Room room) {
        return null;
    }

    public void createTags(RequestCreateRoomDto data, Room room) {

    }
}
