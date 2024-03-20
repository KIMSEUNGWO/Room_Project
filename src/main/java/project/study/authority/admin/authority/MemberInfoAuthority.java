package project.study.authority.admin.authority;

import org.springframework.data.domain.Page;
import project.study.authority.admin.dto.SearchConditionDto;
import project.study.authority.admin.dto.SearchMemberDto;

public interface MemberInfoAuthority {

    Page<SearchMemberDto> searchMember(String word, String freezeOnly, int pageNumber);
    Page<SearchMemberDto> searchMemberList(int pageNumber, String word, String freezeOnly);

}
