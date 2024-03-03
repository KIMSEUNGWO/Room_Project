package project.study.service;

import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.study.authority.admin.dto.*;
import project.study.domain.*;
import project.study.jpaRepository.AdminJpaRepository;
import project.study.jpaRepository.RoomJpaRepository;
import project.study.repository.AdminRepository;
import project.study.repository.FreezeRepository;
import project.study.repository.RoomRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final AdminJpaRepository adminJpaRepository;
    private final AdminRepository adminRepository;
    private final FreezeRepository freezeRepository;

    public Optional<Admin> adminLogin(String account, String password){

        Optional<Admin> byAccount = adminJpaRepository.findByAccount(account);
        if (byAccount.get().getPassword().equals(password)){
            return adminJpaRepository.findByAccount(account);
        }

        return null;
    }

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

    public SearchNotifyReadMoreDto searchNotifyReadMore(Long notifyId){
        SearchNotifyReadMoreDto searchNotifyReadMoreDto = adminRepository.searchNotifyReadMore(notifyId);
        SearchNotifyImageDto searchNotifyImageDto = adminRepository.searchNotifyImage(notifyId);
        for (String s : searchNotifyImageDto.getNotifyImageOriginalName()){
            searchNotifyReadMoreDto.setNotifyImageStoreName(searchNotifyImageDto.getNotifyImageStoreName());
            searchNotifyReadMoreDto.setNotifyImageOriginalName(searchNotifyImageDto.getNotifyImageOriginalName());
        }
        return searchNotifyReadMoreDto;
    }

    public SearchNotifyMemberInfoDto searchNotifyMemberInfo(String account, Long notifyId){
        SearchNotifyMemberInfoDto searchNotifyMemberInfoDto = adminRepository.searchNotifyMemberInfo(notifyId);
        String memberProfile = adminRepository.findMemberProfile(account);
        searchNotifyMemberInfoDto.setMemberProfile(memberProfile);
        return searchNotifyMemberInfoDto;
    }

    public Page<SearchMemberDto> SearchMemberOnlyFreeze(String word, int pageNumber){
        PageRequest pageable = PageRequest.of(pageNumber - 1, 10);
        return adminRepository.SearchMemberOnlyFreeze(word, pageable);
    }

    public Page<SearchNotifyDto> searchNotifyIncludeComplete(String word, int pageNumber){
        PageRequest pageable = PageRequest.of(pageNumber - 1, 10);
        return adminRepository.searchNotifyIncludeComplete(word, pageable);
    }

    public void notifyStatusChange(RequestNotifyStatusChangeDto dto){
        adminRepository.notifyStatusChange(dto);
    }

    @Transactional
    public void notifyMemberFreeze(RequestNotifyMemberFreezeDto dto){
        adminRepository.notifyMemberFreeze(dto);
        freezeRepository.save(dto);
    }

    @Transactional
    public void deleteRoom (RequestDeleteRoomDto dto){
        adminRepository.deleteJoinRoom(dto);
        adminRepository.insertRoomDelete(dto);
    }


}
