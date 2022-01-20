package kr.co.hulan.aas.mvc.api.orderoffice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.mvc.api.orderoffice.controller.request.OfficeWorkplaceGroupCreateRequest;
import kr.co.hulan.aas.mvc.api.orderoffice.controller.request.OfficeWorkplaceGroupExportRequest;
import kr.co.hulan.aas.mvc.api.orderoffice.controller.request.OfficeWorkplaceGroupPagingListRequest;
import kr.co.hulan.aas.mvc.api.orderoffice.controller.request.OfficeWorkplaceGroupUpdateRequest;
import kr.co.hulan.aas.mvc.api.orderoffice.service.OfficeWorkplaceGroupMgrService;
import kr.co.hulan.aas.mvc.api.orderoffice.service.OrderingOfficeMgrService;
import kr.co.hulan.aas.mvc.api.orderoffice.vo.OfficeWorkplaceGroupVo;
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

//@RestController
//@RequestMapping("/api/officegroup/mgr")
//@Api(tags = "발주사 현장그룹 관리")
public class OfficeWorkplaceGroupController {

  /*
  @Autowired
  private OfficeWorkplaceGroupMgrService officeWorkplaceGroupMgrService;

  @ApiOperation(value = "발주사 현장그룹 검색", notes = "발주사 현장그룹 검색 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping(value="/search", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<DefaultPageResult<OfficeWorkplaceGroupVo>> search(
      @Valid @RequestBody OfficeWorkplaceGroupPagingListRequest request) {
    return new DefaultHttpRes(BaseCode.SUCCESS, officeWorkplaceGroupMgrService.findListPage(request));
  }

  @ApiOperation(value = "발주사 현장그룹 데이터 Export", notes = "발주사 현장그룹 데이터 Export 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping(value="/export", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<OfficeWorkplaceGroupVo>> export(
      @Valid @RequestBody OfficeWorkplaceGroupExportRequest request) {
    return new DefaultHttpRes<List<OfficeWorkplaceGroupVo>>(BaseCode.SUCCESS, officeWorkplaceGroupMgrService.findListByCondition(request.getConditionMap()));
  }

  @ApiOperation(value = "발주사 현장그룹 조회", notes = "발주사 현장그룹 정보를 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpGrpNo", value = "발주사 현장그룹 관리번호", required = true, dataType = "long", paramType = "path")
  })
  @GetMapping(value="/{wpGrpNo}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<OfficeWorkplaceGroupVo> detail(
      @PathVariable(value = "wpGrpNo", required = true) long wpGrpNo) {
    return new DefaultHttpRes(BaseCode.SUCCESS,  officeWorkplaceGroupMgrService.findInfo(wpGrpNo));
  }

  @ApiOperation(value = "발주사 현장그룹 정보 등록", notes = "발주사 현장그룹 정보 등록 제공한다.")
  @PostMapping(produces="application/json;charset=UTF-8")
  public DefaultHttpRes<Long> create(
      @Valid @RequestBody OfficeWorkplaceGroupCreateRequest request) {
    return new DefaultHttpRes<Long>(BaseCode.SUCCESS, officeWorkplaceGroupMgrService.create(request));
  }


  @ApiOperation(value = "발주사 현장그룹 정보 수정", notes = "발주사 현장그룹 정보 수정 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpGrpNo", value = "발주사 현장그룹 관리번호", required = true, dataType = "long", paramType = "path")
  })
  @PutMapping(value="/{wpGrpNo}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes update(
      @Valid @RequestBody OfficeWorkplaceGroupUpdateRequest request,
      @PathVariable(value = "wpGrpNo", required = true) long wpGrpNo) {
    officeWorkplaceGroupMgrService.update(request, wpGrpNo);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }


  @ApiOperation(value = "발주사 현장그룹 정보 삭제", notes = "발주사 현장그룹 정보 삭제 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpGrpNo", value = "발주사 현장그룹 관리번호", required = true, dataType = "long", paramType = "path")
  })
  @DeleteMapping(value="/{wpGrpNo}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes delete(
      @PathVariable(value = "wpGrpNo", required = true) long wpGrpNo) {
    officeWorkplaceGroupMgrService.delete(wpGrpNo);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  @ApiOperation(value = "발주사 현장그룹 정보 복수 삭제", notes = "발주사 현장그룹 정보 복수 삭제 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpGrpNo", value = "발주사 현장그룹 관리번호", required = true, dataType = "long", paramType = "query")
  })
  @DeleteMapping(produces="application/json;charset=UTF-8")
  public DefaultHttpRes deleteMultiple(
      @RequestParam(value = "wpGrpNo", required = true) List<Long> wpGrpNoList) {
    officeWorkplaceGroupMgrService.deleteMultiple(wpGrpNoList);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }
   */

}
