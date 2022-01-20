package kr.co.hulan.aas.mvc.api.safetySituation.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.member.controller.request.CreateConstructionCompanyRequest;
import kr.co.hulan.aas.mvc.api.member.controller.request.UpdateConstructionCompanyRequest;
import kr.co.hulan.aas.mvc.api.member.dto.WorkerDto;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.request.CreateWorkerWorkPrintRequest;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.request.ListWorkerWorkPrintRequest;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.request.UpdateWorkerWorkPrintRequest;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.response.ListWorkerWorkPrintResponse;
import kr.co.hulan.aas.mvc.api.safetySituation.dto.AttendantDto;
import kr.co.hulan.aas.mvc.api.safetySituation.dto.WorkPrintDto;
import kr.co.hulan.aas.mvc.api.safetySituation.service.WorkerWorkPrintService;
import kr.co.hulan.aas.mvc.model.dto.WorkerWorkPrintDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/safety/print")
@Api(tags = "출력 일보 관리")
public class WorkerWorkPrintController {

    @Autowired
    private WorkerWorkPrintService workerWorkPrintService;

    @ApiOperation(value = "출력 일보 검색", notes = "출력 일보 검색 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("search")
    public DefaultHttpRes<ListWorkerWorkPrintResponse> searchWorker(
            @Valid @RequestBody ListWorkerWorkPrintRequest request) {
        return new DefaultHttpRes<ListWorkerWorkPrintResponse>(BaseCode.SUCCESS, workerWorkPrintService.findListPage(request));
    }


    @ApiOperation(value = "출력 일보 조회", notes = "출력 일보 정보를 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wwpIdx", value = "출력 일보 아이디", required = true, dataType = "int", paramType = "path")
    })
    @GetMapping("detail/{wwpIdx}")
    public DefaultHttpRes<WorkPrintDto> detail(
            @PathVariable(value = "wwpIdx", required = true) int wwpIdx) {
        return new DefaultHttpRes(BaseCode.SUCCESS,  workerWorkPrintService.findById(wwpIdx));
    }

    @ApiOperation(value = "출력 일보 조회", notes = "출력 일보 정보를 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "coopMbId", value = "협력사 아이디", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "wwpDate", value = "작성일 (yyyy-MM-dd)", required = true, dataType = "string", paramType = "query")
    })
    @GetMapping("detail")
    public DefaultHttpRes<WorkPrintDto> findWorkerWorkPrint(
            @RequestParam(value = "wpId", required = true) String wpId,
            @RequestParam(value = "coopMbId", required = true) String coopMbId,
            @RequestParam(value = "wwpDate", required = true) @DateTimeFormat(pattern="yyyy-MM-dd") Date wwpDate) {
        return new DefaultHttpRes(BaseCode.SUCCESS,  workerWorkPrintService.findByWpIdAndCoopMbIdAndWwpDate(wpId, coopMbId, wwpDate ));
    }

    @ApiOperation(value = "출근자 리스트 조회", notes = "출근자 리스트 정보를 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "coopMbId", value = "협력사 아이디", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "wwpDate", value = "작성일 (yyyy-MM-dd)", required = true, dataType = "string", paramType = "query")
    })
    @GetMapping("attendants")
    public DefaultHttpRes<AttendantDto> attendants(
            @RequestParam(value = "wpId", required = true) String wpId,
            @RequestParam(value = "coopMbId", required = true) String coopMbId,
            @RequestParam(value = "wwpDate", required = true) @DateTimeFormat(pattern="yyyy-MM-dd") Date wwpDate) {
        return new DefaultHttpRes(BaseCode.SUCCESS,  workerWorkPrintService.findAttendants(wpId, coopMbId, wwpDate ));
    }


    @ApiOperation(value = "출력 일보 등록", notes = "출력 일보 등록 제공한다.")
    @PostMapping("create")
    public DefaultHttpRes create(
            @Valid @RequestBody CreateWorkerWorkPrintRequest request) {
        workerWorkPrintService.create(request);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "출력 일보 수정", notes = "출력 일보 수정 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wwpIdx", value = "출력 일보 아이디", required = true, dataType = "int", paramType = "path")
    })
    @PostMapping("update/{wwpIdx}")
    public DefaultHttpRes<WorkerDto> update(
            @Valid @RequestBody UpdateWorkerWorkPrintRequest request,
            @PathVariable(value = "wwpIdx", required = true) int wwpIdx) {
        workerWorkPrintService.update(request, wwpIdx);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "출력 일보 상태 변경", notes = "출력 일보 상태 변경 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wwpIdx", value = "출력 일보 아이디", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "wwpStatus", value = "출력 일보 상태", required = true, dataType = "int", paramType = "path")
    })
    @PostMapping("updateStatus/{wwpIdx}/{wwpStatus}")
    public DefaultHttpRes<WorkerDto> updateStatus(
            @PathVariable(value = "wwpIdx", required = true) int wwpIdx,
            @PathVariable(value = "wwpStatus", required = true) int wwpStatus ) {
        workerWorkPrintService.updateStatus(wwpIdx, wwpStatus);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "출력 일보 정보 삭제", notes = "출력 일보 정보 삭제 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wwpIdx", value = "출력 일보 아이디", required = true, dataType = "int", paramType = "path")
    })
    @PostMapping("delete/{wwpIdx}")
    public DefaultHttpRes deleteWorker(
            @PathVariable(value = "wwpIdx", required = true) int wwpIdx) {
        workerWorkPrintService.delete(wwpIdx);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "출력 일보 복수 삭제", notes = "출력 일보 복수 삭제 제공한다.")
    @PostMapping("deleteMultiple")
    public DefaultHttpRes deleteWorkers(
            @RequestParam(value = "wwpIdxs", required = true) List<Integer> wwpIdxs) {
        workerWorkPrintService.deleteMultiple(wwpIdxs);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

}
