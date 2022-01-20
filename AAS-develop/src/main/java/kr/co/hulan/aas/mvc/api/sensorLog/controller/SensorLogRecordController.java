package kr.co.hulan.aas.mvc.api.sensorLog.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.sensorLog.controller.request.ExportDangerZoneSensorLogDataRequest;
import kr.co.hulan.aas.mvc.api.sensorLog.controller.request.ListSensorLogRequest;
import kr.co.hulan.aas.mvc.api.sensorLog.controller.request.NotifyAlarmRequest;
import kr.co.hulan.aas.mvc.api.sensorLog.controller.response.ListSensorLogResponse;
import kr.co.hulan.aas.mvc.api.sensorLog.service.SensorLogRecordService;
import kr.co.hulan.aas.mvc.model.dto.SensorLogDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/*
@RestController
@RequestMapping("/api/sensorLog/record")
@Api(tags = "안전세부기록 관리")
 */
public class SensorLogRecordController {


    @Autowired
    private SensorLogRecordService sensorLogRecordService;

    @ApiOperation(value = "안전세부기록 리스트", notes = "안전세부기록 검색 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("search")
    public DefaultHttpRes<ListSensorLogResponse> search(
            @Valid @RequestBody ListSensorLogRequest request) {
        return new DefaultHttpRes<ListSensorLogResponse>(BaseCode.SUCCESS, sensorLogRecordService.findListPage(request));
    }

    @ApiOperation(value = "안전세부기록 데이터 Export", notes = "안전세부기록 데이터 Export 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("export")
    public DefaultHttpRes<List<SensorLogDto>> export(
            @Valid @RequestBody ExportDangerZoneSensorLogDataRequest request) {
        return new DefaultHttpRes<List<SensorLogDto>>(BaseCode.SUCCESS, sensorLogRecordService.findListByCondition(request.getConditionMap()));
    }

    @ApiOperation(value = "안전세부기록 알림 발송", notes = "안전세부기록 알림 발송 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("notifyAlert")
    public DefaultHttpRes notifyAlert(
            @Valid @RequestBody NotifyAlarmRequest request) {
        sensorLogRecordService.sendAlarm(request);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }
}
