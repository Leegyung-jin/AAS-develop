package kr.co.hulan.aas.mvc.api.workplace.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.workplace.controller.request.*;
import kr.co.hulan.aas.mvc.api.workplace.controller.response.ListWorkerCheckResponse;
import kr.co.hulan.aas.mvc.api.workplace.controller.response.ListWorkerWarnResponse;
import kr.co.hulan.aas.mvc.api.workplace.service.WorkerCheckService;
import kr.co.hulan.aas.mvc.api.workplace.service.WorkerWarnService;
import kr.co.hulan.aas.mvc.model.dto.WorkerCheckDto;
import kr.co.hulan.aas.mvc.model.dto.WorkerWarnDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/workplace/workerWarn")
@Api(tags = "근로자 경고 관리")
public class WorkerWarnController {


    @Autowired
    private WorkerWarnService workerWarnService;

    @ApiOperation(value = "근로자 경고 리스트", notes = "근로자 경고 검색 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("search")
    public DefaultHttpRes<ListWorkerWarnResponse> search(
            @Valid @RequestBody ListWorkerWarnRequest request) {
        return new DefaultHttpRes<ListWorkerWarnResponse>(BaseCode.SUCCESS, workerWarnService.findListPage(request));
    }

    @ApiOperation(value = "근로자 경고 데이터 Export", notes = "근로자 경고 데이터 Export 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("export")
    public DefaultHttpRes<List<WorkerWarnDto>> export(
            @Valid @RequestBody ExportWorkerWarnDataRequest request) {
        return new DefaultHttpRes<List<WorkerWarnDto>>(BaseCode.SUCCESS, workerWarnService.findListByCondition(request.getConditionMap()));
    }

    @ApiOperation(value = "근로자 경고 조회", notes = "근로자 경고 정보를 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wwIdx", value = "근로자 경고 아이디", required = true, dataType = "int", paramType = "path")
    })
    @GetMapping("detail/{wwIdx}")
    public DefaultHttpRes<WorkerWarnDto> detail(
            @PathVariable(value = "wwIdx", required = true) int wwIdx) {
        return new DefaultHttpRes(BaseCode.SUCCESS,  workerWarnService.findById(wwIdx));
    }

    @ApiOperation(value = "근로자 경고 정보 등록", notes = "근로자 경고 정보 등록 제공한다.")
    @PostMapping("create")
    public DefaultHttpRes create(
            @Valid @RequestBody CreateWorkerWarnRequest request) {
        workerWarnService.create(request);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }


    @ApiOperation(value = "근로자 경고 정보 수정", notes = "근로자 경고 정보 수정 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wwIdx", value = "근로자 경고 아이디", required = true, dataType = "int", paramType = "path")
    })
    @PostMapping("update/{wwIdx}")
    public DefaultHttpRes updateWorker(
            @Valid @RequestBody UpdateWorkerWarnRequest request,
            @PathVariable(value = "wwIdx", required = true) int wwIdx) {
        workerWarnService.update(request, wwIdx);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }


    @ApiOperation(value = "근로자 경고 정보 삭제", notes = "근로자 경고 정보 삭제 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wwIdx", value = "근로자 경고 아이디", required = true, dataType = "int", paramType = "path")
    })
    @PostMapping("delete/{wwIdx}")
    public DefaultHttpRes delete(
            @PathVariable(value = "wwIdx", required = true) int wwIdx) {
        workerWarnService.delete(wwIdx);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "근로자 경고 정보 복수 삭제", notes = "근로자 경고 정보 복수 삭제 제공한다.")
    @PostMapping("deleteMultiple")
    public DefaultHttpRes deleteMultiple(
            @RequestParam(value = "wwIdxs", required = true) List<Integer> wwIdxs) {
        workerWarnService.deleteMultiple(wwIdxs);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }
}
