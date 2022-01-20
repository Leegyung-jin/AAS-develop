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
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.request.ImosMagneticOpeningExportRequest;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.request.ImosMagneticOpeningPagingListRequest;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.response.ImosMagneticOpeningStateResponse;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.response.ImosSafetyHookCoopStateResponse;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.response.ImosSafetyHookCurrentStateResponse;
import kr.co.hulan.aas.mvc.api.monitor4_2.service.ImosMagneticOpeningService;
import kr.co.hulan.aas.mvc.api.monitor4_2.service.ImosSafetyHookComponentService;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.ImosMagneticOpeningStateVo;
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
@RequestMapping("/api/monitor/4.2/workplace/{wpId}/opening")
@Api(tags = "[4.2]IMOS 개구부 컴포넌트 API")
public class ImosMagneticOpeningCompController {


  @Autowired
  private ImosMagneticOpeningService imosMagneticOpeningService;

  @ApiOperation(value = "IMOS 개구부 현황 정보", notes = "IMOS 개구부 현황 정보 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/state", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<ImosMagneticOpeningStateResponse> state(
      @PathVariable(value = "wpId", required = true) String wpId
  ) {
    return new DefaultHttpRes<ImosMagneticOpeningStateResponse>(
        BaseCode.SUCCESS, imosMagneticOpeningService.findCurrentState(wpId));
  }

  @ApiOperation(value = "IMOS 개구부 상태 정보 검색", notes = "IMOS 개구부 상태 정보 검색 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @PostMapping(value="/search", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<DefaultPageResult<ImosMagneticOpeningStateVo>> search(
      @PathVariable(value = "wpId", required = true) String wpId,
      @Valid @RequestBody ImosMagneticOpeningPagingListRequest request
  ) {
    return new DefaultHttpRes<DefaultPageResult<ImosMagneticOpeningStateVo>>(
        BaseCode.SUCCESS, imosMagneticOpeningService.findPagingList(wpId, request));
  }

  @ApiOperation(value = "IMOS 개구부 상태 정보 Export", notes = "IMOS 개구부 상태 정보 Export 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @PostMapping(value="/export", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<ImosMagneticOpeningStateVo>> export(
      @PathVariable(value = "wpId", required = true) String wpId,
      @Valid @RequestBody ImosMagneticOpeningExportRequest request
  ) {
    Map<String,Object> condition = AuthenticationHelper.addAdditionalConditionByLevel(request.getConditionMap());
    condition.put("wpId", wpId);
    return new DefaultHttpRes<List<ImosMagneticOpeningStateVo>>(
        BaseCode.SUCCESS, imosMagneticOpeningService.findDeviceStateList(condition));
  }


  @ApiOperation(value = "개구부 위험 알림 ON/OFF", notes = "개구부 알림 ON/OFF 제공한다.", produces="application/json;charset=UTF-8")
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
    imosMagneticOpeningService.updateDeviceAlarm(wpId, idx, alarmFlag);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }
}
