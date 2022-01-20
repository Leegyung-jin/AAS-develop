package kr.co.hulan.aas.mvc.api.sensorTrace.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.sensorTrace.controller.request.ExportDangerZoneSensorTraceDataRequest;
import kr.co.hulan.aas.mvc.api.sensorTrace.controller.request.ExportSensorTraceDataRequest;
import kr.co.hulan.aas.mvc.api.sensorTrace.controller.request.ListDangerZoneSensorTraceRequest;
import kr.co.hulan.aas.mvc.api.sensorTrace.controller.request.NotifySensorTraceAlarmRequest;
import kr.co.hulan.aas.mvc.api.sensorTrace.controller.response.ListDangerZoneSensorTraceResponse;
import kr.co.hulan.aas.mvc.api.sensorTrace.service.DangerZoneSensorTraceService;
import kr.co.hulan.aas.mvc.model.dto.SensorLogTraceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/sensorLog/dangerZone")
@Api(tags = "위험지역기록 관리")
public class DangerZoneSensorTraceController {


    @Autowired
    private DangerZoneSensorTraceService dangerZoneSensorTraceService;

    @ApiOperation(value = "위험지역기록 리스트", notes = "위험지역기록 검색 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("search")
    public DefaultHttpRes<ListDangerZoneSensorTraceResponse> search(
            @Valid @RequestBody ListDangerZoneSensorTraceRequest request) {
        return new DefaultHttpRes<ListDangerZoneSensorTraceResponse>(BaseCode.SUCCESS, dangerZoneSensorTraceService.findListPage(request));
    }

    @ApiOperation(value = "위험지역기록 데이터 Export", notes = "위험지역기록 데이터 Export 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("export")
    public DefaultHttpRes<List<SensorLogTraceDto>> export(
            @Valid @RequestBody ExportDangerZoneSensorTraceDataRequest request) {
        return new DefaultHttpRes<List<SensorLogTraceDto>>(BaseCode.SUCCESS, dangerZoneSensorTraceService.findListByCondition(request.getConditionMap()));
    }

    @ApiOperation(value = "위험지역기록 데이터 Excel Download", notes = "위험지역기록 Excel Download 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("downloadExcel")
    public void downloadExcel(
            HttpServletResponse response,
            @Valid @RequestBody ExportDangerZoneSensorTraceDataRequest request) {
        dangerZoneSensorTraceService.downloadExcel(response, request);
    }

    @ApiOperation(value = "위험지역기록 알림 발송", notes = "위험지역기록 알림 발송 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("notifyAlert")
    public DefaultHttpRes notifyAlert(
            @Valid @RequestBody NotifySensorTraceAlarmRequest request) {
        dangerZoneSensorTraceService.sendAlarm(request);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

}
