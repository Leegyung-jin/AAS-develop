package kr.co.hulan.aas.mvc.api.workplace.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.mvc.api.workplace.controller.request.*;
import kr.co.hulan.aas.mvc.api.workplace.controller.response.ListWorkerCheckResponse;
import kr.co.hulan.aas.mvc.api.workplace.service.WorkerCheckService;
import kr.co.hulan.aas.mvc.model.dto.WorkerCheckDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/workplace/workerCheck")
@Api(tags = "고위험/주요근로자 관리")
public class WorkerCheckController {


    @Autowired
    private WorkerCheckService workerCheckService;

    @ApiOperation(value = "고위험/주요근로자 리스트", notes = "고위험/주요근로자 검색 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("search")
    public DefaultHttpRes<ListWorkerCheckResponse> search(
            @Valid @RequestBody ListWorkerCheckRequest request) {
        return new DefaultHttpRes<ListWorkerCheckResponse>(BaseCode.SUCCESS, workerCheckService.findListPage(request));
    }

    @ApiOperation(value = "고위험/주요근로자 데이터 Export", notes = "고위험/주요근로자 데이터 Export 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("export")
    public DefaultHttpRes<List<WorkerCheckDto>> export(
            @Valid @RequestBody ExportWorkerCheckDataRequest request) {
        return new DefaultHttpRes<List<WorkerCheckDto>>(BaseCode.SUCCESS, workerCheckService.findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(request.getConditionMap())));
    }

    @ApiOperation(value = "고위험/주요근로자 조회", notes = "고위험/주요근로자 정보를 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wcIdx", value = "고위험/주요근로자 아이디", required = true, dataType = "int", paramType = "path")
    })
    @GetMapping("detail/{wcIdx}")
    public DefaultHttpRes<WorkerCheckDto> detail(
            @PathVariable(value = "wcIdx", required = true) int wcIdx) {
        return new DefaultHttpRes(BaseCode.SUCCESS,  workerCheckService.findById(wcIdx));
    }

    @ApiOperation(value = "고위험/주요근로자 정보 등록", notes = "고위험/주요근로자 정보 등록 제공한다.")
    @PostMapping("create")
    public DefaultHttpRes<Integer> create(
            @Valid @RequestBody CreateWorkerCheckRequest request) {
        return new DefaultHttpRes(BaseCode.SUCCESS, workerCheckService.create(request));
    }


    @ApiOperation(value = "고위험/주요근로자 정보 수정", notes = "고위험/주요근로자 정보 수정 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wcIdx", value = "고위험/주요근로자 아이디", required = true, dataType = "int", paramType = "path")
    })
    @PostMapping("update/{wcIdx}")
    public DefaultHttpRes updateWorker(
            @Valid @RequestBody UpdateWorkerCheckRequest request,
            @PathVariable(value = "wcIdx", required = true) int wcIdx) {
        workerCheckService.update(request, wcIdx);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }


    @ApiOperation(value = "고위험/주요근로자 정보 삭제", notes = "고위험/주요근로자 정보 삭제 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wcIdx", value = "고위험/주요근로자 아이디", required = true, dataType = "int", paramType = "path")
    })
    @PostMapping("delete/{wcIdx}")
    public DefaultHttpRes delete(
            @PathVariable(value = "wcIdx", required = true) int wcIdx) {
        workerCheckService.delete(wcIdx);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "고위험/주요근로자 정보 복수 삭제", notes = "고위험/주요근로자 정보 복수 삭제 제공한다.")
    @PostMapping("deleteMultiple")
    public DefaultHttpRes deleteMultiple(
            @RequestParam(value = "wcIdxs", required = true) List<Integer> wcIdxs) {
        workerCheckService.deleteMultiple(wcIdxs);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }
}
