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
import kr.co.hulan.aas.mvc.api.member.controller.request.OfficeGroupManagerCreateRequest;
import kr.co.hulan.aas.mvc.api.member.controller.request.OfficeGroupManagerExportRequest;
import kr.co.hulan.aas.mvc.api.member.controller.request.OfficeGroupManagerPagingListRequest;
import kr.co.hulan.aas.mvc.api.member.controller.request.OfficeGroupManagerUpdateRequest;
import kr.co.hulan.aas.mvc.api.member.controller.request.OfficeManagerCreateRequest;
import kr.co.hulan.aas.mvc.api.member.controller.request.OfficeManagerExportRequest;
import kr.co.hulan.aas.mvc.api.member.controller.request.OfficeManagerPagingListRequest;
import kr.co.hulan.aas.mvc.api.member.controller.request.OfficeManagerUpdateRequest;
import kr.co.hulan.aas.mvc.api.member.dto.OfficeGroupManagerDto;
import kr.co.hulan.aas.mvc.api.member.dto.OfficeManagerDto;
import kr.co.hulan.aas.mvc.api.member.service.OfficeGroupManagerService;
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
@RequestMapping("/api/member/groupmanager")
@Api(tags = "발주사 현장그룹 매니저 관리")
public class OfficeGroupManagerController {

  @Autowired
  private OfficeGroupManagerService officeGroupManagerService;

  @ApiOperation(value = "발주사 현장그룹 매니저 검색", notes = "발주사 현장그룹 매니저 검색 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping(value="/search", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<DefaultPageResult<OfficeGroupManagerDto>> search(
      @Valid @RequestBody OfficeGroupManagerPagingListRequest request) {
    return new DefaultHttpRes(BaseCode.SUCCESS, officeGroupManagerService.findListPage(request));
  }

  @ApiOperation(value = "발주사 현장그룹 매니저 데이터 Export", notes = "발주사 현장그룹 매니저 데이터 Export 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping(value="/export", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<OfficeGroupManagerDto>> export(
      @Valid @RequestBody OfficeGroupManagerExportRequest request) {
    return new DefaultHttpRes<List<OfficeGroupManagerDto>>(BaseCode.SUCCESS, officeGroupManagerService.findListByCondition(request.getConditionMap()));
  }

  @ApiOperation(value = "발주사 현장그룹 매니저 조회", notes = "발주사 현장그룹 매니저 정보를 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "mbId", value = "발주사 현장그룹 매니저 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/{mbId}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<OfficeGroupManagerDto> detail(
      @PathVariable(value = "mbId", required = true) String mbId) {
    return new DefaultHttpRes(BaseCode.SUCCESS,  officeGroupManagerService.findInfo(mbId));
  }

  @ApiOperation(value = "발주사 현장그룹 매니저 정보 등록", notes = "발주사 현장그룹 매니저 정보 등록 제공한다.")
  @PostMapping(produces="application/json;charset=UTF-8")
  public DefaultHttpRes create(
      @Valid @RequestBody OfficeGroupManagerCreateRequest request) {
    officeGroupManagerService.create(request);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  @ApiOperation(value = "발주사 현장그룹 매니저 정보 수정", notes = "발주사 현장그룹 매니저 정보 수정 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "mbId", value = "발주사 관리자 아이디", required = true, dataType = "string", paramType = "path")
  })
  @PutMapping(value="/{mbId}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes update(
      @Valid @RequestBody OfficeGroupManagerUpdateRequest request,
      @PathVariable(value = "mbId", required = true) String mbId) {
    officeGroupManagerService.update(request, mbId);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  @ApiOperation(value = "발주사 현장그룹 매니저 정보 삭제", notes = "발주사 현장그룹 매니저 정보 삭제 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "mbId", value = "발주사 현장그룹 매니저 아이디", required = true, dataType = "string", paramType = "path")
  })
  @DeleteMapping(value="/{mbId}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes delete(
      @PathVariable(value = "mbId", required = true) String mbId) {
    officeGroupManagerService.delete(mbId);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  @ApiOperation(value = "발주사 현장그룹 매니저 정보 복수 삭제", notes = "발주사 현장그룹 매니저 정보 복수 삭제 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "mbId", value = "발주사 현장그룹 매니저 아이디", required = true, dataType = "string", paramType = "path")
  })
  @DeleteMapping(produces="application/json;charset=UTF-8")
  public DefaultHttpRes deleteMultiple(
      @RequestParam(value = "mbId", required = true) List<String> mbIdList) {
    officeGroupManagerService.deleteMultiple(mbIdList);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }


  @ApiOperation(value = "발주사 현장그룹 매니저 그룹 할당", notes = "발주사 현장그룹 매니저 그룹 할당 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "mbId", value = "발주사 현장그룹 매니저 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "wrGrpNo", value = "발주사 현장그룹 관리번호", required = true, dataType = "long", paramType = "path")
  })
  @PostMapping(value="/{mbId}/assign/{wrGrpNo}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes assignGroup(
      @PathVariable(value = "mbId", required = true) String mbId,
      @PathVariable(value = "wrGrpNo", required = true) long wrGrpNo
  ) {
    officeGroupManagerService.assignGroup(mbId, wrGrpNo);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  @ApiOperation(value = "발주사 현장그룹 매니저 그룹 할당 회수", notes = "발주사 현장그룹 매니저 그룹 할당 회수 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "mbId", value = "발주사 현장그룹 매니저 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "wrGrpNo", value = "발주사 현장그룹 관리번호", required = true, dataType = "long", paramType = "path")
  })
  @DeleteMapping(value="/{mbId}/assign/{wrGrpNo}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes dismissGroup(
      @PathVariable(value = "mbId", required = true) String mbId,
      @PathVariable(value = "wrGrpNo", required = true) long wrGrpNo
  ) {
    officeGroupManagerService.dismissGroup(mbId, wrGrpNo);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

}
