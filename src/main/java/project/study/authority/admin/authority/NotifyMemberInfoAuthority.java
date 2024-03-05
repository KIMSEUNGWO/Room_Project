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

    Page<SearchNotifyDto> searchNotify(String word, int pageNumber);
    Page<SearchNotifyDto> searchNotifyIncludeComplete(String word, int pageNumber);
    SearchNotifyReadMoreDto searchNotifyReadMore(Long notifyId);
    SearchNotifyMemberInfoDto searchNotifyMemberInfo(String account, Long notifyId);

}
