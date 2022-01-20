package kr.co.hulan.aas.mvc.dao.mapper;

import kr.co.hulan.aas.mvc.model.dto.G5MemberDto;
import kr.co.hulan.aas.mvc.model.dto.SensorLogDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SensorLogDao {

    List<SensorLogDto> findListByCondition(Map<String,Object> condition);
    Long findListCountByCondition(Map<String,Object> condition);



    List<SensorLogDto> findByIdList(Map<String,Object> condition);
    SensorLogDto findById(int slIdx);

    List<SensorLogDto> findWorkerSafetySensorLogListByCondition(Map<String,Object> conditionMap);
    Long findWorkerSafetySensorLogListCountByCondition(Map<String,Object> conditionMap);
}
