package kr.co.hulan.aas.mvc.api.workplace.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.mvc.api.workplace.controller.request.ConstructionSiteCreateRequest;
import kr.co.hulan.aas.mvc.api.workplace.controller.request.ConstructionSiteExportRequest;
import kr.co.hulan.aas.mvc.api.workplace.controller.request.ConstructionSitePagingListRequest;
import kr.co.hulan.aas.mvc.api.workplace.controller.request.ConstructionSiteUpdateRequest;
import kr.co.hulan.aas.mvc.api.workplace.service.ConstructionSiteService;
import kr.co.hulan.aas.mvc.api.workplace.vo.ConstructionSiteManagerVo;
import kr.co.hulan.aas.mvc.api.workplace.vo.ConstrunctionSiteVo;
import kr.co.hulan.aas.mvc.model.domain.ConstructionSiteKey;
import kr.co.hulan.aas.mvc.model.domain.ConstructionSiteManager;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/workplace/conCompany")
@Api(tags = "건설사 현장 편입 정보 관리")
public class ConstructionSiteController {

  @Autowired
  private ConstructionSiteService constructionSiteService;

  @ApiOperation(value = "건설사 현장 편입 검색", notes = "건설사 현장 편입 검색 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping(value="/search", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<DefaultPageResult<ConstrunctionSiteVo>> search(
      @Valid @RequestBody ConstructionSitePagingListRequest request) {
    return new DefaultHttpRes(BaseCode.SUCCESS, constructionSiteService.findListPage(request));
  }

  @ApiOperation(value = "건설사 현장 편입 데이터 Export", notes = "건설사 현장 편입 데이터 Export 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping(value="/export", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<ConstrunctionSiteVo>> export(
      @Valid @RequestBody ConstructionSiteExportRequest request) {
    return new DefaultHttpRes<List<ConstrunctionSiteVo>>(BaseCode.SUCCESS, constructionSiteService.findListByCondition(request.getConditionMap()));
  }

  @ApiOperation(value = "건설사 현장 편입 조회", notes = "건설사 현장 편입 정보를 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "ccId", value = "건설사 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/{wpId}_{ccId}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<ConstrunctionSiteVo> detail(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "ccId", required = true) String ccId
  ) {
    return new DefaultHttpRes(BaseCode.SUCCESS,  constructionSiteService.findInfo(
        ConstructionSiteKey.builder()
            .wpId(wpId)
            .ccId(ccId)
            .build())
    );
  }

  @ApiOperation(value = "건설사 현장 편입 정보 등록", notes = "건설사 현장 편입 정보 등록 제공한다.")
  @PostMapping(produces="application/json;charset=UTF-8")
  public DefaultHttpRes create(
      @Valid @RequestBody ConstructionSiteCreateRequest request
  ) {
    constructionSiteService.create(request);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  @ApiOperation(value = "건설사 현장 편입 정보 수정", notes = "건설사 현장 편입 정보 수정 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "ccId", value = "건설사 아이디", required = true, dataType = "string", paramType = "path")
  })
  @PutMapping(value="/{wpId}_{ccId}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes update(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "ccId", required = true) String ccId,
      @Valid @RequestBody ConstructionSiteUpdateRequest request
  ) {
    constructionSiteService.update(ConstructionSiteKey.builder()
        .wpId(wpId)
        .ccId(ccId)
        .build(),
        request
    );
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }


  @ApiOperation(value = "건설사 현장 편입 정보 삭제", notes = "건설사 현장 편입 정보 삭제 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "ccId", value = "건설사 아이디", required = true, dataType = "string", paramType = "path")
  })
  @DeleteMapping(value="/{wpId}_{ccId}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes delete(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "ccId", required = true) String ccId
  ) {
    constructionSiteService.delete(ConstructionSiteKey.builder()
        .wpId(wpId)
        .ccId(ccId)
        .build()
    );
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  @ApiOperation(value = "건설사 현장 편입 정보 복수 삭제", notes = "건설사 현장 편입 정보 복수 삭제 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId_ccid", value = " '현장 아이디'_'건설사 아이디' ", required = true, dataType = "string", paramType = "query")
  })
  @DeleteMapping(produces="application/json;charset=UTF-8")
  public DefaultHttpRes deleteMultiple(
      @RequestParam(value = "wpId_ccid", required = true) List<String> keyList) {
    constructionSiteService.deleteMultiple(keyList);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }


  @ApiOperation(value = "건설현장 매니저 리스트 요청", notes = "건설현장 매니저 리스트 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "ccId", value = "건설사 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "mbId", value = "건설현장 매니저 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/{wpId}_{ccId}/manager", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<ConstructionSiteManagerVo>> assignManager(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "ccId", required = true) String ccId
  ) {
    return new DefaultHttpRes(BaseCode.SUCCESS, constructionSiteService.findConstructionSiteMangerListByKey(ConstructionSiteKey.builder()
        .wpId(wpId)
        .ccId(ccId)
        .build()));
  }

  @ApiOperation(value = "건설현장 매니저 편입 요청", notes = "건설현장 매니저 편입 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "ccId", value = "건설사 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "mbId", value = "건설현장 매니저 아이디", required = true, dataType = "string", paramType = "path")
  })
  @PostMapping(value="/{wpId}_{ccId}/manager/{mbId}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<ConstrunctionSiteVo> assignManager(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "ccId", required = true) String ccId,
      @PathVariable(value = "mbId", required = true) String mbId
  ) {
    constructionSiteService.assignManager(wpId, ccId, mbId);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  @ApiOperation(value = "건설현장 매니저 편입 삭제 요청", notes = "건설현장 매니저 편입 삭제를 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "ccId", value = "건설사 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "mbId", value = "건설현장 매니저 아이디", required = true, dataType = "string", paramType = "path")
  })
  @DeleteMapping(value="/{wpId}_{ccId}/manager/{mbId}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<ConstrunctionSiteVo> dismissManager(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "ccId", required = true) String ccId,
      @PathVariable(value = "mbId", required = true) String mbId
  ) {
    constructionSiteService.dismissManager(wpId, ccId, mbId);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

}
