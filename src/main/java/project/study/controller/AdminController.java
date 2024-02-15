package project.study.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.study.dto.admin.AdminMembersDto;
import project.study.service.AdminService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/admin/members/get")
    public String memberList(Model model){
        List<AdminMembersDto> adminMembersDtos = adminService.memberList();
        model.addAttribute("member", adminMembersDtos);
        return "/admin/admin_members";
    }
}
