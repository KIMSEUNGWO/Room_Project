package project.study.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.study.authority.admin.dto.SearchExpireMemberDto;
import project.study.authority.admin.dto.AdminMembersDto;
import project.study.authority.admin.dto.SearchMemberDto;
import project.study.service.AdminService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/admin/members/get")
    public String searchMember(@RequestParam(value = "word", required = false, defaultValue = "") String word,
                                @RequestParam(defaultValue = "1", value = "page") int pageNumber,  Model model){
        Page<SearchMemberDto> searchMemberDtoList = adminService.searchMembers(word, pageNumber);
        model.addAttribute("page", searchMemberDtoList);
        model.addAttribute("word", word);
        return "/admin/admin_members";
    }

    @GetMapping("/admin/expire/get")
    public String searchExpireMember(@RequestParam(value = "word", required = false, defaultValue = "") String word,
                                   @RequestParam(defaultValue = "1", value = "page") int pageNumber,  Model model){
        Page<SearchExpireMemberDto> searchExpireMemberList = adminService.searchExpireMembers(word, pageNumber);

        if(searchExpireMemberList.isEmpty()){
            return "redirect:/";
        }
        model.addAttribute("page", searchExpireMemberList);
        model.addAttribute("word", word);
        return "/admin/admin_expire";
    }

//    @GetMapping("/admin/members/get")
//    public String page(@RequestParam(defaultValue = "1", value = "page") int pageNumber, Model model){
//        Page<AdminMembersDto> page = adminService.page(pageNumber);
//        model.addAttribute("page", page);
//        return "/admin/admin_members";
//    }

    @GetMapping("/admin/FreezeMembers/get")
    public String findAllByFreezeMember(Model model){
        List<AdminMembersDto> adminFreezeMemberDtoList = adminService.findAllByFreezeMember();
        model.addAttribute("member", adminFreezeMemberDtoList);
        return "/admin/admin_members";
    }

    @GetMapping("/admin/rooms/get")
    public String roomList(){
        return "/admin/admin_rooms";
    }



    @GetMapping("/admin/notify/get")
    public String notifyMemberList(){
        return "/admin/admin_notify";
    }
}
