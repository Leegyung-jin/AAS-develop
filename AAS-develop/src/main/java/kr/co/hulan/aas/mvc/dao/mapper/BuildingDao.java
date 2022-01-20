package kr.co.hulan.aas.mvc.dao.mapper;

import kr.co.hulan.aas.mvc.model.dto.BuildingDto;
import kr.co.hulan.aas.mvc.model.dto.BuildingFloorDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository

public interface BuildingDao {

  List<BuildingDto> findListByCondition(Map<String,Object> condition);
  Long findListCountByCondition(Map<String,Object> condition);

  BuildingDto findById(long buildingNo);


  List<BuildingFloorDto> findFloorListByCondition(Map<String,Object> condition);
  Long findFloorListCountByCondition(Map<String,Object> condition);

  BuildingFloorDto findFloor(BuildingFloorDto dto);
}
