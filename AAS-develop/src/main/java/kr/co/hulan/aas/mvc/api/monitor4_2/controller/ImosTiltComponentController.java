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
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.mvc.api.monitor4_1.controller.response.HazardStatusResponse;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.request.ImosTiltDeviceStateExportRequest;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.request.ImosTiltDeviceStatePagingListRequest;
import kr.co.hulan.aas.mvc.api.monitor4_2.service.ImosTiltComponentService;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.ImosTiltDeviceStateVo;
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
@RequestMapping("/api/monitor/4.2/workplace/{wpId}/tilt")
@Api(tags = "[4.2]IMOS 기울기 센서 컴포넌트 API")
public class ImosTiltComponentController {

  @Autowired
  private ImosTiltComponentService imosTiltComponentService;

  @ApiOperation(value = "IMOS 기울기 센서 상태 정보 검색", notes = "IMOS 기울기 센서 상태 정보 검색 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @PostMapping(value="/search", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<DefaultPageResult<ImosTiltDeviceStateVo>> searchTiltDevice(
      @PathVariable(value = "wpId", required = true) String wpId,
      @Valid @RequestBody ImosTiltDeviceStatePagingListRequest request
  ) {
    return new DefaultHttpRes<DefaultPageResult<ImosTiltDeviceStateVo>>(
        BaseCode.SUCCESS, imosTiltComponentService.findTiltDeviceStatePagingList(wpId, request));
  }

  @ApiOperation(value = "IMOS 기울기 센서 상태 정보 Export", notes = "IMOS 기울기 센서 상태 정보 Export 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @PostMapping(value="/export", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<ImosTiltDeviceStateVo>> exportTiltDevice(
      @PathVariable(value = "wpId", required = true) String wpId,
      @Valid @RequestBody ImosTiltDeviceStateExportRequest request
  ) {
    Map<String,Object> condition = AuthenticationHelper.addAdditionalConditionByLevel(request.getConditionMap());
    condition.put("wpId", wpId);
    return new DefaultHttpRes<List<ImosTiltDeviceStateVo>>(
        BaseCode.SUCCESS, imosTiltComponentService.findTiltDeviceStateList(condition));
  }

  @ApiOperation(value = "기울기 센서 상태 정보", notes = "기울기 센서 상태 정보 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "idx", value = "디바이스 넘버", required = true, dataType = "int", paramType = "path")
  })
  @GetMapping(value="/{idx}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<ImosTiltDeviceStateVo> deviceState(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "idx", required = true) int idx
  ) {
    return new DefaultHttpRes<ImosTiltDeviceStateVo>(BaseCode.SUCCESS, imosTiltComponentService.findTiltDeviceState(wpId, idx));
  }

  @ApiOperation(value = "기울기 센서 위험 알림 ON/OFF", notes = "기울기 센서 알림 ON/OFF 제공한다.", produces="application/json;charset=UTF-8")
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
    imosTiltComponentService.updateTiltDeviceAlarm(wpId, idx, alarmFlag);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }
}
