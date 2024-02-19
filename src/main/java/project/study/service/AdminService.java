package project.study.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.study.authority.admin.dto.SearchExpireMemberDto;
import project.study.authority.admin.dto.AdminMembersDto;
import project.study.authority.admin.dto.SearchMemberDto;
import project.study.jpaRepository.AdminJpaRepository;
import project.study.repository.AdminRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final AdminJpaRepository adminJpaRepository;
    private final AdminRepository adminRepository;

    public Page<SearchMemberDto> searchMembers(String word, int pageNumber){
        PageRequest pageable = PageRequest.of(pageNumber - 1, 10);
        return adminRepository.searchMembers(word, pageable);
    }

    public Page<SearchExpireMemberDto> searchExpireMembers(String word, int pageNumber){
        PageRequest pageable = PageRequest.of(pageNumber - 1, 10);
        return adminRepository.searchExpireMembers(word, pageable);
    }

//    public List<AdminMembersDto> findAllByMember(){
//        return adminRepository.findAllByMember();

//    }

    public List<AdminMembersDto> findAllByFreezeMember(){
        return adminRepository.findAllByFreezeMember();
    }


}
