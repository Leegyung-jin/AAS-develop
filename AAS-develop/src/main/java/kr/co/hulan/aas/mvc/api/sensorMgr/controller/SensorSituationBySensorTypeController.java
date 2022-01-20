package kr.co.hulan.aas.mvc.api.sensorMgr.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.request.ExportSensorInfoDataRequest;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.request.ExportSensorSituationBySensorTypeDataRequest;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.request.ListSensorInfoRequest;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.request.ListSensorSituationBySensorTypeRequest;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.response.ListSensorSituationBySensorTypeResponse;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.response.ListSensorSituationByWorkPlaceResponse;
import kr.co.hulan.aas.mvc.api.sensorMgr.dto.SensorSituationBySensorTypeDto;
import kr.co.hulan.aas.mvc.api.sensorMgr.dto.SensorSituationByWorkplaceDto;
import kr.co.hulan.aas.mvc.api.sensorMgr.service.SensorSituationBySensorTypeService;
import kr.co.hulan.aas.mvc.api.sensorMgr.service.SensorSituationByWorkPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/sensorMgr/sensorTypeSituation")
@Api(tags = "센서 타입별 센서 현황")
public class SensorSituationBySensorTypeController {

    @Autowired
    private SensorSituationBySensorTypeService sensorSituationBySensorTypeService;

    @ApiOperation(value = "센서 타입별 센서 현황 리스트", notes = "센서 타입별 센서 현황 리스트 검색 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("search")
    public DefaultHttpRes<ListSensorSituationBySensorTypeResponse> search(
            @Valid @RequestBody ListSensorSituationBySensorTypeRequest request) {
        return new DefaultHttpRes<ListSensorSituationBySensorTypeResponse>(BaseCode.SUCCESS, sensorSituationBySensorTypeService.findListPage(request));
    }

    @ApiOperation(value = "센서 타입별 센서 현황 데이터 Export", notes = "센서 타입별 센서 현황 데이터 Export 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("export")
    public DefaultHttpRes<List<SensorSituationBySensorTypeDto>> export(
            @Valid @RequestBody ExportSensorSituationBySensorTypeDataRequest request) {
        return new DefaultHttpRes<List<SensorSituationBySensorTypeDto>>(BaseCode.SUCCESS, sensorSituationBySensorTypeService.findListByCondition(request.getConditionMap()));
    }


    @ApiOperation(value = "센서 타입 정보 삭제", notes = "센서 타입 삭제 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "siType", value = "안전센서 아이디", required = true, dataType = "int", paramType = "path")
    })
    @PostMapping("delete/{siType}")
    public DefaultHttpRes delete(
            @PathVariable(value = "siType", required = true) int siType) {
        sensorSituationBySensorTypeService.delete(siType);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "센서 타입 정보 복수 삭제", notes = "센서 타입 복수 삭제 제공한다.")
    @PostMapping("deleteMultiple")
    public DefaultHttpRes deleteMultiple(
            @RequestParam(value = "siTypes", required = true) List<Integer> siTypes) {
        sensorSituationBySensorTypeService.deleteMultiple(siTypes);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }
}
