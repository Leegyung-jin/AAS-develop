package kr.co.hulan.aas.mvc.dao.mapper;

import kr.co.hulan.aas.mvc.model.dto.SensorInfoDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SensorInfoDao {

    List<SensorInfoDto> findListByCondition(Map<String,Object> condition);
    Long findListCountByCondition(Map<String,Object> condition);

    List<SensorInfoDto> findSensorSituationListByCondition(Map<String,Object> condition);
    Long findSensorSituationListCountByCondition(Map<String,Object> condition);

    SensorInfoDto findById(int siIdx);
    int create(SensorInfoDto dto);
    int update(SensorInfoDto dto);

}
