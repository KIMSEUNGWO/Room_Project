package project.study.authority.admin.authority;

import project.study.authority.admin.dto.AdminMembersDto;
import project.study.authority.admin.dto.SearchMemberDto;
import project.study.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberInfoAuthority {

//    List<AdminMembersDto> findAllByMember();
    List<Member> searchMember(SearchMemberDto data);
    Optional<Member> findByMemberId(Long memberId);
}
