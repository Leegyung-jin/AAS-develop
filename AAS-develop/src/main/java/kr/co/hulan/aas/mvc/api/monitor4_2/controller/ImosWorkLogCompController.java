package kr.co.hulan.aas.mvc.api.monitor4_2.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.AttendantSituationVo;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.request.ImosWorkLogWorkerExportRequest;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.response.ImosWorkStatusResponse;
import kr.co.hulan.aas.mvc.api.monitor4_2.service.ImosWorkLogComponentService;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.ImosCoopStatVo;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.ImosWorkingWorkerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/monitor/4.2/workplace/{wpId}/workstatus")
@Api(tags = "[4.2]IMOS 근로자 출역관리 컴포넌트 API")
public class ImosWorkLogCompController {

  @Autowired
  private ImosWorkLogComponentService imosWorkLogComponentService;

  @ApiOperation(value = "전체 출력현황 정보", notes = "전체 출력현황 정보 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "monitorType", value = "모니터링 타입. 유효값은 [ble|gps|none] 값 중 하나이며 none 입력시 현장에 설정된 지원 모니터링 정보 토대로 지정된다. ( gps 우선 )", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/{monitorType}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<ImosWorkStatusResponse> workStatusByMode(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "monitorType", required = true) String monitorType
  ) {
    return new DefaultHttpRes<ImosWorkStatusResponse>(BaseCode.SUCCESS, imosWorkLogComponentService.findWorkStatusSummary(wpId, monitorType));
  }

  @ApiOperation(value = "협력사 출력/작업인원 현황 정보", notes = "협력사 출력/작업인원 현황 정보 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "monitorType", value = "모니터링 타입. 유효값은 [ble|gps|3d|none] 값 중 하나이며 none 입력시 현장에 설정된 지원 모니터링 정보 토대로 지정된다. ( gps 우선 )", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/{monitorType}/coop", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<ImosCoopStatVo>> workStatusPerCoop(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "monitorType", required = true) String monitorType
  ) {
    return new DefaultHttpRes(BaseCode.SUCCESS, imosWorkLogComponentService.findCoopList(wpId, monitorType));
  }

  @ApiOperation(value = "출근 유형에 따른 협력사 출력인원 현황 정보", notes = "출근 유형에 따른 협력사 출력인원 현황 정보 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "monitorType", value = "모니터링 타입. 유효값은 [ble|gps|3d|none] 값 중 하나이며 none 입력시 현장에 설정된 지원 모니터링 정보 토대로 지정된다. ( gps 우선 )", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "commuteType", value = "출근 유형. 2: QRCode, 3: 안면인식, 4: 출입센서, 5: Geofence, 6: GeofencePoligon, 99: 기타 ", required = true, dataType = "int", paramType = "path")
  })
  @GetMapping(value="/{monitorType}/coop/commute/{commuteType}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<ImosCoopStatVo>> coopSituationByCommuteType(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "monitorType", required = true) String monitorType,
      @PathVariable(value = "commuteType", required = true) int commuteType
  ) {
    return new DefaultHttpRes(BaseCode.SUCCESS, imosWorkLogComponentService.findCoopListByCommuteType(wpId, monitorType, commuteType));
  }

  @ApiOperation(value = "실시간 작업/종료인원 협력사 출력인원 현황 정보", notes = "실시간 작업/종료인원 협력사 출력인원 현황 정보 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "monitorType", value = "모니터링 타입. 유효값은 [ble|gps|3d|none] 값 중 하나이며 none 입력시 현장에 설정된 지원 모니터링 정보 토대로 지정된다. ( gps 우선 )", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "currentWorking", value = "현재 작업여부. 0: 종료, 1: 작업", required = true, dataType = "int", paramType = "path")
  })
  @GetMapping(value="/{monitorType}/coop/active/{currentWorking}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<ImosCoopStatVo>> coopSituationByCurrentWorking(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "monitorType", required = true) String monitorType,
      @PathVariable(value = "currentWorking", required = true) int currentWorking
  ) {
    return new DefaultHttpRes(BaseCode.SUCCESS, imosWorkLogComponentService.findCoopListByCurrentWorking(wpId, monitorType ,currentWorking));
  }

  @ApiOperation(value = "근로자 정보 Export", notes = "근로자 정보 Export 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "monitorType", value = "모니터링 타입. 유효값은 [ble|gps|3d|none] 값 중 하나이며 none 입력시 현장에 설정된 지원 모니터링 정보 토대로 지정된다. ( gps 우선 )", required = true, dataType = "string", paramType = "path")
  })
  @PostMapping(value="/{monitorType}/worker/export", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<ImosWorkingWorkerVo>> coopWorkerListByCommuteType(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "monitorType", required = true) String monitorType,
      @Valid @RequestBody ImosWorkLogWorkerExportRequest request
  ) {
    return new DefaultHttpRes(BaseCode.SUCCESS, imosWorkLogComponentService.findWorkerList(wpId, monitorType, request));
  }


}
