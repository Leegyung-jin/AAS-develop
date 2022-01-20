package kr.co.hulan.aas.mvc.api.monitor4_1.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.infra.weather.WeatherInfo;
import kr.co.hulan.aas.mvc.api.bls.controller.response.BleUserVo;
import kr.co.hulan.aas.mvc.api.bls.controller.response.MonitoringInfoWorkersBySensorResponse;
import kr.co.hulan.aas.mvc.api.gps.controller.request.SearchGpsLocationHistoryRequest;
import kr.co.hulan.aas.mvc.api.gps.controller.request.SearchGpsUserRequest;
import kr.co.hulan.aas.mvc.api.gps.controller.response.GpsUserVo;
import kr.co.hulan.aas.mvc.api.gps.service.GpsService;
import kr.co.hulan.aas.mvc.api.monitor.controller.request.AnalyticInfoMgrRequest;
import kr.co.hulan.aas.mvc.api.monitor.controller.request.SearchGpsLocationHistoryRequestV4_1;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.AttendantSituationVo;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.AttendantVo;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.BleCrossSectionDataResponse;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.BleFloorDataResponse;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.BleViewMapDataResponse;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.GpsDataResponse;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.GpsFenceGroupResponse;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.WorkplaceMainResponse;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.WorkplaceSupportMonitoringResponse;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.WorkplaceUiComponentResponse;
import kr.co.hulan.aas.mvc.api.monitor.dto.EnvironmentMeasureDeviceVo;
import kr.co.hulan.aas.mvc.api.monitor4_1.controller.request.EntergateWorkerHistoryExportRequest;
import kr.co.hulan.aas.mvc.api.monitor4_1.controller.request.EntergateWorkerHistoryPagingListRequest;
import kr.co.hulan.aas.mvc.api.monitor4_1.controller.request.EntergateWorkerListRequest;
import kr.co.hulan.aas.mvc.api.monitor4_1.controller.request.MonitorMainRequest;
import kr.co.hulan.aas.mvc.api.monitor4_1.controller.response.EntergateMemberStatResponse;
import kr.co.hulan.aas.mvc.api.monitor4_1.controller.response.HazardStatusResponse;
import kr.co.hulan.aas.mvc.api.monitor4_1.controller.response.OpeEquipmentStatusResponse;
import kr.co.hulan.aas.mvc.api.monitor4_1.controller.response.WatchOutResponse;
import kr.co.hulan.aas.mvc.api.monitor4_1.controller.response.WorkStatusSummaryResponse;
import kr.co.hulan.aas.mvc.api.monitor4_1.service.SmartMonitorService;
import kr.co.hulan.aas.mvc.api.monitor4_1.vo.OpeEquipmentVo;
import kr.co.hulan.aas.mvc.model.dto.GasLogDto;
import kr.co.hulan.aas.mvc.model.dto.GpsLocationHistoryDto;
import kr.co.hulan.aas.mvc.model.dto.SensorBuildingLocationDto;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceCctvDto;
import kr.co.hulan.aas.mvc.model.dto.WorkerCheckDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/monitor/4.1")
@Api(tags = "[4.1] IMOS 일반 모니터링 관리")
public class SmartMonitorController {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private SmartMonitorService smartMonitorService;

  @Autowired
  private GpsService gpsService;

  @ApiOperation(value = "선택가능한 현장 정보", notes = "선택가능한 현장 정보 리스트를 제공한다.", produces="application/json;charset=UTF-8")
  @GetMapping("/workplace")
  public DefaultHttpRes<WorkplaceSupportMonitoringResponse> availableWorkplace(){
    return new DefaultHttpRes<WorkplaceSupportMonitoringResponse>(BaseCode.SUCCESS, smartMonitorService.findAvailableMonitoringList());
  }

  @ApiOperation(value = "현장 메인 정보", notes = "현장 및 UI 컴포넌트, 날씨, 최근 공지사항 정보를 제공한다. (스마트 안전 모니터 4.1) ", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @PostMapping(value="/workplace/{wpId}/main", produces="application/json;charset=UTF-8")
  @Deprecated
  public DefaultHttpRes<WorkplaceMainResponse> workplaceMain(
      @PathVariable(value = "wpId", required = true) String wpId,
      @RequestBody MonitorMainRequest request
  ){
    return new DefaultHttpRes<WorkplaceMainResponse>(BaseCode.SUCCESS, smartMonitorService.findWorkplaceMainInfo(wpId, request));
  }

  @ApiOperation(value = "현장 날씨 정보", notes = "현장 날씨 정보를 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/workplace/{wpId}/weather", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<WeatherInfo> workplaceWeather(
      @PathVariable(value = "wpId", required = true) String wpId
  ){
    return new DefaultHttpRes<WeatherInfo>(BaseCode.SUCCESS, smartMonitorService.findWorkplaceWeather(wpId));
  }

  @ApiOperation(value = "특정 UI 위치 할당 컴포넌트 정보 조회", notes = "특정 UI 위치 할당 컴포넌트 정보와 할당가능한 컴포넌트 리스트 조회 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "location", value = "UI 위치", required = true, dataType = "integer", paramType = "path")
  })
  @GetMapping(value="/workplace/{wpId}/ui/{location}", produces="application/json;charset=UTF-8")
  @Deprecated
  public DefaultHttpRes<WorkplaceUiComponentResponse> workplaceUiComponent(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "location", required = true) Integer location
  ){
    return new DefaultHttpRes<WorkplaceUiComponentResponse>(BaseCode.SUCCESS, smartMonitorService.findWorkplaceUiComponent(wpId, location));
  }

  @ApiOperation(value = "특정 UI 위치 컴포넌트 할당 요청", notes = "특정 UI 위치 컴포넌트 할당 요청 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "location", value = "UI 위치", required = true, dataType = "integer", paramType = "path"),
      @ApiImplicitParam(name = "cmptId", value = "할당 컴포넌트 아이디", required = true, dataType = "string", paramType = "path")
  })
  @PostMapping(value="/workplace/{wpId}/ui/{location}/{cmptId}", produces="application/json;charset=UTF-8")
  @Deprecated
  public DefaultHttpRes<Object> assignWorkplaceUiComponent(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "location", required = true) Integer location,
      @PathVariable(value = "cmptId", required = true) String cmptId
  ){
    smartMonitorService.assignWorkplaceUiComponent(wpId, location, cmptId);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  @ApiOperation(value = "특정 UI 위치 할당 컴포넌트 해제 요청", notes = "특정 UI 위치 할당 컴포넌트 해제 요청 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "location", value = "UI 위치", required = true, dataType = "integer", paramType = "path")
  })
  @DeleteMapping(value="/workplace/{wpId}/ui/{location}", produces="application/json;charset=UTF-8")
  @Deprecated
  public DefaultHttpRes<Object> unassignWorkplaceUiComponent(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "location", required = true) Integer location
  ){
    smartMonitorService.unassignWorkplaceUiComponent(wpId, location);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }


  @ApiOperation(value = "GPS Fence 정보 조회", notes = "GPS Fence 정보 리스트 조회 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/workplace/{wpId}/fence", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<GpsFenceGroupResponse> workplaceGpsFence(
      @PathVariable(value = "wpId", required = true) String wpId
  ){
    return new DefaultHttpRes<GpsFenceGroupResponse>(BaseCode.SUCCESS, smartMonitorService.findWorkplaceFenceGroupList(wpId));
  }

  @ApiOperation(value = "[V4.1] GPS Monitoring Data 검색", notes = "GPS Monitoring Data 검색 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "marker_mb_id", value = "마킹 근로자 아이디", required = false, dataType = "string", paramType = "query")
  })
  @GetMapping(value="/workplace/{wpId}/gps", produces= MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<GpsDataResponse> workplaceGpsMonitoringData(
      @PathVariable(value = "wpId", required = true) String wpId,
      @RequestParam(value = "marker_mb_id", required = false) String markerMbId
  ){
    return new DefaultHttpRes<GpsDataResponse>(BaseCode.SUCCESS, smartMonitorService.getGpsMonitoringData(wpId, markerMbId));
  }


  @ApiOperation(value = "[V4.1] GPS 안전모니터 근로자 검색", notes = "GPS 안전모니터 근로자 검색 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "mb_name", value = "근로자명", required = false, dataType = "string", paramType = "query")
  })
  @GetMapping(value="/workplace/{wpId}/gps/user", produces= MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<List<GpsUserVo>> searchGpsUserByName(
      @PathVariable(value = "wpId", required = true) String wpId,
      @RequestParam(value = "mb_name", required = false) String mbName
  ) {
    return new DefaultHttpRes(BaseCode.SUCCESS, gpsService.searchGpsUser(SearchGpsUserRequest.builder()
        .wpId(wpId)
        .searchName("mbName")
        .searchValue(mbName)
        .build()));
  }

  @ApiOperation(value = "[V4.1] 근로자 이동경로 정보", notes = "근로자 이동경로 정보 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "mb_name", value = "근로자명", required = false, dataType = "string", paramType = "query")
  })
  @PostMapping(value="/workplace/{wpId}/gps/user/history", produces= MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<List<GpsLocationHistoryDto>> searchGpsLocationHistory(
      @PathVariable(value = "wpId", required = true) String wpId,
      @Valid @RequestBody SearchGpsLocationHistoryRequestV4_1 request) {

    SearchGpsLocationHistoryRequest originRequest = modelMapper.map(request, SearchGpsLocationHistoryRequest.class);
    originRequest.setWpId(wpId);
    return new DefaultHttpRes(BaseCode.SUCCESS, gpsService.searchGpsLocationHistory(originRequest));
  }

  @ApiOperation(value = "BLE (조감도) Monitoring Data 검색", notes = "BLE (조감도) Monitoring Data 검색 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/workplace/{wpId}/ble", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<BleViewMapDataResponse> workplaceBleMonitoringData(
      @PathVariable(value = "wpId", required = true) String wpId
  ){
    return new DefaultHttpRes<BleViewMapDataResponse>(BaseCode.SUCCESS, smartMonitorService.getBleViewMapMonitoringData(wpId));
  }

  @ApiOperation(value = "BLE (단면도) Monitoring Data 검색", notes = "BLE (단면도) Monitoring Data 검색 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "buildingNo", value = "빌딩 넘버", required = true, dataType = "long", paramType = "path")
  })
  @GetMapping(value="/workplace/{wpId}/ble/{buildingNo}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<BleCrossSectionDataResponse> workplaceBleBuildingMonitoringData(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "buildingNo", required = true) long buildingNo
  ){
    return new DefaultHttpRes<BleCrossSectionDataResponse>(BaseCode.SUCCESS, smartMonitorService.getBleCrossSectionMonitoringData(wpId, buildingNo));
  }

  @ApiOperation(value = "BLE (평면도) Monitoring Data 검색", notes = "BLE (평면도) Monitoring Data 검색 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "buildingNo", value = "빌딩 넘버", required = true, dataType = "long", paramType = "path"),
      @ApiImplicitParam(name = "floor", value = "층 넘버", required = true, dataType = "int", paramType = "path"),
      @ApiImplicitParam(name = "marker", value = "추적 대상 근로자 넘버(mb_id)", required = false, dataType = "string", paramType = "query")
  })
  @GetMapping(value="/workplace/{wpId}/ble/{buildingNo}/{floor}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<BleFloorDataResponse> workplaceBleBuildingFloorMonitoringData(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "buildingNo", required = true) long buildingNo,
      @PathVariable(value = "floor", required = true) int floor,
      @RequestParam(value = "marker", required = false) String marker
  ){
    return new DefaultHttpRes<BleFloorDataResponse>(BaseCode.SUCCESS, smartMonitorService.getBleFloorMonitoringData(wpId, buildingNo, floor, marker));
  }


  @ApiOperation(value = "BLE 안전모니터 근로자 검색", notes = "BLE 안전모니터 근로자 검색 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "mb_name", value = "근로자명", required = false, dataType = "string", paramType = "query")
  })
  @GetMapping(value="/workplace/{wpId}/ble/user", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<BleUserVo>> searchBleUser(
      @PathVariable(value = "wpId", required = true) String wpId,
      @RequestParam(value = "mb_name", required = false) String mbName
  ) {
    return new DefaultHttpRes<List<BleUserVo>>(BaseCode.SUCCESS, smartMonitorService.searchBleUser(wpId, mbName));
  }

  @ApiOperation(value = "BLE 스마트 안전모니터 근로자 위치 검색", notes = "BLE 스마트 안전모니터 근로자 위치 정보 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "mbId", value = "근로자 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/workplace/{wpId}/ble/user/{mbId}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<SensorBuildingLocationDto> searchWorkerLocation(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "mbId", required = true) String mbId
  ) {
    return new DefaultHttpRes<SensorBuildingLocationDto>(BaseCode.SUCCESS, smartMonitorService.findBleMonitoringWorerLocation(wpId, mbId));
  }


  /*
  @ApiOperation(value = "위험지역 접근자 검색", notes = "위험지역 접근자 검색 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/workplace/{wpId}/worker/danger", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<DetectedSensorWorkerVo>> monitorForDangerAreaWorker(
      @PathVariable(value = "wpId", required = true) String wpId
  ) {
    return new DefaultHttpRes(BaseCode.SUCCESS, smartMonitorService.findDangerAreaWorkerList(wpId));
  }
   */


  @ApiOperation(value = "특정 Sensor 내 접근자 검색", notes = "특정 Sensor 내 접근자 검색 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "siIdx", value = "센서 아이디", required = true, dataType = "int", paramType = "path")
  })
  @GetMapping(value="/sensor/{siIdx}/worker", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<MonitoringInfoWorkersBySensorResponse> monitorForWorker(
      @PathVariable(value = "siIdx") int siIdx
  ) {
    return new DefaultHttpRes(BaseCode.SUCCESS, smartMonitorService.findCurrentWorkerListBySensor(siIdx));
  }

  @ApiOperation(value = "요주의 근로자 정보 검색", notes = "요주의 근로자 정보 검색 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/workplace/{wpId}/watchout", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<WatchOutResponse> monitorForDangerAreaWorker(
      @PathVariable(value = "wpId", required = true) String wpId
  ) {
    return new DefaultHttpRes(BaseCode.SUCCESS, smartMonitorService.findWatchOutInfo(wpId));
  }


  @ApiOperation(value = "추락감지 이벤트 삭제 처리", notes = "추락감지 이벤트 삭제 처리 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "mbId", value = "근로자 아이디", required = true, dataType = "string", paramType = "path")
  })
  @DeleteMapping(value="/workplace/{wpId}/watchout/falling/{mbId}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<WatchOutResponse> deleteFallingAccident(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "mbId", required = true) String mbId
  ) {
    smartMonitorService.deleteFallingAccident(wpId, mbId);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  @ApiOperation(value = "추락감지 이벤트 확인 처리", notes = "추락감지 이벤트 확인 처리 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "mbId", value = "근로자 아이디", required = true, dataType = "string", paramType = "path")
  })
  @PostMapping(value="/workplace/{wpId}/watchout/falling/{mbId}/confirm", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<WatchOutResponse> confirmFallingAccident(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "mbId", required = true) String mbId
  ) {
    // smartMonitorService.confirmFallingAccident(wpId, mbId);
    smartMonitorService.deleteFallingAccident(wpId, mbId);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  @ApiOperation(value = "영상 감지 이벤트 조작", notes = "영상 감지 이벤트 조작 제공한다.", consumes="application/json;charset=UTF-8")
  @PostMapping(value="/workplace/analyticInfo", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<Object> analyticInfo(
      @Valid @RequestBody AnalyticInfoMgrRequest request) {
    smartMonitorService.updateAnalyticViewInfo(request);
    return new DefaultHttpRes<>(BaseCode.SUCCESS);
  }


  @ApiOperation(value = "고위험 근로자 등록 현황 검색", notes = "요주의 근로자 등록 현황 검색 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/workplace/{wpId}/watchout/highrisk", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<WorkerCheckDto>> highRiskWorker(
      @PathVariable(value = "wpId", required = true) String wpId
  ) {
    return new DefaultHttpRes(BaseCode.SUCCESS, smartMonitorService.findHighRiskWorkers(wpId));
  }


  @Deprecated
  @ApiOperation(value = "BLE 전체 출력현황 정보", notes = "BLE 전체 출력현황 정보 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/workplace/{wpId}/ble/workstatus", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<WorkStatusSummaryResponse> bleWorkStatus(
      @PathVariable(value = "wpId", required = true) String wpId
  ) {
    return new DefaultHttpRes(BaseCode.SUCCESS, smartMonitorService.findBleWorkStatusSummary(wpId));
  }

  @Deprecated
  @ApiOperation(value = "BLE 실시간 근로자 현황 정보", notes = "BLE 실시간 근로자 근로자 현황 정보 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "currentWorking", value = "현재 작업여부. 0: 종료, 1: 작업", required = true, dataType = "int", paramType = "path")
  })
  @GetMapping(value="/workplace/{wpId}/ble/workstatus/workers/{currentWorking}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<AttendantVo>> bleWorkerListByCurrentWorking(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "currentWorking", required = true) int currentWorking
  ) {
    return new DefaultHttpRes(BaseCode.SUCCESS, smartMonitorService.findCurrentEntranceWorkerList(wpId, "ble" ,currentWorking));
  }

  @Deprecated
  @ApiOperation(value = "BLE 출근타입별 근로자 현황 정보", notes = "BLE 출근타입별 근로자 현황 정보 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "commuteType", value = "출근 유형, qr: QRCode 출근, app: app 출근(비콘)", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/workplace/{wpId}/ble/workstatus/commute/{commuteType}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<AttendantVo>> bleWorkerListByCommuteType(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "commuteType", required = true) String commuteType
  ) {
    return new DefaultHttpRes(BaseCode.SUCCESS, smartMonitorService.findWorkerListByCommuteType(wpId, commuteType));
  }

  @Deprecated
  @ApiOperation(value = "BLE 협력사별 출력현황 정보", notes = "BLE 협력사별 출력현황 정보 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/workplace/{wpId}/ble/workstatus/coop", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<AttendantSituationVo>> bleWorkStatusPerCoop(
      @PathVariable(value = "wpId", required = true) String wpId
  ) {
    return new DefaultHttpRes(BaseCode.SUCCESS, smartMonitorService.findBleWorkStatusPerCoop(wpId));
  }

  @Deprecated
  @ApiOperation(value = "BLE 협력사 출력인원 정보", notes = "BLE 협력사 출력인원 정보 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "coopMbId", value = "협력사 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/workplace/{wpId}/ble/workstatus/coop/{coopMbId}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<AttendantVo>> bleCoopAttendants(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "coopMbId", required = true) String coopMbId
  ) {
    return new DefaultHttpRes(BaseCode.SUCCESS, smartMonitorService.findCoopAttendants(wpId, coopMbId));
  }

  @Deprecated
  @ApiOperation(value = "GPS 전체 출력현황 정보", notes = "GPS 전체 출력현황 정보 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/workplace/{wpId}/gps/workstatus", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<WorkStatusSummaryResponse> gpsWorkStatus(
      @PathVariable(value = "wpId", required = true) String wpId
  ) {
    return new DefaultHttpRes(BaseCode.SUCCESS, smartMonitorService.findGpsWorkStatusSummary(wpId));
  }

  @Deprecated
  @ApiOperation(value = "GPS 출근타입별 근로자 현황 정보", notes = "BLE 출근타입별 근로자 현황 정보 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "commuteType", value = "출근 유형, qr: QRCode 출근, app: app 출근(비콘)", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/workplace/{wpId}/gps/workstatus/commute/{commuteType}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<AttendantVo>> gpsWorkerListByCommuteType(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "commuteType", required = true) String commuteType
  ) {
    return new DefaultHttpRes(BaseCode.SUCCESS, smartMonitorService.findWorkerListByCommuteType(wpId, commuteType));
  }

  @Deprecated
  @ApiOperation(value = "GPS 실시간 근로자 현황 정보", notes = "GPS 실시간 근로자 근로자 현황 정보 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "currentWorking", value = "현재 작업여부. 0: 종료, 1: 작업", required = true, dataType = "int", paramType = "path")
  })
  @GetMapping(value="/workplace/{wpId}/gps/workstatus/workers/{currentWorking}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<AttendantVo>> gpsWorkerListByCurrentWorking(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "currentWorking", required = true) int currentWorking
  ) {
    return new DefaultHttpRes(BaseCode.SUCCESS, smartMonitorService.findCurrentEntranceWorkerList(wpId, "gps" ,currentWorking));
  }

  @Deprecated
  @ApiOperation(value = "GPS 협력사별 출력현황 정보", notes = "GPS 협력사별 출력현황 정보 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/workplace/{wpId}/gps/workstatus/coop", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<AttendantSituationVo>> gpsWorkStatusPerCoop(
      @PathVariable(value = "wpId", required = true) String wpId
  ) {
    return new DefaultHttpRes(BaseCode.SUCCESS, smartMonitorService.findGpsWorkStatusPerCoop(wpId));
  }

  @Deprecated
  @ApiOperation(value = "GPS 협력사 출력인원 정보", notes = "GPS 협력사 출력인원 정보 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "coopMbId", value = "협력사 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/workplace/{wpId}/gps/workstatus/coop/{coopMbId}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<AttendantVo>> gpsCoopAttendants(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "coopMbId", required = true) String coopMbId
  ) {
    return new DefaultHttpRes(BaseCode.SUCCESS, smartMonitorService.findCoopAttendants(wpId, coopMbId));
  }


  @Deprecated
  @ApiOperation(value = "장비 및 차량 현황 정보", notes = "장비 및 차량 현황 정보 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/workplace/{wpId}/equipment", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<OpeEquipmentStatusResponse> equipmentStatus(
      @PathVariable(value = "wpId", required = true) String wpId
  ) {
    return new DefaultHttpRes(BaseCode.SUCCESS, smartMonitorService.findOpeEquipmentStatus(wpId));
  }

  @Deprecated
  @ApiOperation(value = "장비 및 차량 리스트 정보", notes = "장비 및 차량 리스트 정보 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "equipmentType", value = "장비 타입 아이디", required = true, dataType = "int", paramType = "path")
  })
  @GetMapping(value="/workplace/{wpId}/equipment/{equipmentType}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<OpeEquipmentVo>> equipmentTypeList(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "equipmentType", required = true) int equipmentType
  ) {
    return new DefaultHttpRes(BaseCode.SUCCESS, smartMonitorService.findOpeEquipmentListByType(wpId,equipmentType ));
  }

  @ApiOperation(value = "유해물질 측정 정보 리스트", notes = "유해물질 측정 정보 리스트 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/workplace/{wpId}/hazard", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<EnvironmentMeasureDeviceVo>> hazardList(
      @PathVariable(value = "wpId", required = true) String wpId
  ) {
    return new DefaultHttpRes(BaseCode.SUCCESS, smartMonitorService.findHazardDeviceList(wpId));
  }

  @ApiOperation(value = "유해물질 측정 상세 현황", notes = "유해물질 측정 상세 현황 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "idx", value = "디바이스 넘버", required = true, dataType = "int", paramType = "path")
  })
  @GetMapping(value="/workplace/{wpId}/hazard/{idx}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<HazardStatusResponse> hazardDeviceStatus(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "idx", required = true) int idx
  ) {
    return new DefaultHttpRes(BaseCode.SUCCESS, smartMonitorService.findHazardDeviceStatus(wpId, idx));
  }


  @ApiOperation(value = "유해물질 위험 알림 ON/OFF", notes = "유해물질 위험 알림 ON/OFF 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "idx", value = "디바이스 넘버", required = true, dataType = "int", paramType = "path"),
      @ApiImplicitParam(name = "alarmFlag", value = "알림 ON/OFF. 0:OFF, 1:ON", required = true, dataType = "int", paramType = "path")
  })
  @PostMapping(value="/workplace/{wpId}/hazard/{idx}/{alarmFlag}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<HazardStatusResponse> hazardDeviceAlarmOnOff(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "idx", required = true) int idx,
      @PathVariable(value = "alarmFlag", required = true) int alarmFlag
  ) {
    smartMonitorService.updateHazardAlarm(wpId, idx, alarmFlag);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  @ApiOperation(value = "현장 CCTV 리스트 정보", notes = "현장 CCTV 리스트제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/workplace/{wpId}/cctv", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<List<WorkPlaceCctvDto>> export(
      @PathVariable(value = "wpId", required = true) String wpId
  ) {
    return new DefaultHttpRes<List<WorkPlaceCctvDto>>(BaseCode.SUCCESS, smartMonitorService.findCctvList(wpId));
  }

  @ApiOperation(value = "출입게이트 현황 정보", notes = "출입게이트 현황 정보 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/workplace/{wpId}/entergate/stat", produces="application/json;charset=UTF-8")
  @Deprecated
  public DefaultHttpRes<EntergateMemberStatResponse> entergateStatus(
      @PathVariable(value = "wpId", required = true) String wpId
  ) {
    return new DefaultHttpRes(BaseCode.SUCCESS, smartMonitorService.findGateSystemStat(wpId));
  }

  @ApiOperation(value = "출입게이트 출근자 검색", notes = "출입게이트 출근자 검색 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @PostMapping(value="/workplace/{wpId}/entergate/worker/search", produces="application/json;charset=UTF-8")
  @Deprecated
  public DefaultHttpRes<List<AttendantVo>> appStatusWorkerList(
      @PathVariable(value = "wpId", required = true) String wpId,
      @Valid @RequestBody EntergateWorkerListRequest request
  ) {
    return new DefaultHttpRes<List<AttendantVo>>(BaseCode.SUCCESS, smartMonitorService.findGateSystemEntranceUserList(wpId, request));
  }

  @ApiOperation(value = "출입게이트 출근자 이력 검색", notes = "출입게이트 출근자 이력 검색 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @PostMapping(value="/workplace/{wpId}/entergate/worker/history/search", produces="application/json;charset=UTF-8")
  @Deprecated
  public DefaultHttpRes<DefaultPageResult<AttendantVo>> entergateWorkerHistorySearch(
      @PathVariable(value = "wpId", required = true) String wpId,
      @Valid @RequestBody EntergateWorkerHistoryPagingListRequest request
  ) {
    return new DefaultHttpRes<DefaultPageResult<AttendantVo>>(BaseCode.SUCCESS, smartMonitorService.findGateEntranceHistoryPagingList(wpId, request));
  }

  @ApiOperation(value = "출입게이트 출근자 이력 Export", notes = "출입게이트 이력 Export 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @PostMapping(value="/workplace/{wpId}/entergate/worker/history/export", produces="application/json;charset=UTF-8")
  @Deprecated
  public DefaultHttpRes<List<AttendantVo>> entergateWorkerHistorySearch(
      @PathVariable(value = "wpId", required = true) String wpId,
      @Valid @RequestBody EntergateWorkerHistoryExportRequest request
  ) {
    Map<String,Object> condition = request.getConditionMap();
    condition.put("wpId", wpId);
    return new DefaultHttpRes<List<AttendantVo>>(BaseCode.SUCCESS, smartMonitorService.findGateEntranceHistoryList(condition));
  }
}
