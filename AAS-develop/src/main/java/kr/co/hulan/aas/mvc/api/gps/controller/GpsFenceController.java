package kr.co.hulan.aas.mvc.api.gps.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.gps.controller.request.CreateGeofenceGroupRequest;
import kr.co.hulan.aas.mvc.api.gps.controller.request.ExportGeofenceGroupRequest;
import kr.co.hulan.aas.mvc.api.gps.controller.request.ListGeofenceGroupRequest;
import kr.co.hulan.aas.mvc.api.gps.controller.request.UpdateGeofenceGroupRequest;
import kr.co.hulan.aas.mvc.api.gps.controller.response.ListGeofenceGroupResponse;
import kr.co.hulan.aas.mvc.api.gps.service.GpsFenceService;
import kr.co.hulan.aas.mvc.model.dto.WorkGeofenceGroupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gpsfence")
@Api(tags = "GPS fence 관리")
public class GpsFenceController {

  @Autowired
  private GpsFenceService gpsFenceService;

  @ApiOperation(value = "geofence 검색", notes = "geofence 검색 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<ListGeofenceGroupResponse> search(
      @Valid @RequestBody ListGeofenceGroupRequest request) {
    return new DefaultHttpRes<ListGeofenceGroupResponse>(BaseCode.SUCCESS, gpsFenceService.findGroupListPage(request));
  }

  @ApiOperation(value = "geofence 데이터 Export", notes = "geofence 데이터 Export 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping(value="/export", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<List<WorkGeofenceGroupDto>> export(
      @Valid @RequestBody ExportGeofenceGroupRequest request) {
    return new DefaultHttpRes<List<WorkGeofenceGroupDto>>(BaseCode.SUCCESS, gpsFenceService.findGroupListByCondition(request.getConditionMap()));
  }


  @ApiOperation(value = "geofence 정보 등록", notes = "geofence 정보 등록 제공한다.")
  @PostMapping(value="/create", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<Integer> create(
      @Valid @RequestBody CreateGeofenceGroupRequest request) {
    return new DefaultHttpRes(BaseCode.SUCCESS, gpsFenceService.createFenceGroup(request));
  }


  @ApiOperation(value = "geofence 정보 조회", notes = "geofence 정보 조회 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "wpSeq", value = "fence 번호", required = true, dataType = "integer", paramType = "path")
  })
  @GetMapping(value="/{wpId}_{wpSeq}", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<WorkGeofenceGroupDto> findGeofenceGroupDetail(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "wpSeq", required = true) int wpSeq
  ) {
    return new DefaultHttpRes<WorkGeofenceGroupDto>(BaseCode.SUCCESS, gpsFenceService.findGroupInfo(wpId, wpSeq));
  }


  @ApiOperation(value = "geofence 정보 수정", notes = "geofence 정보 수정 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "wpSeq", value = "fence 번호", required = true, dataType = "integer", paramType = "path")
  })
  @PostMapping(value="/{wpId}_{wpSeq}", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes updateGeofenceGroup(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "wpSeq", required = true) int wpSeq,
      @Valid @RequestBody UpdateGeofenceGroupRequest request
  ) {
    gpsFenceService.updateFenceGroup(wpId, wpSeq, request);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  @ApiOperation(value = "geofence 정보 삭제", notes = "geofence 정보 삭제 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "wpSeq", value = "fence 번호", required = true, dataType = "integer", paramType = "path")
  })
  @DeleteMapping(value="/{wpId}_{wpSeq}", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes delete(
      @PathVariable(value = "wpId", required = true) String wpId,
      @PathVariable(value = "wpSeq", required = true) int wpSeq
  ) {
    gpsFenceService.delete(wpId, wpSeq);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  @ApiOperation(value = "geofence 정보 복수 삭제", notes = "geofence 정보 복수 삭제 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "idxs", value = "현장 아이디+'_'+fence 번호 리스트", required = true, dataType = "string", paramType = "query")
  })
  @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes deleteMultiple(
      @RequestParam(value = "idxs", required = true) List<String> idxs) {
    gpsFenceService.deleteMultiple(idxs);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }


}
