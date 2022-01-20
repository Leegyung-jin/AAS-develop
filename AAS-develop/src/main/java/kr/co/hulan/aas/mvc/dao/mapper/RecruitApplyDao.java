package kr.co.hulan.aas.mvc.dao.mapper;

import kr.co.hulan.aas.mvc.model.dto.G5MemberDto;
import kr.co.hulan.aas.mvc.model.dto.RecruitApplyDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RecruitApplyDao {
    List<RecruitApplyDto> findListByCondition(Map<String,Object> condition);
    Long findListCountByCondition(Map<String,Object> condition);

    RecruitApplyDto findById(int raIdx);

    // int create(RecruitApplyDto dto);
    int updateStatus(RecruitApplyDto dto);

    int updateMbName(G5MemberDto dto);
}
