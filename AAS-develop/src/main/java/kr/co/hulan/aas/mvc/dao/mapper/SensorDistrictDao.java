package kr.co.hulan.aas.mvc.dao.mapper;

import kr.co.hulan.aas.mvc.model.dto.SensorDistrictDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SensorDistrictDao {

    List<SensorDistrictDto> findListByCondition(Map<String,Object> condition);
    Long findListCountByCondition(Map<String,Object> condition);

    List<SensorDistrictDto> findSensorSituationListByCondition(Map<String,Object> condition);
    Long findSensorSituationListCountByCondition(Map<String,Object> condition);

    SensorDistrictDto findById(int sdIdx);
    int create(SensorDistrictDto dto);
    int update(SensorDistrictDto dto);
}
