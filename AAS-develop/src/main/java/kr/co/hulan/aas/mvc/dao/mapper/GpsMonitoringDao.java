package kr.co.hulan.aas.mvc.dao.mapper;

import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.AttendantSituationVo;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.AttendantVo;
import org.springframework.stereotype.Repository;

@Repository
public interface GpsMonitoringDao {

  List<AttendantSituationVo> findAttendantSituationListForMonitoring(Map<String,Object> condition);
  // List<AttendantVo> findAttendantListForMonitoring(Map<String,Object> condition);
  List<AttendantVo> findCurrentAttendantListForMonitoring(Map<String,Object> condition);

  Integer findCurrentWorkerCount(String wpId);

}
