package kr.co.hulan.aas.mvc.api.monitor4_2.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.monitor4_1.controller.response.OpeEquipmentStatusResponse;
import kr.co.hulan.aas.mvc.api.monitor4_1.vo.OpeEquipmentVo;
import kr.co.hulan.aas.mvc.api.monitor4_2.service.ImosEquipmentComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/monitor/4.2/workplace/{wpId}/equipment")
@Api(tags = "[4.2]IMOS 장비 및 차량 현황 API ")
public class ImosEquipmentCompController {

  @Autowired
  private ImosEquipmentComponentService imosEquipmentComponentService;

  @ApiOperation(value = "장비 및 차량 현황 정보", notes = "장비 및 차량 현황 정보 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(produces="application/json;charset=UTF-8")
  public DefaultHttpRes<OpeEquipmentStatusResponse> equipmentStatus(
      @PathVariable(value = "wpId", required = true) String wpId
  ) {
    return new DefaultHttpRes(BaseCode.SUCCESS, imosEquipmentComponentService.findEquipmentStatus(wpId));
  }

  @ApiOperation(value = "장비 및 차량 리스트 정보", notes = "장비 및 차량 리스트 정보 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "equipmentType", value = "장비 타입 아이디", required = true, dataType = "int", paramType = "path")
  })
  @GetMapping(value="/{equipmentType}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<OpeEquipmentVo>> equipmentTypeList(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "equipmentType", required = true) int equipmentType
  ) {
    return new DefaultHttpRes(BaseCode.SUCCESS, imosEquipmentComponentService.findEquipmentListByType(wpId,equipmentType ));
  }
}
