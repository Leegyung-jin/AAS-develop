package kr.co.hulan.aas.mvc.api.member.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.mvc.api.member.controller.request.OfficeManagerCreateRequest;
import kr.co.hulan.aas.mvc.api.member.controller.request.OfficeManagerExportRequest;
import kr.co.hulan.aas.mvc.api.member.controller.request.OfficeManagerPagingListRequest;
import kr.co.hulan.aas.mvc.api.member.controller.request.OfficeManagerUpdateRequest;
import kr.co.hulan.aas.mvc.api.member.dto.OfficeManagerDto;
import kr.co.hulan.aas.mvc.api.member.service.OfficeManagerService;
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
@RequestMapping("/api/member/officemanager")
@Api(tags = "발주사 관리자 관리")
public class OfficeManagerController {

  @Autowired
  private OfficeManagerService officeManagerService;

  @ApiOperation(value = "발주사 관리자 검색", notes = "발주사 관리자 검색 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping(value="/search", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<DefaultPageResult<OfficeManagerDto>> search(
      @Valid @RequestBody OfficeManagerPagingListRequest request) {
    return new DefaultHttpRes(BaseCode.SUCCESS, officeManagerService.findListPage(request));
  }

  @ApiOperation(value = "발주사 관리자 데이터 Export", notes = "발주사 관리자 데이터 Export 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping(value="/export", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<OfficeManagerDto>> export(
      @Valid @RequestBody OfficeManagerExportRequest request) {
    return new DefaultHttpRes<List<OfficeManagerDto>>(BaseCode.SUCCESS, officeManagerService.findListByCondition(request.getConditionMap()));
  }

  @ApiOperation(value = "발주사 관리자 조회", notes = "발주사 관리자 정보를 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "mbId", value = "발주사 관리자 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/{mbId}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<OfficeManagerDto> detail(
      @PathVariable(value = "mbId", required = true) String mbId) {
    return new DefaultHttpRes(BaseCode.SUCCESS,  officeManagerService.findInfo(mbId));
  }

  @ApiOperation(value = "발주사 관리자 정보 등록", notes = "발주사 관리자 정보 등록 제공한다.")
  @PostMapping(produces="application/json;charset=UTF-8")
  public DefaultHttpRes create(
      @Valid @RequestBody OfficeManagerCreateRequest request) {
    officeManagerService.create(request);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  @ApiOperation(value = "발주사 관리자 정보 수정", notes = "발주사 관리자 정보 수정 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "mbId", value = "발주사 관리자 아이디", required = true, dataType = "string", paramType = "path")
  })
  @PutMapping(value="/{mbId}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes update(
      @Valid @RequestBody OfficeManagerUpdateRequest request,
      @PathVariable(value = "mbId", required = true) String mbId) {
    officeManagerService.update(request, mbId);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }


  @ApiOperation(value = "발주사 관리자 정보 삭제", notes = "발주사 관리자 정보 삭제 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "mbId", value = "발주사 관리자 아이디", required = true, dataType = "string", paramType = "path")
  })
  @DeleteMapping(value="/{mbId}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes delete(
      @PathVariable(value = "mbId", required = true) String mbId) {
    officeManagerService.delete(mbId);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  @ApiOperation(value = "발주사 관리자 정보 복수 삭제", notes = "발주사 관리자 정보 복수 삭제 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "mbId", value = "발주사 관리자 아이디", required = true, dataType = "string", paramType = "query")
  })
  @DeleteMapping(produces="application/json;charset=UTF-8")
  public DefaultHttpRes deleteMultiple(
      @RequestParam(value = "mbId", required = true) List<String> mbIdList) {
    officeManagerService.deleteMultiple(mbIdList);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

}
