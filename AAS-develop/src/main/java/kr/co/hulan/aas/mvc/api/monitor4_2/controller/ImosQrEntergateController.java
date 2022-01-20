package kr.co.hulan.aas.mvc.api.monitor4_2.controller;

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
import kr.co.hulan.aas.mvc.api.monitor.controller.response.AttendantVo;
import kr.co.hulan.aas.mvc.api.monitor4_1.controller.request.EntergateWorkerHistoryExportRequest;
import kr.co.hulan.aas.mvc.api.monitor4_1.controller.request.EntergateWorkerHistoryPagingListRequest;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.response.ImosQrGateAttendantListResponse;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.response.ImosQrGateCoopAttendanceResponse;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.response.ImosQrGateCoopWorkStatusResponse;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.response.ImosQrGateCoopWorkingWorkerListResponse;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.response.ImosQrGateManagerListResponse;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.response.ImosQrGateStateResponse;
import kr.co.hulan.aas.mvc.api.monitor4_2.service.ImosQrEntergateService;
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
@RequestMapping("/api/monitor/4.2/workplace/{wpId}/entergate/qr")
@Api(tags = "[4.2]IMOS QR Reader 출입게이트 컴포넌트 API")
public class ImosQrEntergateController {

  @Autowired
  private ImosQrEntergateService imosQrEntergateService;

  @ApiOperation(value = "QR Reader 출입게이트 현황 정보", notes = "QR Reader 출입게이트 현황 정보 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/state", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<ImosQrGateStateResponse> stat(
      @PathVariable(value = "wpId", required = true) String wpId
  ) {
    return new DefaultHttpRes<ImosQrGateStateResponse>(BaseCode.SUCCESS, imosQrEntergateService.findGateSystemStat(wpId));
  }

  @ApiOperation(value = "QR Reader 출입게이트 출근자 유형별 협력사 현황 정보", notes = "QR Reader 출입게이트 출근자 유형별 협력사 현황 정보 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "attendantType", value = "출근자 유형. 1: 근로자-QR (QR Scan 근로자), 2: 근로자-기타 (기타 출역 근로자)", required = true, dataType = "integer", paramType = "path")
  })
  @GetMapping(value="/attendant/{attendantType}/coop", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<ImosQrGateCoopAttendanceResponse> appStatusWorkerList(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "attendantType", required = true) int attendantType
  ) {
    return new DefaultHttpRes<ImosQrGateCoopAttendanceResponse>(BaseCode.SUCCESS, imosQrEntergateService.findGateCoopState(wpId, attendantType));
  }

  @ApiOperation(value = "QR Reader 출입게이트 출근자 유형별 협력사 근로자 리스트", notes = "QR Reader 출입게이트 출역유형별 협력사 근로자 리스트 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "attendantType", value = "출근자 유형. 1: 근로자-QR (QR Scan 근로자), 2: 근로자-기타 (기타 출역 근로자)", required = true, dataType = "integer", paramType = "path"),
      @ApiImplicitParam(name = "coopMbId", value = "협력사 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/attendant/{attendantType}/coop/{coopMbId}/worker", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<ImosQrGateAttendantListResponse> appStatusWorkerList(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "attendantType", required = true) int attendantType,
      @PathVariable(value = "coopMbId", required = true) String coopMbId
  ) {
    return new DefaultHttpRes<ImosQrGateAttendantListResponse>(BaseCode.SUCCESS, imosQrEntergateService.findGateAttendantWorkerList(wpId, attendantType, coopMbId));
  }

  @ApiOperation(value = "QR Reader 출입게이트 출근 관리자 리스트", notes = "QR Reader 출입게이트 출근 관리자 리스트 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/attendant/manager", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<ImosQrGateManagerListResponse> appStatusWorkerList(
      @PathVariable(value = "wpId", required = true) String wpId
  ) {
    return new DefaultHttpRes<ImosQrGateManagerListResponse>(BaseCode.SUCCESS, imosQrEntergateService.findGateAttendantManagerList(wpId));
  }

  @ApiOperation(value = "QR Reader 출입게이트 협력사별 근로자 작업현황 정보", notes = "QR Reader 출입게이트 협력사별 근로자 작업현황 정보 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "currentWorking", value = "현재 작업여부. 0: 종료, 1: 작업", required = true, dataType = "int", paramType = "path")
  })
  @GetMapping(value="/workstatus/{currentWorking}/coop", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<ImosQrGateCoopWorkStatusResponse> bleWorkerListByCurrentWorking(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "currentWorking", required = true) int currentWorking
  ) {
    return new DefaultHttpRes<ImosQrGateCoopWorkStatusResponse>(BaseCode.SUCCESS, imosQrEntergateService.findCoopListByWorkingStatus(wpId, currentWorking));
  }

  @ApiOperation(value = "QR Reader 출입게이트 작업현황 근로자 리스트 정보", notes = "QR Reader 출입게이트 작업현황 근로자 리스트 정보 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "currentWorking", value = "현재 작업여부. 0: 종료, 1: 작업", required = true, dataType = "int", paramType = "path"),
      @ApiImplicitParam(name = "coopMbId", value = "협력사 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/workstatus/{currentWorking}/coop/{coopMbId}/worker", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<ImosQrGateCoopWorkingWorkerListResponse> bleWorkerListByCurrentWorking(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "currentWorking", required = true) int currentWorking,
      @PathVariable(value = "coopMbId", required = true) String coopMbId
  ) {
    return new DefaultHttpRes<ImosQrGateCoopWorkingWorkerListResponse>(BaseCode.SUCCESS, imosQrEntergateService.findCoopWorkerListByWorkingStatus(wpId, currentWorking, coopMbId));
  }

  @ApiOperation(value = "QR Reader 출입게이트 출근자 이력 검색", notes = "QR Reader 출입게이트 출근자 이력 검색 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @PostMapping(value="/worker/history/search", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<DefaultPageResult<AttendantVo>> entergateWorkerHistorySearch(
      @PathVariable(value = "wpId", required = true) String wpId,
      @Valid @RequestBody EntergateWorkerHistoryPagingListRequest request
  ) {
    return new DefaultHttpRes<DefaultPageResult<AttendantVo>>(BaseCode.SUCCESS, imosQrEntergateService.findGateEntranceHistoryPagingList(wpId, request));
  }

  @ApiOperation(value = "QR Reader 출입게이트 출근자 이력 Export", notes = "QR Reader 출입게이트 이력 Export 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @PostMapping(value="/worker/history/export", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<AttendantVo>> entergateWorkerHistorySearch(
      @PathVariable(value = "wpId", required = true) String wpId,
      @Valid @RequestBody EntergateWorkerHistoryExportRequest request
  ) {
    Map<String,Object> condition = request.getConditionMap();
    condition.put("wpId", wpId);
    return new DefaultHttpRes<List<AttendantVo>>(BaseCode.SUCCESS, imosQrEntergateService.findGateEntranceHistoryList(condition));
  }

}
