package project.study.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.study.authority.admin.dto.AdminExpireMembersDto;
import project.study.authority.admin.dto.AdminMembersDto;
import project.study.jpaRepository.AdminJpaRepository;
import project.study.repository.AdminRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final AdminJpaRepository adminJpaRepository;
    private final AdminRepository adminRepository;

    public List<AdminMembersDto> findAllByMember(){
        return adminRepository.findAllByMember();
    }

    public List<AdminExpireMembersDto> findAllByExpireMember(){
        return adminRepository.findAllByExpireMember();
    }
}
