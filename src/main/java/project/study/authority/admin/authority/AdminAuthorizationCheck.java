package project.study.authority.admin.authority;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.study.authority.admin.OverallAdmin;
import project.study.authority.admin.ReportAdmin;

@Component
@RequiredArgsConstructor
public class AdminAuthorizationCheck {

    private final OverallAdmin overallAdmin;
    private final ReportAdmin reportAdmin;


}
