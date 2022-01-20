package kr.co.hulan.aas.mvc.api.sensorMgr.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.request.ExportSensorInfoDataRequest;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.request.ExportSensorSituationByWorkPlaceDataRequest;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.request.ListSensorInfoRequest;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.request.ListSensorSituationByWorkPlaceRequest;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.response.ListSensorSituationByWorkPlaceResponse;
import kr.co.hulan.aas.mvc.api.sensorMgr.dto.SensorSituationByWorkplaceDto;
import kr.co.hulan.aas.mvc.api.sensorMgr.service.SensorSituationByWorkPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/sensorMgr/workplaceSituation")
@Api(tags = "현장별 센서 현황")
public class SensorSituationByWorkPlaceController {

    @Autowired
    private SensorSituationByWorkPlaceService workPlaceSensorInfoService;

    @ApiOperation(value = "현장별 센서 현황 리스트", notes = "현장별 센서 현황 리스트 검색 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("search")
    public DefaultHttpRes<ListSensorSituationByWorkPlaceResponse> search(
            @Valid @RequestBody ListSensorSituationByWorkPlaceRequest request) {
        return new DefaultHttpRes<ListSensorSituationByWorkPlaceResponse>(BaseCode.SUCCESS, workPlaceSensorInfoService.findListPage(request));
    }

    @ApiOperation(value = "현장별 센서 현황 데이터 Export", notes = "현장별 센서 현황 데이터 Export 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("export")
    public DefaultHttpRes<List<SensorSituationByWorkplaceDto>> export(
            @Valid @RequestBody ExportSensorSituationByWorkPlaceDataRequest request) {
        return new DefaultHttpRes<List<SensorSituationByWorkplaceDto>>(BaseCode.SUCCESS, workPlaceSensorInfoService.findListByCondition(request.getConditionMap()));
    }

}
