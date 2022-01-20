package kr.co.hulan.aas.mvc.api.workplace.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.member.controller.request.CreateConstructionCompanyRequest;
import kr.co.hulan.aas.mvc.api.member.controller.request.ExportConstructionCompanyDataRequest;
import kr.co.hulan.aas.mvc.api.member.controller.request.ListConstructionCompanyRequest;
import kr.co.hulan.aas.mvc.api.member.controller.request.UpdateConstructionCompanyRequest;
import kr.co.hulan.aas.mvc.api.member.controller.response.ListConstructionCompanyResponse;
import kr.co.hulan.aas.mvc.api.member.dto.ConstructionCompanyDto;
import kr.co.hulan.aas.mvc.api.member.dto.WorkerDto;
import kr.co.hulan.aas.mvc.api.workplace.controller.request.CreateWorkplaceRequest;
import kr.co.hulan.aas.mvc.api.workplace.controller.request.ExportWorkplaceDataRequest;
import kr.co.hulan.aas.mvc.api.workplace.controller.request.ListWorkplaceRequest;
import kr.co.hulan.aas.mvc.api.workplace.controller.request.UpdateWorkplaceRequest;
import kr.co.hulan.aas.mvc.api.workplace.controller.response.ListWorkplaceResponse;
import kr.co.hulan.aas.mvc.api.workplace.service.WorkplaceService;
import kr.co.hulan.aas.mvc.model.dto.ConCompanyDto;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/workplace/manage")
@Api(tags = "현장 관리")
public class WorkplaceController {

    @Autowired
    private WorkplaceService workplaceService;

    @ApiOperation(value = "현장 리스트", notes = "현장 검색 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("search")
    public DefaultHttpRes<ListWorkplaceResponse> search(
            @Valid @RequestBody ListWorkplaceRequest request) {
        return new DefaultHttpRes<ListWorkplaceResponse>(BaseCode.SUCCESS, workplaceService.findListPage(request));
    }

    @ApiOperation(value = "현장 리스트 데이터 Export", notes = "현장 리스트 데이터 Export 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("export")
    public DefaultHttpRes<List<WorkPlaceDto>> exportConCompany(
            @Valid @RequestBody ExportWorkplaceDataRequest request) {
        return new DefaultHttpRes<List<WorkPlaceDto>>(BaseCode.SUCCESS, workplaceService.findListByCondition(request.getConditionMap()));
    }


    @ApiOperation(value = "현장 조회", notes = "현장 정보를 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
    })
    @GetMapping("detail/{wpId}")
    public DefaultHttpRes<WorkPlaceDto> detailEmployee(
            @PathVariable(value = "wpId", required = true) String wpId) {
        return new DefaultHttpRes(BaseCode.SUCCESS,  workplaceService.findById(wpId));
    }

    @ApiOperation(value = "현장 정보 등록", notes = "현장 정보 등록 제공한다.")
    @PostMapping("create")
    public DefaultHttpRes create(
            @Valid @RequestBody CreateWorkplaceRequest request) {
        workplaceService.create(request);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }


    @ApiOperation(value = "현장 정보 수정", notes = "현장 정보 수정 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
    })
    @PostMapping("update/{wpId}")
    public DefaultHttpRes updateWorker(
            @Valid @RequestBody UpdateWorkplaceRequest request,
            @PathVariable(value = "wpId", required = true) String wpId) {
        workplaceService.update(request, wpId);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }


    @ApiOperation(value = "현장 정보 삭제", notes = "현장 정보 삭제 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
    })
    @PostMapping("delete/{wpId}")
    public DefaultHttpRes deleteWorker(
            @PathVariable(value = "wpId", required = true) String wpId) {
        workplaceService.delete(wpId);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "현장 정보 복수 삭제", notes = "현장 정보 복수 삭제 제공한다.")
    @PostMapping("deleteMultiple")
    public DefaultHttpRes deleteWorkers(
            @RequestParam(value = "wpIds", required = true) List<String> wpIds) {
        workplaceService.deleteMultiple(wpIds);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

}
