package project.study.authority.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.study.authority.admin.authority.BanAuthority;
import project.study.authority.admin.authority.NotifyMemberInfoAuthority;
import project.study.authority.admin.dto.SearchNotifyDto;
import project.study.domain.Member;
import project.study.domain.Notify;
import project.study.enums.BanEnum;
import project.study.enums.NotifyStatus;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ReportAdmin implements NotifyMemberInfoAuthority, BanAuthority {

    private final NotifyMemberInfoAuthority notifyMemberInfoAuthority;
    private final BanAuthority banAuthority;
    @Override
    public void ban(Long memberId, BanEnum banEnum, String banReason) {
        banAuthority.ban(memberId, banEnum, banReason);
    }
    @Override
    public void banCancel(Long memberId) {
        banAuthority.banCancel(memberId);
    }
    @Override
    public List<Notify> searchNotify(SearchNotifyDto data) {
        return notifyMemberInfoAuthority.searchNotify(data);
    }
    @Override
    public List<Notify> findAllByNotify() {
        return notifyMemberInfoAuthority.findAllByNotify();
    }
    @Override
    public List<Notify> findAllByNotifyWhereNotifyStatus(NotifyStatus notifyStatus) {
        return notifyMemberInfoAuthority.findAllByNotifyWhereNotifyStatus(notifyStatus);
    }
    @Override
    public Optional<Member> findByNotifyMember(Long notifyId) {
        return notifyMemberInfoAuthority.findByNotifyMember(notifyId);
    }
    @Override
    public void notifyConfirm(Long notifyId) {
        notifyMemberInfoAuthority.notifyConfirm(notifyId);
    }
}
