package project.study.authority.admin.authority;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import project.study.authority.admin.dto.*;
import project.study.domain.Member;
import project.study.domain.Notify;
import project.study.enums.NotifyStatus;
import project.study.service.AdminService;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class NotifyMemberInfoAuthorityImpl implements NotifyMemberInfoAuthority{

    private final AdminService adminService;

    @Override
    public Page<SearchNotifyDto> searchNotify(String word, int pageNumber) {
        return adminService.searchNotify(word, pageNumber);
    }

    @Override
    public Page<SearchNotifyDto> searchNotifyIncludeComplete(String word, int pageNumber) {
        return adminService.searchNotifyIncludeComplete(word, pageNumber);
    }

    @Override
    public SearchNotifyReadMoreDto searchNotifyReadMore(Long notifyId) {
        return adminService.searchNotifyReadMore(notifyId);
    }

    @Override
    public SearchNotifyMemberInfoDto searchNotifyMemberInfo(String account, Long notifyId) {
        return adminService.searchNotifyMemberInfo(account, notifyId);
    }


}
