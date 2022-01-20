package kr.co.hulan.aas.mvc.dao.mapper;

import kr.co.hulan.aas.mvc.model.dto.SensorLogInoutDto;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SensorLogInoutDao {

    List<SensorLogInoutDto> findSafetySituationListByCondition(Map<String,Object> condition);
    Long findSafetySituationListCountByCondition(Map<String,Object> condition);
    Integer getWorkCount(Map<String,Object> condition);
}
