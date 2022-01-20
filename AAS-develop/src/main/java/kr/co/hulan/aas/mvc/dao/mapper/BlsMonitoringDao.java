package kr.co.hulan.aas.mvc.dao.mapper;

import kr.co.hulan.aas.mvc.api.bls.controller.response.BleUserVo;
import kr.co.hulan.aas.mvc.api.bls.dto.BuildingSituation;
import kr.co.hulan.aas.mvc.api.bls.dto.FloorGridSituation;
import kr.co.hulan.aas.mvc.api.bls.dto.FloorSituation;
import kr.co.hulan.aas.mvc.api.bls.dto.FloorSituationDto;
import kr.co.hulan.aas.mvc.api.bls.dto.FloorSituations;
import kr.co.hulan.aas.mvc.api.bls.dto.WorkerBySensor;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.AttendantSituationVo;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.AttendantVo;
import kr.co.hulan.aas.mvc.model.dto.SensorBuildingLocationDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BlsMonitoringDao {

  List<BuildingSituation> findBuildingSituations(Map<String,Object> condition);
  List<FloorSituation> findFloorSituations(Map<String,Object> condition);
  List<FloorSituation> findFloorAll(Map<String,Object> condition);
  List<FloorGridSituation> findGridSituations(Map<String,Object> condition);
  List<WorkerBySensor> findCurrentWorkerListBySensor(Map<String,Object> condition);
  Integer findAllWorkerCountInWorkplaceSensor(String wpId);
  Map<String,Object> findCurrentWorkerCountPerType(String wpId);
  Integer findCurrentWorkerCount(Map<String,Object> condition);

  List<FloorSituationDto> findWorkplaceSituationPerFloor(String wpId);

  List<FloorSituations> findBuildingCrossSectionSituationForSmartMonitoring(Map<String,Object> condition);
  List<FloorGridSituation> findFloorSituationForSmartMonitoring(Map<String,Object> condition);
  SensorBuildingLocationDto findDetectedUserSensorLocation(Map<String,Object> condition);

  List<BleUserVo> searchBleUser(Map<String,Object> condition);

  List<AttendantSituationVo> findAttendantSituationListForMonitoring(Map<String,Object> condition);
  List<AttendantVo> findAttendantListForMonitoring(Map<String,Object> condition);
  List<AttendantVo> findCurrentAttendantListForMonitoring(Map<String,Object> condition);

  List<AttendantVo> findCoopAttendantsForMonitoring(Map<String,Object> condition);

}
