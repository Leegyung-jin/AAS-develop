package kr.co.hulan.aas.mvc.api.component.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.component.controller.request.CreateUiComponentRequest;
import kr.co.hulan.aas.mvc.api.component.controller.request.DeleteMultiUiComponentRequest;
import kr.co.hulan.aas.mvc.api.component.controller.request.ExportUiComponentDataRequest;
import kr.co.hulan.aas.mvc.api.component.controller.request.ListUiComponentRequest;
import kr.co.hulan.aas.mvc.api.component.controller.request.UpdateUiComponentRequest;
import kr.co.hulan.aas.mvc.api.component.controller.response.ListUiComponentResponse;
import kr.co.hulan.aas.mvc.api.component.service.UiComponentService;
import kr.co.hulan.aas.mvc.api.tracker.controller.request.CreateTrackerRequest;
import kr.co.hulan.aas.mvc.api.tracker.controller.request.ExportTrackerDataRequest;
import kr.co.hulan.aas.mvc.api.tracker.controller.request.ListTrackerRequest;
import kr.co.hulan.aas.mvc.api.tracker.controller.request.UpdateTrackerRequest;
import kr.co.hulan.aas.mvc.api.tracker.controller.response.ListTrackerResponse;
import kr.co.hulan.aas.mvc.api.tracker.service.TrackerService;
import kr.co.hulan.aas.mvc.model.dto.TrackerDto;
import kr.co.hulan.aas.mvc.model.dto.UiComponentDto;
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
@RequestMapping("/api/ui/component")
@Api(tags = "현장 모니터링 UI 컴포넌트 API")
public class UiComponentController {

  @Autowired
  private UiComponentService uiComponentService;

  @ApiOperation(value = "현장 모니터링 UI 컴포넌트 검색", notes = "현장 모니터링 UI 컴포넌트 검색 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<ListUiComponentResponse> search(
      @Valid @RequestBody ListUiComponentRequest request) {
    return new DefaultHttpRes<ListUiComponentResponse>(BaseCode.SUCCESS, uiComponentService.findListPage(request));
  }

  @ApiOperation(value = "현장 모니터링 UI 데이터 Export", notes = "현장 모니터링 UI 데이터 Export 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping(value="/export", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<List<UiComponentDto>> export(
      @Valid @RequestBody ExportUiComponentDataRequest request) {
    return new DefaultHttpRes<List<UiComponentDto>>(BaseCode.SUCCESS, uiComponentService.findListByCondition(request.getConditionMap()));
  }

  @ApiOperation(value = "현장 모니터링 UI 조회", notes = "현장 모니터링 UI 정보를 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "cmptId", value = "컴포넌트 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/{cmptId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<UiComponentDto> detail(
      @PathVariable(value = "cmptId", required = true) String cmptId) {
    return new DefaultHttpRes(BaseCode.SUCCESS,  uiComponentService.findById(cmptId));
  }

  @ApiOperation(value = "현장 모니터링 UI 정보 등록", notes = "현장 모니터링 UI 정보 등록 제공한다.")
  @PostMapping(value="/create", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes create(
      @Valid @RequestBody CreateUiComponentRequest request) {
    uiComponentService.create(request);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }


  @ApiOperation(value = "현장 모니터링 UI 정보 수정", notes = "현장 모니터링 UI 정보 수정 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "cmptId", value = "컴포넌트 아이디", required = true, dataType = "string", paramType = "path")
  })
  @PostMapping(value="/{cmptId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes update(
      @PathVariable(value = "cmptId", required = true) String cmptId,
      @Valid @RequestBody UpdateUiComponentRequest request
      ) {
    uiComponentService.update(request, cmptId);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }


  @ApiOperation(value = "현장 모니터링 UI 정보 삭제", notes = "현장 모니터링 UI 정보 삭제 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "cmptId", value = "컴포넌트 아이디", required = true, dataType = "string", paramType = "path")
  })
  @DeleteMapping(value="/{cmptId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes delete(
      @PathVariable(value = "cmptId", required = true) String cmptId) {
    uiComponentService.delete(cmptId);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  @ApiOperation(value = "현장 모니터링 UI 정보 복수 삭제", notes = "현장 모니터링 UI 정보 복수 삭제 제공한다.")
  @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes deleteMultiple(
      @RequestParam(value = "cmptId", required = true) List<String> cmptIdList) {
    uiComponentService.deleteMultiple(cmptIdList);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }
}
