package kr.co.hulan.aas.mvc.api.safetySituation.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.request.ExportWorkplaceSafetySituationRequest;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.request.ListWorkplaceSafetySituationRequest;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.response.ListWorkplaceSafetySituationResponse;
import kr.co.hulan.aas.mvc.api.safetySituation.dto.WorkPlaceSafetySituationDto;
import kr.co.hulan.aas.mvc.api.safetySituation.service.WorkPlaceSafetySituationService;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/safety/workplace")
@Api(tags = "현장별 안전관리 현황")
public class WorkplaceSafetySituationController {

    @Autowired
    private WorkPlaceSafetySituationService workplaceSafetySituationService;

    @ApiOperation(value = "현장별 안전관리 현황 리스트", notes = "현장별 안전관리 현황 검색 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("search")
    public DefaultHttpRes<ListWorkplaceSafetySituationResponse> searchWorker(
            @Valid @RequestBody ListWorkplaceSafetySituationRequest request) {
        return new DefaultHttpRes<ListWorkplaceSafetySituationResponse>(BaseCode.SUCCESS, workplaceSafetySituationService.findListPage(request));

    }

    @ApiOperation(value = "현장별 안전관리 현황 데이터 Export", notes = "현장별 안전관리 현황 데이터 Export 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("export")
    public DefaultHttpRes<List<WorkPlaceSafetySituationDto>> exportConCompany(
            @Valid @RequestBody ExportWorkplaceSafetySituationRequest request) {
        return new DefaultHttpRes<List<WorkPlaceSafetySituationDto>>(BaseCode.SUCCESS, workplaceSafetySituationService.findListByCondition(request.getConditionMap()));
    }
}
