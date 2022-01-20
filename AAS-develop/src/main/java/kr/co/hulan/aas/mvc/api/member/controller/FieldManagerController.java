package kr.co.hulan.aas.mvc.api.member.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.mvc.api.member.controller.request.*;
import kr.co.hulan.aas.mvc.api.member.controller.response.ListFieldManagerResponse;
import kr.co.hulan.aas.mvc.api.member.dto.FieldManagerDto;
import kr.co.hulan.aas.mvc.api.member.dto.WorkerDto;
import kr.co.hulan.aas.mvc.api.member.service.FieldManagerService;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/member/fieldManager")
@Api(tags = "현장관리자 관리")
public class FieldManagerController {

    @Autowired
    private FieldManagerService fieldManagerService;

    @ApiOperation(value = "현장관리자 리스트", notes = "현장관리자 검색 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("search")
    public DefaultHttpRes<ListFieldManagerResponse> searchFieldManager(
            @Valid @RequestBody ListFieldManagerRequest request) {
        return new DefaultHttpRes<ListFieldManagerResponse>(BaseCode.SUCCESS, fieldManagerService.findListPage(request));
    }

    @ApiOperation(value = "현장관리자 리스트 데이터 Export", notes = "현장관리자 리스트 데이터 Export 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("export")
    public DefaultHttpRes<List<FieldManagerDto>> export(
            @Valid @RequestBody ExportFieldManagerDataRequest request) {
        return new DefaultHttpRes<List<FieldManagerDto>>(BaseCode.SUCCESS, fieldManagerService.findListByCondition(request.getConditionMap()));
    }

    @ApiOperation(value = "현장관리자 조회", notes = "현장관리자 정보를 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mbId", value = "현장관리자 아이디", required = true, dataType = "string", paramType = "path")
    })
    @GetMapping("detail/{mbId}")
    public DefaultHttpRes<FieldManagerDto> detailFieldManager(
            @PathVariable(value = "mbId", required = true) String mbId) {
        return new DefaultHttpRes(BaseCode.SUCCESS,  fieldManagerService.findFieldManagerByMbId(mbId));
    }

    @ApiOperation(value = "현장관리자 정보 등록", notes = "현장관리자 정보 등록 제공한다.")
    @PostMapping("create")
    public DefaultHttpRes<FieldManagerDto> createFieldManager(
            @Valid @RequestBody CreateFieldManagerRequest request) {
        return new DefaultHttpRes(BaseCode.SUCCESS, fieldManagerService.create(request));
    }

    @ApiOperation(value = "현장관리자 정보 수정", notes = "현장관리자 정보 수정 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mbId", value = "회원 아이디", required = true, dataType = "string", paramType = "path")
    })
    @PostMapping("update/{mbId}")
    public DefaultHttpRes updateFieldManager(
            @Valid @RequestBody UpdateFieldManagerRequest request,
            @PathVariable(value = "mbId", required = true) String mbId) {
        fieldManagerService.update(request, mbId);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "현장관리자 정보 삭제", notes = "현장관리자 정보 삭제 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mbId", value = "회원 아이디", required = true, dataType = "string", paramType = "path")
    })
    @PostMapping("delete/{mbId}")
    public DefaultHttpRes deleteFieldManger(
            @PathVariable(value = "mbId", required = true) String mbId) {
        fieldManagerService.delete(mbId);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "현장관리자 정보 복수 삭제", notes = "현장관리자 정보 복수 삭제 제공한다.")
    @PostMapping("deleteMultiple")
    public DefaultHttpRes deleteWorkers(
            @RequestParam(value = "mbIds", required = true) List<String> mbIds) {
        fieldManagerService.deleteFieldManagers(mbIds);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }


}
