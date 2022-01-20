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
import kr.co.hulan.aas.mvc.api.code.dto.CodeDto;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.request.ImosIntelliVixEventConfirmRequest;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.request.ImosIntelliVixEventExportRequest;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.request.ImosIntelliVixEventMultiConfirmRequest;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.request.ImosIntelliVixEventPagingListRequest;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.request.WorkplaceNvrMonitorConfigUpdateRequest;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.response.ImosIntelliVixMainResponse;
import kr.co.hulan.aas.mvc.api.monitor4_2.service.ImosIntelliVixComponentService;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.nvr.NvrEventDetailVo;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.nvr.NvrEventSummaryVo;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.nvr.WorkplaceNvrMonitorConfigVo;
import kr.co.hulan.aas.mvc.model.dto.NetworkVideoRecoderChannelDto;
import kr.co.hulan.aas.mvc.model.dto.NetworkVideoRecoderDto;
import kr.co.hulan.aas.mvc.model.dto.NvrEventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/monitor/4.2/workplace/{wpId}/intellivix")
@Api(tags = "[4.2]IMOS 지능형 CCTV 컴포넌트 API")
public class ImosIntelliVixCompController {

  @Autowired
  private ImosIntelliVixComponentService imosIntelliVixComponentService;

  @ApiOperation(value = "지능형 CCTV NVR 데이터 Export", notes = "지능형 CCTV NVR 데이터 Export 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/main",produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<ImosIntelliVixMainResponse> main(
      @PathVariable(value = "wpId", required = true) String wpId
  ) {
    return new DefaultHttpRes<ImosIntelliVixMainResponse>(BaseCode.SUCCESS, imosIntelliVixComponentService.findMainInfoById(wpId));
  }

  /* ***********************************************************
   현장 관련 지능형 CCTV 관련 코드
  *********************************************************** */
  @ApiOperation(value = "지능형 CCTV 이벤트 유형 코드", notes = "지능형 CCTV 이벤트 유형 코드 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/code/type", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<List<CodeDto>> searchCodeType(
      @PathVariable(value = "wpId", required = true) String wpId
  ) {
    return new DefaultHttpRes<List<CodeDto>>(BaseCode.SUCCESS, imosIntelliVixComponentService
        .findEventTypeCode());
  }

  @ApiOperation(value = "지능형 CCTV 이벤트 처리 유형 코드", notes = "지능형 CCTV 이벤트 처리 유형 코드 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/code/actionMethod", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<List<CodeDto>> searchCodeActionMethod(
      @PathVariable(value = "wpId", required = true) String wpId
  ) {
    return new DefaultHttpRes<List<CodeDto>>(BaseCode.SUCCESS, imosIntelliVixComponentService
        .findEventActionMethodCode());
  }

  /* ***********************************************************
   현장 관련 지능형 CCTV 설정
  *********************************************************** */
  @ApiOperation(value = "지능형 CCTV 현장 설정 조회", notes = "지능형 CCTV 현장 설정 조회 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/config",produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<WorkplaceNvrMonitorConfigVo> config(
      @PathVariable(value = "wpId", required = true) String wpId
  ) {
    return new DefaultHttpRes<WorkplaceNvrMonitorConfigVo>(BaseCode.SUCCESS, imosIntelliVixComponentService.findWorkplaceNvrConfig(wpId));
  }

  @ApiOperation(value = "지능형 CCTV 현장 설정 업데이트", notes = "지능형 CCTV 현장 설정 업데이트 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
  })
  @PutMapping(value="/config",produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes updateConfig(
      @PathVariable(value = "wpId", required = true) String wpId,
      @Valid @RequestBody WorkplaceNvrMonitorConfigUpdateRequest request
  ) {
    imosIntelliVixComponentService.updateWorkplaceNvrConfig(wpId, request);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  @ApiOperation(value = "지능형 CCTV 알람 ON/OFF 설정", notes = "지능형 CCTV 알람 ON/OFF 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "alarmFlag", value = "알림 ON/OFF. 0:OFF, 1:ON", required = true, dataType = "int", paramType = "path")
  })
  @PutMapping(value="/config/nvrEvent/{alarmFlag}",produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes updateConfig(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "alarmFlag", required = true) Integer alarmFlag
  ) {
    imosIntelliVixComponentService.updateWorkplaceNvrConfig(wpId, new WorkplaceNvrMonitorConfigUpdateRequest(alarmFlag));
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  /* ***********************************************************
  NVR 채널
  *********************************************************** */

  @ApiOperation(value = "지능형 CCTV NVR 데이터 Export", notes = "지능형 CCTV NVR 데이터 Export 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/nvr",produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<List<NetworkVideoRecoderDto>> exportNvr(
      @PathVariable(value = "wpId", required = true) String wpId
  ) {
    return new DefaultHttpRes<List<NetworkVideoRecoderDto>>(BaseCode.SUCCESS, imosIntelliVixComponentService.findNvrListByWpId(wpId));
  }

  @ApiOperation(value = "지능형 CCTV 채널 데이터 Export", notes = "지능형 CCTV 채널 데이터 Export 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/channel",produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<List<NetworkVideoRecoderChannelDto>> export(
      @PathVariable(value = "wpId", required = true) String wpId
  ) {
    return new DefaultHttpRes<List<NetworkVideoRecoderChannelDto>>(BaseCode.SUCCESS, imosIntelliVixComponentService.findNvrChannelListByWpId(wpId));
  }

  /* ***********************************************************
  CCTV 이벤트
  *********************************************************** */

  @ApiOperation(value = "지능형 CCTV 이벤트 리스트", notes = "지능형 CCTV 이벤트 검색 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @PostMapping(value="/event/search", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<DefaultPageResult<NvrEventSummaryVo>> search(
      @PathVariable(value = "wpId", required = true) String wpId,
      @Valid @RequestBody ImosIntelliVixEventPagingListRequest request) {
    return new DefaultHttpRes<DefaultPageResult<NvrEventSummaryVo>>(BaseCode.SUCCESS, imosIntelliVixComponentService
        .findListPage(request));
  }

  @ApiOperation(value = "지능형 CCTV 이벤트 데이터 Export", notes = "지능형 CCTV 이벤트 데이터 Export 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @PostMapping(value="/event/export",produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<List<NvrEventSummaryVo>> export(
      @PathVariable(value = "wpId", required = true) String wpId,
      @Valid @RequestBody ImosIntelliVixEventExportRequest request) {
    return new DefaultHttpRes<List<NvrEventSummaryVo>>(BaseCode.SUCCESS, imosIntelliVixComponentService.findListByCondition(request.getConditionMap()));
  }

  @ApiOperation(value = "지능형 CCTV 이벤트 조회", notes = "지능형 CCTV 이벤트 정보를 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "eventNo", value = "지능형 CCTV 이벤트 번호", required = true, dataType = "long", paramType = "path")
  })
  @GetMapping(value="/event/{eventNo}", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<NvrEventDetailVo> detail(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "eventNo", required = true) long eventNo) {
    return new DefaultHttpRes(BaseCode.SUCCESS,  imosIntelliVixComponentService.findDetailInfo(eventNo));
  }


  @ApiOperation(value = "지능형 CCTV 이벤트 조치처리", notes = "지능형 CCTV 이벤트 조치처리 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "eventNo", value = "지능형 CCTV 이벤트 번호", required = true, dataType = "long", paramType = "path")
  })
  @PostMapping(value="/event/{eventNo}/confirm", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<NvrEventDto> confirmTreatment(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "eventNo", required = true) long eventNo,
      @Valid @RequestBody ImosIntelliVixEventConfirmRequest request
  ) {
    imosIntelliVixComponentService.confirmTreatment(eventNo, request);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  @ApiOperation(value = "지능형 CCTV 이벤트 일괄 조치처리", notes = "지능형 CCTV 이벤트 일괄 조치처리 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @PostMapping(value="/event/confirm", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<NvrEventDto> confirmTreatment(
      @PathVariable(value = "wpId", required = true) String wpId,
      @Valid @RequestBody ImosIntelliVixEventMultiConfirmRequest request
  ) {
    imosIntelliVixComponentService.confirmTreatment(request);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }


}
