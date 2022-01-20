package kr.co.hulan.aas.mvc.dao.mapper;

import kr.co.hulan.aas.mvc.model.dto.SensorLogTraceDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SensorLogTraceDao {


    List<SensorLogTraceDto> findListByCondition(Map<String,Object> condition);
    Long findListCountByCondition(Map<String,Object> condition);



    List<SensorLogTraceDto> findByIdList(Map<String,Object> condition);
    SensorLogTraceDto findById(int slIdx);

    List<SensorLogTraceDto> findWorkerSafetySensorLogListByCondition(Map<String,Object> conditionMap);
    Long findWorkerSafetySensorLogListCountByCondition(Map<String,Object> conditionMap);
}
