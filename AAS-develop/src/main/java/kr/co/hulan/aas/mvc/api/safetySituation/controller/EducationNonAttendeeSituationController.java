package kr.co.hulan.aas.mvc.api.safetySituation.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.request.ExportEducationNonAttendeeSituationRequest;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.request.ListEducationAttendeeRequest;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.request.ListEducationnonAttendeeSituationRequest;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.response.ListEducationAttendeeResponse;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.response.ListEducationnonAttendeeSituationResponse;
import kr.co.hulan.aas.mvc.api.safetySituation.dto.EducationNonAttendeeSituationDto;
import kr.co.hulan.aas.mvc.api.safetySituation.dto.WorkerFatiqueDto;
import kr.co.hulan.aas.mvc.api.safetySituation.service.EducationNonAttendeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/safety/education")
@Api(tags = "안전조회미참석자현황 현황")
public class EducationNonAttendeeSituationController {

    @Autowired
    private EducationNonAttendeeService safetyEducationNonAttendeesService;

    @ApiOperation(value = "안전조회미참석자현황 현황 리스트", notes = "안전조회미참석자현황 현황 검색 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("search")
    public DefaultHttpRes<ListEducationnonAttendeeSituationResponse> search(
            @Valid @RequestBody ListEducationnonAttendeeSituationRequest request) {
        return new DefaultHttpRes<ListEducationnonAttendeeSituationResponse>(BaseCode.SUCCESS, safetyEducationNonAttendeesService.findListPage(request));
    }

    @ApiOperation(value = "안전조회미참석자현황 현황 데이터 Export", notes = "안전조회미참석자현황 현황 데이터 Export 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("export")
    public DefaultHttpRes<List<EducationNonAttendeeSituationDto>> export(
            @Valid @RequestBody ExportEducationNonAttendeeSituationRequest request) {
        return new DefaultHttpRes<List<EducationNonAttendeeSituationDto>>(BaseCode.SUCCESS, safetyEducationNonAttendeesService.findListByCondition(request.getConditionMap()));
    }

    @ApiOperation(value = "안전조회미참석자현황 현황 리스트", notes = "안전조회미참석자현황 현황 검색 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("detail")
    public DefaultHttpRes<ListEducationAttendeeResponse> searchNonAttendee(
            @Valid @RequestBody ListEducationAttendeeRequest request) {
        return new DefaultHttpRes<ListEducationAttendeeResponse>(BaseCode.SUCCESS, safetyEducationNonAttendeesService.findWorkerListByAttendStatus(request));
    }
}
