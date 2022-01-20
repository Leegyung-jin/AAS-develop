package kr.co.hulan.aas.mvc.api.component.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.mvc.api.component.controller.request.HulanUiComponentCreateRequest;
import kr.co.hulan.aas.mvc.api.component.controller.request.HulanUiComponentExportRequest;
import kr.co.hulan.aas.mvc.api.component.controller.request.HulanUiComponentPagingListRequest;
import kr.co.hulan.aas.mvc.api.component.controller.request.HulanUiComponentUpdateRequest;
import kr.co.hulan.aas.mvc.api.component.service.HulanUiComponentService;
import kr.co.hulan.aas.mvc.api.component.vo.HulanUiComponentDetailVo;
import kr.co.hulan.aas.mvc.api.component.vo.HulanUiComponentLevelSelectVo;
import kr.co.hulan.aas.mvc.api.component.vo.HulanUiComponentVo;
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
@RequestMapping("/api/ui/v2/component")
@Api(tags = "현장 모니터링 UI 컴포넌트 [V2.0] API")
public class HulanUiComponentController {

  @Autowired
  private HulanUiComponentService uiComponentService;

  @ApiOperation(value = "현장 모니터링 UI 컴포넌트 검색", notes = "현장 모니터링 UI 컴포넌트 검색 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping(value="search", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<DefaultPageResult<HulanUiComponentVo>> search(
      @Valid @RequestBody HulanUiComponentPagingListRequest request) {
    return new DefaultHttpRes<DefaultPageResult<HulanUiComponentVo>>(BaseCode.SUCCESS, uiComponentService.findListPage(request));
  }

  @ApiOperation(value = "현장 모니터링 UI 데이터 Export", notes = "현장 모니터링 UI 데이터 Export 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping(value="/export", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<List<HulanUiComponentVo>> export(
      @Valid @RequestBody HulanUiComponentExportRequest request) {
    return new DefaultHttpRes<List<HulanUiComponentVo>>(BaseCode.SUCCESS, uiComponentService.findListByCondition(request.getConditionMap()));
  }

  @ApiOperation(value = "현장 모니터링 UI 조회", notes = "현장 모니터링 UI 정보를 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "hcmptId", value = "컴포넌트 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/{hcmptId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<HulanUiComponentDetailVo> detail(
      @PathVariable(value = "hcmptId", required = true) String hcmptId) {
    return new DefaultHttpRes<HulanUiComponentDetailVo>(BaseCode.SUCCESS,  uiComponentService.findById(hcmptId));
  }

  /*
  @ApiOperation(value = "현장 모니터링 UI 사용자 레벨(등급) 선택 리스트", notes = "현장 모니터링 UI 사용자 레벨(등급) 선택 리스트를 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "hcmptId", value = "컴포넌트 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/{hcmptId}/level", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<List<HulanUiComponentLevelSelectVo>> selectLevelList(
      @PathVariable(value = "hcmptId", required = true) String hcmptId) {
    return new DefaultHttpRes<List<HulanUiComponentLevelSelectVo>>(BaseCode.SUCCESS,  uiComponentService.findSelectLevelListByHcmptId(hcmptId));
  }
   */

  @ApiOperation(value = "현장 모니터링 UI 정보 등록", notes = "현장 모니터링 UI 정보 등록 제공한다.")
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes create(
      @Valid @RequestBody HulanUiComponentCreateRequest request) {
    uiComponentService.create(request);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }


  @ApiOperation(value = "현장 모니터링 UI 정보 수정", notes = "현장 모니터링 UI 정보 수정 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "hcmptId", value = "컴포넌트 아이디", required = true, dataType = "string", paramType = "path")
  })
  @PutMapping(value="/{hcmptId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes update(
      @PathVariable(value = "hcmptId", required = true) String hcmptId,
      @Valid @RequestBody HulanUiComponentUpdateRequest request
  ) {
    uiComponentService.update(request, hcmptId);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }


  @ApiOperation(value = "현장 모니터링 UI 정보 삭제", notes = "현장 모니터링 UI 정보 삭제 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "hcmptId", value = "컴포넌트 아이디", required = true, dataType = "string", paramType = "path")
  })
  @DeleteMapping(value="/{hcmptId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes delete(
      @PathVariable(value = "hcmptId", required = true) String hcmptId) {
    uiComponentService.delete(hcmptId);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  @ApiOperation(value = "현장 모니터링 UI 정보 복수 삭제", notes = "현장 모니터링 UI 정보 복수 삭제 제공한다.")
  @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes deleteMultiple(
      @RequestParam(value = "hcmptId", required = true) List<String> hcmptIdList) {
    uiComponentService.deleteMultiple(hcmptIdList);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }
}
