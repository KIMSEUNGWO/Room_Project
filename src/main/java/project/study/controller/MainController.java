package project.study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {

    @GetMapping("/room")
    public String roomCreate(){
        return "room";
    }

    @GetMapping("/")
    public String main(){
        return "main";
    }
}
