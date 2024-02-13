package project.study.authority.admin.authority;

import project.study.authority.admin.dto.SearchNotifyDto;
import project.study.domain.Member;
import project.study.domain.Notify;
import project.study.enums.NotifyStatus;

import java.util.List;
import java.util.Optional;

public interface NotifyMemberInfoAuthority {

    List<Notify> searchNotify(SearchNotifyDto data);
    List<Notify> findAllByNotify();
    List<Notify> findAllByNotifyWhereNotifyStatus(NotifyStatus notifyStatus);
    Optional<Member> findByNotifyMember(Long notifyId);
    void notifyConfirm(Long notifyId);
}
