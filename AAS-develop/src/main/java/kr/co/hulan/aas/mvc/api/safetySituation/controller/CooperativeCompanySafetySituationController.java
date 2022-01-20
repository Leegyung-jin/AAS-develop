package kr.co.hulan.aas.mvc.api.safetySituation.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.request.ExportCooperativeCompanySafetySituationRequest;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.request.ListCooperativeCompanySafetySituationRequest;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.request.ListWorkplaceSafetySituationRequest;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.response.ListCooperativeCompanySafetySituationResponse;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.response.ListWorkplaceSafetySituationResponse;
import kr.co.hulan.aas.mvc.api.safetySituation.dto.CooperativeCompanySafetySituationDto;
import kr.co.hulan.aas.mvc.api.safetySituation.service.CooperativeCompanySafetySituationService;
import kr.co.hulan.aas.mvc.api.safetySituation.service.WorkPlaceSafetySituationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/safety/cooperativeCompany")
@Api(tags = "협력사별 안전관리 현황")
public class CooperativeCompanySafetySituationController {

    @Autowired
    private CooperativeCompanySafetySituationService cooperativeCompanySafetySituationService;

    @ApiOperation(value = "협력사별 안전관리 현황 리스트", notes = "협력사별 안전관리 현황 검색 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("search")
    public DefaultHttpRes<ListCooperativeCompanySafetySituationResponse> searchWorker(
            @Valid @RequestBody ListCooperativeCompanySafetySituationRequest request) {
        return new DefaultHttpRes<ListCooperativeCompanySafetySituationResponse>(BaseCode.SUCCESS, cooperativeCompanySafetySituationService.findListPage(request));
    }

    @ApiOperation(value = "협력사별 안전관리 현황 데이터 Export", notes = "협력사별 안전관리 현황 데이터 Export 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("export")
    public DefaultHttpRes<List<CooperativeCompanySafetySituationDto>> exportConCompany(
            @Valid @RequestBody ExportCooperativeCompanySafetySituationRequest request) {
        return new DefaultHttpRes<List<CooperativeCompanySafetySituationDto>>(BaseCode.SUCCESS, cooperativeCompanySafetySituationService.findListByCondition(request.getConditionMap()));
    }
}
