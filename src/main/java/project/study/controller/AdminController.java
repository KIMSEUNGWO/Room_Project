package project.study.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.study.authority.admin.dto.*;
import project.study.domain.Admin;
import project.study.service.AdminService;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/admin/login")
    public String adminLogin(){
        return "/admin/admin_login";
    }

    @PostMapping("/admin/login")
    public String adminLogin(@RequestParam(value = "account") String account,
                             @RequestParam(value = "password") String password){
        Optional<Admin> admin1 = adminService.adminLogin(account, password);
        if (admin1.isEmpty()){
            return "redirect:/admin/login";
        }

        return "redirect:/admin/members/get";
    }

    @GetMapping("/admin/members/get")
    public String searchMember(@RequestParam(value = "word", required = false, defaultValue = "") String word,
                               @RequestParam(defaultValue = "1", value = "page") int pageNumber, Model model,
                               @RequestParam(value = "onlyFreezeMembers", required = false) String freezeOnly){

        if (freezeOnly == null || !freezeOnly.equals("on")) {

            Page<SearchMemberDto> searchMemberDtoList = adminService.searchMember(word, pageNumber);

            if(searchMemberDtoList.isEmpty()){
                model.addAttribute("page", searchMemberDtoList);
                model.addAttribute("word", word);
                model.addAttribute("errorMsg", "결과가 존재하지 않습니다.");
                model.addAttribute("freezeOnly", freezeOnly != null);

                return "/admin/admin_members";
            }
            model.addAttribute("page", searchMemberDtoList);
            model.addAttribute("word", word);
            model.addAttribute("freezeOnly", freezeOnly != null);

            return "/admin/admin_members";

        }

        Page<SearchMemberDto> freezeMemberList = adminService.SearchMemberOnlyFreeze(word, pageNumber);

        if (freezeMemberList.isEmpty()) {
            model.addAttribute("page", freezeMemberList);
            model.addAttribute("word", word);
            model.addAttribute("errorMsg", "결과가 존재하지 않습니다.");
            model.addAttribute("freezeOnly", freezeOnly != null);
            return "/admin/admin_members";
        }

        model.addAttribute("page", freezeMemberList);
        model.addAttribute("word", word);
        model.addAttribute("freezeOnly", freezeOnly != null);
        return "/admin/admin_members";

    }

    @GetMapping("/admin/expire/get")
    public String searchExpireMember(@RequestParam(value = "word", required = false, defaultValue = "") String word,
                                   @RequestParam(defaultValue = "1", value = "page") int pageNumber,  Model model){
        Page<SearchExpireMemberDto> searchExpireMemberList = adminService.searchExpireMember(word, pageNumber);

        if(searchExpireMemberList.isEmpty()){
            model.addAttribute("page", searchExpireMemberList);
            model.addAttribute("word", word);
            model.addAttribute("errorMsg", "결과가 존재하지 않습니다.");
            return "/admin/admin_expire";
        }
        model.addAttribute("page", searchExpireMemberList);
        model.addAttribute("word", word);
        return "/admin/admin_expire";
    }


    @GetMapping("/admin/rooms/get")
    public String searchRoom(@RequestParam(value = "word", required = false, defaultValue = "") String word,
                           @RequestParam(defaultValue = "1", value = "page") int pageNumber,  Model model){
        Page<SearchRoomDto> searchRoomList = adminService.searchRoom(word, pageNumber);

        if(searchRoomList.isEmpty()){
            model.addAttribute("page", searchRoomList);
            model.addAttribute("word", word);
            model.addAttribute("errorMsg", "결과가 존재하지 않습니다.");
            return "/admin/admin_rooms";
        }
        model.addAttribute("page", searchRoomList);
        model.addAttribute("word", word);

        return "/admin/admin_rooms";
    }

    @GetMapping("/admin/notify/get")
    public String searchNotify(@RequestParam(value = "word", required = false, defaultValue = "") String word,
                                   @RequestParam(defaultValue = "1", value = "page") int pageNumber,  Model model,
                                   @RequestParam(value = "withComplete", required = false) String containComplete){

        if(containComplete==null || !containComplete.equals("on")){

            Page<SearchNotifyDto> searchNotifyList = adminService.searchNotify(word, pageNumber);
            if(searchNotifyList.isEmpty()){
                model.addAttribute("page", searchNotifyList);
                model.addAttribute("word", word);
                model.addAttribute("errorMsg", "결과가 존재하지 않습니다.");
                model.addAttribute("containComplete", containComplete != null);
                return "/admin/admin_notify";
            }
            model.addAttribute("page", searchNotifyList);
            model.addAttribute("word", word);
            model.addAttribute("containComplete", containComplete != null);
            return "/admin/admin_notify";
        }

        Page<SearchNotifyDto> searchNotifyList = adminService.searchNotifyIncludeComplete(word, pageNumber);

        if(searchNotifyList.isEmpty()){
            model.addAttribute("page", searchNotifyList);
            model.addAttribute("word", word);
            model.addAttribute("errorMsg", "결과가 존재하지 않습니다.");
            model.addAttribute("containComplete", containComplete != null);
            return "/admin/admin_notify";
        }
        model.addAttribute("page", searchNotifyList);
        model.addAttribute("word", word);
        model.addAttribute("containComplete", containComplete != null);
        return "/admin/admin_notify";
    }

    @GetMapping("/admin/notify/reed_more")
    public String notifyReadMore(@RequestParam(value = "word", required = false, defaultValue = "") String word,
                                 @RequestParam(defaultValue = "1", value = "page") int pageNumber){
        Page<SearchNotifyDto> searchNotifyList = adminService.searchNotify(word, pageNumber);
        for (SearchNotifyDto searchNotifyDto : searchNotifyList.getContent()) {
            System.out.println("searchNotifyDto.getNotifyId() = " + searchNotifyDto.getNotifyId());
        }
        return "/admin/notify_reed_more";
    }

    @GetMapping("/reciveData")
    public void some(@RequestParam("buttonId") Long buttonId){
        System.out.println("buttonId = " + buttonId);
    }

}
