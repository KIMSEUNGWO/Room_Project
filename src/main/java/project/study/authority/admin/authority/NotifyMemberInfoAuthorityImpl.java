package project.study.authority.admin.authority;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import project.study.authority.admin.dto.*;
import project.study.domain.Member;
import project.study.domain.Notify;
import project.study.enums.NotifyStatus;

import java.util.List;
import java.util.Optional;

@Component
public class NotifyMemberInfoAuthorityImpl implements NotifyMemberInfoAuthority{

    @Override
    public Page<SearchNotifyDto> searchNotify(String word, Pageable pageable) {
        return null;
    }

    @Override
    public Page<SearchNotifyDto> searchNotifyIncludeComplete(String word, Pageable pageable) {
        return null;
    }

    @Override
    public SearchNotifyReadMoreDto searchNotifyReadMore(Long notifyId) {
        return null;
    }

    @Override
    public SearchNotifyMemberInfoDto searchNotifyMemberInfo(Long notifyId) {
        return null;
    }


}
