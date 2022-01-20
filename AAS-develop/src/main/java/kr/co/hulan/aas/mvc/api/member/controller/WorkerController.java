package kr.co.hulan.aas.mvc.api.member.controller;


import io.swagger.annotations.*;
import kr.co.hulan.aas.mvc.api.member.controller.request.*;
import kr.co.hulan.aas.mvc.api.member.dto.WorkerDto;
import kr.co.hulan.aas.mvc.api.member.controller.response.ListWorkerResponse;
import kr.co.hulan.aas.mvc.api.member.service.WorkerService;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.common.code.BaseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/member/worker")
@Api(tags = "근로자 관리")
public class WorkerController {

    @Autowired
    private WorkerService workerService;


    @ApiOperation(value = "근로자 조회", notes = "근로자 정보를 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mbId", value = "회원 아이디 (근로자:휴대폰번호)", required = true, dataType = "string", paramType = "path")
    })
    @GetMapping("detail/{mbId}")
    public DefaultHttpRes<WorkerDto> detailEmployee(
            @PathVariable(value = "mbId", required = true) String mbId) {
        return new DefaultHttpRes(BaseCode.SUCCESS,  workerService.findWorkerByMbId(mbId));
    }

    @ApiOperation(value = "근로자 정보 등록", notes = "근로자 정보 등록 제공한다.")
    @PostMapping("create")
    public DefaultHttpRes<WorkerDto> createWorker(
            @Valid @RequestBody CreateWorkerRequest request) {
        return new DefaultHttpRes(BaseCode.SUCCESS, workerService.createWorker(request));
    }

    @ApiOperation(value = "근로자 정보 수정", notes = "근로자 정보 수정 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mbId", value = "회원 아이디 (근로자:휴대폰번호)", required = true, dataType = "string", paramType = "path")
    })
    @PostMapping("update/{mbId}")
    public DefaultHttpRes<WorkerDto> updateWorker(
            @Valid @RequestBody UpdateWorkerRequest request,
            @PathVariable(value = "mbId", required = true) String mbId) {
        workerService.updateWorker(request, mbId);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "근로자 정보 삭제", notes = "근로자 정보 삭제 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mbId", value = "회원 아이디 (근로자:휴대폰번호)", required = true, dataType = "string", paramType = "path")
    })
    @PostMapping("delete/{mbId}")
    public DefaultHttpRes<WorkerDto> deleteWorker(
            @PathVariable(value = "mbId", required = true) String mbId) {
        workerService.deleteWorker(mbId);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "근로자 정보 복수 삭제", notes = "근로자 정보 복수 삭제 제공한다.")
    @PostMapping("deleteMultiple")
    public DefaultHttpRes<WorkerDto> deleteWorkers(
            @RequestParam(value = "mbIds", required = true) List<String> mbIds) {
        workerService.deleteWorkers(mbIds);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }


    @ApiOperation(value = "근로자 리스트", notes = "근로자 검색 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("search")
    /* @ValidGrade(grades = { 3, 4, 10} ) */
    public DefaultHttpRes<ListWorkerResponse> searchWorker(
            @Valid @RequestBody ListWorkerRequest request) {
        return new DefaultHttpRes<ListWorkerResponse>(BaseCode.SUCCESS, workerService.findWorkerListPage(request));
    }

    @ApiOperation(value = "근로자 리스트 데이터 Export", notes = "근로자 리스트 데이터 Export 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("export")
    public DefaultHttpRes<List<WorkerDto>> exportWorker(
            @Valid @RequestBody ExportWorkerDataRequest request) {
        return new DefaultHttpRes<List<WorkerDto>>(BaseCode.SUCCESS, workerService.findWorkerList(request.getConditionMap()));
    }
}
