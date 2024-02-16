package project.study.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.study.authority.admin.dto.AdminExpireMembersDto;
import project.study.authority.admin.dto.AdminMembersDto;
import project.study.service.AdminService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/admin/members/get")
    public String findAllByMember(Model model){
        List<AdminMembersDto> adminMembersDtoList = adminService.findAllByMember();
        model.addAttribute("member", adminMembersDtoList);
        return "/admin/admin_members";
    }

    @GetMapping("/admin/rooms/get")
    public String roomList(){
        return "/admin/admin_rooms";
    }

    @GetMapping("/admin/expire/get")
    public String expireMemberList(Model model){
        List<AdminExpireMembersDto> adminExpireMembersDtoList = adminService.findAllByExpireMember();
        model.addAttribute("member", adminExpireMembersDtoList);
        return "/admin/admin_expire";
    }

    @GetMapping("/admin/notify/get")
    public String notifyMemberList(){
        return "/admin/admin_notify";
    }
}
