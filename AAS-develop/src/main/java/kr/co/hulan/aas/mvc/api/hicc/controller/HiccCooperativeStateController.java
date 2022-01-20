package kr.co.hulan.aas.mvc.api.hicc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.mvc.api.hicc.controller.request.CooperativeAttendanceStatExportRequest;
import kr.co.hulan.aas.mvc.api.hicc.controller.request.CooperativeAttendanceStatListRequest;
import kr.co.hulan.aas.mvc.api.hicc.controller.request.CooperativeWorkplaceAttendanceStatExportRequest;
import kr.co.hulan.aas.mvc.api.hicc.controller.request.CooperativeWorkplaceAttendanceStatRequest;
import kr.co.hulan.aas.mvc.api.hicc.controller.response.HiccCoopWorkplaceAttendanceStatResponse;
import kr.co.hulan.aas.mvc.api.hicc.controller.response.HiccCooperativeSectionStatResponse;
import kr.co.hulan.aas.mvc.api.hicc.controller.response.HiccCooperativeStateResponse;
import kr.co.hulan.aas.mvc.api.hicc.service.HiccCooperativeStateService;
import kr.co.hulan.aas.mvc.api.hicc.vo.coop.CooperativeAttendanceStatVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceAttendanceStatVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hicc/v1/coop")
@Api(tags = "Hulan Integrated Control Center 협력사 현황 API")
public class HiccCooperativeStateController {

  @Autowired
  private HiccCooperativeStateService hiccCooperativeStateService;

  @ApiOperation(value = "협력사 현황 조회", notes = "협력사 현황 조회 제공한다.", produces="application/json;charset=UTF-8")
  @GetMapping(value="/state", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<HiccCooperativeStateResponse> cooperativeState(){
    return new DefaultHttpRes<HiccCooperativeStateResponse>(
        BaseCode.SUCCESS, hiccCooperativeStateService.findCooperativeState());
  }


  @ApiOperation(value = "공종별 협력사 풀 조회", notes = "공종별 협력사 풀 조회 제공한다.", produces="application/json;charset=UTF-8")
  @GetMapping(value="/section", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<HiccCooperativeSectionStatResponse> cooperativeSectionStat(){
    return new DefaultHttpRes<HiccCooperativeSectionStatResponse>(
        BaseCode.SUCCESS, hiccCooperativeStateService.findCooperativeSectionStat());
  }

  @ApiOperation(value = "협력사 출력현황 리스트 조회", notes = "협력사 출력현황 리스트 조회 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping(value="/search", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<DefaultPageResult<CooperativeAttendanceStatVo>> cooperativeAttendanceStatPagingList(
      @Valid @RequestBody CooperativeAttendanceStatListRequest request
  ){
    return new DefaultHttpRes<DefaultPageResult<CooperativeAttendanceStatVo>>(
        BaseCode.SUCCESS, hiccCooperativeStateService.findCooperativeAttendanceStatPagingList(request));
  }

  @ApiOperation(value = "협력사 출력현황 데이터 Export", notes = "협력사 출력현황 데이터 Export 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping(value="/export", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<CooperativeAttendanceStatVo>> cooperativeAttendanceStatList(
      @Valid @RequestBody CooperativeAttendanceStatExportRequest request
  ){
    return new DefaultHttpRes<List<CooperativeAttendanceStatVo>>(
        BaseCode.SUCCESS, hiccCooperativeStateService.findCooperativeAttendanceStatList(request.getConditionMap()));
  }

  @ApiOperation(value = "협력사 현장별 출력 현황 조회", notes = "협력사 현장별 출력 현황 조회 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "coop_mb_id", value = "협력사 아이디", required = true, dataType = "integer", paramType = "path")
  })
  @PostMapping(value="/{coop_mb_id}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<HiccCoopWorkplaceAttendanceStatResponse> cooperativeAttendanceStatList(
      @Valid @RequestBody CooperativeWorkplaceAttendanceStatRequest request,
      @PathVariable(value = "coop_mb_id", required = true) String coopMbId
  ){
    return new DefaultHttpRes<HiccCoopWorkplaceAttendanceStatResponse>(
        BaseCode.SUCCESS, hiccCooperativeStateService.findCooperativeWorkplaceAttendanceStatPagingList(coopMbId, request));
  }


  @ApiOperation(value = "협력사 현장별 출력 현황 데이터 export", notes = "협력사 현장별 출력 현황 데이터 export 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "coop_mb_id", value = "협력사 아이디", required = true, dataType = "integer", paramType = "path")
  })
  @PostMapping(value="/{coop_mb_id}/export", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<HiccWorkplaceAttendanceStatVo>> cooperativeAttendanceStatList(
      @Valid @RequestBody CooperativeWorkplaceAttendanceStatExportRequest request,
      @PathVariable(value = "coop_mb_id", required = true) String coopMbId
  ){
    return new DefaultHttpRes<List<HiccWorkplaceAttendanceStatVo>>(
        BaseCode.SUCCESS, hiccCooperativeStateService.findCooperativeWorkplaceAttendanceStatList(coopMbId, request));
  }
}
