package kr.co.hulan.aas.mvc.api.gps.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.gps.controller.request.SearchAttendantRequest;
import kr.co.hulan.aas.mvc.api.gps.controller.request.SearchAttendantSituationRequest;
import kr.co.hulan.aas.mvc.api.gps.controller.request.SearchGpsInfoRequest;
import kr.co.hulan.aas.mvc.api.gps.controller.request.SearchGpsMonitoringDataRequest;
import kr.co.hulan.aas.mvc.api.gps.controller.request.SearchGpsLocationHistoryRequest;
import kr.co.hulan.aas.mvc.api.gps.controller.request.SearchGpsUserRequest;
import kr.co.hulan.aas.mvc.api.gps.controller.response.*;
import kr.co.hulan.aas.mvc.api.gps.service.GpsService;
import kr.co.hulan.aas.mvc.api.monitor.controller.request.ExportAttendantRequest;
import kr.co.hulan.aas.mvc.api.monitor.controller.request.ExportAttendantSituationRequest;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.AttendantSituationVo;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.AttendantVo;
import kr.co.hulan.aas.mvc.model.dto.GpsLocationHistoryDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/gpsMgr")
@Api(tags = "GPS 관리")
public class GpsController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private GpsService gpsService;

    @ApiOperation(value = "<Deprecated> GPS 정보 리스트", notes = "GPS 및 날씨정보 검색 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("search")
    public DefaultHttpRes<Map> search(
            @Valid @RequestBody SearchGpsInfoRequest request) {
        return new DefaultHttpRes(BaseCode.SUCCESS, gpsService.searchGps(request));
    }

    @ApiOperation(value = "<Deprecated> 협력사별 출력인원 현황 정보 리스트", notes = "협력사별 출력인원 현황 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("searchAttendantSituation")
    public DefaultHttpRes<SearchAttendantSituationResponse> searchAttendant(
            @Valid @RequestBody SearchAttendantSituationRequest request) {
        return new DefaultHttpRes(BaseCode.SUCCESS, gpsService.searchAttendantSituation(request));
    }

    @ApiOperation(value = "<Deprecated> 출력인원 정보 리스트", notes = "출력인원 검색 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("searchAttendant")
    public DefaultHttpRes<SearchAttendantResponse> searchAttendant(
            @Valid @RequestBody SearchAttendantRequest request) {
        return new DefaultHttpRes(BaseCode.SUCCESS, gpsService.searchAttendant(request));
    }

    @ApiOperation(value = "Geo-Fence 좌표 리스트", notes = "Geo-Fence 좌표 리스트 제공한다.", consumes="application/json;charset=UTF-8")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "query")
    })
    @GetMapping("searchGeofence")
    public DefaultHttpRes<GeoFenceInfoResponse> searchGeofence(
        @RequestParam(value = "wpId", required = true) String wpId) {
        return new DefaultHttpRes(BaseCode.SUCCESS, gpsService.searchGeofenceList(wpId));
    }


    @ApiOperation(value = "GPS 안전모니터 정보 검색", notes = "GPS 안전모니터 정보 검색 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("searchGpsMonitoring")
    public DefaultHttpRes<Map> searchGpsMonitoring(
        @Valid @RequestBody SearchGpsMonitoringDataRequest request) {
        return new DefaultHttpRes(BaseCode.SUCCESS, gpsService.searchGpsMonitoringDataRequest(request));
    }


    @ApiOperation(value = "근로자 이동경로 정보", notes = "근로자 이동경로 정보 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("searchGpsHist")
    public DefaultHttpRes<List<GpsLocationHistoryDto>> searchGpsLocationHistory(
        @Valid @RequestBody SearchGpsLocationHistoryRequest request) {
        return new DefaultHttpRes(BaseCode.SUCCESS, gpsService.searchGpsLocationHistory(request));
    }

    @ApiOperation(value = "GPS 안전모니터 근로자 검색", notes = "GPS 안전모니터 근로자 검색 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("searchGpsUser")
    public DefaultHttpRes<List<GpsUserVo>> searchGpsUserByName(
        @Valid @RequestBody SearchGpsUserRequest request) {
        return new DefaultHttpRes(BaseCode.SUCCESS, gpsService.searchGpsUser(request));
    }

    @ApiOperation(value = "협력사별 출력인원 현황 정보 리스트", notes = "협력사별 출력인원 현황 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("searchAttendantSituationForMonitoring")
    public DefaultHttpRes<List<AttendantSituationVo>> searchAttendantSituationForMonitoring(
        @Valid @RequestBody ExportAttendantSituationRequest request) {
        return new DefaultHttpRes<List<AttendantSituationVo>>(BaseCode.SUCCESS, gpsService.findAttendantSituationListForMonitoring(request.getConditionMap()));
    }

    @ApiOperation(value = "출력인원 정보 리스트", notes = "출력인원 검색 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("searchAttendantForMonitoring")
    public DefaultHttpRes<List<AttendantVo>> searchAttendantForMonitoring(
        @Valid @RequestBody ExportAttendantRequest request) {
        return new DefaultHttpRes<List<AttendantVo>>(BaseCode.SUCCESS, gpsService.findAttendantListForMonitoring(request.getConditionMap()));
    }

    /*
    @ApiOperation(value = "현재인원 정보 리스트", notes = "현재인원 검색 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("searchCurrentAttendantForMonitoring")
    public DefaultHttpRes<List<AttendantVo>> searchCurrentAttendantForMonitoring(
        @Valid @RequestBody ExportAttendantRequest request) {
        return new DefaultHttpRes<List<AttendantVo>>(BaseCode.SUCCESS, gpsService.findCurrentAttendantListForMonitoring(request.getConditionMap()));
    }
     */

}
