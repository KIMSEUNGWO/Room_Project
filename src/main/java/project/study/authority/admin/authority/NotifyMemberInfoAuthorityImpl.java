package project.study.authority.admin.authority;

import org.springframework.stereotype.Component;
import project.study.authority.admin.dto.SearchNotifyDto;
import project.study.domain.Member;
import project.study.domain.Notify;
import project.study.enums.NotifyStatus;

import java.util.List;
import java.util.Optional;

@Component
public class NotifyMemberInfoAuthorityImpl implements NotifyMemberInfoAuthority{
    @Override
    public List<Notify> searchNotify(SearchNotifyDto data) {
        System.out.println("searchNotify 실행");
        return null;
    }

    @Override
    public List<Notify> findAllByNotify() {
        System.out.println("findAllByNotify 실행");
        return null;
    }

    @Override
    public List<Notify> findAllByNotifyWhereNotifyStatus(NotifyStatus notifyStatus) {
        System.out.println("findAllByNotifyWhereNotifyStatus 실행");
        return null;
    }

    @Override
    public Optional<Member> findByNotifyMember(Long notifyId) {
        System.out.println("findByNotifyMember 실행");
        return Optional.empty();
    }

    @Override
    public void notifyConfirm(Long notifyId) {
        System.out.println("notifyConfirm 실행");

    }
}
