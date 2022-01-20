package kr.co.hulan.aas.mvc.dao.mapper;

import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.mvc.api.gps.controller.response.GpsUserVo;
import kr.co.hulan.aas.mvc.api.monitor.dto.GpsObjectVo;
import kr.co.hulan.aas.mvc.model.dto.GpsLocationHistoryDto;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository
public interface GpsLocationDao {
  List<Map<String, Object>> selectGpsLocationWorker(Map<String, Object> map) throws DataAccessException;

  List<Map<String, Object>> selectGpsLocationEquipment(Map<String, Object> map) throws DataAccessException;

  List<GpsLocationHistoryDto> selectGpsLocationHistory(Map<String, Object> map);
  List<GpsUserVo> searchGpsUser(Map<String, Object> map);


  List<GpsObjectVo> findGpsLocationWorker(Map<String, Object> map) throws DataAccessException;
  List<GpsObjectVo> findGpsLocationEquipment(Map<String, Object> map) throws DataAccessException;

}
