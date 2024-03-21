package project.study.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.study.authority.admin.dto.*;
import project.study.domain.*;
import project.study.dto.admin.Criteria;
import project.study.enums.BanEnum;
import project.study.enums.NotifyStatus;
import project.study.jpaRepository.AdminJpaRepository;
import project.study.repository.AdminMapper;
import project.study.repository.AdminRepository;
import project.study.repository.FreezeRepository;
import retrofit2.http.PUT;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final AdminJpaRepository adminJpaRepository;
    private final AdminRepository adminRepository;
    private final FreezeRepository freezeRepository;
    private final AdminMapper adminMapper;
    private final Criteria criteria;

    public Page<SearchMemberDto> searchMemberList(int pageNumber, String word, String freezeOnly){
        List<SearchMemberDto> data = adminMapper.searchMemberList(criteria.getStartNum(pageNumber), criteria.getEndNum(pageNumber), word, freezeOnly);
        return new PageImpl<>(data, criteria.getPageable(pageNumber), adminMapper.getTotalMemberCnt(word, freezeOnly));
    }

    public Page<SearchExpireMemberDto> searchExpireMemberList(int pageNumber, String word){
        List<SearchExpireMemberDto> data = adminMapper.searchExpireMemberList(criteria.getStartNum(pageNumber), criteria.getEndNum(pageNumber), word);
        return new PageImpl<>(data, criteria.getPageable(pageNumber), adminMapper.getTotalExpireMemberCnt(word));
    }

    public Page<SearchRoomDto> searchRoomList(int pageNumber, String word){
        List<SearchRoomDto> data = adminMapper.searchRoomList(criteria.getStartNum(pageNumber), criteria.getEndNum(pageNumber), word);
        return new PageImpl<>(data, criteria.getPageable(pageNumber), adminMapper.getTotalRoomCnt(word));
    }

    @Transactional
    public void roomDelete(RequestDeleteRoomDto dto){
        adminMapper.joinRoomDelete(dto);
        adminMapper.insertRoomDelete(dto);
    }

    public Page<SearchNotifyDto> searchNotifyList(int pageNumber, String word, String containComplete){
        List<SearchNotifyDto> data = adminMapper.searchNotifyList(criteria.getStartNum(pageNumber), criteria.getEndNum(pageNumber), word, containComplete);
        return new PageImpl<>(data, criteria.getPageable(pageNumber), adminMapper.getTotalNotifyCnt(word, containComplete));
    }

    public SearchNotifyReadMoreDtoBatis notifyReedMoreBatis(Long notifyId){
        SearchNotifyReadMoreDtoBatis searchNotifyReadMoreDtoBatis = adminMapper.notifyReedMore(notifyId);
        List<SearchNotifyImageDtoBatis> searchNotifyImageDtoBatis = adminMapper.notifyImage(notifyId);

        searchNotifyReadMoreDtoBatis.setNotifyImages(searchNotifyImageDtoBatis);
        return searchNotifyReadMoreDtoBatis;
    }

    @Transactional
    public void notifyComplete(RequestNotifyStatusChangeDto dto){
        adminMapper.notifyStatusChange(dto);
    }

    @Transactional
    public void notifyFreeze(RequestNotifyMemberFreezeDto dto){
        adminMapper.notifyMemberFreeze(dto.getMemberId());
        Long select = adminMapper.freezeMemberSelect(dto.getMemberId());
        if(select==null){
            int banEnum = dto.getFreezePeriod().getBanEnum();
            adminMapper.newFreeze(dto);
        }
    }

    @Transactional
    public Optional<Admin> adminLogin(String account, String password){
        Optional<Admin> byAccount = adminJpaRepository.findByAccount(account);

        // TODO
        // 검증해야됨
        if(byAccount.isPresent() && byAccount.get().isMatchesPassword(password)){
            return adminJpaRepository.findByAccount(account);
        }
        return Optional.empty();
    }

    public Optional<Admin> findById(Long id){
        if (id == null) return Optional.empty();
        return adminJpaRepository.findById(id);
    }

    public Page<SearchMemberDto> searchMember(String word, String freezeOnly, int pageNumber){
        PageRequest pageable = PageRequest.of(pageNumber - 1, 10);
        return adminRepository.searchMember(word, freezeOnly, pageable);
    }

    public Page<SearchExpireMemberDto> searchExpireMember(String word, int pageNumber){
        PageRequest pageable = PageRequest.of(pageNumber - 1, 10);
        return adminRepository.searchExpireMember(word, pageable);
    }

    public Page<SearchRoomDto> searchRoom(String word, int pageNumber){
        PageRequest pageable = PageRequest.of(pageNumber - 1, 10);
        return adminRepository.searchRoom(word, pageable);
    }

    public Page<SearchNotifyDto> searchNotify(String word, int pageNumber, String containComplete){
        PageRequest pageable = PageRequest.of(pageNumber - 1, 10);
        return adminRepository.searchNotify(word, pageable, containComplete);
    }

    public SearchNotifyReadMoreDto searchNotifyReadMore(Long notifyId){
        SearchNotifyReadMoreDto readMore = adminRepository.searchNotifyReadMore(notifyId);
        SearchNotifyImageDto notifyImage = adminRepository.searchNotifyImage(notifyId);

        readMore.setImage(notifyImage);

        return readMore;
    }

    public SearchNotifyMemberInfoDto searchNotifyMemberInfo(String account, Long notifyId){
        SearchNotifyMemberInfoDto searchNotifyMemberInfoDto = adminRepository.searchNotifyMemberInfo(notifyId);
        String memberProfile = adminRepository.findMemberProfile(account);
        searchNotifyMemberInfoDto.setMemberProfile(memberProfile);
        return searchNotifyMemberInfoDto;
    }


    @Transactional
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

    public int getTotalMemberCnt(String word, String freezeOnly) {
        return adminMapper.getTotalMemberCnt(word, freezeOnly);
    }
}
