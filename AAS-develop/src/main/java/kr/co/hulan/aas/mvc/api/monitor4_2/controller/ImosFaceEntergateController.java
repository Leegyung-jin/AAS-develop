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
import kr.co.hulan.aas.mvc.api.monitor4_1.controller.request.EntergateWorkerListRequest;
import kr.co.hulan.aas.mvc.api.monitor4_1.controller.response.EntergateMemberStatResponse;
import kr.co.hulan.aas.mvc.api.monitor4_2.service.ImosFaceEntergateService;
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
@RequestMapping("/api/monitor/4.2/workplace/{wpId}/entergate/face")
@Api(tags = "[4.2]IMOS 안면인식 출입게이트 컴포넌트 API")
public class ImosFaceEntergateController {

  @Autowired
  private ImosFaceEntergateService imosFaceEntergateService;

  @ApiOperation(value = "안면인식 출입게이트 현황 정보", notes = "안면인식 출입게이트 현황 정보 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/state", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<EntergateMemberStatResponse> entergateStatus(
      @PathVariable(value = "wpId", required = true) String wpId
  ) {
    return new DefaultHttpRes(BaseCode.SUCCESS, imosFaceEntergateService.findGateSystemStat(wpId));
  }

  @ApiOperation(value = "안면인식 출입게이트 출근자 검색", notes = "안면인식 출입게이트 출근자 검색 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @PostMapping(value="/worker/search", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<AttendantVo>> appStatusWorkerList(
      @PathVariable(value = "wpId", required = true) String wpId,
      @Valid @RequestBody EntergateWorkerListRequest request
  ) {
    return new DefaultHttpRes<List<AttendantVo>>(BaseCode.SUCCESS, imosFaceEntergateService.findGateSystemEntranceUserList(wpId, request));
  }

  @ApiOperation(value = "안면인식 출입게이트 출근자 이력 검색", notes = "안면인식 출입게이트 출근자 이력 검색 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @PostMapping(value="/worker/history/search", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<DefaultPageResult<AttendantVo>> entergateWorkerHistorySearch(
      @PathVariable(value = "wpId", required = true) String wpId,
      @Valid @RequestBody EntergateWorkerHistoryPagingListRequest request
  ) {
    return new DefaultHttpRes<DefaultPageResult<AttendantVo>>(BaseCode.SUCCESS, imosFaceEntergateService.findGateEntranceHistoryPagingList(wpId, request));
  }

  @ApiOperation(value = "안면인식 출입게이트 출근자 이력 Export", notes = "안면인식 출입게이트 이력 Export 제공한다.", produces="application/json;charset=UTF-8")
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
    return new DefaultHttpRes<List<AttendantVo>>(BaseCode.SUCCESS, imosFaceEntergateService.findGateEntranceHistoryList(condition));
  }
}
