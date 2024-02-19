package project.study.authority.admin.authority;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.study.authority.admin.dto.AdminMembersDto;
import project.study.authority.admin.dto.SearchMemberDto;
import project.study.domain.Member;
import project.study.service.AdminService;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberInfoAuthorityImpl implements MemberInfoAuthority {

    private final AdminService service;

//    @Override
//    public List<AdminMembersDto> findAllByMember() {
//        return service.findAllByMember();
//    }

    @Override
    public List<Member> searchMember(SearchMemberDto data) {
        System.out.println("searchMember 실행");
        return null;
    }

    @Override
    public Optional<Member> findByMemberId(Long memberId) {
        System.out.println("findByMemberId 실행");
        return Optional.empty();
    }
}
