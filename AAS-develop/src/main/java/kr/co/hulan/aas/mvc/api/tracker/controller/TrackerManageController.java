package kr.co.hulan.aas.mvc.api.tracker.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.tracker.controller.request.AssignTrackerRequest;
import kr.co.hulan.aas.mvc.api.tracker.controller.request.CollectTrackerRequest;
import kr.co.hulan.aas.mvc.api.tracker.controller.request.CreateTrackerRequest;
import kr.co.hulan.aas.mvc.api.tracker.controller.request.ExportTrackerDataRequest;
import kr.co.hulan.aas.mvc.api.tracker.controller.request.ListTrackerRequest;
import kr.co.hulan.aas.mvc.api.tracker.controller.request.UpdateTrackerRequest;
import kr.co.hulan.aas.mvc.api.tracker.controller.response.ListTrackerResponse;
import kr.co.hulan.aas.mvc.api.tracker.service.TrackerService;
import kr.co.hulan.aas.mvc.model.dto.TrackerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tracker/manage")
@Api(tags = "트랙커 관리")
public class TrackerManageController {

  @Autowired
  private TrackerService trackerService;

  @ApiOperation(value = "트랙커 검색", notes = "트랙커 검색 제공한다.", consumes="application/json;charset=UTF-8")
  @PostMapping("search")
  public DefaultHttpRes<ListTrackerResponse> search(
      @Valid @RequestBody ListTrackerRequest request) {
    return new DefaultHttpRes<ListTrackerResponse>(BaseCode.SUCCESS, trackerService.findListPage(request));
  }

  @ApiOperation(value = "트랙커 데이터 Export", notes = "트랙커 데이터 Export 제공한다.", consumes="application/json;charset=UTF-8")
  @PostMapping("export")
  public DefaultHttpRes<List<TrackerDto>> export(
      @Valid @RequestBody ExportTrackerDataRequest request) {
    return new DefaultHttpRes<List<TrackerDto>>(BaseCode.SUCCESS, trackerService.findListByCondition(request.getConditionMap()));
  }

  @ApiOperation(value = "트랙커 조회", notes = "트랙커 정보를 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "trackerId", value = "트랙커 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping("detail/{trackerId}")
  public DefaultHttpRes<TrackerDto> detail(
      @PathVariable(value = "trackerId", required = true) String trackerId) {
    return new DefaultHttpRes(BaseCode.SUCCESS,  trackerService.findById(trackerId));
  }

  @ApiOperation(value = "트랙커 정보 등록", notes = "트랙커 정보 등록 제공한다.")
  @PostMapping("create")
  public DefaultHttpRes create(
      @Valid @RequestBody CreateTrackerRequest request) {
    trackerService.create(request);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }


  @ApiOperation(value = "트랙커 정보 수정", notes = "트랙커 정보 수정 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "trackerId", value = "트랙커 아이디", required = true, dataType = "string", paramType = "path")
  })
  @PostMapping("update/{trackerId}")
  public DefaultHttpRes update(
      @Valid @RequestBody UpdateTrackerRequest request,
      @PathVariable(value = "trackerId", required = true) String trackerId) {
    trackerService.update(request, trackerId);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }


  @ApiOperation(value = "트랙커 정보 삭제", notes = "트랙커 정보 삭제 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "trackerId", value = "트랙커 아이디", required = true, dataType = "string", paramType = "path")
  })
  @PostMapping("delete/{trackerId}")
  public DefaultHttpRes delete(
      @PathVariable(value = "trackerId", required = true) String trackerId) {
    trackerService.delete(trackerId);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  @ApiOperation(value = "트랙커 정보 복수 삭제", notes = "트랙커 정보 복수 삭제 제공한다.")
  @PostMapping("deleteMultiple")
  public DefaultHttpRes deleteMultiple(
      @RequestParam(value = "trackerIds", required = true) List<String> trackerIds) {
    trackerService.deleteMultiple(trackerIds);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  @ApiOperation(value = "트랙커 할당", notes = "트랙커를 근로자에게 할당한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "trackerId", value = "트랙커 아이디", required = true, dataType = "string", paramType = "path")
  })
  @PostMapping("assign/{trackerId}")
  public DefaultHttpRes assignTrackerToWorker(
      @Valid @RequestBody AssignTrackerRequest request,
      @PathVariable(value = "trackerId", required = true) String trackerId) {
    trackerService.assignTrackerToWorker(request, trackerId);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  @ApiOperation(value = "트랙커 회수", notes = "트랙커를 회수처리합니다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "trackerId", value = "트랙커 아이디", required = true, dataType = "string", paramType = "path")
  })
  @PostMapping("collect/{trackerId}")
  public DefaultHttpRes collectTrackerFromWorker(
      @Valid @RequestBody CollectTrackerRequest request,
      @PathVariable(value = "trackerId", required = true) String trackerId) {
    trackerService.collectTrackerFromWorker(request, trackerId);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }
}
