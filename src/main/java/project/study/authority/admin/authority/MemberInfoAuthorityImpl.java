package project.study.authority.admin.authority;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import project.study.authority.admin.dto.SearchMemberDto;
import project.study.domain.Member;
import project.study.service.AdminService;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberInfoAuthorityImpl implements MemberInfoAuthority {

    private final AdminService adminService;

    @Override
    public Page<SearchMemberDto> searchMember(String word, Pageable pageable) {
        return null;
    }

    @Override
    public Page<SearchMemberDto> SearchMemberOnlyFreeze(String word, Pageable pageable) {
        return null;
    }
}
