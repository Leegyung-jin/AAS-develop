package kr.co.hulan.aas.mvc.api.entergate.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.mvc.api.entergate.controller.request.EntrGateWorkplaceCreateRequest;
import kr.co.hulan.aas.mvc.api.entergate.controller.request.EntrGateWorkplaceExportRequest;
import kr.co.hulan.aas.mvc.api.entergate.controller.request.EntrGateWorkplaceListRequest;
import kr.co.hulan.aas.mvc.api.entergate.controller.request.EntrGateWorkplaceUpdateRequest;
import kr.co.hulan.aas.mvc.api.entergate.service.EntrGateWorkplaceService;
import kr.co.hulan.aas.mvc.api.entergate.vo.EntrGateWorkplaceVo;
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
@RequestMapping("/api/entergate/workplace")
@Api(tags = "출입게이트 현장 할당 정보 관리 API")
public class EntrGateWorkplaceController {

  @Autowired
  private EntrGateWorkplaceService entrGateWorkplaceService;

  @ApiOperation(value = "출입게이트 현장 할당 정보 검색", notes = "출입게이트 현장 할당 정보 검색 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping(value="/search",produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<DefaultPageResult<EntrGateWorkplaceVo>> search(
      @Valid @RequestBody EntrGateWorkplaceListRequest request) {
    return new DefaultHttpRes<DefaultPageResult<EntrGateWorkplaceVo>>(BaseCode.SUCCESS, entrGateWorkplaceService.findListPage(request));
  }

  @ApiOperation(value = "출입게이트 현장 할당 정보 데이터 Export", notes = "출입게이트 현장 할당 정보 데이터 Export 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping(value="/export", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<List<EntrGateWorkplaceVo>> export(
      @Valid @RequestBody EntrGateWorkplaceExportRequest request) {
    return new DefaultHttpRes<List<EntrGateWorkplaceVo>>(BaseCode.SUCCESS, entrGateWorkplaceService.findListByCondition(request.getConditionMap()));
  }

  @ApiOperation(value = "출입게이트 현장 할당 정보 조회", notes = "출입게이트 현장 할당 정보 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wp_id", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/{wp_id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<EntrGateWorkplaceVo> detail(
      @PathVariable(value = "wp_id", required = true) String wpId
  ) {
    return new DefaultHttpRes(BaseCode.SUCCESS,  entrGateWorkplaceService.findInfo(wpId));
  }

  @ApiOperation(value = "출입게이트 현장 할당 정보 등록", notes = "출입게이트 현장 할당 정보 등록 제공한다.")
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes create(
      @Valid @RequestBody EntrGateWorkplaceCreateRequest request) {
    entrGateWorkplaceService.create(request);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }


  @ApiOperation(value = "출입게이트 현장 할당 정보 수정", notes = "출입게이트 현장 할당 정보 수정 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wp_id", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @PutMapping(value="/{wp_id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes update(
      @PathVariable(value = "wp_id", required = true) String wpId,
      @Valid @RequestBody EntrGateWorkplaceUpdateRequest request
  ) {
    entrGateWorkplaceService.update(wpId, request);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  @ApiOperation(value = "출입게이트 현장 할당 정보 삭제", notes = "출입게이트 현장 할당 정보 삭제 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wp_id", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @DeleteMapping(value="/{wp_id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes delete(
      @PathVariable(value = "wp_id", required = true) String wpId
  ) {
    entrGateWorkplaceService.delete(wpId);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  @ApiOperation(value = "출입게이트 현장 할당 정보 복수 삭제", notes = "출입게이트 현장 할당 정보 복수 삭제 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wp_id", value = "현장 아이디", required = true, dataType = "string", paramType = "query")
  })
  @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes deleteMultiple(
      @RequestParam(value = "wp_id", required = true) List<String> wpIdList) {
    entrGateWorkplaceService.deleteMultiple(wpIdList);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }
}
