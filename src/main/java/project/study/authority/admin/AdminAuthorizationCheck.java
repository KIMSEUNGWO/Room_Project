package project.study.authority.admin;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.study.authority.admin.OverallAdmin;
import project.study.authority.admin.ReportAdmin;
import project.study.domain.Admin;
import project.study.enums.AuthorityAdminEnum;
import project.study.exceptions.authority.AuthorizationException;
import project.study.jpaRepository.AdminJpaRepository;
import project.study.service.AdminService;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AdminAuthorizationCheck {

    private final AdminJpaRepository adminJpaRepository;
    private final OverallAdmin overallAdmin;
    private final ReportAdmin reportAdmin;

    public OverallAdmin getOverallAdmin(Long adminId, HttpServletResponse response) {
        Optional<Admin> findAdmin = findById(adminId);
        if (findAdmin.isEmpty() || !findAdmin.get().isOverall()) {
            throw new AuthorizationException(response, "권한이 없습니다");
        }
        return overallAdmin;
    }

    public ReportAdmin getReportAdmin(Long adminId, HttpServletResponse response){
        Optional<Admin> findAdmin = findById(adminId);
        if (findAdmin.isEmpty() || !findAdmin.get().isReport()) {
            throw new AuthorizationException(response, "권한이 없습니다");
        }
        return reportAdmin;
    }

    private Optional<Admin> findById(Long adminId) {
        if (adminId == null) return Optional.empty();
        return adminJpaRepository.findById(adminId);
    }
}
