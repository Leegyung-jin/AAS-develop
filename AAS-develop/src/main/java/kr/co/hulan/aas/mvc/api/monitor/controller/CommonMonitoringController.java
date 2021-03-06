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
@Api(tags = "?????? ???????????? ??????")
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

    @ApiOperation(value = "?????? ???????????? ?????? ??????", notes = "???????????? ?????????, ???????????????, ????????????, ?????? ??? ????????????.", consumes="application/json;charset=UTF-8")
    @PostMapping("search")
    public DefaultHttpRes<Map> search(
            @Valid @RequestBody CommonMonitoringRequest request) {
        return new DefaultHttpRes(BaseCode.SUCCESS, commonMonitoringService.searchCommonMonitor(request));
    }


    @ApiOperation(value = "??????????????? ?????? ??????", notes = "??????????????? ?????? ?????? ???????????? ????????????.", consumes="application/json;charset=UTF-8")
    @GetMapping("/workplace")
    public DefaultHttpRes<WorkplaceSupportMonitoringResponse> availableWorkplace(){
        return new DefaultHttpRes<WorkplaceSupportMonitoringResponse>(BaseCode.SUCCESS, workplaceService.findAvailableMonitoringList());
    }

    @ApiOperation(value = "???????????? ???????????? ?????? ?????? ?????????", notes = "???????????? ???????????? ?????? ????????????.", consumes="application/json;charset=UTF-8")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "monitorType", value = "???????????? ??????. gps / ble", required = true, dataType = "string", paramType = "path")
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
            throw new CommonException(BaseCode.ERR_API_EXCEPTION.code(), "???????????? ?????? ???????????? ???????????????.["+monitorType+"]");
        }
    }

    @ApiOperation(value = "???????????? ?????? ?????????", notes = "???????????? ?????? ????????????.", consumes="application/json;charset=UTF-8")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "monitorType", value = "???????????? ??????. gps / ble", required = true, dataType = "string", paramType = "path")
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
            throw new CommonException(BaseCode.ERR_API_EXCEPTION.code(), "???????????? ?????? ???????????? ???????????????.["+monitorType+"]");
        }


    }

    @ApiOperation(value = "?????? ?????? ????????? ??????", notes = "?????? ?????? ????????? ?????? ????????????.", consumes="application/json;charset=UTF-8")
    @PostMapping("analyticInfo")
    public DefaultHttpRes<Object> analyticInfo(
        @Valid @RequestBody AnalyticInfoMgrRequest request) {
        commonMonitoringService.updateAnalyticViewInfo(request);
        return new DefaultHttpRes<>(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "???????????? ???????????? ?????? ?????? On/Off ????????????", notes = "???????????? ???????????? ?????? ?????? On/Off  ???????????? ????????????.", consumes="application/json;charset=UTF-8")
    @PostMapping("gas/update")
    public DefaultHttpRes<Object> updateGas(
        @Valid @RequestBody UpdateGasDevicePopupRequest request) {
        commonMonitoringService.updateGasDevicePopup(request);
        return new DefaultHttpRes<>(BaseCode.SUCCESS);
    }




}
