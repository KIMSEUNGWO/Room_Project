package project.study.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.study.authority.admin.AdminAuthorizationCheck;
import project.study.authority.admin.OverallAdmin;
import project.study.authority.admin.dto.SearchMemberDto;
import project.study.domain.Admin;
import project.study.service.AdminService;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AdminRestController {

    private final AdminService adminService;
    private final AdminAuthorizationCheck check;

    @PostMapping("/admin/login.do")
    public ResponseEntity<String> adminLogin(@RequestParam(value = "account") String account,
                           @RequestParam(value = "password") String password,
                           HttpSession session){

        Optional<Admin> findAdmin = adminService.adminLogin(account, password);
        if (findAdmin.isEmpty()) return ResponseEntity.ok("/admin/login");

        Admin admin = findAdmin.get();
        session.setAttribute("adminId", admin.getAdminId());

        if(!admin.isOverall() && admin.isReport()){
            return ResponseEntity.ok("/admin/notify");
        }
        return ResponseEntity.ok("/admin/members");
    }

    @PostMapping("/admin/logout")
    public ResponseEntity<String> adminLogout(HttpServletRequest request){
        HttpSession session = request.getSession();

        if(session!=null){
            session.invalidate();
        }
        return ResponseEntity.ok("/admin/login");
    }

    @GetMapping("/admin/members/get")
    public ResponseEntity<Page<SearchMemberDto>> searchMember(@RequestParam(value = "word", required = false, defaultValue = "") String word,
                                                              @RequestParam(defaultValue = "1", value = "page") int pageNumber, Model model,
                                                              @RequestParam(value = "onlyFreezeMembers", required = false) String freezeOnly,
                                                              @SessionAttribute(name = "adminId", required = false) Long adminId,
                                                              HttpServletResponse response){
        OverallAdmin overallAdmin = check.getOverallAdmin(adminId, response);

        Admin admin = adminService.findById(adminId).get();
        Page<SearchMemberDto> searchMemberDto = overallAdmin.searchMember(word, pageNumber, freezeOnly);
        return ResponseEntity.ok(searchMemberDto);
    }
}
