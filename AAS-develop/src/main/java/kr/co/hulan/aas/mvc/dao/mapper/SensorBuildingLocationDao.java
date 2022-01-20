package kr.co.hulan.aas.mvc.dao.mapper;

import kr.co.hulan.aas.mvc.api.building.dto.SensorBuildingSituationDto;
import kr.co.hulan.aas.mvc.model.dto.SensorBuildingLocationDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SensorBuildingLocationDao {

  List<SensorBuildingLocationDto> findListByFloor(Map<String,Object> condition);

  List<SensorBuildingLocationDto> findListByCondition(Map<String,Object> condition);
  Long findListCountByCondition(Map<String,Object> condition);


  List<SensorBuildingSituationDto> findSituationListByCondition(Map<String,Object> condition);
  Long findSituationListCountByCondition(Map<String,Object> condition);

  SensorBuildingLocationDto findById(int siIdx);
}
