package kr.co.hulan.aas.mvc.api.workplace.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.workplace.controller.request.*;
import kr.co.hulan.aas.mvc.api.workplace.controller.response.ListWorkplaceCooperativeCompanyResponse;
import kr.co.hulan.aas.mvc.api.workplace.service.WorkplaceCooperativeCompanyService;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceCoopDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/workplace/cooperativeCompany")
@Api(tags = "협력사 현장 편입 정보 관리")
public class WorkplaceCooperativeCompanyController {

    @Autowired
    private WorkplaceCooperativeCompanyService workplaceCooperativeCompanyService;

    @ApiOperation(value = "협력사 현장 편입 정보 리스트", notes = "협력사 현장 편입 정보 검색 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("search")
    public DefaultHttpRes<ListWorkplaceCooperativeCompanyResponse> search(
            @Valid @RequestBody ListWorkplaceCooperativeCompanyRequest request) {
        return new DefaultHttpRes<ListWorkplaceCooperativeCompanyResponse>(BaseCode.SUCCESS, workplaceCooperativeCompanyService.findListPage(request));
    }


    @ApiOperation(value = "협력사 현장 편입 정보 데이터 Export", notes = "협력사 현장 편입 정보 데이터 Export 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("export")
    public DefaultHttpRes<List<WorkPlaceCoopDto>> export(
            @Valid @RequestBody ExportWorkplaceCooperativeCompanyDataRequest request) {
        return new DefaultHttpRes<List<WorkPlaceCoopDto>>(BaseCode.SUCCESS, workplaceCooperativeCompanyService.findListByCondition(request.getConditionMap()));
    }

    @ApiOperation(value = "협력사 현장 편입 정보 조회", notes = "협력사 현장 편입 정보를 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wpcId", value = "협력사 현장 편입 아이디", required = true, dataType = "string", paramType = "path")
    })
    @GetMapping("detail/{wpcId}")
    public DefaultHttpRes<WorkPlaceCoopDto> detail(
            @PathVariable(value = "wpcId", required = true) String wpcId) {
        return new DefaultHttpRes(BaseCode.SUCCESS,  workplaceCooperativeCompanyService.findById(wpcId));
    }

    @ApiOperation(value = "협력사 현장 편입 정보 조회", notes = "협력사 현장 편입 정보를 제공한다.")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
        @ApiImplicitParam(name = "coopMbId", value = "협력사 아이디", required = true, dataType = "string", paramType = "path")
    })
    @GetMapping("detail/{wpId}/{coopMbId}")
    public DefaultHttpRes<WorkPlaceCoopDto> detail(
        @PathVariable(value = "wpId", required = true) String wpId,
        @PathVariable(value = "coopMbId", required = true) String coopMbId ) {
        return new DefaultHttpRes(BaseCode.SUCCESS,  workplaceCooperativeCompanyService.findByWpIdAndCoopMbId(wpId, coopMbId));
    }

    @ApiOperation(value = "협력사 현장 편입 정보 등록", notes = "협력사 현장 편입 정보 등록 제공한다.")
    @PostMapping("create")
    public DefaultHttpRes create(
            @Valid @RequestBody CreateWorkplaceCooperativeCompanyRequest request) {
        workplaceCooperativeCompanyService.create(request);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }


    @ApiOperation(value = "협력사 현장 편입 정보 수정", notes = "협력사 현장 편입 정보 수정 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wpcId", value = "협력사 현장 편입 아이디", required = true, dataType = "string", paramType = "path")
    })
    @PostMapping("update/{wpcId}")
    public DefaultHttpRes updateWorker(
            @Valid @RequestBody UpdateWorkplaceCooperativeCompanyRequest request,
            @PathVariable(value = "wpcId", required = true) String wpcId) {
        workplaceCooperativeCompanyService.update(request, wpcId);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }


    @ApiOperation(value = "협력사 현장 편입 정보 삭제", notes = "협력사 현장 편입 정보 삭제 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wpcId", value = "협력사 현장 편입 아이디", required = true, dataType = "string", paramType = "path")
    })
    @PostMapping("delete/{wpcId}")
    public DefaultHttpRes deleteWorker(
            @PathVariable(value = "wpcId", required = true) String wpcId) {
        workplaceCooperativeCompanyService.delete(wpcId);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "협력사 현장 편입 정보 복수 삭제", notes = "협력사 현장 편입 정보 복수 삭제 제공한다.")
    @PostMapping("deleteMultiple")
    public DefaultHttpRes deleteWorkers(
            @RequestParam(value = "wpcIds", required = true) List<String> wpcIds) {
        workplaceCooperativeCompanyService.deleteMultiple(wpcIds);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }
}
