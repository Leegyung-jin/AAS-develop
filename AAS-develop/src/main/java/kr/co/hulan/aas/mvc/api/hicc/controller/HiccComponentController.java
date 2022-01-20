package kr.co.hulan.aas.mvc.api.hicc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.hicc.controller.response.HiccUiComponentResponse;
import kr.co.hulan.aas.mvc.api.hicc.service.HiccUiComponentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/hicc/v1/component/location")
@Api(tags = "Hulan Integrated Control Center 컴포넌트 API")
public class HiccComponentController {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private HiccUiComponentService hiccUiComponentService;

  @ApiOperation(value = "특정 UI 위치 할당 컴포넌트 정보 조회", notes = "특정 UI 위치 할당 컴포넌트 정보와 할당가능한 컴포넌트 리스트 조회 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "deploy_page", value = "배포 페이지", required = true, dataType = "integer", paramType = "path"),
      @ApiImplicitParam(name = "pos_x", value = "x 좌표", required = true, dataType = "integer", paramType = "path"),
      @ApiImplicitParam(name = "pos_y", value = "y 좌표", required = true, dataType = "integer", paramType = "path")
  })
  @GetMapping(value="/{deploy_page}/{pos_x}_{pos_y}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<HiccUiComponentResponse> workplaceUiComponent(
      @PathVariable(value = "deploy_page", required = true) Integer deployPage,
      @PathVariable(value = "pos_x", required = true) Integer posX,
      @PathVariable(value = "pos_y", required = true) Integer posY
  ){
    return new DefaultHttpRes<HiccUiComponentResponse>(BaseCode.SUCCESS, hiccUiComponentService.findHiccUiComponent(deployPage, posX, posY));
  }

  @ApiOperation(value = "특정 UI 위치 컴포넌트 할당 요청", notes = "특정 UI 위치 컴포넌트 할당 요청 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "deploy_page", value = "배포 페이지", required = true, dataType = "integer", paramType = "path"),
      @ApiImplicitParam(name = "pos_x", value = "x 좌표", required = true, dataType = "integer", paramType = "path"),
      @ApiImplicitParam(name = "pos_y", value = "y 좌표", required = true, dataType = "integer", paramType = "path"),
      @ApiImplicitParam(name = "hcmpt_id", value = "할당 컴포넌트 아이디", required = true, dataType = "string", paramType = "path")
  })
  @PostMapping(value="/{deploy_page}/{pos_x}_{pos_y}/{hcmpt_id}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<Object> assignWorkplaceUiComponent(
      @Valid @PathVariable(value = "deploy_page", required = true) @Min(value = 1, message = "페이지는 1이상이어야 합니다.") Integer deployPage,
      @Valid @PathVariable(value = "pos_x", required = true) @Min(value = 1, message = "X 값은 1 ~ 4 까지만 허용됩니다.") @Max(value = 4, message = "X 값은 1 ~ 4 까지만 허용됩니다.") Integer posX,
      @Valid @PathVariable(value = "pos_y", required = true) @Min(value = 1, message = "Y 값은 1 ~ 3 까지만 허용됩니다.") @Max(value = 3, message = "Y 값은 1 ~ 3 까지만 허용됩니다.") Integer posY,
      @PathVariable(value = "hcmpt_id", required = true) String hcmptId,
      @RequestBody(required = false) String customData
  ){
    hiccUiComponentService.assignUiComponent(deployPage, posX, posY, hcmptId, customData);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  @ApiOperation(value = "특정 UI 위치 할당 컴포넌트 해제 요청", notes = "특정 UI 위치 할당 컴포넌트 해제 요청 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "deploy_page", value = "배포 페이지", required = true, dataType = "integer", paramType = "path"),
      @ApiImplicitParam(name = "pos_x", value = "x 좌표", required = true, dataType = "integer", paramType = "path"),
      @ApiImplicitParam(name = "pos_y", value = "y 좌표", required = true, dataType = "integer", paramType = "path")
  })
  @DeleteMapping(value="/{deploy_page}/{pos_x}_{pos_y}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<Object> unassignWorkplaceUiComponent(
      @PathVariable(value = "deploy_page", required = true) Integer deployPage,
      @PathVariable(value = "pos_x", required = true) Integer posX,
      @PathVariable(value = "pos_y", required = true) Integer posY
  ){
    hiccUiComponentService.unassignUiComponent(deployPage, posX, posY);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

}
