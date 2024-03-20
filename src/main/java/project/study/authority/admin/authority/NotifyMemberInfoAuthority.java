package project.study.authority.admin.authority;

import org.springframework.data.domain.Page;
import project.study.authority.admin.dto.*;

public interface NotifyMemberInfoAuthority {

    Page<SearchNotifyDto> searchNotify(String word, int pageNumber, String containComplete);
    Page<SearchNotifyDto> searchNotifyList(int pageNumber, String word, String containComplete);
    SearchNotifyReadMoreDto searchNotifyReadMore(Long notifyId);
    SearchNotifyMemberInfoDto searchNotifyMemberInfo(String account, Long notifyId);

}
