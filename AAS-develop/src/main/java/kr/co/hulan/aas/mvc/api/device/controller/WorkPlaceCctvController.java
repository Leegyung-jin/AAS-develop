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
import kr.co.hulan.aas.mvc.api.component.controller.request.ExportUiComponentDataRequest;
import kr.co.hulan.aas.mvc.api.device.controller.request.WorkPlaceCctvCreateRequest;
import kr.co.hulan.aas.mvc.api.device.controller.request.WorkPlaceCctvExportRequest;
import kr.co.hulan.aas.mvc.api.device.controller.request.WorkPlaceCctvListRequest;
import kr.co.hulan.aas.mvc.api.device.controller.request.WorkPlaceCctvUpdateRequest;
import kr.co.hulan.aas.mvc.api.device.service.WorkPlaceCctvService;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceCctvDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/device/cctv")
@Api(tags = "현장 CCTV 관리 API")
public class WorkPlaceCctvController {

  @Autowired
  private WorkPlaceCctvService workplaceCctvService;

  @ApiOperation(value = "현장 CCTV  검색", notes = "현장 CCTV  검색 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping(value="/search",produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<DefaultPageResult<WorkPlaceCctvDto>> search(
      @Valid @RequestBody WorkPlaceCctvListRequest request) {
    return new DefaultHttpRes<DefaultPageResult<WorkPlaceCctvDto>>(BaseCode.SUCCESS, workplaceCctvService.findListPage(request));
  }

  @ApiOperation(value = "현장 CCTV  데이터 Export", notes = "현장 CCTV  데이터 Export 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping(value="/export", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<List<WorkPlaceCctvDto>> export(
      @Valid @RequestBody WorkPlaceCctvExportRequest request) {
    return new DefaultHttpRes<List<WorkPlaceCctvDto>>(BaseCode.SUCCESS, workplaceCctvService.findListByCondition(request.getConditionMap()));
  }

  @ApiOperation(value = "현장 CCTV  조회", notes = "현장 CCTV  정보를 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "cctv_no", value = "CCTV 번호", required = true, dataType = "long", paramType = "path")
  })
  @GetMapping(value="/{cctv_no}", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<WorkPlaceCctvDto> detail(
      @PathVariable(value = "cctv_no", required = true) long cctvNo) {
    return new DefaultHttpRes(BaseCode.SUCCESS,  workplaceCctvService.findInfo(cctvNo));
  }

  @ApiOperation(value = "현장 CCTV  정보 등록", notes = "현장 CCTV  정보 등록 제공한다.")
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<WorkPlaceCctvDto> create(
      @Valid @RequestBody WorkPlaceCctvCreateRequest request) {
    return new DefaultHttpRes(BaseCode.SUCCESS, workplaceCctvService.create(request));
  }


  @ApiOperation(value = "현장 CCTV  정보 수정", notes = "현장 CCTV  정보 수정 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "cctvNo", value = "CCTV 번호", required = true, dataType = "long", paramType = "path")
  })
  @PutMapping(value="/{cctv_no}", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes update(
      @PathVariable(value = "cctv_no", required = true) long cctvNo,
      @Valid @RequestBody WorkPlaceCctvUpdateRequest request
  ) {
    workplaceCctvService.update(cctvNo, request);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }


  @ApiOperation(value = "현장 CCTV  정보 삭제", notes = "현장 CCTV  정보 삭제 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "cctv_no", value = "CCTV 번호", required = true, dataType = "long", paramType = "path")
  })
  @DeleteMapping(value="/{cctv_no}", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes delete(
      @PathVariable(value = "cctv_no", required = true) long cctvNo) {
    workplaceCctvService.delete(cctvNo);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  @ApiOperation(value = "현장 CCTV  정보 복수 삭제", notes = "현장 CCTV  정보 복수 삭제 제공한다.")
  @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes deleteMultiple(
      @RequestParam(value = "cctv_no", required = true) List<Long> cctvNoList) {
    workplaceCctvService.deleteMultiple(cctvNoList);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }
}
