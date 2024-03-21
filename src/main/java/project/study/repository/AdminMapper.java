package project.study.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;
import project.study.authority.admin.dto.*;

import java.util.List;

@Mapper
public interface AdminMapper {

    List<SearchMemberDto> searchMemberList(@Param("startNum") int startNum, @Param("endNum") int endNum, @Param("word") String word, @Param("freezeOnly") String freezeOnly);
    int getTotalMemberCnt(@Param("word") String word, @Param("freezeOnly") String freezeOnly);

    List<SearchExpireMemberDto> searchExpireMemberList(@Param("startNum") int startNum, @Param("endNum") int endNum, @Param("word") String word);
    int getTotalExpireMemberCnt(String word);

    List<SearchRoomDto> searchRoomList(@Param("startNum") int startNum, @Param("endNum") int endNum, @Param("word") String word);
    int getTotalRoomCnt(String word);

    void joinRoomDelete(RequestDeleteRoomDto dto);
    void insertRoomDelete(RequestDeleteRoomDto dto);

    List<SearchNotifyDto> searchNotifyList(@Param("startNum") int startNum, @Param("endNum") int endNum, @Param("word") String word, @Param("containComplete") String containComplete);
    int getTotalNotifyCnt(@Param("word") String word, @Param("containComplete") String containComplete);

    SearchNotifyReadMoreDtoBatis notifyReedMore(Long notifyId);
    List<SearchNotifyImageDtoBatis> notifyImage(Long notifyId);
    void notifyStatusChange(RequestNotifyStatusChangeDto dto);
    void notifyMemberFreeze(Long memberId);
    Long freezeMemberSelect(Long memberId);
    void newFreeze(RequestNotifyMemberFreezeDto dto);
}
