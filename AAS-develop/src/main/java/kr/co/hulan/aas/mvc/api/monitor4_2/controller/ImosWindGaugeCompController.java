package kr.co.hulan.aas.mvc.api.monitor4_2.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.request.ImosNotifyAlarmRequest;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.request.ImosTiltDeviceStatePagingListRequest;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.request.ImosWindGaugeLogExportRequest;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.response.ImosWindGaugeMainResponse;
import kr.co.hulan.aas.mvc.api.monitor4_2.service.ImosNotifyAlarmService;
import kr.co.hulan.aas.mvc.api.monitor4_2.service.ImosTiltComponentService;
import kr.co.hulan.aas.mvc.api.monitor4_2.service.ImosWindGaugeCompService;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.ImosTiltDeviceStateVo;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.windgauge.WindGaugeStatusVo;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.windgauge.WindSpeedFigureVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/monitor/4.2/workplace/{wpId}/windgauge")
@Api(tags = "[4.2]IMOS 풍속계 컴포넌트 API")
public class ImosWindGaugeCompController {

  @Autowired
  private ImosWindGaugeCompService imosWindGaugeCompService;

  @Autowired
  private ImosNotifyAlarmService imosNotifyAlarmService;

  @ApiOperation(value = "IMOS 풍속계 현황 조회", notes = "IMOS 풍속계 현황 조회 검색 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<WindGaugeStatusVo>> componentInfo(
      @PathVariable(value = "wpId", required = true) String wpId
  ) {
    return new DefaultHttpRes<List<WindGaugeStatusVo>>(
        BaseCode.SUCCESS, imosWindGaugeCompService.findWindGaugeStatusList(wpId));
  }

  @ApiOperation(value = "IMOS 풍속계 메인 정보", notes = "IMOS 풍속계 메인 정보 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/main", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<ImosWindGaugeMainResponse> main(
      @PathVariable(value = "wpId", required = true) String wpId
  ) {
    return new DefaultHttpRes<ImosWindGaugeMainResponse>(
        BaseCode.SUCCESS, imosWindGaugeCompService.findWindGaugeMainInfo(wpId));
  }

  @ApiOperation(value = "IMOS 풍속계 위험 알림 요청", notes = "IMOS 풍속계 위험 알림 요청 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @PostMapping(value="/notifyAlarm", produces="application/json;charset=UTF-8")
  public DefaultHttpRes  notifyAlarm(
      @PathVariable(value = "wpId", required = true) String wpId,
      @Valid @RequestBody ImosNotifyAlarmRequest request
  ) {
    imosNotifyAlarmService.sendAlarm(wpId, request);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  @ApiOperation(value = "IMOS 풍속계 풍속 이력 export", notes = "IMOS 풍속계 풍속 이력 export 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @PostMapping(value="/log/export", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<WindSpeedFigureVo>> deviceLogExport(
      @PathVariable(value = "wpId", required = true) String wpId,
      @Valid @RequestBody ImosWindGaugeLogExportRequest request
  ) {
    Map<String,Object> condition = request.getConditionMap();
    condition.put("wpId", wpId);
    return new DefaultHttpRes<List<WindSpeedFigureVo>>(
        BaseCode.SUCCESS, imosWindGaugeCompService.findWindSpeedHistoryList(condition));
  }

  @ApiOperation(value = "IMOS 풍속계 상세 정보 조회", notes = "IMOS 풍속계 상세 정보 조회 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "idx", value = "디바이스 넘버", required = true, dataType = "int", paramType = "path")
  })
  @GetMapping(value="/{idx}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<WindGaugeStatusVo> main(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "idx", required = true) int idx
  ) {
    return new DefaultHttpRes<WindGaugeStatusVo>(
        BaseCode.SUCCESS, imosWindGaugeCompService.findWindGaugeStatus(wpId, idx));
  }

  @ApiOperation(value = "IMOS 풍속계 풍속 이력 export", notes = "IMOS 풍속계 풍속 이력 export 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "idx", value = "디바이스 넘버", required = true, dataType = "int", paramType = "path")
  })
  @GetMapping(value="/{idx}/export", produces="application/json;charset=UTF-8")
  @Deprecated
  public DefaultHttpRes<List<WindSpeedFigureVo>> deviceHistory(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "idx", required = true) int idx
  ) {
    Map<String,Object> condition = new HashMap<String,Object>();
    condition.put("wpId", wpId);
    condition.put("idx", idx);
    return new DefaultHttpRes<List<WindSpeedFigureVo>>(
        BaseCode.SUCCESS, imosWindGaugeCompService.findWindSpeedHistoryList(condition));
  }


  @ApiOperation(value = "IMOS 풍속계 위험 알림 ON/OFF", notes = "IMOS 풍속계 위험 알림 ON/OFF 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "idx", value = "디바이스 넘버", required = true, dataType = "int", paramType = "path"),
      @ApiImplicitParam(name = "alarmFlag", value = "알림 ON/OFF. 0:OFF, 1:ON", required = true, dataType = "int", paramType = "path")
  })
  @PostMapping(value="/{idx}/{alarmFlag}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes deviceAlarmOnOff(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "idx", required = true) int idx,
      @PathVariable(value = "alarmFlag", required = true) int alarmFlag
  ) {
    imosWindGaugeCompService.updateAlarm(wpId, idx, alarmFlag);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }



}
