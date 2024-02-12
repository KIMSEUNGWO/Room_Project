package project.study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import project.study.constant.WebConst;

import static project.study.constant.WebConst.LOGIN_MEMBER;

@Controller
public class RoomController {


    @GetMapping("/room")
    public String roomCreate(){
        return "room";
    }

    @GetMapping("/")
    public String main(@SessionAttribute(name = LOGIN_MEMBER, required = false) Long memberId){
        System.out.println("memberId = " + memberId);
        return "main";
    }
}
