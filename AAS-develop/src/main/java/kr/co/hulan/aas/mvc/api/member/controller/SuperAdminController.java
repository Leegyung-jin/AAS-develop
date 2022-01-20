package kr.co.hulan.aas.mvc.api.member.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.mvc.api.member.controller.request.*;
import kr.co.hulan.aas.mvc.api.member.controller.response.ListSuperAdminResponse;
import kr.co.hulan.aas.mvc.api.member.dto.FieldManagerDto;
import kr.co.hulan.aas.mvc.api.member.dto.SuperAdminDto;
import kr.co.hulan.aas.mvc.api.member.dto.WorkerDto;
import kr.co.hulan.aas.mvc.api.member.service.SuperAdminService;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/member/super")
@Api(tags = "최고관리자 관리")
public class SuperAdminController {

    @Autowired
    private SuperAdminService superAdminService;

    @ApiOperation(value = "최고관리자 리스트", notes = "최고관리자 검색 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("search")
    public DefaultHttpRes<ListSuperAdminResponse> searchWorker(
            @Valid @RequestBody ListSuperAdminRequest request) {
        return new DefaultHttpRes<ListSuperAdminResponse>(BaseCode.SUCCESS, superAdminService.findListPage(request));
    }

    @ApiOperation(value = "최고관리자 조회", notes = "최고관리자 정보를 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mbId", value = "최고관리자 아이디", required = true, dataType = "string", paramType = "path")
    })
    @GetMapping("detail/{mbId}")
    public DefaultHttpRes<SuperAdminDto> detailFieldManager(
            @PathVariable(value = "mbId", required = true) String mbId) {
        return new DefaultHttpRes(BaseCode.SUCCESS,  superAdminService.findByMbId(mbId));
    }

    @ApiOperation(value = "최고관리자 정보 등록", notes = "최고관리자 정보 등록 제공한다.")
    @PostMapping("create")
    public DefaultHttpRes createFieldManager(
            @Valid @RequestBody CreateSuperAdminRequest request) {
        superAdminService.create(request);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "최고관리자 정보 수정", notes = "최고관리자 정보 수정 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mbId", value = "최고관리자 아이디", required = true, dataType = "string", paramType = "path")
    })
    @PostMapping("update/{mbId}")
    public DefaultHttpRes updateFieldManager(
            @Valid @RequestBody UpdateSuperAdminRequest request,
            @PathVariable(value = "mbId", required = true) String mbId) {
        superAdminService.update(request, mbId);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "최고관리자 정보 삭제", notes = "최고관리자 정보 삭제 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mbId", value = "최고관리자 아이디", required = true, dataType = "string", paramType = "path")
    })
    @PostMapping("delete/{mbId}")
    public DefaultHttpRes<WorkerDto> deleteWorker(
            @PathVariable(value = "mbId", required = true) String mbId) {
        superAdminService.delete(mbId);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "최고관리자 정보 복수 삭제", notes = "최고관리자 정보 복수 삭제 제공한다.")
    @PostMapping("deleteMultiple")
    public DefaultHttpRes deleteList(
            @RequestParam(value = "mbIds", required = true) List<String> mbIds) {
        superAdminService.deleteList(mbIds);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }
}
