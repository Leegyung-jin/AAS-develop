package kr.co.hulan.aas.mvc.api.device.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.mvc.api.device.controller.request.nvr.NetworkVideoRecoderCreateRequest;
import kr.co.hulan.aas.mvc.api.device.controller.request.nvr.NetworkVideoRecoderExportRequest;
import kr.co.hulan.aas.mvc.api.device.controller.request.nvr.NetworkVideoRecoderPagingListRequest;
import kr.co.hulan.aas.mvc.api.device.controller.request.nvr.NetworkVideoRecoderUpdateRequest;
import kr.co.hulan.aas.mvc.api.device.service.NetworkVideoRecoderMgrService;
import kr.co.hulan.aas.mvc.model.dto.NetworkVideoRecoderChannelDto;
import kr.co.hulan.aas.mvc.model.dto.NetworkVideoRecoderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/device/nvr")
@Api(tags = "현장 NVR 관리 API")
public class NetworkVideoRecoderMgrController {

  @Autowired
  private NetworkVideoRecoderMgrService networkVideoRecoderMgrService;

  @ApiOperation(value = "NVR 정보 검색", notes = "NVR 정보 검색 제공한다", produces="application/json;charset=UTF-8")
  @PostMapping(value="/search", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<DefaultPageResult<NetworkVideoRecoderDto>> search(
      @RequestBody @Valid NetworkVideoRecoderPagingListRequest request
  ){
    return new DefaultHttpRes( BaseCode.SUCCESS, networkVideoRecoderMgrService.findListPage(request) );
  }


  @ApiOperation(value = "NVR 정보 데이터 Export", notes = "NVR 정보 데이터 Export 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping(value="/export", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<NetworkVideoRecoderDto>> export(
      @Valid @RequestBody NetworkVideoRecoderExportRequest request) {
    return new DefaultHttpRes<List<NetworkVideoRecoderDto>>(BaseCode.SUCCESS, networkVideoRecoderMgrService.findListByCondition(request.getConditionMap()));
  }

  @ApiOperation(value = "NVR 정보 조회", notes = "NVR 정보를 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "nvrNo", value = "NVR 관리번호", required = true, dataType = "long", paramType = "path")
  })
  @GetMapping(value="/{nvrNo}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<NetworkVideoRecoderDto> detail(
      @PathVariable(value = "nvrNo", required = true) long nvrNo) {
    return new DefaultHttpRes(BaseCode.SUCCESS,  networkVideoRecoderMgrService.findInfo(nvrNo));
  }

  @ApiOperation(value = "NVR 정보 등록", notes = "NVR 정보 등록 제공한다.")
  @PostMapping(produces="application/json;charset=UTF-8")
  public DefaultHttpRes create(
      @Valid @RequestBody NetworkVideoRecoderCreateRequest request) {
    return new DefaultHttpRes(BaseCode.SUCCESS, networkVideoRecoderMgrService.create(request));
  }

  @ApiOperation(value = "NVR 정보 수정", notes = "NVR 정보 수정 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "nvrNo", value = "NVR 관리번호", required = true, dataType = "long", paramType = "path")
  })
  @PutMapping(value="/{nvrNo}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes update(
      @Valid @RequestBody NetworkVideoRecoderUpdateRequest request,
      @PathVariable(value = "nvrNo", required = true) long nvrNo) {
    networkVideoRecoderMgrService.update(request, nvrNo);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }


  @ApiOperation(value = "NVR 정보 삭제", notes = "NVR 정보 삭제 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "nvrNo", value = "NVR 관리번호", required = true, dataType = "long", paramType = "path")
  })
  @DeleteMapping(value="/{nvrNo}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes delete(
      @PathVariable(value = "nvrNo", required = true) long nvrNo) {
    networkVideoRecoderMgrService.delete(nvrNo);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }


  @ApiOperation(value = "NVR 채널 리스트 조회", notes = "NVR 채널 리스트 정보를 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "nvrNo", value = "NVR 관리번호", required = true, dataType = "long", paramType = "path")
  })
  @GetMapping(value="/{nvrNo}/channel", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<NetworkVideoRecoderChannelDto>> channels(
      @PathVariable(value = "nvrNo", required = true) long nvrNo) {
    return new DefaultHttpRes(BaseCode.SUCCESS,  networkVideoRecoderMgrService.findChannelList(nvrNo));
  }

  @ApiOperation(value = "NVR 채널 동기화 요청 조회", notes = "NVR 채널 동기화 요청 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "nvrNo", value = "NVR 관리번호", required = true, dataType = "long", paramType = "path")
  })
  @PostMapping(value="/{nvrNo}/channel/sync", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<NetworkVideoRecoderChannelDto>> syncChannels(
      @PathVariable(value = "nvrNo", required = true) long nvrNo) {
    return new DefaultHttpRes(BaseCode.SUCCESS,  networkVideoRecoderMgrService.syncChannels(nvrNo));
  }
}
