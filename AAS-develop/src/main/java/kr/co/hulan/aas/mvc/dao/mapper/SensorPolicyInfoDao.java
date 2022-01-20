package kr.co.hulan.aas.mvc.dao.mapper;

import kr.co.hulan.aas.mvc.model.dto.SensorPolicyInfoDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SensorPolicyInfoDao {

    List<SensorPolicyInfoDto> findListByCondition(Map<String,Object> condition);
    Long findListCountByCondition(Map<String,Object> condition);

    List<SensorPolicyInfoDto> findSiTypeCodeList();

    SensorPolicyInfoDto findByRange(Map<String,Object> condition);
    SensorPolicyInfoDto findById(int spIdx);
    int createDefaultPolicy(SensorPolicyInfoDto dto);
    int update(SensorPolicyInfoDto dto);
    int resetPolicy(SensorPolicyInfoDto dto);
}
