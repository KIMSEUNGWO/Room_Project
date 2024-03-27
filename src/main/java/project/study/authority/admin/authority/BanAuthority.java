package project.study.authority.admin.authority;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import project.study.authority.admin.dto.*;
import project.study.enums.BanEnum;

public interface BanAuthority {

    void notifyComplete(RequestNotifyStatusChangeDto dto);
    void notifyFreeze(RequestNotifyMemberFreezeDto dto, HttpServletResponse response);

    Page<SearchBanDto> searchBanList(int pageNumber, String word);
    void liftTheBan(RequestLiftBanDto dto);
}
