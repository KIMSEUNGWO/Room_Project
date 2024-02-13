package project.study.authority.admin.authority;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import project.study.enums.BanEnum;

@Component
public class BanAuthorityImpl implements BanAuthority{

    @Override
    public void ban(Long memberId, BanEnum banEnum, String banReason) {
        System.out.println("ban 실행");
    }

    @Override
    public void banCancel(Long memberId) {
        System.out.println("banCancel 실행");
    }
}
