package project.study.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import project.study.customAnnotation.PathRoom;
import project.study.customAnnotation.SessionLogin;
import project.study.domain.Member;
import project.study.domain.Room;


@Controller
@RequiredArgsConstructor
public class MainController {


    @GetMapping("/room/{roomId}")
    public String roomCreate(@PathRoom("roomId") Room roomId){
        System.out.println("roomId = " + roomId.getRoomId());
        return "room";
    }

    @GetMapping("/")
    public String main(@SessionLogin Member member){

        return "main";
    }
}
