package project.study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class MainController {

    @GetMapping("/room/{roomId}")
    public String roomCreate(@PathVariable(name = "roomId") Long roomId){
        return "room";
    }

    @GetMapping("/")
    public String main(){
        return "main";
    }
}
