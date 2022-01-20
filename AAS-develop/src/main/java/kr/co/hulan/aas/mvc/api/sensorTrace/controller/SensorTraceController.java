package kr.co.hulan.aas.mvc.api.sensorTrace.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.sensorTrace.controller.request.ExportSensorTraceDataRequest;
import kr.co.hulan.aas.mvc.api.sensorTrace.controller.request.ListSensorTraceRequest;
import kr.co.hulan.aas.mvc.api.sensorTrace.controller.request.NotifySensorTraceAlarmRequest;
import kr.co.hulan.aas.mvc.api.sensorTrace.controller.response.ListSensorTraceResponse;
import kr.co.hulan.aas.mvc.api.sensorTrace.service.SensorTraceService;
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
@RequestMapping("/api/sensorLog/record")
@Api(tags = "위치세부기록 관리")
public class SensorTraceController {

    @Autowired
    private SensorTraceService sensorTraceService;

    @ApiOperation(value = "위치세부기록 리스트", notes = "위치세부기록 검색 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("search")
    public DefaultHttpRes<ListSensorTraceResponse> search(
            @Valid @RequestBody ListSensorTraceRequest request) {
        return new DefaultHttpRes<ListSensorTraceResponse>(BaseCode.SUCCESS, sensorTraceService.findListPage(request));
    }

    @ApiOperation(value = "위치세부기록 데이터 Export", notes = "위치세부기록 데이터 Export 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("export")
    public DefaultHttpRes<List<SensorLogTraceDto>> export(
            @Valid @RequestBody ExportSensorTraceDataRequest request) {
        return new DefaultHttpRes<List<SensorLogTraceDto>>(BaseCode.SUCCESS, sensorTraceService.findListByCondition(request.getConditionMap()));
    }


    @ApiOperation(value = "위치세부기록 데이터 Excel Download", notes = "위치세부기록 Excel Download 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("downloadExcel")
    public void downloadExcel(
            HttpServletResponse response,
            @Valid @RequestBody ExportSensorTraceDataRequest request) {
        sensorTraceService.downloadExcel(response, request);
    }

    @ApiOperation(value = "위치세부기록 알림 발송", notes = "위치세부기록 알림 발송 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("notifyAlert")
    public DefaultHttpRes notifyAlert(
            @Valid @RequestBody NotifySensorTraceAlarmRequest request) {
        sensorTraceService.sendAlarm(request);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }
}
