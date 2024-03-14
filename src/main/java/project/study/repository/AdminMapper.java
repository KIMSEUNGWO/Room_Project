package project.study.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import project.study.authority.admin.dto.SearchMemberDto;

import java.util.List;

@Mapper
public interface AdminMapper {

    List<SearchMemberDto> searchMember(@Param("startNum") int startNum, @Param("endNum") int endNum);
    int getTotalCnt();

}
