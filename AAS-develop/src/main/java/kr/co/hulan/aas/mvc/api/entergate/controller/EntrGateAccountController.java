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
import kr.co.hulan.aas.mvc.api.entergate.controller.request.EntrGateAccountCreateRequest;
import kr.co.hulan.aas.mvc.api.entergate.controller.request.EntrGateAccountExportRequest;
import kr.co.hulan.aas.mvc.api.entergate.controller.request.EntrGateAccountListRequest;
import kr.co.hulan.aas.mvc.api.entergate.controller.request.EntrGateAccountUpdateRequest;
import kr.co.hulan.aas.mvc.api.entergate.controller.request.EntrGateWorkplaceListRequest;
import kr.co.hulan.aas.mvc.api.entergate.service.EntrGateAccountService;
import kr.co.hulan.aas.mvc.api.entergate.vo.EntrGateAccountDetailVo;
import kr.co.hulan.aas.mvc.api.entergate.vo.EntrGateAccountListVo;
import kr.co.hulan.aas.mvc.api.entergate.vo.EntrGateWorkplaceVo;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceDto;
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
@RequestMapping("/api/entergate/account")
@Api(tags = "출입게이트 업체 계정 정보 관리 API")
public class EntrGateAccountController {

  @Autowired
  private EntrGateAccountService entrGateAccountService;

  @ApiOperation(value = "출입게이트 업체 계정 정보 검색", notes = "출입게이트 업체 계정 정보 검색 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping(value="/search",produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<DefaultPageResult<EntrGateAccountListVo>> search(
      @Valid @RequestBody EntrGateAccountListRequest request) {
    return new DefaultHttpRes<DefaultPageResult<EntrGateAccountListVo>>(BaseCode.SUCCESS, entrGateAccountService.findListPage(request));
  }

  @ApiOperation(value = "출입게이트 업체 계정 정보 데이터 Export", notes = "출입게이트 업체 계정 정보 데이터 Export 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping(value="/export", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<List<EntrGateAccountListVo>> export(
      @Valid @RequestBody EntrGateAccountExportRequest request) {
    return new DefaultHttpRes<List<EntrGateAccountListVo>>(BaseCode.SUCCESS, entrGateAccountService.findListByCondition(request.getConditionMap()));
  }

  @ApiOperation(value = "출입게이트 업체 계정 정보 조회", notes = "출입게이트 업체 계정 정보 정보를 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "account_id", value = "연동 계정", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/{account_id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<EntrGateAccountDetailVo> detail(
      @PathVariable(value = "account_id", required = true) String accountId
  ) {
    return new DefaultHttpRes(BaseCode.SUCCESS,  entrGateAccountService.findInfo(accountId));
  }

  @ApiOperation(value = "출입게이트 업체 계정 정보 등록", notes = "출입게이트 업체 계정 정보 등록 제공한다.")
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes create(
      @Valid @RequestBody EntrGateAccountCreateRequest request) {
    entrGateAccountService.create(request);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }


  @ApiOperation(value = "출입게이트 업체 계정 정보 수정", notes = "출입게이트 업체 계정 정보 수정 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "account_id", value = "연동 계정", required = true, dataType = "string", paramType = "path")
  })
  @PutMapping(value="/{account_id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes update(
      @PathVariable(value = "account_id", required = true) String accountId,
      @Valid @RequestBody EntrGateAccountUpdateRequest request
  ) {
    entrGateAccountService.update(accountId, request);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  @ApiOperation(value = "출입게이트 업체 계정 정보 삭제", notes = "출입게이트 업체 계정 정보 삭제 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "account_id", value = "연동 계정", required = true, dataType = "string", paramType = "path")
  })
  @DeleteMapping(value="/{account_id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes delete(
      @PathVariable(value = "account_id", required = true) String accountId
  ) {
    entrGateAccountService.delete(accountId);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  @ApiOperation(value = "출입게이트 업체 계정 정보 복수 삭제", notes = "출입게이트 업체 계정 정보 복수 삭제 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "account_id", value = "연동 계정", required = true, dataType = "string", paramType = "query")
  })
  @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes deleteMultiple(
      @RequestParam(value = "account_id", required = true) List<String> accountIdList) {
    entrGateAccountService.deleteMultiple(accountIdList);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }
}
