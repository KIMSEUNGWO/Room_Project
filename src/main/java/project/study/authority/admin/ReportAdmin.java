package project.study.authority.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import project.study.authority.admin.authority.BanAuthority;
import project.study.authority.admin.authority.NotifyMemberInfoAuthority;
import project.study.authority.admin.dto.*;

@Component
@RequiredArgsConstructor
public class ReportAdmin implements NotifyMemberInfoAuthority, BanAuthority {

    private final NotifyMemberInfoAuthority notifyMemberInfoAuthority;
    private final BanAuthority banAuthority;

    @Override
    public void notifyStatusChange(RequestNotifyStatusChangeDto dto) {
        banAuthority.notifyStatusChange(dto);
    }

    @Override
    public void notifyComplete(RequestNotifyStatusChangeDto dto) {
        banAuthority.notifyComplete(dto);
    }

    @Override
    public void notifyMemberFreeze(RequestNotifyMemberFreezeDto dto) {
        banAuthority.notifyMemberFreeze(dto);
    }

    @Override
    public void notifyFreeze(RequestNotifyMemberFreezeDto dto) {
        banAuthority.notifyFreeze(dto);
    }

    @Override
    public Page<SearchNotifyDto> searchNotify(String word, int pageNumber, String containComplete) {
        return notifyMemberInfoAuthority.searchNotify(word, pageNumber, containComplete);
    }

    @Override
    public Page<SearchNotifyDto> searchNotifyList(int pageNumber, String word, String containComplete) {
        return notifyMemberInfoAuthority.searchNotifyList(pageNumber, word, containComplete);
    }

    @Override
    public SearchNotifyReadMoreDto searchNotifyReadMore(Long notifyId) {
        return notifyMemberInfoAuthority.searchNotifyReadMore(notifyId);
    }

    @Override
    public SearchNotifyReadMoreDtoBatis notifyReedMoreBatis(Long notifyId) {
        return notifyMemberInfoAuthority.notifyReedMoreBatis(notifyId);
    }

    @Override
    public SearchNotifyMemberInfoDto searchNotifyMemberInfo(String account, Long notifyId) {
        return notifyMemberInfoAuthority.searchNotifyMemberInfo(account, notifyId);
    }
}
