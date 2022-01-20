package kr.co.hulan.aas.mvc.api.hicc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.hicc.controller.response.HiccNationWideZoneResponse;
import kr.co.hulan.aas.mvc.api.hicc.controller.response.HiccSafetyScoreStateResponse;
import kr.co.hulan.aas.mvc.api.hicc.controller.response.HiccStateIndicatorResponse;
import kr.co.hulan.aas.mvc.api.hicc.controller.response.HiccWorkplaceSafetyScoreStateResponse;
import kr.co.hulan.aas.mvc.api.hicc.controller.response.HiccWorkplaceState2Response;
import kr.co.hulan.aas.mvc.api.hicc.service.HiccWorkplace2Service;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceSummaryVo;
import kr.co.hulan.aas.mvc.model.dto.AdminMsgInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hicc/v2/workplace")
@Api(tags = "[IMOC-C 2.0] 현장 현황 API")
public class HiccWorkplaceV2Controller {

  @Autowired
  private HiccWorkplace2Service hiccWorkplace2Service;

  @ApiOperation(value = "현장 리스트 조회", notes = "현장 리스트 제공한다.", produces="application/json;charset=UTF-8")
  @GetMapping(produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<HiccWorkplaceSummaryVo>> supportedWorkplaceByAccount(){
    return new DefaultHttpRes<List<HiccWorkplaceSummaryVo>>(
        BaseCode.SUCCESS, hiccWorkplace2Service.findSupportedWorkplaceByAccount());
  }

  @ApiOperation(value = "권역별 현장 리스트 정보 조회", notes = "권역별 현장 리스트 정보 제공한다.", produces="application/json;charset=UTF-8")
  @GetMapping(value="/nationwide/zone", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<HiccNationWideZoneResponse> nationwideWorkplace(){
    return new DefaultHttpRes<HiccNationWideZoneResponse>(
        BaseCode.SUCCESS, hiccWorkplace2Service.findNationWideWorkplaceInfo());
  }

  @ApiOperation(value = "전국 현황 조회", notes = "전국 현황 제공한다.", produces="application/json;charset=UTF-8")
  @GetMapping(value="/state", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<HiccStateIndicatorResponse> totalWorkplaceState(){
    return new DefaultHttpRes<HiccStateIndicatorResponse>(
        BaseCode.SUCCESS, hiccWorkplace2Service.findTotalWorkplaceState());
  }

  @ApiOperation(value = "현장 현황 조회", notes = "현장 현황 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wp_id", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/state/{wp_id}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<HiccWorkplaceState2Response> workplaceState(
      @PathVariable(value = "wp_id", required = true) String wpId
  ){
    return new DefaultHttpRes<HiccWorkplaceState2Response>(
        BaseCode.SUCCESS, hiccWorkplace2Service.findWorkplaceState(wpId));
  }

  @ApiOperation(value = "안전점수 현황 조회", notes = "안전점수 현황 제공한다.", produces="application/json;charset=UTF-8")
  @GetMapping(value="/safetyscore", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<HiccSafetyScoreStateResponse> safetyScoreStateAll(){
    return new DefaultHttpRes<HiccSafetyScoreStateResponse>(
        BaseCode.SUCCESS, hiccWorkplace2Service.findSafeyScoreState());
  }

  @ApiOperation(value = "현장 안전점수 현황 상세 조회", notes = "현장 안전점수 현황 상세 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wp_id", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/safetyscore/{wp_id}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<HiccWorkplaceSafetyScoreStateResponse> workplaceSafetyScoreState(
      @PathVariable(value = "wp_id", required = true) String wpId
  ){
    return new DefaultHttpRes<HiccWorkplaceSafetyScoreStateResponse>(
        BaseCode.SUCCESS, hiccWorkplace2Service.findSafeyScoreStateByWpId(wpId));
  }


  @ApiOperation(value = "최근 위험 알림 리스트 조회", notes = "최근 위험 알림 리스트 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "alarm_grade", value = "위험 등급", required = false, dataType = "integer", paramType = "query")
  })
  @GetMapping(value="/alarm", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<AdminMsgInfoDto>> workplaceAdminMsgList(
      @PathVariable(value = "alarm_grade", required = false) List<Integer> alarmGrades
  ){
    return new DefaultHttpRes<List<AdminMsgInfoDto>>(
        BaseCode.SUCCESS, hiccWorkplace2Service.findRecentAdminMsgList(alarmGrades));
  }

}
