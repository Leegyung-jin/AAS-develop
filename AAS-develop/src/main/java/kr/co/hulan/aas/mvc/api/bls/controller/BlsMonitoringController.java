package kr.co.hulan.aas.mvc.api.bls.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.bls.controller.request.BleMonitoringDataRequest;
import kr.co.hulan.aas.mvc.api.bls.controller.request.MonitoringInfoForBuildingRequest;
import kr.co.hulan.aas.mvc.api.bls.controller.request.MonitoringInfoForFloorRequest;
import kr.co.hulan.aas.mvc.api.bls.controller.request.SearchBleUserRequest;
import kr.co.hulan.aas.mvc.api.bls.controller.request.SearchUserLocationRequest;
import kr.co.hulan.aas.mvc.api.bls.controller.response.BleMonitoringDataResponse;
import kr.co.hulan.aas.mvc.api.bls.controller.response.BleUserVo;
import kr.co.hulan.aas.mvc.api.bls.controller.response.MonitoringInfoForBuildingResponse;
import kr.co.hulan.aas.mvc.api.bls.controller.response.MonitoringInfoForFloorResponse;
import kr.co.hulan.aas.mvc.api.bls.controller.response.MonitoringInfoForWorkplaceResponse;
import kr.co.hulan.aas.mvc.api.bls.controller.response.MonitoringInfoWorkersBySensorResponse;
import kr.co.hulan.aas.mvc.api.bls.controller.response.MonitoringWokspaceResponse;
import kr.co.hulan.aas.mvc.api.bls.service.BlsMonitoringService;
import kr.co.hulan.aas.mvc.api.monitor.controller.request.ExportAttendantRequest;
import kr.co.hulan.aas.mvc.api.monitor.controller.request.ExportAttendantSituationRequest;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.AttendantSituationVo;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.AttendantVo;
import kr.co.hulan.aas.mvc.model.dto.SensorBuildingLocationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/blsMgr")
@Api(tags = "BLS 모니터링")
public class BlsMonitoringController {


  @Autowired
  private BlsMonitoringService blsMonitoringService;

  /*
  @ApiOperation(value = "BLS monitoring 정보 검색", notes = "BLS monitoring 정보 검색 제공한다.", consumes="application/json;charset=UTF-8")
  @PostMapping("monitor")
  public DefaultHttpRes<SearchBlsMonitoringInfoResponse> monitor(
      @Valid @RequestBody SearchBlsMonitoringInfoRequest request) {
    // return new DefaultHttpRes(BaseCode.SUCCESS, blsMonitoringService.searchMonitoringInfo(request));
    return null;
  }
   */

  @ApiOperation(value = "<Deprecated> BLS monitoring 현장내 빌딩 상황 정보 검색 ", notes = "BLS monitoring 현장내 빌딩 상황  검색 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @PostMapping("monitor/{wpId}")
  @Deprecated
  public DefaultHttpRes<MonitoringInfoForWorkplaceResponse> monitorForWorkplace(
      @PathVariable(value = "wpId", required = true) String wpId
  ) {
    return new DefaultHttpRes(BaseCode.SUCCESS, blsMonitoringService.findBuildingSituations(wpId));
  }

  @ApiOperation(value = "<Deprecated> BLS monitoring 빌딩 내 층별 상황 정보 검색", notes = "BLS monitoring 빌딩 내 층별 상황 검색 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @PostMapping("monitor/{wpId}/building")
  @Deprecated
  public DefaultHttpRes<MonitoringInfoForBuildingResponse> monitorForBuilding(
      @PathVariable(value = "wpId", required = true) String wpId,
      @Valid @RequestBody MonitoringInfoForBuildingRequest request
  ) {
    Map<String,Object> conditionMap = request.getConditionMap();
    conditionMap.put("wpId", wpId);
    return new DefaultHttpRes(BaseCode.SUCCESS, blsMonitoringService.findFloorSituations(conditionMap));
  }

  @ApiOperation(value = "<Deprecated> BLS monitoring 빌딩 층별 상황 정보 검색", notes = "BLS monitoring 빌딩 층별  상황 정보 검색 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "buildingNo", value = "빌딩 넘버", required = true, dataType = "long", paramType = "path"),
  })
  @PostMapping("monitor/{wpId}/building/{buildingNo}")
  @Deprecated
  public DefaultHttpRes<MonitoringInfoForFloorResponse> monitorForFloor(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "buildingNo", required = true) long buildingNo,
      @Valid @RequestBody MonitoringInfoForFloorRequest request) {

    Map<String,Object> conditionMap = request.getConditionMap();
    conditionMap.put("wpId", wpId);
    conditionMap.put("buildingNo", buildingNo);

    return new DefaultHttpRes(BaseCode.SUCCESS, blsMonitoringService.findGridSituations(buildingNo, conditionMap));
  }

  @ApiOperation(value = "특정 Sensor 내 접근자 검색", notes = "특정 Sensor 내 접근자 검색 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "siIdx", value = "센서 아이디", required = true, dataType = "int", paramType = "path")
  })
  @PostMapping("monitor/sensor/{siIdx}/worker")
  public DefaultHttpRes<MonitoringInfoWorkersBySensorResponse> monitorForWorker(
      @PathVariable(value = "siIdx") int siIdx
  ) {
    return new DefaultHttpRes(BaseCode.SUCCESS, blsMonitoringService.findCurrentWorkerListBySensor(siIdx));
  }

  @ApiOperation(value = "BLE 스마트 안전모니터 데이터 검색", notes = "BLE 스마트 안전모니터 데이터 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping("monitor")
  public DefaultHttpRes<BleMonitoringDataResponse> monitor(
      @Valid @RequestBody BleMonitoringDataRequest request
  ) {
    return new DefaultHttpRes<BleMonitoringDataResponse>(BaseCode.SUCCESS, blsMonitoringService.findBleMonitoringData(request));
  }

  @ApiOperation(value = "BLE 스마트 안전모니터 현장 정보 검색", notes = "BLE 스마트 안전모니터 현장 정보 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
  })
  @PostMapping("monitor/workspace/{wpId}")
  public DefaultHttpRes<MonitoringWokspaceResponse> monitorWorkplace(
      @PathVariable(value = "wpId", required = true) String wpId
  ) {
    return new DefaultHttpRes<MonitoringWokspaceResponse>(BaseCode.SUCCESS, blsMonitoringService.findBleMonitoringWorkspace(wpId));
  }

  @ApiOperation(value = "BLE 스마트 안전모니터 근로자 위치 검색", notes = "BLE 스마트 안전모니터 근로자 위치 정보 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping("searchWorkerLocation")
  public DefaultHttpRes<SensorBuildingLocationDto> searchWorkerLocation(
      @Valid @RequestBody SearchUserLocationRequest request
  ) {
    return new DefaultHttpRes<SensorBuildingLocationDto>(BaseCode.SUCCESS, blsMonitoringService.findBleMonitoringWorerLocation(request));
  }

  @ApiOperation(value = "BLE 안전모니터 근로자 검색", notes = "BLE 안전모니터 근로자 검색 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping("searchBleUser")
  public DefaultHttpRes<List<BleUserVo>> searchBleUser(
      @Valid @RequestBody SearchBleUserRequest request) {
    return new DefaultHttpRes<List<BleUserVo>>(BaseCode.SUCCESS, blsMonitoringService.searchBleUser(request));
  }


  @ApiOperation(value = "협력사별 출력인원 현황 정보 리스트", notes = "협력사별 출력인원 현황 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping("searchAttendantSituationForMonitoring")
  public DefaultHttpRes<List<AttendantSituationVo>> searchAttendantSituationForMonitoring(
      @Valid @RequestBody ExportAttendantSituationRequest request) {
    return new DefaultHttpRes<List<AttendantSituationVo>>(BaseCode.SUCCESS, blsMonitoringService.findAttendantSituationListForMonitoring(request.getConditionMap()));
  }

  @ApiOperation(value = "출력인원 정보 리스트", notes = "출력인원 검색 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping("searchAttendantForMonitoring")
  public DefaultHttpRes<List<AttendantVo>> searchAttendantForMonitoring(
      @Valid @RequestBody ExportAttendantRequest request) {
    return new DefaultHttpRes<List<AttendantVo>>(BaseCode.SUCCESS, blsMonitoringService.findAttendantListForMonitoring(request.getConditionMap()));
  }



  /*
    @ApiOperation(value = "현재인원 정보 리스트", notes = "현재인원 검색 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("searchCurrentAttendantForMonitoring")
    public DefaultHttpRes<List<AttendantVo>> searchCurrentAttendantForMonitoring(
        @Valid @RequestBody ExportAttendantRequest request) {
        return new DefaultHttpRes<List<AttendantVo>>(BaseCode.SUCCESS, blsMonitoringService.findCurrentAttendantListForMonitoring(request.getConditionMap()));
    }
     */
}
