package project.study.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.study.authority.admin.dto.*;
import project.study.jpaRepository.AdminJpaRepository;
import project.study.repository.AdminRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final AdminJpaRepository adminJpaRepository;
    private final AdminRepository adminRepository;

    public Page<SearchMemberDto> searchMember(String word, int pageNumber){
        PageRequest pageable = PageRequest.of(pageNumber - 1, 10);
        return adminRepository.searchMember(word, pageable);
    }

    public Page<SearchExpireMemberDto> searchExpireMember(String word, int pageNumber){
        PageRequest pageable = PageRequest.of(pageNumber - 1, 10);
        return adminRepository.searchExpireMember(word, pageable);
    }

    public Page<SearchRoomDto> searchRoom(String word, int pageNumber){
        PageRequest pageable = PageRequest.of(pageNumber - 1, 10);
        return adminRepository.searchRoom(word, pageable);
    }


    public Page<SearchNotifyDto> searchNotify(String word, int pageNumber){
        PageRequest pageable = PageRequest.of(pageNumber - 1, 10);
        return adminRepository.searchNotify(word, pageable);
    }


    public Page<SearchMemberDto> findAllByFreezeMember(String word, int pageNumber){
        PageRequest pageable = PageRequest.of(pageNumber - 1, 10);
        return adminRepository.findAllByFreezeMember(word, pageable);
    }



}
