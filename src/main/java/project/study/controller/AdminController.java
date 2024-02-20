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
import project.study.authority.admin.dto.SearchRoomDto;
import project.study.service.AdminService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/admin/members/get")
    public String searchMember(@RequestParam(value = "word", required = false, defaultValue = "") String word,
                                @RequestParam(defaultValue = "1", value = "page") int pageNumber,  Model model){
        Page<SearchMemberDto> searchMemberDtoList = adminService.searchMember(word, pageNumber);

        if(searchMemberDtoList.isEmpty()){
            model.addAttribute("page", searchMemberDtoList);
            model.addAttribute("word", word);
            model.addAttribute("errorMsg", "결과가 존재하지 않습니다.");
            return "/admin/admin_members";
        }
        model.addAttribute("page", searchMemberDtoList);
        model.addAttribute("word", word);
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
    public String notifyMemberList(){
        return "/admin/admin_notify";
    }

    @GetMapping("/admin/FreezeMembers/get")
    public String findAllByFreezeMember(Model model){
        List<AdminMembersDto> adminFreezeMemberDtoList = adminService.findAllByFreezeMember();
        model.addAttribute("member", adminFreezeMemberDtoList);
        return "/admin/admin_members";
    }
}
