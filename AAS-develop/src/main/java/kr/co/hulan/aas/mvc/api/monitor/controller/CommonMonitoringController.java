package kr.co.hulan.aas.mvc.api.monitor.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.infra.weather.WeatherInfo;
import kr.co.hulan.aas.mvc.api.bls.controller.request.BleMonitoringDataRequest;
import kr.co.hulan.aas.mvc.api.bls.controller.request.BleMonitoringDataRequest.BLE_SMART_SEARCH_TYPE;
import kr.co.hulan.aas.mvc.api.bls.controller.response.BleMonitoringDataResponse;
import kr.co.hulan.aas.mvc.api.gps.controller.request.SearchGpsLocationHistoryRequest;
import kr.co.hulan.aas.mvc.api.gps.controller.request.SearchGpsMonitoringDataRequest;
import kr.co.hulan.aas.mvc.api.gps.controller.request.SearchGpsUserRequest;
import kr.co.hulan.aas.mvc.api.gps.controller.response.GpsUserVo;
import kr.co.hulan.aas.mvc.api.gps.service.GpsFenceService;
import kr.co.hulan.aas.mvc.api.monitor.controller.request.AnalyticInfoMgrRequest;
import kr.co.hulan.aas.mvc.api.bls.service.BlsMonitoringService;
import kr.co.hulan.aas.mvc.api.monitor.controller.request.AssignWorkplaceUiComponentRequest;
import kr.co.hulan.aas.mvc.api.monitor.controller.request.SearchGpsLocationHistoryRequestV4_1;
import kr.co.hulan.aas.mvc.api.monitor.controller.request.UpdateGasDevicePopupRequest;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.AttendantVo;
import kr.co.hulan.aas.mvc.api.gps.service.GpsService;
import kr.co.hulan.aas.mvc.api.monitor.controller.request.CommonMonitoringRequest;
import kr.co.hulan.aas.mvc.api.monitor.controller.request.ExportAttendantRequest;
import kr.co.hulan.aas.mvc.api.monitor.controller.request.ExportAttendantSituationRequest;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.AttendantSituationVo;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.BleCrossSectionDataResponse;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.BleFloorDataResponse;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.BleViewMapDataResponse;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.GpsDataResponse;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.GpsFenceGroupResponse;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.WorkplaceSupportMonitoringResponse;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.WorkplaceUiComponentResponse;
import kr.co.hulan.aas.mvc.api.monitor.service.CommonMonitoringService;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.WorkplaceMainResponse;
import kr.co.hulan.aas.mvc.api.workplace.service.WorkplaceService;
import kr.co.hulan.aas.mvc.model.dto.GpsLocationHistoryDto;
import org.apache.commons.lang3.StringUtils;
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

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/monitor")
@Api(tags = "일반 모니터링 관리")
public class CommonMonitoringController {

    @Autowired
    private CommonMonitoringService commonMonitoringService;

    @Autowired
    private BlsMonitoringService blsMonitoringService;

    @Autowired
    private GpsService gpsService;

    @Autowired
    private WorkplaceService workplaceService;

    @Autowired
    private GpsFenceService gpsFenceService;

    @ApiOperation(value = "일반 모니터링 정보 검색", notes = "위험지역 접근자, 주요관리자, 공지사항, 날씨 등 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("search")
    public DefaultHttpRes<Map> search(
            @Valid @RequestBody CommonMonitoringRequest request) {
        return new DefaultHttpRes(BaseCode.SUCCESS, commonMonitoringService.searchCommonMonitor(request));
    }


    @ApiOperation(value = "선택가능한 현장 정보", notes = "선택가능한 현장 정보 리스트를 제공한다.", consumes="application/json;charset=UTF-8")
    @GetMapping("/workplace")
    public DefaultHttpRes<WorkplaceSupportMonitoringResponse> availableWorkplace(){
        return new DefaultHttpRes<WorkplaceSupportMonitoringResponse>(BaseCode.SUCCESS, workplaceService.findAvailableMonitoringList());
    }

    @ApiOperation(value = "협력사별 출력인원 현황 정보 리스트", notes = "협력사별 출력인원 현황 제공한다.", consumes="application/json;charset=UTF-8")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "monitorType", value = "모니터링 종류. gps / ble", required = true, dataType = "string", paramType = "path")
    })
    @PostMapping("searchAttendantSituation/{monitorType}")
    public DefaultHttpRes<List<AttendantSituationVo>> searchAttendant(
        @PathVariable(value = "monitorType", required = true) String monitorType,
        @Valid @RequestBody ExportAttendantSituationRequest request) {
        if( StringUtils.equals(monitorType, "gps")){
            return new DefaultHttpRes<List<AttendantSituationVo>>(BaseCode.SUCCESS, gpsService.findAttendantSituationListForMonitoring(request.getConditionMap()));
        }
        else if( StringUtils.equals(monitorType, "ble")){
            return new DefaultHttpRes<List<AttendantSituationVo>>(BaseCode.SUCCESS, blsMonitoringService.findAttendantSituationListForMonitoring(request.getConditionMap()));
        }
        else {
            throw new CommonException(BaseCode.ERR_API_EXCEPTION.code(), "지원하지 않는 모니터링 타입입니다.["+monitorType+"]");
        }
    }

    @ApiOperation(value = "출력인원 정보 리스트", notes = "출력인원 검색 제공한다.", consumes="application/json;charset=UTF-8")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "monitorType", value = "모니터링 종류. gps / ble", required = true, dataType = "string", paramType = "path")
    })
    @PostMapping("searchAttendant/{monitorType}")
    public DefaultHttpRes<List<AttendantVo>> searchAttendant(
        @PathVariable(value = "monitorType", required = true) String monitorType,
        @Valid @RequestBody ExportAttendantRequest request) {
        if( StringUtils.equals(monitorType, "gps")){
            return new DefaultHttpRes<List<AttendantVo>>(BaseCode.SUCCESS, gpsService.findAttendantListForMonitoring(request.getConditionMap()));
        }
        else if( StringUtils.equals(monitorType, "ble")){
            return new DefaultHttpRes<List<AttendantVo>>(BaseCode.SUCCESS, blsMonitoringService.findAttendantListForMonitoring(request.getConditionMap()));
        }
        else {
            throw new CommonException(BaseCode.ERR_API_EXCEPTION.code(), "지원하지 않는 모니터링 타입입니다.["+monitorType+"]");
        }


    }

    @ApiOperation(value = "영상 감지 이벤트 조작", notes = "영상 감지 이벤트 조작 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("analyticInfo")
    public DefaultHttpRes<Object> analyticInfo(
        @Valid @RequestBody AnalyticInfoMgrRequest request) {
        commonMonitoringService.updateAnalyticViewInfo(request);
        return new DefaultHttpRes<>(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "위험물질 디바이스 경고 팝업 On/Off 업데이트", notes = "위험물질 디바이스 경고 팝업 On/Off  업데이트 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("gas/update")
    public DefaultHttpRes<Object> updateGas(
        @Valid @RequestBody UpdateGasDevicePopupRequest request) {
        commonMonitoringService.updateGasDevicePopup(request);
        return new DefaultHttpRes<>(BaseCode.SUCCESS);
    }




}
