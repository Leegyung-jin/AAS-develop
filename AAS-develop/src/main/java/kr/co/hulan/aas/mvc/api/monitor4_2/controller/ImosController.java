package kr.co.hulan.aas.mvc.api.monitor4_2.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.request.ImosWorkplaceMainRequest;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.response.ImosUiComponentResponse;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.response.ImosWorkplaceMainResponse;
import kr.co.hulan.aas.mvc.api.monitor4_2.service.ImosMemberComponentService;
import kr.co.hulan.aas.mvc.api.monitor4_2.service.ImosService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/monitor/4.2")
@Api(tags = "[4.2] IMOS 모니터링 관리")
public class ImosController {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private ImosService imosService;

  @Autowired
  private ImosMemberComponentService imosMemberComponentService;

  @ApiOperation(value = "현장 메인 정보", notes = "현장 및 UI 컴포넌트, 날씨, 최근 공지사항 정보를 제공한다. (스마트 안전 모니터 4.1) ", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @PostMapping(value="/workplace/{wpId}/main", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<ImosWorkplaceMainResponse> workplaceMain(
      @PathVariable(value = "wpId", required = true) String wpId,
      @Valid @RequestBody ImosWorkplaceMainRequest request
  ){
    return new DefaultHttpRes<ImosWorkplaceMainResponse>(
        BaseCode.SUCCESS, imosService.findWorkplaceMainInfo(wpId, request));
  }

  @ApiOperation(value = "특정 UI 위치 할당 컴포넌트 정보 조회", notes = "특정 UI 위치 할당 컴포넌트 정보와 할당가능한 컴포넌트 리스트 조회 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "deploy_page", value = "배포 페이지", required = true, dataType = "integer", paramType = "path"),
      @ApiImplicitParam(name = "pos_x", value = "x 좌표", required = true, dataType = "integer", paramType = "path"),
      @ApiImplicitParam(name = "pos_y", value = "y 좌표", required = true, dataType = "integer", paramType = "path")
  })
  @GetMapping(value="/workplace/{wpId}/ui/{deploy_page}/{pos_x}_{pos_y}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<ImosUiComponentResponse> workplaceUiComponent(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "deploy_page", required = true) Integer deployPage,
      @PathVariable(value = "pos_x", required = true) Integer posX,
      @PathVariable(value = "pos_y", required = true) Integer posY
  ){
    return new DefaultHttpRes<ImosUiComponentResponse>(BaseCode.SUCCESS, imosMemberComponentService.findImosUiComponent(wpId, deployPage, posX, posY));
  }

  @ApiOperation(value = "특정 UI 위치 컴포넌트 할당 요청", notes = "특정 UI 위치 컴포넌트 할당 요청 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "deploy_page", value = "배포 페이지", required = true, dataType = "integer", paramType = "path"),
      @ApiImplicitParam(name = "pos_x", value = "x 좌표", required = true, dataType = "integer", paramType = "path"),
      @ApiImplicitParam(name = "pos_y", value = "y 좌표", required = true, dataType = "integer", paramType = "path"),
      @ApiImplicitParam(name = "hcmpt_id", value = "할당 컴포넌트 아이디", required = true, dataType = "string", paramType = "path")
  })
  @PostMapping(value="/workplace/{wpId}/ui/{deploy_page}/{pos_x}_{pos_y}/{hcmpt_id}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<Object> assignWorkplaceUiComponent(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "deploy_page", required = true) @Min(value = 1, message = "페이지는 2개만 허용됩니다.") @Max(value = 2, message = "페이지는 2개만 허용됩니다.") Integer deployPage,
      @Valid @PathVariable(value = "pos_x", required = true) @Min(value = 1, message = "X 값은 1 ~ 4 까지만 허용됩니다.") @Max(value = 4, message = "X 값은 1 ~ 4 까지만 허용됩니다.") Integer posX,
      @Valid @PathVariable(value = "pos_y", required = true) @Min(value = 1, message = "Y 값은 1 ~ 3 까지만 허용됩니다.") @Max(value = 3, message = "Y 값은 1 ~ 3 까지만 허용됩니다.") Integer posY,
      @PathVariable(value = "hcmpt_id", required = true) String hcmptId,
      @RequestBody(required = false) String customData
  ){
    imosMemberComponentService.assignUiComponent(wpId, deployPage, posX, posY, hcmptId, customData);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  @ApiOperation(value = "특정 UI 위치 할당 컴포넌트 해제 요청", notes = "특정 UI 위치 할당 컴포넌트 해제 요청 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "deploy_page", value = "배포 페이지", required = true, dataType = "integer", paramType = "path"),
      @ApiImplicitParam(name = "pos_x", value = "x 좌표", required = true, dataType = "integer", paramType = "path"),
      @ApiImplicitParam(name = "pos_y", value = "y 좌표", required = true, dataType = "integer", paramType = "path")
  })
  @DeleteMapping(value="/workplace/{wpId}/ui/{deploy_page}/{pos_x}_{pos_y}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<Object> unassignWorkplaceUiComponent(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "deploy_page", required = true) Integer deployPage,
      @PathVariable(value = "pos_x", required = true) Integer posX,
      @PathVariable(value = "pos_y", required = true) Integer posY
  ){
    imosMemberComponentService.unassignUiComponent(wpId, deployPage, posX, posY);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

}
