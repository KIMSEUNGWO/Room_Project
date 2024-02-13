package project.study.authority.admin.authority;

import org.springframework.stereotype.Component;
import project.study.authority.admin.dto.SearchMemberDto;
import project.study.domain.Member;

import java.util.List;
import java.util.Optional;

@Component
public class MemberInfoAuthorityImpl implements MemberInfoAuthority {

    @Override
    public List<Member> findAllByMember() {
        System.out.println("findAllByMember 실행");
        return null;
    }

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
