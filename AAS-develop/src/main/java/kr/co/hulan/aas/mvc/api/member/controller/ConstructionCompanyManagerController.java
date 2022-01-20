package kr.co.hulan.aas.mvc.api.member.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.mvc.api.member.controller.request.ConCompanyManagerCreateRequest;
import kr.co.hulan.aas.mvc.api.member.controller.request.ConCompanyManagerExportRequest;
import kr.co.hulan.aas.mvc.api.member.controller.request.ConCompanyManagerPagingListRequest;
import kr.co.hulan.aas.mvc.api.member.controller.request.ConCompanyManagerUpdateRequest;
import kr.co.hulan.aas.mvc.api.member.dto.ConCompanyManagerDto;
import kr.co.hulan.aas.mvc.api.member.dto.OfficeManagerDto;
import kr.co.hulan.aas.mvc.api.member.service.ConCompanyManagerService;
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
@RequestMapping("/api/member/ccmanager")
@Api(tags = "건설사 관리자 관리")
public class ConstructionCompanyManagerController {

  @Autowired
  private ConCompanyManagerService conCompanyManagerService;

  @ApiOperation(value = "건설사 관리자 검색", notes = "건설사 관리자 검색 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping(value="/search", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<DefaultPageResult<ConCompanyManagerDto>> search(
      @Valid @RequestBody ConCompanyManagerPagingListRequest request) {
    return new DefaultHttpRes(BaseCode.SUCCESS, conCompanyManagerService.findListPage(request));
  }

  @ApiOperation(value = "건설사 관리자 데이터 Export", notes = "건설사 관리자 데이터 Export 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping(value="/export", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<ConCompanyManagerDto>> export(
      @Valid @RequestBody ConCompanyManagerExportRequest request) {
    return new DefaultHttpRes<List<ConCompanyManagerDto>>(BaseCode.SUCCESS, conCompanyManagerService.findListByCondition(request.getConditionMap()));
  }

  @ApiOperation(value = "건설사 관리자 조회", notes = "건설사 관리자 정보를 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "mbId", value = "건설사 관리자 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/{mbId}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<ConCompanyManagerDto> detail(
      @PathVariable(value = "mbId", required = true) String mbId) {
    return new DefaultHttpRes(BaseCode.SUCCESS,  conCompanyManagerService.findInfo(mbId));
  }

  @ApiOperation(value = "건설사 관리자 정보 등록", notes = "건설사 관리자 정보 등록 제공한다.")
  @PostMapping(produces="application/json;charset=UTF-8")
  public DefaultHttpRes<ConCompanyManagerDto> create(
      @Valid @RequestBody ConCompanyManagerCreateRequest request) {

    return new DefaultHttpRes(BaseCode.SUCCESS, conCompanyManagerService.create(request));
  }

  @ApiOperation(value = "건설사 관리자 정보 수정", notes = "건설사 관리자 정보 수정 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "mbId", value = "건설사 관리자 아이디", required = true, dataType = "string", paramType = "path")
  })
  @PutMapping(value="/{mbId}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes update(
      @Valid @RequestBody ConCompanyManagerUpdateRequest request,
      @PathVariable(value = "mbId", required = true) String mbId) {
    conCompanyManagerService.update(request, mbId);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }


  @ApiOperation(value = "건설사 관리자 정보 삭제", notes = "건설사 관리자 정보 삭제 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "mbId", value = "건설사 관리자 아이디", required = true, dataType = "string", paramType = "path")
  })
  @DeleteMapping(value="/{mbId}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes delete(
      @PathVariable(value = "mbId", required = true) String mbId) {
    conCompanyManagerService.delete(mbId);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  @ApiOperation(value = "건설사 관리자 정보 복수 삭제", notes = "건설사 관리자 정보 복수 삭제 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "mbId", value = "건설사 관리자 아이디", required = true, dataType = "string", paramType = "query")
  })
  @DeleteMapping(produces="application/json;charset=UTF-8")
  public DefaultHttpRes deleteMultiple(
      @RequestParam(value = "mbId", required = true) List<String> mbIdList) {
    conCompanyManagerService.deleteMultiple(mbIdList);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }
}
