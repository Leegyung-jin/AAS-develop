package kr.co.hulan.aas.mvc.api.hicc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.hicc.controller.response.HiccNationWideInfoResponse;
import kr.co.hulan.aas.mvc.api.hicc.controller.response.HiccSafetyScoreStateResponse;
import kr.co.hulan.aas.mvc.api.hicc.controller.response.HiccWorkplaceSafetyScoreStateResponse;
import kr.co.hulan.aas.mvc.api.hicc.controller.response.HiccWorkplaceStateResponse;
import kr.co.hulan.aas.mvc.api.hicc.controller.response.HiccWorkplaceSummaryPerSidoResponse;
import kr.co.hulan.aas.mvc.api.hicc.controller.response.HiccWorkplaceSummaryPerSigunguResponse;
import kr.co.hulan.aas.mvc.api.hicc.service.HiccWorkplaceService;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccStateIndicatorVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceSummaryVo;
import kr.co.hulan.aas.mvc.model.dto.AdminMsgInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Deprecated
@RestController
@RequestMapping("/api/hicc/v1/workplace")
@Api(tags = "Hulan Integrated Control Center 현장 현황 API")
public class HiccWorkplaceController {

  @Autowired
  private HiccWorkplaceService hiccWorkplaceService;

  @Deprecated
  @ApiOperation(value = "현장 리스트 조회", notes = "현장 리스트 제공한다.", produces="application/json;charset=UTF-8")
  @GetMapping(produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<HiccWorkplaceSummaryVo>> supportedWorkplaceByAccount(){
    return new DefaultHttpRes<List<HiccWorkplaceSummaryVo>>(
        BaseCode.SUCCESS, hiccWorkplaceService.findSupportedWorkplaceByAccount());
  }

  @Deprecated
  @ApiOperation(value = "전국 현황 정보 조회", notes = "전국 현황 정보 제공한다.", produces="application/json;charset=UTF-8")
  @GetMapping(value="/nationwide", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<HiccNationWideInfoResponse> nationwide(){
    return new DefaultHttpRes<HiccNationWideInfoResponse>(
        BaseCode.SUCCESS, hiccWorkplaceService.findNationWideInfo());
  }

  @Deprecated
  @ApiOperation(value = "전국 현장 현황 정보 조회", notes = "전국 현장 현황 정보 제공한다.", produces="application/json;charset=UTF-8")
  @GetMapping(value="/nationwide/workplace", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<HiccNationWideInfoResponse> nationwideWorkplace(){
    return new DefaultHttpRes<HiccNationWideInfoResponse>(
        BaseCode.SUCCESS, hiccWorkplaceService.findNationWideWorkplaceInfo());
  }

  @Deprecated
  @ApiOperation(value = "전체 현황 정보 조회", notes = "전체 현황 정보 제공한다.", produces="application/json;charset=UTF-8")
  @GetMapping(value="/nationwide/stat", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<HiccNationWideInfoResponse> nationwideStat(){
    return new DefaultHttpRes<HiccNationWideInfoResponse>(
        BaseCode.SUCCESS, hiccWorkplaceService.findNationWideStat());
  }

  @Deprecated
  @ApiOperation(value = "시도별 현장 리스트 조회", notes = "시도별 현장 리스트 제공한다.", produces="application/json;charset=UTF-8")
  @GetMapping(value="/region", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<HiccWorkplaceSummaryPerSidoResponse> sidoList(){
    return new DefaultHttpRes<HiccWorkplaceSummaryPerSidoResponse>(
        BaseCode.SUCCESS, HiccWorkplaceSummaryPerSidoResponse.builder()
        .list(hiccWorkplaceService.findWorkplaceStateListPerSido())
        .build());
  }

  @Deprecated
  @ApiOperation(value = "시도 내 시군구별 현장 현황 리스트 정보 조회", notes = "시도 내 시군구별 현장 현황 리스트 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "sido_cd", value = "시도 코드 (법정코드)", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/region/{sido_cd}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<HiccWorkplaceSummaryPerSigunguResponse> workplacePerRegion(
      @PathVariable(value = "sido_cd", required = true) String sidoCd
  ){
    return new DefaultHttpRes<HiccWorkplaceSummaryPerSigunguResponse>(
        BaseCode.SUCCESS, HiccWorkplaceSummaryPerSigunguResponse.builder()
        .sidoCd(sidoCd)
        .list(hiccWorkplaceService.findWorkplaceStateListBySido(sidoCd))
        .build());
  }

  @Deprecated
  @ApiOperation(value = "전국 현황 조회", notes = "전국 현황 제공한다.", produces="application/json;charset=UTF-8")
  @GetMapping(value="/state", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<HiccStateIndicatorVo> totalWorkplaceState(){
    return new DefaultHttpRes<HiccStateIndicatorVo>(
        BaseCode.SUCCESS, hiccWorkplaceService.findTotalWorkplaceState());
  }

  @Deprecated
  @ApiOperation(value = "현장 현황 조회", notes = "현장 현황 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wp_id", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/state/{wp_id}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<HiccWorkplaceStateResponse> workplaceState(
      @PathVariable(value = "wp_id", required = true) String wpId
  ){
    return new DefaultHttpRes<HiccWorkplaceStateResponse>(
        BaseCode.SUCCESS, hiccWorkplaceService.findWorkplaceState(wpId));
  }

  @Deprecated
  @ApiOperation(value = "안전점수 현황 조회", notes = "안전점수 현황 제공한다.", produces="application/json;charset=UTF-8")
  @GetMapping(value="/safetyscore", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<HiccSafetyScoreStateResponse> safetyScoreStateAll(){
    return new DefaultHttpRes<HiccSafetyScoreStateResponse>(
        BaseCode.SUCCESS, hiccWorkplaceService.findSafeyScoreState());
  }

  @Deprecated
  @ApiOperation(value = "현장 안전점수 현황 상세 조회", notes = "현장 안전점수 현황 상세 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wp_id", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/safetyscore/{wp_id}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<HiccWorkplaceSafetyScoreStateResponse> workplaceSafetyScoreState(
      @PathVariable(value = "wp_id", required = true) String wpId
  ){
    return new DefaultHttpRes<HiccWorkplaceSafetyScoreStateResponse>(
        BaseCode.SUCCESS, hiccWorkplaceService.findSafeyScoreStateByWpId(wpId));
  }

  @Deprecated
  @ApiOperation(value = "최근 위험 알림 리스트 조회", notes = "최근 위험 알림 리스트 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "alarm_grade", value = "위험 등급", required = false, dataType = "integer", paramType = "query")
  })
  @GetMapping(value="/alarm", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<AdminMsgInfoDto>> workplaceAdminMsgList(
      @PathVariable(value = "alarm_grade", required = false) List<Integer> alarmGrades
  ){
    return new DefaultHttpRes<List<AdminMsgInfoDto>>(
        BaseCode.SUCCESS, hiccWorkplaceService.findRecentAdminMsgList(alarmGrades));
  }
}
