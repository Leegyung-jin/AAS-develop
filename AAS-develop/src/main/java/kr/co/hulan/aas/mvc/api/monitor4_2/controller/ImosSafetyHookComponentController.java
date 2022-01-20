package kr.co.hulan.aas.mvc.api.monitor4_2.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.request.ImosTiltDeviceStatePagingListRequest;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.response.ImosSafetyHookCoopStateResponse;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.response.ImosSafetyHookCurrentStateResponse;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.response.ImosSafetyHookWorkerListResponse;
import kr.co.hulan.aas.mvc.api.monitor4_2.service.ImosSafetyHookComponentService;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.ImosSafetyHookCoopStateVo;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.ImosTiltDeviceStateVo;
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
@RequestMapping("/api/monitor/4.2/workplace/{wpId}/safetyhook")
@Api(tags = "[4.2]IMOS 안전고리 컴포넌트 API")
public class ImosSafetyHookComponentController {

  @Autowired
  private ImosSafetyHookComponentService imosSafetyHookComponentService;

  @ApiOperation(value = "IMOS 안전고리 현황 정보", notes = "IMOS 안전고리 현황 정보 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/state", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<ImosSafetyHookCurrentStateResponse> state(
      @PathVariable(value = "wpId", required = true) String wpId
  ) {
    return new DefaultHttpRes<ImosSafetyHookCurrentStateResponse>(
        BaseCode.SUCCESS, imosSafetyHookComponentService.findCurrentState(wpId));
  }

  @ApiOperation(value = "IMOS 안전고리 협력사별 현황 조회", notes = "IMOS 안전고리 협력사별 현황 조회 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "eventType", value = "이벤트 유형. 1: pairing, 2: 감지구역 진입/이탈, 3: 안전고리 잠금/해제", required = true, dataType = "integer", paramType = "path"),
      @ApiImplicitParam(name = "eventStatus", value = "상태. 0: 해제(이탈), 1: 연결(진입)", required = true, dataType = "integer", paramType = "path")
  })
  @GetMapping(value="/state/{eventType}/{eventStatus}/coop", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<ImosSafetyHookCoopStateResponse> coopState(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "eventType", required = true) int eventType,
      @PathVariable(value = "eventStatus", required = true) int state
  ) {
    return new DefaultHttpRes<ImosSafetyHookCoopStateResponse>(
        BaseCode.SUCCESS, imosSafetyHookComponentService.findCurrentCoopStateList(wpId, eventType, state));
  }

  @ApiOperation(value = "IMOS 안전고리 협력사 근로자 리스트 조회", notes = "IMOS 안전고리 협력사 근로자 리스트 조회 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "eventType", value = "이벤트 유형. 1: pairing, 2: 감지구역 진입/이탈, 3: 안전고리 잠금/해제", required = true, dataType = "integer", paramType = "path"),
      @ApiImplicitParam(name = "eventStatus", value = "상태. 0: 해제(이탈), 1: 연결(진입)", required = true, dataType = "integer", paramType = "path"),
      @ApiImplicitParam(name = "coopMbId", value = "협력사 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/state/{eventType}/{eventStatus}/coop/{coopMbId}/worker", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<ImosSafetyHookWorkerListResponse> workers(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "eventType", required = true) int eventType,
      @PathVariable(value = "eventStatus", required = true) int state,
      @PathVariable(value = "coopMbId", required = true) String coopMbId
  ) {
    return new DefaultHttpRes<ImosSafetyHookWorkerListResponse>(
        BaseCode.SUCCESS, imosSafetyHookComponentService.findCurrentWorkerList(wpId, eventType, state, coopMbId));
  }
}
