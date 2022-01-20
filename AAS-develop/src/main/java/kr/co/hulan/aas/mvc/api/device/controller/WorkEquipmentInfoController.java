package kr.co.hulan.aas.mvc.api.device.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.device.controller.request.CreateWorkEquipmentInfoRequest;
import kr.co.hulan.aas.mvc.api.device.controller.request.ExportWorkEquipmentInfoDataRequest;
import kr.co.hulan.aas.mvc.api.device.controller.request.ListWorkEquipmentInfoRequest;
import kr.co.hulan.aas.mvc.api.device.controller.request.UpdateWorkEquipmentInfoRequest;
import kr.co.hulan.aas.mvc.api.device.controller.response.ListWorkEquipmentInfoResponse;
import kr.co.hulan.aas.mvc.api.device.service.WorkEquipmentInfoService;
import kr.co.hulan.aas.mvc.model.dto.RecruitDto;
import kr.co.hulan.aas.mvc.model.dto.WorkEquipmentInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/equipment")
@Api(tags = "현장 장비 정보 관리")
public class WorkEquipmentInfoController {

    @Autowired
    private WorkEquipmentInfoService workEquipmentInfoService;

    @ApiOperation(value = "현장 장비 정보 검색", notes = "현장 장비 정보 검색 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("search")
    public DefaultHttpRes<ListWorkEquipmentInfoResponse> search(
            @Valid @RequestBody ListWorkEquipmentInfoRequest request) {
        return new DefaultHttpRes<ListWorkEquipmentInfoResponse>(BaseCode.SUCCESS, workEquipmentInfoService.findListPage(request));
    }

    @ApiOperation(value = "현장 장비 정보 Export", notes = "현장 장비 정보 검색 Export 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("export")
    public DefaultHttpRes<List<WorkEquipmentInfoDto>> export(
            @Valid @RequestBody ExportWorkEquipmentInfoDataRequest request) {
        return new DefaultHttpRes<List<WorkEquipmentInfoDto>>(BaseCode.SUCCESS, workEquipmentInfoService.findListByCondition(request.getConditionMap()));
    }

    @ApiOperation(value = "현장 장비 정보 조회", notes = "현장 장비 정보를 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idx", value = "현장 장비 번호", required = true, dataType = "int", paramType = "path")
    })
    @GetMapping("detail/{idx}")
    public DefaultHttpRes<WorkEquipmentInfoDto> detail(
            @PathVariable(value = "idx", required = true) int idx) {
        return new DefaultHttpRes(BaseCode.SUCCESS,  workEquipmentInfoService.findById(idx));
    }


    @ApiOperation(value = "현장 장비 정보 등록", notes = "현장 장비 정보 등록 제공한다.")
    @PostMapping("create")
    public DefaultHttpRes create(
            @Valid @RequestBody CreateWorkEquipmentInfoRequest request) {
        return new DefaultHttpRes(BaseCode.SUCCESS, workEquipmentInfoService.create(request));
    }

    @ApiOperation(value = "현장 장비 정보 수정", notes = "현장 장비 정보 수정 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idx", value = "현장 장비 번호", required = true, dataType = "int", paramType = "path")
    })
    @PostMapping("update/{idx}")
    public DefaultHttpRes update(
            @Valid @RequestBody UpdateWorkEquipmentInfoRequest request,
            @PathVariable(value = "idx", required = true) int idx) {
        workEquipmentInfoService.update(request, idx);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "현장 장비 정보 삭제", notes = "현장 장비 정보 삭제 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idx", value = "현장 장비 번호", required = true, dataType = "int", paramType = "path")
    })
    @PostMapping("delete/{idx}")
    public DefaultHttpRes delete(
            @PathVariable(value = "idx", required = true) int idx) {
        workEquipmentInfoService.delete(idx);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "현장 장비 복수 삭제", notes = "현장 장비 복수 삭제 제공한다.")
    @PostMapping("deleteMultiple")
    public DefaultHttpRes deleteMultiple(
        @RequestParam(value = "idxs", required = true) List<Integer> idxs) {
        workEquipmentInfoService.deleteMultiple(idxs);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }
}
