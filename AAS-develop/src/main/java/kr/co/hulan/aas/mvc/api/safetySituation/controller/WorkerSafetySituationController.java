package kr.co.hulan.aas.mvc.api.safetySituation.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.request.ExportWorkerSafetySituationRequest;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.request.ListWorkerSafetySituationDetailRequest;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.request.ListWorkerSafetySituationRequest;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.response.ListWorkerSafetySituationResponse;
import kr.co.hulan.aas.mvc.api.safetySituation.dto.WorkerSafetySituationDto;
import kr.co.hulan.aas.mvc.api.safetySituation.service.WorkerSafetySituationService;
import kr.co.hulan.aas.mvc.model.dto.SensorLogDto;
import kr.co.hulan.aas.mvc.model.dto.SensorLogTraceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/safety/worker")
@Api(tags = "근로자별 안전관리 현황")
public class WorkerSafetySituationController {

    @Autowired
    private WorkerSafetySituationService workerSafetySituationService;

    @ApiOperation(value = "근로자별 안전관리 현황 리스트", notes = "근로자별 안전관리 현황 검색 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("search")
    public DefaultHttpRes<ListWorkerSafetySituationResponse> search(
            @Valid @RequestBody ListWorkerSafetySituationRequest request) {
        return new DefaultHttpRes<ListWorkerSafetySituationResponse>(BaseCode.SUCCESS, workerSafetySituationService.findListPage(request));
    }

    @ApiOperation(value = "근로자별 안전관리 현황 데이터 Export", notes = "근로자별 안전관리 현황 데이터 Export 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("export")
    public DefaultHttpRes<List<WorkerSafetySituationDto>> export(
            @Valid @RequestBody ExportWorkerSafetySituationRequest request) {
        return new DefaultHttpRes<List<WorkerSafetySituationDto>>(BaseCode.SUCCESS, workerSafetySituationService.findListByCondition(request.getConditionMap()));
    }

    @ApiOperation(value = "위치세부기록 리스트", notes = "위치세부기록 리스트 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("detail")
    public DefaultHttpRes<List<SensorLogTraceDto>> detail(
            @Valid @RequestBody ListWorkerSafetySituationDetailRequest request) {
        return new DefaultHttpRes<List<SensorLogTraceDto>>(BaseCode.SUCCESS, workerSafetySituationService.findWorkerSafetySensorLogListByCondition(request.getConditionMap()));
    }
}
