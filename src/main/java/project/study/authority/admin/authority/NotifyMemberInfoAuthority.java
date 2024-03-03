package project.study.authority.admin.authority;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.study.authority.admin.dto.*;
import project.study.domain.Member;
import project.study.domain.Notify;
import project.study.enums.NotifyStatus;

import java.util.List;
import java.util.Optional;

public interface NotifyMemberInfoAuthority {

    Page<SearchNotifyDto> searchNotify(String word, Pageable pageable);
    Page<SearchNotifyDto> searchNotifyIncludeComplete(String word, Pageable pageable);
    SearchNotifyReadMoreDto searchNotifyReadMore(Long notifyId);
    SearchNotifyMemberInfoDto searchNotifyMemberInfo(Long notifyId);

}
