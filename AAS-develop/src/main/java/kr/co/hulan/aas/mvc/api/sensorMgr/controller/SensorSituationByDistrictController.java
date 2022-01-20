package kr.co.hulan.aas.mvc.api.sensorMgr.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.request.CreateSensorDistrictRequest;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.request.ExportSensorSituationByDistrictDataRequest;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.request.ListSensorSituationByDistrictRequest;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.request.UpdateSensorDistrictRequest;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.response.ListSensorSituationByDistrictResponse;
import kr.co.hulan.aas.mvc.api.sensorMgr.service.SensorSituationByDistrictService;
import kr.co.hulan.aas.mvc.model.dto.SensorDistrictDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/sensorMgr/districtSituation")
@Api(tags = "구역별 센서 현황")
public class SensorSituationByDistrictController {

    @Autowired
    private SensorSituationByDistrictService districtSensorInfoService;

    @ApiOperation(value = "구역별 센서 현황 리스트", notes = "구역별 센서 현황 리스트 검색 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("search")
    public DefaultHttpRes<ListSensorSituationByDistrictResponse> search(
            @Valid @RequestBody ListSensorSituationByDistrictRequest request) {
        return new DefaultHttpRes<ListSensorSituationByDistrictResponse>(BaseCode.SUCCESS, districtSensorInfoService.findListPage(request));
    }

    @ApiOperation(value = "구역별 센서 현황 데이터 Export", notes = "구역별 센서 현황 데이터 Export 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("export")
    public DefaultHttpRes<List<SensorDistrictDto>> export(
            @Valid @RequestBody ExportSensorSituationByDistrictDataRequest request) {
        return new DefaultHttpRes<List<SensorDistrictDto>>(BaseCode.SUCCESS, districtSensorInfoService.findListByCondition(request.getConditionMap()));
    }

    @ApiOperation(value = "센서 구역 정보 조회", notes = "센서 구역 정보를 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sdIdx", value = "센서 구역 아이디", required = true, dataType = "int", paramType = "path")
    })
    @GetMapping("detail/{sdIdx}")
    public DefaultHttpRes<SensorDistrictDto> detail(
            @PathVariable(value = "sdIdx", required = true) int sdIdx) {
        return new DefaultHttpRes(BaseCode.SUCCESS,  districtSensorInfoService.findById(sdIdx));
    }

    @ApiOperation(value = "센서 구역 정보 등록", notes = "센서 구역 정보 등록 제공한다.")
    @PostMapping("create")
    public DefaultHttpRes create(
            @Valid @RequestBody CreateSensorDistrictRequest request) {
        districtSensorInfoService.create(request);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "센서 구역 정보 수정", notes = "센서 구역 정보 수정 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sdIdx", value = "센서 구역 정보 아이디", required = true, dataType = "int", paramType = "path")
    })
    @PostMapping("update/{sdIdx}")
    public DefaultHttpRes update(
            @PathVariable(value = "sdIdx", required = true) int sdIdx,
            @Valid @RequestBody UpdateSensorDistrictRequest request) {
        districtSensorInfoService.update(request, sdIdx);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }


    @ApiOperation(value = "센서 구역 정보 삭제", notes = "센서 구역 정보 삭제 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sdIdx", value = "센서 구역 정보 아이디", required = true, dataType = "int", paramType = "path")
    })
    @PostMapping("delete/{sdIdx}")
    public DefaultHttpRes delete(
            @PathVariable(value = "sdIdx", required = true) int sdIdx) {
        districtSensorInfoService.delete(sdIdx);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "센서 구역 정보 복수 삭제", notes = "센서 구역 정보 복수 삭제 제공한다.")
    @PostMapping("deleteMultiple")
    public DefaultHttpRes deleteMultiple(
            @RequestParam(value = "sdIdxs", required = true) List<Integer> sdIdxs) {
        districtSensorInfoService.deleteMultiple(sdIdxs);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }


}
