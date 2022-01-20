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
import kr.co.hulan.aas.mvc.api.orderoffice.controller.request.OrderingOfficeCreateRequest;
import kr.co.hulan.aas.mvc.api.orderoffice.controller.request.OrderingOfficeExportRequest;
import kr.co.hulan.aas.mvc.api.orderoffice.controller.request.OrderingOfficePagingListRequest;
import kr.co.hulan.aas.mvc.api.orderoffice.controller.request.OrderingOfficeUpdateRequest;
import kr.co.hulan.aas.mvc.api.orderoffice.controller.request.OfficeWorkplaceGroupCreateRequest;
import kr.co.hulan.aas.mvc.api.orderoffice.controller.request.OfficeWorkplaceGroupUpdateRequest;
import kr.co.hulan.aas.mvc.api.orderoffice.service.OrderingOfficeMgrService;
import kr.co.hulan.aas.mvc.api.orderoffice.vo.OrderingOfficeListVo;
import kr.co.hulan.aas.mvc.api.orderoffice.vo.OrderingOfficeVo;
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
@RequestMapping("/api/orderoffice/mgr")
@Api(tags = "발주사 관리")
public class OrderingOfficeMgrController {

  @Autowired
  private OrderingOfficeMgrService orderingOfficeMgrService;

  @ApiOperation(value = "발주사 검색", notes = "발주사 검색 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping(value="/search", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<DefaultPageResult<OrderingOfficeListVo>> search(
      @Valid @RequestBody OrderingOfficePagingListRequest request) {
    return new DefaultHttpRes(BaseCode.SUCCESS, orderingOfficeMgrService.findListPage(request));
  }

  @ApiOperation(value = "발주사 데이터 Export", notes = "발주사 데이터 Export 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping(value="/export", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<OrderingOfficeListVo>> export(
      @Valid @RequestBody OrderingOfficeExportRequest request) {
    return new DefaultHttpRes<List<OrderingOfficeListVo>>(BaseCode.SUCCESS, orderingOfficeMgrService.findListByCondition(request.getConditionMap()));
  }

  @ApiOperation(value = "발주사 조회", notes = "발주사 정보를 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "officeNo", value = "발주사 관리번호", required = true, dataType = "long", paramType = "path")
  })
  @GetMapping(value="/{officeNo}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<OrderingOfficeVo> detail(
      @PathVariable(value = "officeNo", required = true) long officeNo) {
    return new DefaultHttpRes(BaseCode.SUCCESS,  orderingOfficeMgrService.findInfo(officeNo));
  }

  @ApiOperation(value = "발주사 정보 등록", notes = "발주사 정보 등록 제공한다.")
  @PostMapping(produces="application/json;charset=UTF-8")
  public DefaultHttpRes create(
      @Valid @RequestBody OrderingOfficeCreateRequest request) {
    return new DefaultHttpRes(BaseCode.SUCCESS, orderingOfficeMgrService.create(request));
  }


  @ApiOperation(value = "발주사 정보 수정", notes = "발주사 정보 수정 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "officeNo", value = "발주사 관리번호", required = true, dataType = "long", paramType = "path")
  })
  @PutMapping(value="/{officeNo}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes update(
      @Valid @RequestBody OrderingOfficeUpdateRequest request,
      @PathVariable(value = "officeNo", required = true) long officeNo) {
    orderingOfficeMgrService.update(request, officeNo);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }


  @ApiOperation(value = "발주사 정보 삭제", notes = "발주사 정보 삭제 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "officeNo", value = "발주사 관리번호", required = true, dataType = "long", paramType = "path")
  })
  @DeleteMapping(value="/{officeNo}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes delete(
      @PathVariable(value = "officeNo", required = true) long officeNo) {
    orderingOfficeMgrService.delete(officeNo);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  @ApiOperation(value = "발주사 정보 복수 삭제", notes = "발주사 정보 복수 삭제 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "officeNo", value = "발주사 관리번호", required = true, dataType = "long", paramType = "path")
  })
  @DeleteMapping(produces="application/json;charset=UTF-8")
  public DefaultHttpRes deleteMultiple(
      @RequestParam(value = "officeNo", required = true) List<Long> officeNoList) {
    orderingOfficeMgrService.deleteMultiple(officeNoList);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }


}
