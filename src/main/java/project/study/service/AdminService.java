package project.study.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.study.authority.admin.dto.*;
import project.study.domain.Basic;
import project.study.domain.Member;
import project.study.domain.Notify;
import project.study.domain.Social;
import project.study.jpaRepository.AdminJpaRepository;
import project.study.repository.AdminRepository;

import java.time.LocalDateTime;
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
        Page<Notify> notifies = adminRepository.searchNotify(word, pageable);

        return notifies.map( data-> SearchNotifyDto.builder()
            .criminalMemberAccount(getAccount(data.getCriminal()))
            .reporterMemberAccount(getAccount(data.getReporter()))
            .roomId(data.getRoom().getRoomId())
            .notifyReason(data.getNotifyReason())
            .notifyDate(dateFormat(data.getNotifyDate()))
            .notifyId(data.getNotifyId())
            .notifyContent(data.getNotifyContent())
            .notifyStatus(data.getNotifyStatus())
            .build()
        );
    }

    private String dateFormat(LocalDateTime notifyDate) {
        int year = notifyDate.getYear();
        int month = notifyDate.getMonth().getValue();
        int day = notifyDate.getDayOfMonth();
        return String.format("%d-%d-%d", year, month, day);
    }

    public String getAccount(Member member) {
        Basic basic = member.getBasic();
        Social social = member.getSocial();

        if (basic == null) {
            return social.getSocialEmail();
        }
        return basic.getAccount();
    }


    public Page<SearchMemberDto> findAllByFreezeMember(String word, int pageNumber){
        PageRequest pageable = PageRequest.of(pageNumber - 1, 10);
        return adminRepository.findAllByFreezeMember(word, pageable);
    }



}
