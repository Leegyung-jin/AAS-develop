package kr.co.hulan.aas.mvc.api.safetySituation.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.request.ExportWorkerFatiqueRequest;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.request.ExportWorkerSafetySituationRequest;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.request.ListWorkerFatiqueRequest;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.request.ListWorkerSafetySituationRequest;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.response.ListWorkerFatiqueResponse;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.response.ListWorkerSafetySituationResponse;
import kr.co.hulan.aas.mvc.api.safetySituation.dto.WorkerFatiqueDto;
import kr.co.hulan.aas.mvc.api.safetySituation.dto.WorkerSafetySituationDto;
import kr.co.hulan.aas.mvc.api.safetySituation.service.WorkerFatiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/safety/fatique")
@Api(tags = "근로자 피로도 현황")
public class WorkerFatigueController {

    @Autowired
    private WorkerFatiqueService workerFatiqueService;

    @ApiOperation(value = "근로자 피로도 현황 리스트", notes = "근로자 피로도 현황 검색 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("search")
    public DefaultHttpRes<ListWorkerFatiqueResponse> searchWorker(
            @Valid @RequestBody ListWorkerFatiqueRequest request) {
        return new DefaultHttpRes<ListWorkerFatiqueResponse>(BaseCode.SUCCESS, workerFatiqueService.findListPage(request));
    }

    @ApiOperation(value = "근로자 피로도 현황 데이터 Export", notes = "근로자 피로도 현황 데이터 Export 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("export")
    public DefaultHttpRes<List<WorkerFatiqueDto>> exportConCompany(
            @Valid @RequestBody ExportWorkerFatiqueRequest request) {
        return new DefaultHttpRes<List<WorkerFatiqueDto>>(BaseCode.SUCCESS, workerFatiqueService.findListByCondition(request.getConditionMap()));
    }
}
