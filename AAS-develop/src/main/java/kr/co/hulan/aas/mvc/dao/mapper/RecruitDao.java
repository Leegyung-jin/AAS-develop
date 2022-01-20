package kr.co.hulan.aas.mvc.dao.mapper;

import kr.co.hulan.aas.mvc.model.dto.RecruitDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RecruitDao {

    List<RecruitDto> findListByCondition(Map<String,Object> condition);
    Long findListCountByCondition(Map<String,Object> condition);

    RecruitDto findById(int rcIdx);

    int create(RecruitDto dto);
    int update(RecruitDto dto);
    int updateExposeTime(int rcIdx);

}
