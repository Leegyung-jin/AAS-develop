package kr.co.hulan.aas.mvc.api.member.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.mvc.api.member.controller.request.*;
import kr.co.hulan.aas.mvc.api.member.controller.response.ListConstructionCompanyResponse;
import kr.co.hulan.aas.mvc.api.member.dto.ConstructionCompanyDto;
import kr.co.hulan.aas.mvc.api.member.dto.WorkerDto;
import kr.co.hulan.aas.mvc.api.member.service.ConstructionCompanyService;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.model.dto.ConCompanyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/member/conCompany")
@Api(tags = "건설사 관리")
public class ConstructionCompanyController {

    @Autowired
    private ConstructionCompanyService constructionCompanyService;

    @ApiOperation(value = "건설사 리스트", notes = "건설사 검색 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("search")
    public DefaultHttpRes<ListConstructionCompanyResponse> searchConCompany(
            @Valid @RequestBody ListConstructionCompanyRequest request) {
        return new DefaultHttpRes<ListConstructionCompanyResponse>(BaseCode.SUCCESS, constructionCompanyService.findListPage(request));
    }

    @ApiOperation(value = "건설사 리스트 데이터 Export", notes = "건설사 리스트 데이터 Export 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("export")
    public DefaultHttpRes<List<ConstructionCompanyDto>> exportConCompany(
            @Valid @RequestBody ExportConstructionCompanyDataRequest request) {
        return new DefaultHttpRes<List<ConstructionCompanyDto>>(BaseCode.SUCCESS, constructionCompanyService.findListByCondition(request.getConditionMap()));
    }

    @ApiOperation(value = "건설사 조회", notes = "건설사 정보를 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ccId", value = "건설사 아이디", required = true, dataType = "string", paramType = "path")
    })
    @GetMapping("detail/{ccId}")
    public DefaultHttpRes<ConCompanyDto> detailEmployee(
            @PathVariable(value = "ccId", required = true) String ccId) {
        return new DefaultHttpRes(BaseCode.SUCCESS,  constructionCompanyService.findConCompanyByCcId(ccId));
    }

    @ApiOperation(value = "건설사 정보 등록", notes = "건설사 정보 등록 제공한다.")
    @PostMapping("create")
    public DefaultHttpRes<ConCompanyDto> createCompany(
            @Valid @RequestBody CreateConstructionCompanyRequest request) {
        return new DefaultHttpRes(BaseCode.SUCCESS, constructionCompanyService.create(request));
    }

    @ApiOperation(value = "건설사 정보 수정", notes = "건설사 정보 수정 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ccId", value = "건설사 아이디", required = true, dataType = "string", paramType = "path")
    })
    @PostMapping("update/{ccId}")
    public DefaultHttpRes<WorkerDto> updateWorker(
            @Valid @RequestBody UpdateConstructionCompanyRequest request,
            @PathVariable(value = "ccId", required = true) String ccId) {
        constructionCompanyService.update(request, ccId);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "건설사 정보 삭제", notes = "건설사 정보 삭제 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ccId", value = "건설사 아이디", required = true, dataType = "string", paramType = "path")
    })
    @PostMapping("delete/{ccId}")
    public DefaultHttpRes deleteWorker(
            @PathVariable(value = "ccId", required = true) String ccId) {
        constructionCompanyService.delete(ccId);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "건설사 정보 복수 삭제", notes = "건설사 정보 복수 삭제 제공한다.")
    @PostMapping("deleteMultiple")
    public DefaultHttpRes deleteWorkers(
            @RequestParam(value = "ccIds", required = true) List<String> ccIds) {
        constructionCompanyService.deleteConCompanyList(ccIds);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }
}
