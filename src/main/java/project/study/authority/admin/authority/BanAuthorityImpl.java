package project.study.authority.admin.authority;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import project.study.authority.admin.dto.RequestNotifyMemberFreezeDto;
import project.study.authority.admin.dto.RequestNotifyStatusChangeDto;
import project.study.enums.BanEnum;

@Component
public class BanAuthorityImpl implements BanAuthority{

    @Override
    public void notifyStatusChange(RequestNotifyStatusChangeDto dto) {

    }

    @Override
    public void notifyMemberFreeze(RequestNotifyMemberFreezeDto dto) {

    }
}
