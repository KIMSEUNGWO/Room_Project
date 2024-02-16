package project.study.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.study.authority.member.dto.RequestJoinRoomDto;
import project.study.controller.image.FileUpload;
import project.study.controller.image.FileUploadType;
import project.study.domain.Room;
import project.study.domain.RoomPassword;
import project.study.domain.Tag;
import project.study.authority.member.dto.RequestCreateRoomDto;
import project.study.enums.PublicEnum;
import project.study.exceptions.authority.joinroom.FullRoomException;
import project.study.jpaRepository.RoomJpaRepository;
import project.study.jpaRepository.RoomPasswordJpaRepository;
import project.study.jpaRepository.TagJpaRepository;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class RoomRepository {

    private final RoomJpaRepository roomJpaRepository;
    private final TagJpaRepository tagJpaRepository;
    private final RoomPasswordJpaRepository roomPasswordJpaRepository;
    private final FileUpload fileUpload;

    public void validRoomTitle(String title) {

    }

    public void validRoomIntro(String intro) {

    }

    public void validRoomMaxPerson(String max) {

    }

    public void validTagList(List<String> tags) {

    }

    @Transactional
    public Room createRoom(RequestCreateRoomDto data) {
        Room saveRoom = Room.builder()
            .roomTitle(data.getTitle())
            .roomIntro(data.getIntro())
            .roomLimit(Integer.parseInt(data.getMax()))
            .roomPublic(data.getRoomPublic())
            .build();
        return roomJpaRepository.save(saveRoom);
    }
    @Transactional
    public void createRoomImage(RequestCreateRoomDto data, Room room) {
        fileUpload.saveFile(data.getProfile(), FileUploadType.ROOM_PROFILE, room);
    }
    @Transactional
    public void createTags(RequestCreateRoomDto data, Room room) {
        List<String> tags = data.getTags();
        for (String tag : tags) {
            Tag saveTag = Tag.builder()
                .tagName(tag)
                .room(room)
                .build();
            tagJpaRepository.save(saveTag);
        }
    }

    @Transactional
    public void createPassword(RequestCreateRoomDto data, Room room) {
        if (data.getRoomPublic() == PublicEnum.PUBLIC) return;

        RoomPassword saveRoomPassword = RoomPassword.builder()
            .roomPassword(data.getPassword())
            .room(room)
            .build();

        roomPasswordJpaRepository.save(saveRoomPassword);
    }

    public void validFullRoom(RequestJoinRoomDto data) {
        Room room = data.getRoom();
        int maxPerson = room.getRoomLimit();
        int nowPerson = room.getJoinRoomList().size();
        if (nowPerson >= maxPerson) throw new FullRoomException(data.getResponse(), "방이 가득찼습니다.");
    }
}
