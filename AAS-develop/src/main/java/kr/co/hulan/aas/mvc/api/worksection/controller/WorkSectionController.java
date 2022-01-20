package kr.co.hulan.aas.mvc.api.worksection.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.code.dto.CodeDto;
import kr.co.hulan.aas.mvc.api.workplace.controller.WorkplaceWorkerController;
import kr.co.hulan.aas.mvc.api.workplace.controller.request.CreateWorkplaceWorkerRequest;
import kr.co.hulan.aas.mvc.api.workplace.controller.request.ExportWorkplaceWorkerDataRequest;
import kr.co.hulan.aas.mvc.api.workplace.controller.request.ListWorkplaceWorkerRequest;
import kr.co.hulan.aas.mvc.api.workplace.controller.request.UpdateWorkplaceRequest;
import kr.co.hulan.aas.mvc.api.workplace.controller.response.ListWorkplaceWorkerResponse;
import kr.co.hulan.aas.mvc.api.workplace.service.WorkplaceWorkerService;
import kr.co.hulan.aas.mvc.api.worksection.controller.request.CreateWorkSectionRequest;
import kr.co.hulan.aas.mvc.api.worksection.controller.request.ExportWorkSectionDataRequest;
import kr.co.hulan.aas.mvc.api.worksection.controller.request.ListWorkSectionRequest;
import kr.co.hulan.aas.mvc.api.worksection.controller.request.UpdateWorkSectionRequest;
import kr.co.hulan.aas.mvc.api.worksection.controller.response.ListWorkSectionResponse;
import kr.co.hulan.aas.mvc.api.worksection.controller.response.WorkSectionDetailResponse;
import kr.co.hulan.aas.mvc.api.worksection.service.WorkSectionService;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceWorkerDto;
import kr.co.hulan.aas.mvc.model.dto.WorkSectionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/worksection")
@Api(tags = "공정 관리")
public class WorkSectionController {
    private Logger logger = LoggerFactory.getLogger(WorkplaceWorkerController.class);

    @Autowired
    private WorkSectionService workSectionService;

    @ApiOperation(value = "최상위 공정(공정A) 정보 코드 리스트", notes = "최상위 공정(공정A) 정보 코드 리스트 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("searchTopLevel")
    public DefaultHttpRes<List<CodeDto>> searchTopLevel() {
        return new DefaultHttpRes<List<CodeDto>>(BaseCode.SUCCESS, workSectionService.findTopLevelList());
    }

    @ApiOperation(value = "공정 정보 리스트", notes = "공정 정보 검색 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("search")
    public DefaultHttpRes<ListWorkSectionResponse> search(
            @Valid @RequestBody ListWorkSectionRequest request) {
        return new DefaultHttpRes<ListWorkSectionResponse>(BaseCode.SUCCESS, workSectionService.findListPage(request));
    }

    @ApiOperation(value = "공정 정보 데이터 Export", notes = "공정 정보 데이터 Export 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("export")
    public DefaultHttpRes<List<WorkSectionDto>> export(
            @Valid @RequestBody ExportWorkSectionDataRequest request) {
        return new DefaultHttpRes<List<WorkSectionDto>>(BaseCode.SUCCESS, workSectionService.findListByCondition(request.getConditionMap()));
    }

    @ApiOperation(value = "공정 정보 조회", notes = "공정 정보를 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sectionCd", value = "공정 코드", required = true, dataType = "string", paramType = "path")
    })
    @GetMapping("detail/{sectionCd}")
    public DefaultHttpRes<WorkSectionDetailResponse> detail(
            @PathVariable(value = "sectionCd", required = true) String sectionCd) {
        return new DefaultHttpRes(BaseCode.SUCCESS,  workSectionService.findById(sectionCd));
    }

    @ApiOperation(value = "공정 정보 등록", notes = "공정 정보 등록 제공한다.")
    @PostMapping("create")
    public DefaultHttpRes create(
            @Valid @RequestBody CreateWorkSectionRequest request) {
        workSectionService.create(request);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "공정 정보 수정", notes = "공정 정보 수정 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sectionCd", value = "공정 코드", required = true, dataType = "string", paramType = "path")
    })
    @PostMapping("update/{sectionCd}")
    public DefaultHttpRes updateWorker(
            @Valid @RequestBody UpdateWorkSectionRequest request,
            @PathVariable(value = "sectionCd", required = true) String sectionCd) {
        workSectionService.update(request, sectionCd);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "공정 정보 삭제", notes = "공정 정보 삭제 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sectionCd", value = "공정 코드", required = true, dataType = "string", paramType = "path")
    })
    @PostMapping("delete/{sectionCd}")
    public DefaultHttpRes deleteWorker(
            @PathVariable(value = "sectionCd", required = true) String sectionCd) {
        workSectionService.delete(sectionCd);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "공정 정보 복수 삭제", notes = "공정 정보 복수 삭제 제공한다.")
    @PostMapping("deleteMultiple")
    public DefaultHttpRes deleteMultiple(
            @RequestParam(value = "sectionCds", required = true) List<String> sectionCds) {
        workSectionService.deleteMultiple(sectionCds);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }
}
