package project.study.authority.admin.authority;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.study.authority.admin.dto.SearchMemberDto;
import project.study.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberInfoAuthority {

    Page<SearchMemberDto> searchMember(String word, int pageNumber);
    Page<SearchMemberDto> SearchMemberOnlyFreeze(String word, int pageNumber);

}
