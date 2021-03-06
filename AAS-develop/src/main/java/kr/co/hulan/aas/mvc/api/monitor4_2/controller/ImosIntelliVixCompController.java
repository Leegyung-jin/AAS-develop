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
@Api(tags = "[4.2]IMOS ????????? CCTV ???????????? API")
public class ImosIntelliVixCompController {

  @Autowired
  private ImosIntelliVixComponentService imosIntelliVixComponentService;

  @ApiOperation(value = "????????? CCTV NVR ????????? Export", notes = "????????? CCTV NVR ????????? Export ????????????.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "?????? ?????????", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/main",produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<ImosIntelliVixMainResponse> main(
      @PathVariable(value = "wpId", required = true) String wpId
  ) {
    return new DefaultHttpRes<ImosIntelliVixMainResponse>(BaseCode.SUCCESS, imosIntelliVixComponentService.findMainInfoById(wpId));
  }

  /* ***********************************************************
   ?????? ?????? ????????? CCTV ?????? ??????
  *********************************************************** */
  @ApiOperation(value = "????????? CCTV ????????? ?????? ??????", notes = "????????? CCTV ????????? ?????? ?????? ????????????.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "?????? ?????????", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/code/type", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<List<CodeDto>> searchCodeType(
      @PathVariable(value = "wpId", required = true) String wpId
  ) {
    return new DefaultHttpRes<List<CodeDto>>(BaseCode.SUCCESS, imosIntelliVixComponentService
        .findEventTypeCode());
  }

  @ApiOperation(value = "????????? CCTV ????????? ?????? ?????? ??????", notes = "????????? CCTV ????????? ?????? ?????? ?????? ????????????.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "?????? ?????????", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/code/actionMethod", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<List<CodeDto>> searchCodeActionMethod(
      @PathVariable(value = "wpId", required = true) String wpId
  ) {
    return new DefaultHttpRes<List<CodeDto>>(BaseCode.SUCCESS, imosIntelliVixComponentService
        .findEventActionMethodCode());
  }

  /* ***********************************************************
   ?????? ?????? ????????? CCTV ??????
  *********************************************************** */
  @ApiOperation(value = "????????? CCTV ?????? ?????? ??????", notes = "????????? CCTV ?????? ?????? ?????? ????????????.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "?????? ?????????", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/config",produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<WorkplaceNvrMonitorConfigVo> config(
      @PathVariable(value = "wpId", required = true) String wpId
  ) {
    return new DefaultHttpRes<WorkplaceNvrMonitorConfigVo>(BaseCode.SUCCESS, imosIntelliVixComponentService.findWorkplaceNvrConfig(wpId));
  }

  @ApiOperation(value = "????????? CCTV ?????? ?????? ????????????", notes = "????????? CCTV ?????? ?????? ???????????? ????????????.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "?????? ?????????", required = true, dataType = "string", paramType = "path"),
  })
  @PutMapping(value="/config",produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes updateConfig(
      @PathVariable(value = "wpId", required = true) String wpId,
      @Valid @RequestBody WorkplaceNvrMonitorConfigUpdateRequest request
  ) {
    imosIntelliVixComponentService.updateWorkplaceNvrConfig(wpId, request);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  @ApiOperation(value = "????????? CCTV ?????? ON/OFF ??????", notes = "????????? CCTV ?????? ON/OFF ????????????.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "?????? ?????????", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "alarmFlag", value = "?????? ON/OFF. 0:OFF, 1:ON", required = true, dataType = "int", paramType = "path")
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
  NVR ??????
  *********************************************************** */

  @ApiOperation(value = "????????? CCTV NVR ????????? Export", notes = "????????? CCTV NVR ????????? Export ????????????.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "?????? ?????????", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/nvr",produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<List<NetworkVideoRecoderDto>> exportNvr(
      @PathVariable(value = "wpId", required = true) String wpId
  ) {
    return new DefaultHttpRes<List<NetworkVideoRecoderDto>>(BaseCode.SUCCESS, imosIntelliVixComponentService.findNvrListByWpId(wpId));
  }

  @ApiOperation(value = "????????? CCTV ?????? ????????? Export", notes = "????????? CCTV ?????? ????????? Export ????????????.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "?????? ?????????", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/channel",produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<List<NetworkVideoRecoderChannelDto>> export(
      @PathVariable(value = "wpId", required = true) String wpId
  ) {
    return new DefaultHttpRes<List<NetworkVideoRecoderChannelDto>>(BaseCode.SUCCESS, imosIntelliVixComponentService.findNvrChannelListByWpId(wpId));
  }

  /* ***********************************************************
  CCTV ?????????
  *********************************************************** */

  @ApiOperation(value = "????????? CCTV ????????? ?????????", notes = "????????? CCTV ????????? ?????? ????????????.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "?????? ?????????", required = true, dataType = "string", paramType = "path")
  })
  @PostMapping(value="/event/search", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<DefaultPageResult<NvrEventSummaryVo>> search(
      @PathVariable(value = "wpId", required = true) String wpId,
      @Valid @RequestBody ImosIntelliVixEventPagingListRequest request) {
    return new DefaultHttpRes<DefaultPageResult<NvrEventSummaryVo>>(BaseCode.SUCCESS, imosIntelliVixComponentService
        .findListPage(request));
  }

  @ApiOperation(value = "????????? CCTV ????????? ????????? Export", notes = "????????? CCTV ????????? ????????? Export ????????????.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "?????? ?????????", required = true, dataType = "string", paramType = "path")
  })
  @PostMapping(value="/event/export",produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<List<NvrEventSummaryVo>> export(
      @PathVariable(value = "wpId", required = true) String wpId,
      @Valid @RequestBody ImosIntelliVixEventExportRequest request) {
    return new DefaultHttpRes<List<NvrEventSummaryVo>>(BaseCode.SUCCESS, imosIntelliVixComponentService.findListByCondition(request.getConditionMap()));
  }

  @ApiOperation(value = "????????? CCTV ????????? ??????", notes = "????????? CCTV ????????? ????????? ????????????.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "?????? ?????????", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "eventNo", value = "????????? CCTV ????????? ??????", required = true, dataType = "long", paramType = "path")
  })
  @GetMapping(value="/event/{eventNo}", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<NvrEventDetailVo> detail(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "eventNo", required = true) long eventNo) {
    return new DefaultHttpRes(BaseCode.SUCCESS,  imosIntelliVixComponentService.findDetailInfo(eventNo));
  }


  @ApiOperation(value = "????????? CCTV ????????? ????????????", notes = "????????? CCTV ????????? ???????????? ????????????.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "?????? ?????????", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "eventNo", value = "????????? CCTV ????????? ??????", required = true, dataType = "long", paramType = "path")
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

  @ApiOperation(value = "????????? CCTV ????????? ?????? ????????????", notes = "????????? CCTV ????????? ?????? ???????????? ????????????.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "?????? ?????????", required = true, dataType = "string", paramType = "path")
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
