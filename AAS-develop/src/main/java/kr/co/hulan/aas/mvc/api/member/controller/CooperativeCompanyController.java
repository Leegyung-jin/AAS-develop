package kr.co.hulan.aas.mvc.api.member.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.common.model.req.ConditionRequest;
import kr.co.hulan.aas.mvc.api.member.controller.request.*;
import kr.co.hulan.aas.mvc.api.member.controller.response.ListCooperativeCompanyResponse;
import kr.co.hulan.aas.mvc.api.member.dto.CooperativeCompanyDto;
import kr.co.hulan.aas.mvc.api.member.dto.FieldManagerDto;
import kr.co.hulan.aas.mvc.api.member.service.CooperativeCompanyService;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/member/cooperativeCompany")
@Api(tags = "협력사 관리")
public class CooperativeCompanyController  {

    @Autowired
    private CooperativeCompanyService cooperativeCompanyService;

    @ApiOperation(value = "협력사 리스트", notes = "협력사 검색 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("search")
    public DefaultHttpRes<ListCooperativeCompanyResponse> searchWorker(
            @Valid @RequestBody ListCooperativeCompanyRequest request) {
        return new DefaultHttpRes<ListCooperativeCompanyResponse>(BaseCode.SUCCESS, cooperativeCompanyService.findListPage(request));
    }


    @ApiOperation(value = "협력사 리스트 데이터 Export", notes = "협력사 리스트 데이터 Export 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("export")
    public DefaultHttpRes<List<CooperativeCompanyDto>> export(
            @Valid @RequestBody ExportCooperativeCompanyDataRequest request) {
        return new DefaultHttpRes<List<CooperativeCompanyDto>>(BaseCode.SUCCESS, cooperativeCompanyService.findListByCondition(request.getConditionMap()));
    }

    @ApiOperation(value = "협력사 조회", notes = "협력사 정보를 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mbId", value = "협력사 아이디(사업자번호)", required = true, dataType = "string", paramType = "path")
    })
    @GetMapping("detail/{mbId}")
    public DefaultHttpRes<CooperativeCompanyDto> detailFieldManager(
            @PathVariable(value = "mbId", required = true) String mbId) {
        return new DefaultHttpRes(BaseCode.SUCCESS,  cooperativeCompanyService.findCooperativeCompanyByMbId(mbId));
    }

    @ApiOperation(value = "협력사 정보 등록", notes = "협력사 정보 등록 제공한다.")
    @PostMapping("create")
    public DefaultHttpRes<CooperativeCompanyDto> createFieldManager(
            @Valid @RequestBody CreateCooperativeCompanyRequest request) {
        return new DefaultHttpRes(BaseCode.SUCCESS, cooperativeCompanyService.create(request));
    }

    @ApiOperation(value = "협력사 정보 수정", notes = "협력사 정보 수정 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mbId", value = "협력사 아이디(사업자번호)", required = true, dataType = "string", paramType = "path")
    })
    @PostMapping("update/{mbId}")
    public DefaultHttpRes updateFieldManager(
            @Valid @RequestBody UpdateCooperativeCompanyRequest request,
            @PathVariable(value = "mbId", required = true) String mbId) {
        cooperativeCompanyService.update(request, mbId);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "협력사 정보 삭제", notes = "협력사 정보 삭제 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mbId", value = "협력사 아이디(사업자번호)", required = true, dataType = "string", paramType = "path")
    })
    @PostMapping("delete/{mbId}")
    public DefaultHttpRes delete(
            @PathVariable(value = "mbId", required = true) String mbId) {
        cooperativeCompanyService.delete(mbId);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "협력사 정보 복수 삭제", notes = "협력사 정보 복수 삭제 제공한다.")
    @PostMapping("deleteMultiple")
    public DefaultHttpRes deleteList(
            @RequestParam(value = "mbIds", required = true) List<String> mbIds) {
        cooperativeCompanyService.deleteList(mbIds);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }
}
