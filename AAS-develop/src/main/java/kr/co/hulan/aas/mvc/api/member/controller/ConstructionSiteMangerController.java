package kr.co.hulan.aas.mvc.api.member.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.mvc.api.member.controller.request.ConstructionSiteManagerExportRequest;
import kr.co.hulan.aas.mvc.api.member.controller.request.ConstructionSiteManagerPagingListRequest;
import kr.co.hulan.aas.mvc.api.member.controller.response.ListConstructionCompanyResponse;
import kr.co.hulan.aas.mvc.api.member.dto.ConstructionCompanyDto;
import kr.co.hulan.aas.mvc.api.member.dto.ConstructionSiteManagerDto;
import kr.co.hulan.aas.mvc.api.member.service.ConstructionSiteManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member/siteManager")
@Api(tags = "건설현장 관리자 API")
public class ConstructionSiteMangerController {

  @Autowired
  private ConstructionSiteManagerService constructionSiteManagerService;

  @ApiOperation(value = "건설현장 관리자 리스트", notes = "건설현장 관리자 검색 제공한다.", consumes="application/json;charset=UTF-8")
  @PostMapping("search")
  public DefaultHttpRes<DefaultPageResult<ConstructionSiteManagerDto>> searchConCompany(
      @Valid @RequestBody ConstructionSiteManagerPagingListRequest request) {
    return new DefaultHttpRes<DefaultPageResult<ConstructionSiteManagerDto>>(BaseCode.SUCCESS, constructionSiteManagerService.findListPage(request));
  }

  @ApiOperation(value = "건설현장 관리자 리스트 데이터 Export", notes = "건설현장 관리자 리스트 데이터 Export 제공한다.", consumes="application/json;charset=UTF-8")
  @PostMapping("export")
  public DefaultHttpRes<List<ConstructionSiteManagerDto>> exportConCompany(
      @Valid @RequestBody ConstructionSiteManagerExportRequest request) {
    return new DefaultHttpRes<List<ConstructionSiteManagerDto>>(BaseCode.SUCCESS, constructionSiteManagerService.findListByCondition(request.getConditionMap()));
  }

}
