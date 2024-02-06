package project.study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoomController {


    @GetMapping("/room/create")
    public String roomCreate(){
        return "room";
    }

    @GetMapping("/main")
    public String main(){
        return "main";
    }
}
