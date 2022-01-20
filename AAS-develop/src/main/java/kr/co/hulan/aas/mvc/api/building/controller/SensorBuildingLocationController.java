package kr.co.hulan.aas.mvc.api.building.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.building.controller.request.*;
import kr.co.hulan.aas.mvc.api.building.controller.response.ListSensorBuildingLocationByFloorResponse;
import kr.co.hulan.aas.mvc.api.building.controller.response.ListSensorBuildingLocationResponse;
import kr.co.hulan.aas.mvc.api.building.controller.response.ListSensorBuildingSituationResponse;
import kr.co.hulan.aas.mvc.api.building.dto.SensorBuildingSituationDto;
import kr.co.hulan.aas.mvc.api.building.service.SensorBuildingLocationService;
import kr.co.hulan.aas.mvc.model.dto.SensorBuildingLocationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/buildingMgr/sensor")
@Api(tags = "안전센서 빌딩 위치 관리")
public class SensorBuildingLocationController {

  @Autowired
  private SensorBuildingLocationService sensorBuildingLocationService;

  @ApiOperation(value = "안전센서 빌딩 층별 위치 정보 검색", notes = "안전센서 빌딩 층별 위치 정보 검색 제공한다.", consumes="application/json;charset=UTF-8")
  @PostMapping("searchByFloor")
  public DefaultHttpRes<ListSensorBuildingLocationByFloorResponse> search(
      @Valid @RequestBody ListSensorBuildingLocationByFloorRequest request) {
    return new DefaultHttpRes<ListSensorBuildingLocationByFloorResponse>(BaseCode.SUCCESS, sensorBuildingLocationService.findListByFloor(request));
  }

  @ApiOperation(value = "안전센서 위치 정보 검색", notes = "안전센서 위치 정보 검색 제공한다.", consumes="application/json;charset=UTF-8")
  @PostMapping("search")
  public DefaultHttpRes<ListSensorBuildingLocationResponse> search(
      @Valid @RequestBody ListSensorBuildingLocationRequest request) {
    return new DefaultHttpRes<ListSensorBuildingLocationResponse>(BaseCode.SUCCESS, sensorBuildingLocationService.findListPage(request));
  }

  @ApiOperation(value = "안전센서 위치 정보 데이터 Export", notes = "안전센서 위치 정보 데이터 Export 제공한다.", consumes="application/json;charset=UTF-8")
  @PostMapping("export")
  public DefaultHttpRes<List<SensorBuildingLocationDto>> export(
      @Valid @RequestBody ExportSensorBuildingLocationDataRequest request) {
    return new DefaultHttpRes<List<SensorBuildingLocationDto>>(BaseCode.SUCCESS, sensorBuildingLocationService.findListByCondition(request.getConditionMap()));
  }

  @ApiOperation(value = "안전센서 빌딩 층별 현황 정보 검색", notes = "안전센서 빌딩 층별 현황 검색 제공한다.", consumes="application/json;charset=UTF-8")
  @PostMapping("searchSituation")
  public DefaultHttpRes<ListSensorBuildingSituationResponse> searchSituation(
      @Valid @RequestBody ListSensorBuildingSituationRequest request) {
    return new DefaultHttpRes<ListSensorBuildingSituationResponse>(BaseCode.SUCCESS, sensorBuildingLocationService.findSituationListPage(request));
  }

  @ApiOperation(value = "안전센서 빌딩 층별 현황 데이터 Export", notes = "안전센서 빌딩 층별 현황 데이터 Export 제공한다.", consumes="application/json;charset=UTF-8")
  @PostMapping("exportSituation")
  public DefaultHttpRes<List<SensorBuildingSituationDto>> exportSituation(
      @Valid @RequestBody ExportSensorBuildingLocationDataRequest request) {
    return new DefaultHttpRes<List<SensorBuildingSituationDto>>(BaseCode.SUCCESS, sensorBuildingLocationService.findSituationListByCondition(request.getConditionMap()));
  }

  @ApiOperation(value = "안전센서 위치 정보 조회", notes = "안전센서 정보를 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "siIdx", value = "안전센서 아이디", required = true, dataType = "int", paramType = "path")
  })
  @GetMapping("detail/{siIdx}")
  public DefaultHttpRes<SensorBuildingLocationDto> detail(
      @PathVariable(value = "siIdx", required = true) int siIdx) {
    return new DefaultHttpRes(BaseCode.SUCCESS,  sensorBuildingLocationService.findById(siIdx));
  }

  @ApiOperation(value = "안전센서 위치 정보 등록/수정", notes = "안전센서 위치 정보 등록/수정 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "siIdx", value = "안전센서 아이디", required = true, dataType = "int", paramType = "path")
  })
  @PostMapping("update/{siIdx}")
  public DefaultHttpRes updateWorker(
      @Valid @RequestBody UpdateSensorBuildingLocationRequest request,
      @PathVariable(value = "siIdx", required = true) int siIdx) {
    sensorBuildingLocationService.update(request, siIdx);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }


  @ApiOperation(value = "안전센서 위치 정보 삭제", notes = "안전센서 위치 정보 삭제 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "siIdx", value = "안전센서 아이디", required = true, dataType = "int", paramType = "path")
  })
  @PostMapping("delete/{siIdx}")
  public DefaultHttpRes delete(
      @PathVariable(value = "siIdx", required = true) int siIdx) {
    sensorBuildingLocationService.delete(siIdx);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

}
