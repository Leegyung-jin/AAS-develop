package kr.co.hulan.aas.mvc.dao.mapper;

import kr.co.hulan.aas.mvc.model.dto.SensorLogRecentDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SensorLogRecentDao {

  List<SensorLogRecentDto> findListByCondition(Map<String,Object> condition);
}
