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
import kr.co.hulan.aas.mvc.api.hicc.controller.request.CooperativeAttendanceStatExportV2Request;
import kr.co.hulan.aas.mvc.api.hicc.controller.request.CooperativeAttendanceStatListV2Request;
import kr.co.hulan.aas.mvc.api.hicc.controller.request.CooperativeSectionStatExportRequest;
import kr.co.hulan.aas.mvc.api.hicc.controller.request.CooperativeWorkplaceAttendanceStatExportRequest;
import kr.co.hulan.aas.mvc.api.hicc.controller.request.CooperativeWorkplaceAttendanceStatRequest;
import kr.co.hulan.aas.mvc.api.hicc.controller.response.HiccCoopWorkplaceAttendanceStatResponse;
import kr.co.hulan.aas.mvc.api.hicc.controller.response.HiccCooperativeSectionStatResponse;
import kr.co.hulan.aas.mvc.api.hicc.controller.response.HiccCooperativeStateV2Response;
import kr.co.hulan.aas.mvc.api.hicc.service.HiccCooperativeState2Service;
import kr.co.hulan.aas.mvc.api.hicc.vo.coop.CooperativeAttendanceStatVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.coop.CooperativeVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceAttendanceStatVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hicc/v2/coop")
@Api(tags = "[IMOC-C 2.0] 협력사 현황 API")
public class HiccCooperativeStateV2Controller {

  @Autowired
  private HiccCooperativeState2Service hiccCooperativeState2Service;

  @ApiOperation(value = "협력사 리스트 조회", notes = "협력사 리스트 조회 제공한다.", produces="application/json;charset=UTF-8")
  @GetMapping(produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<CooperativeVo>> cooperativeList(){
    return new DefaultHttpRes<List<CooperativeVo>>(
        BaseCode.SUCCESS, hiccCooperativeState2Service.findCooperativeList());
  }

  @ApiOperation(value = "협력사 현황 조회", notes = "협력사 현황 조회 제공한다.", produces="application/json;charset=UTF-8")
  @GetMapping(value="/state", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<HiccCooperativeStateV2Response> cooperativeState(){
    return new DefaultHttpRes<HiccCooperativeStateV2Response>(
        BaseCode.SUCCESS, hiccCooperativeState2Service.findCooperativeState());
  }


  @ApiOperation(value = "공종별 협력사 풀 조회", notes = "공종별 협력사 풀 조회 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping(value="/section/search", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<HiccCooperativeSectionStatResponse> cooperativeSectionStat(
      @Valid @RequestBody CooperativeSectionStatExportRequest request
  ){
    return new DefaultHttpRes<HiccCooperativeSectionStatResponse>(
        BaseCode.SUCCESS, hiccCooperativeState2Service.findCooperativeSectionStatList(request));
  }

  @ApiOperation(value = "협력사 출력현황 리스트 조회", notes = "협력사 출력현황 리스트 조회 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping(value="/state/search", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<DefaultPageResult<CooperativeAttendanceStatVo>> cooperativeAttendanceStatPagingList(
      @Valid @RequestBody CooperativeAttendanceStatListV2Request request
  ){
    return new DefaultHttpRes<DefaultPageResult<CooperativeAttendanceStatVo>>(
        BaseCode.SUCCESS, hiccCooperativeState2Service.findCooperativeAttendanceStatPagingList(request));
  }

  @ApiOperation(value = "협력사 출력현황 데이터 Export", notes = "협력사 출력현황 데이터 Export 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping(value="/state/export", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<CooperativeAttendanceStatVo>> cooperativeAttendanceStatList(
      @Valid @RequestBody CooperativeAttendanceStatExportV2Request request
  ){
    return new DefaultHttpRes<List<CooperativeAttendanceStatVo>>(
        BaseCode.SUCCESS, hiccCooperativeState2Service.findCooperativeAttendanceStatList(request.getConditionMap()));
  }

  @ApiOperation(value = "협력사 현장별 출력 현황 조회", notes = "협력사 현장별 출력 현황 조회 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "coopMbId", value = "협력사 아이디", required = true, dataType = "integer", paramType = "path")
  })
  @PostMapping(value="/state/{coopMbId}/search", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<HiccCoopWorkplaceAttendanceStatResponse> cooperativeAttendanceStatList(
      @Valid @RequestBody CooperativeWorkplaceAttendanceStatRequest request,
      @PathVariable(value = "coopMbId", required = true) String coopMbId
  ){
    return new DefaultHttpRes<HiccCoopWorkplaceAttendanceStatResponse>(
        BaseCode.SUCCESS, hiccCooperativeState2Service.findCooperativeWorkplaceAttendanceStatPagingList(coopMbId, request));
  }


  @ApiOperation(value = "협력사 현장별 출력 현황 데이터 export", notes = "협력사 현장별 출력 현황 데이터 export 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "coopMbId", value = "협력사 아이디", required = true, dataType = "integer", paramType = "path")
  })
  @PostMapping(value="/state/{coopMbId}/export", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<HiccWorkplaceAttendanceStatVo>> cooperativeAttendanceStatList(
      @Valid @RequestBody CooperativeWorkplaceAttendanceStatExportRequest request,
      @PathVariable(value = "coopMbId", required = true) String coopMbId
  ){
    return new DefaultHttpRes<List<HiccWorkplaceAttendanceStatVo>>(
        BaseCode.SUCCESS, hiccCooperativeState2Service.findCooperativeWorkplaceAttendanceStatList(coopMbId, request));
  }
}
