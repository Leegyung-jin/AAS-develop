package kr.co.hulan.aas.mvc.api.accident.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.accident.controller.request.ExportFallingAccidentRequest;
import kr.co.hulan.aas.mvc.api.accident.controller.request.ListFallingAccidentRequest;
import kr.co.hulan.aas.mvc.api.accident.controller.request.UpdateFallingAccidentPopupRequest;
import kr.co.hulan.aas.mvc.api.accident.controller.response.ListFallingAccidentResponse;
import kr.co.hulan.aas.mvc.api.accident.service.FallingAccidentService;
import kr.co.hulan.aas.mvc.api.monitor.controller.request.UpdateGasDevicePopupRequest;
import kr.co.hulan.aas.mvc.api.tracker.controller.request.ExportTrackerDataRequest;
import kr.co.hulan.aas.mvc.api.tracker.controller.request.ListTrackerRequest;
import kr.co.hulan.aas.mvc.api.tracker.controller.response.ListTrackerResponse;
import kr.co.hulan.aas.mvc.api.tracker.service.TrackerService;
import kr.co.hulan.aas.mvc.model.dto.FallingAccidentDto;
import kr.co.hulan.aas.mvc.model.dto.TrackerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accident/falling")
@Api(tags = "낙하 이벤트 관리")
public class FallingAccidentController {

  @Autowired
  private FallingAccidentService fallingAccidentService;

  @ApiOperation(value = "낙하 이벤트 검색", notes = "낙하 이벤트 검색 제공한다.", consumes="application/json;charset=UTF-8")
  @PostMapping("search")
  public DefaultHttpRes<ListFallingAccidentResponse> search(
      @Valid @RequestBody ListFallingAccidentRequest request) {
    return new DefaultHttpRes<ListFallingAccidentResponse>(BaseCode.SUCCESS, fallingAccidentService.findListPage(request));
  }

  @ApiOperation(value = "낙하 이벤트 데이터 Export", notes = "낙하 이벤트 데이터 Export 제공한다.", consumes="application/json;charset=UTF-8")
  @PostMapping("export")
  public DefaultHttpRes<List<FallingAccidentDto>> export(
      @Valid @RequestBody ExportFallingAccidentRequest request) {
    return new DefaultHttpRes<List<FallingAccidentDto>>(BaseCode.SUCCESS, fallingAccidentService.findListByCondition(request.getConditionMap()));
  }

  @ApiOperation(value = "낙하 이벤트 조회", notes = "낙하 이벤트 정보를 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "mbId", value = "근로자 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping("detail/{mbId}")
  public DefaultHttpRes<FallingAccidentDto> detail(
      @PathVariable(value = "mbId", required = true) String mbId) {
    return new DefaultHttpRes(BaseCode.SUCCESS,  fallingAccidentService.findById(mbId));
  }

  @ApiOperation(value = "낙하 이벤트 경고 팝업 On/Off 업데이트", notes = "낙하 이벤트 경고 팝업 On/Off  업데이트 제공한다.", consumes="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "mbId", value = "근로자 아이디", required = true, dataType = "string", paramType = "path")
  })
  @PostMapping("update/{mbId}/popup")
  public DefaultHttpRes<Object> updateGas(
      @PathVariable(value = "mbId", required = true) String mbId,
      @Valid @RequestBody UpdateFallingAccidentPopupRequest request) {
    fallingAccidentService.updateFallingAccidentPopup(mbId, request);
    return new DefaultHttpRes<>(BaseCode.SUCCESS);
  }




}
