package kr.co.hulan.aas.mvc.dao.mapper;

import java.util.Map;
import kr.co.hulan.aas.mvc.model.dto.UiComponentDto;
import kr.co.hulan.aas.mvc.model.dto.WorkGeofenceGroupDto;
import kr.co.hulan.aas.mvc.model.dto.WorkGeofenceInfoDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkGeofenceInfoDao {

  List<WorkGeofenceGroupDto> findGroupListByCondition(Map<String,Object> condition);
  Long countGroupListByCondition(Map<String,Object> condition);

  List<WorkGeofenceInfoDto> findAll(String wpId);

  List<WorkGeofenceGroupDto> findGeofenceGroupListByWorkplace(String wpId);

  WorkGeofenceGroupDto findGroupById(Map<String,Object> condition);
}
