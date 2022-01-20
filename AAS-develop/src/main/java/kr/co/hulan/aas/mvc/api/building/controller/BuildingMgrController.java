package kr.co.hulan.aas.mvc.api.building.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.building.controller.request.*;
import kr.co.hulan.aas.mvc.api.building.controller.response.ListBuildingFloorResponse;
import kr.co.hulan.aas.mvc.api.building.controller.response.ListBuildingResponse;
import kr.co.hulan.aas.mvc.api.building.service.BuildingMgrService;
import kr.co.hulan.aas.mvc.model.dto.BuildingDto;
import kr.co.hulan.aas.mvc.model.dto.BuildingFloorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/buildingMgr")
@Api(tags = "빌딩 정보 관리")
public class BuildingMgrController {

  @Autowired
  private BuildingMgrService buildingMgrService;

  @ApiOperation(value = "빌딩 정보 검색", notes = "빌딩 정보 검색 제공한다.", consumes="application/json;charset=UTF-8")
  @PostMapping("search")
  public DefaultHttpRes<ListBuildingResponse> search(
      @Valid @RequestBody ListBuildingRequest request) {
    return new DefaultHttpRes<ListBuildingResponse>(BaseCode.SUCCESS, buildingMgrService.findListPage(request));
  }

  @ApiOperation(value = "빌딩 정보 Export", notes = "빌딩 정보 검색 Export 제공한다.", consumes="application/json;charset=UTF-8")
  @PostMapping("export")
  public DefaultHttpRes<List<BuildingDto>> export(
      @Valid @RequestBody ExportBuildingDataRequest request) {
    return new DefaultHttpRes<List<BuildingDto>>(BaseCode.SUCCESS, buildingMgrService.findListByCondition(request.getConditionMap()));
  }

  @ApiOperation(value = "빌딩 정보 조회", notes = "빌딩 정보를 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "buildingNo", value = "빌딩 번호", required = true, dataType = "long", paramType = "path")
  })
  @GetMapping("detail/{buildingNo}")
  public DefaultHttpRes<BuildingDto> detail(
      @PathVariable(value = "buildingNo", required = true) long buildingNo) {
    return new DefaultHttpRes(BaseCode.SUCCESS,  buildingMgrService.findById(buildingNo));
  }


  @ApiOperation(value = "빌딩 정보 등록", notes = "빌딩 정보 등록 제공한다.")
  @PostMapping("create")
  public DefaultHttpRes create(
      @Valid @RequestBody CreateBuildingRequest request) {
    return new DefaultHttpRes(BaseCode.SUCCESS, buildingMgrService.create(request));
  }

  @ApiOperation(value = "빌딩 정보 수정", notes = "빌딩 정보 수정 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "buildingNo", value = "빌딩 번호", required = true, dataType = "long", paramType = "path")
  })
  @PostMapping("update/{buildingNo}")
  public DefaultHttpRes update(
      @Valid @RequestBody UpdateBuildingRequest request,
      @PathVariable(value = "buildingNo", required = true) long buildingNo) {
    buildingMgrService.update(request, buildingNo);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  @ApiOperation(value = "빌딩 정보 삭제", notes = "빌딩 정보 삭제 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "buildingNo", value = "빌딩 번호", required = true, dataType = "long", paramType = "path")
  })
  @PostMapping("delete/{buildingNo}")
  public DefaultHttpRes delete(
      @PathVariable(value = "buildingNo", required = true) long buildingNo) {
    buildingMgrService.delete(buildingNo);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }


  @ApiOperation(value = "빌딩 정보 복수 삭제", notes = "빌딩 정보 복수 삭제 제공한다.")
  @PostMapping("deleteMultiple")
  public DefaultHttpRes deleteMultiple(
      @RequestParam(value = "buildingNos", required = true) List<Long> buildingNos) {
    buildingMgrService.deleteMultiple(buildingNos);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  // 빌딩 층 정보

  @ApiOperation(value = "빌딩 층 정보 검색", notes = "빌딩 층 정보 검색 제공한다.", consumes="application/json;charset=UTF-8")
  @PostMapping("floor/search")
  public DefaultHttpRes<ListBuildingFloorResponse> searchFloor(
          @Valid @RequestBody ListBuildingFloorRequest request) {
    return new DefaultHttpRes<ListBuildingFloorResponse>(BaseCode.SUCCESS, buildingMgrService.findFloorListPage(request));
  }

  @ApiOperation(value = "빌딩 층 정보 Export", notes = "빌딩 층 정보 검색 Export 제공한다.", consumes="application/json;charset=UTF-8")
  @PostMapping("floor/export")
  public DefaultHttpRes<List<BuildingFloorDto>> exportFloor(
          @Valid @RequestBody ExportBuildingFloorDataRequest request) {
    return new DefaultHttpRes<List<BuildingFloorDto>>(BaseCode.SUCCESS, buildingMgrService.findFloorListByCondition(request.getConditionMap()));
  }

  @ApiOperation(value = "빌딩 전체 층 리스트 조회", notes = "빌딩 전체 층 리스트 정보를 제공한다.")
  @ApiImplicitParams({
          @ApiImplicitParam(name = "buildingNo", value = "빌딩 번호", required = true, dataType = "long", paramType = "path")
  })
  @GetMapping("floor/{buildingNo}")
  public DefaultHttpRes<List<BuildingFloorDto>> detailFloor(
          @PathVariable(value = "buildingNo", required = true) long buildingNo
  ) {
    return new DefaultHttpRes(BaseCode.SUCCESS,  buildingMgrService.findFloorListByBuildingNo(buildingNo));
  }


  @ApiOperation(value = "층(구획) 정보 추가", notes = "층(구획) 추가를 제공한다. 현재는 구획만 추가됨")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "buildingNo", value = "빌딩 번호", required = true, dataType = "long", paramType = "path")
  })
  @PostMapping("floor/{buildingNo}")
  public DefaultHttpRes createFloor(
      @Valid @RequestBody CreateBuildingFloorRequest request,
      @PathVariable(value = "buildingNo", required = true) long buildingNo
  ) {
    buildingMgrService.createFloor(buildingNo, request);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  @ApiOperation(value = "층(구획) 정보 조회", notes = "층(구획) 정보를 제공한다.")
  @ApiImplicitParams({
          @ApiImplicitParam(name = "buildingNo", value = "빌딩 번호", required = true, dataType = "long", paramType = "path"),
          @ApiImplicitParam(name = "floor", value = "층(구획) 번호", required = true, dataType = "int", paramType = "path")
  })
  @GetMapping("floor/{buildingNo}/{floor}")
  public DefaultHttpRes<BuildingFloorDto> detailFloor(
          @PathVariable(value = "buildingNo", required = true) long buildingNo,
          @PathVariable(value = "floor", required = true) int floor
  ) {
    return new DefaultHttpRes(BaseCode.SUCCESS,  buildingMgrService.findFloor(buildingNo, floor));
  }

  @ApiOperation(value = "층(구획) 정보 수정", notes = "층(구획) 수정을 제공한다.")
  @ApiImplicitParams({
          @ApiImplicitParam(name = "buildingNo", value = "빌딩 번호", required = true, dataType = "long", paramType = "path"),
          @ApiImplicitParam(name = "floor", value = "층(구획) 번호", required = true, dataType = "int", paramType = "path")
  })
  @PutMapping("floor/{buildingNo}/{floor}")
  public DefaultHttpRes updateFloor(
          @Valid @RequestBody UpdateBuildingFloorRequest request,
          @PathVariable(value = "buildingNo", required = true) long buildingNo,
          @PathVariable(value = "floor", required = true) int floor
  ) {
    buildingMgrService.updateFloor(request, buildingNo, floor);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  @ApiOperation(value = "층(구획) 정보 삭제", notes = "층(구획) 정보 삭제를 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "buildingNo", value = "빌딩 번호", required = true, dataType = "long", paramType = "path"),
      @ApiImplicitParam(name = "floor", value = "층(구획) 번호", required = true, dataType = "int", paramType = "path")
  })
  @DeleteMapping("floor/{buildingNo}/{floor}")
  public DefaultHttpRes deleteDistrict(
      @PathVariable(value = "buildingNo", required = true) long buildingNo,
      @PathVariable(value = "floor", required = true) int floor
  ) {
    buildingMgrService.deleteFloor(buildingNo, floor);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }
}
