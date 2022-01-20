package kr.co.hulan.aas.mvc.api.sensorLog.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.sensorLog.controller.request.ExportDangerZoneSensorLogDataRequest;
import kr.co.hulan.aas.mvc.api.sensorLog.controller.request.ListDangerZoneSensorLogRequest;
import kr.co.hulan.aas.mvc.api.sensorLog.controller.request.NotifyAlarmRequest;
import kr.co.hulan.aas.mvc.api.sensorLog.controller.response.ListDangerZoneSensorLogResponse;
import kr.co.hulan.aas.mvc.api.sensorLog.service.DangerZoneSensorLogService;
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
@RequestMapping("/api/sensorLog/dangerZone")
@Api(tags = "위험 지역 접근 기록 관리")
 */
public class DangerZoneSensorLogController {

    @Autowired
    private DangerZoneSensorLogService dangerZoneSensorLogService;

    @ApiOperation(value = "위험 지역 접근 기록 리스트", notes = "위험 지역 접근 기록 검색 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("search")
    public DefaultHttpRes<ListDangerZoneSensorLogResponse> search(
            @Valid @RequestBody ListDangerZoneSensorLogRequest request) {
        return new DefaultHttpRes<ListDangerZoneSensorLogResponse>(BaseCode.SUCCESS, dangerZoneSensorLogService.findListPage(request));
    }

    @ApiOperation(value = "위험 지역 접근 기록 데이터 Export", notes = "위험 지역 접근 기록 데이터 Export 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("export")
    public DefaultHttpRes<List<SensorLogDto>> export(
            @Valid @RequestBody ExportDangerZoneSensorLogDataRequest request) {
        return new DefaultHttpRes<List<SensorLogDto>>(BaseCode.SUCCESS, dangerZoneSensorLogService.findListByCondition(request.getConditionMap()));
    }

    @ApiOperation(value = "위험 지역 접근 기록 알림 발송", notes = "위험 지역 접근 기록 알림 발송 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("notifyAlert")
    public DefaultHttpRes notifyAlert(
            @Valid @RequestBody NotifyAlarmRequest request) {
        dangerZoneSensorLogService.sendAlarm(request);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

}
