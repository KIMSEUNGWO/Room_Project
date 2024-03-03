package project.study.authority.admin.authority;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import project.study.authority.admin.dto.SearchExpireMemberDto;

@Component
@RequiredArgsConstructor
public class ExpireMemberInfoAuthorityImpl implements ExpireMemberInfoAuthority{

    @Override
    public Page<SearchExpireMemberDto> searchExpireMember(String word, Pageable pageable) {
        return null;
    }
}
