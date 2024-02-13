package project.study.controller;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.study.dto.abstractentity.ResponseDto;
import project.study.dto.room.RequestCreateRoomDto;
import project.study.dto.room.ResponseCreateRoomDto;
import project.study.service.RoomService;

@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping(value = "/room/create")
    public ResponseEntity<ResponseDto> createRoom(@ModelAttribute RequestCreateRoomDto data) {
        System.out.println("data = " + data);

        roomService.validRoomData(data);
        Long roomId = roomService.createRoom(data);

        String redirectURI = "/room/" + roomId;
        return new ResponseEntity<>(new ResponseCreateRoomDto("ok", "방 생성 완료", redirectURI), HttpStatus.OK);
    }


}
