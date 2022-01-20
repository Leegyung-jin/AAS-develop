package kr.co.hulan.aas.mvc.dao.mapper;


import kr.co.hulan.aas.mvc.api.member.dto.VisitDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface VisitDao {
    List<VisitDto> findListByCondition(Map<String,Object> condition);
    Long findListCountByCondition(Map<String,Object> condition);
}
