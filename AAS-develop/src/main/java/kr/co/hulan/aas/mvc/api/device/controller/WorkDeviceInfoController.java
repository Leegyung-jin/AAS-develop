package kr.co.hulan.aas.mvc.api.device.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.device.controller.request.CreateWorkDeviceInfoRequest;
import kr.co.hulan.aas.mvc.api.device.controller.request.ExportWorkDeviceInfoDataRequest;
import kr.co.hulan.aas.mvc.api.device.controller.request.ListWorkDeviceInfoRequest;
import kr.co.hulan.aas.mvc.api.device.controller.request.UpdateWorkDeviceInfoRequest;
import kr.co.hulan.aas.mvc.api.device.controller.response.ListWorkDeviceInfoResponse;
import kr.co.hulan.aas.mvc.api.device.service.WorkDeviceInfoService;
import kr.co.hulan.aas.mvc.model.dto.WorkDeviceInfoDto;
import kr.co.hulan.aas.mvc.model.dto.RecruitDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/device")
@Api(tags = "현장 디바이스 정보 관리")
public class WorkDeviceInfoController {

    @Autowired
    private WorkDeviceInfoService workDeviceInfoService;

    @ApiOperation(value = "현장 디바이스 정보 검색", notes = "현장 디바이스 정보 검색 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("search")
    public DefaultHttpRes<ListWorkDeviceInfoResponse> search(
            @Valid @RequestBody ListWorkDeviceInfoRequest request) {
        return new DefaultHttpRes<ListWorkDeviceInfoResponse>(BaseCode.SUCCESS, workDeviceInfoService.findListPage(request));
    }

    @ApiOperation(value = "현장 디바이스 정보 Export", notes = "현장 디바이스 정보 검색 Export 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("export")
    public DefaultHttpRes<List<WorkDeviceInfoDto>> export(
            @Valid @RequestBody ExportWorkDeviceInfoDataRequest request) {
        return new DefaultHttpRes<List<WorkDeviceInfoDto>>(BaseCode.SUCCESS, workDeviceInfoService.findListByCondition(request.getConditionMap()));
    }

    @ApiOperation(value = "현장 디바이스 정보 조회", notes = "현장 디바이스 정보를 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idx", value = "현장 디바이스 번호", required = true, dataType = "int", paramType = "path")
    })
    @GetMapping("detail/{idx}")
    public DefaultHttpRes<WorkDeviceInfoDto> detail(
            @PathVariable(value = "idx", required = true) int idx) {
        return new DefaultHttpRes<WorkDeviceInfoDto>(BaseCode.SUCCESS,  workDeviceInfoService.findById(idx));
    }


    @ApiOperation(value = "현장 디바이스 정보 등록", notes = "현장 디바이스 정보 등록 제공한다.")
    @PostMapping("create")
    public DefaultHttpRes create(
            @Valid @RequestBody CreateWorkDeviceInfoRequest request) {
        return new DefaultHttpRes(BaseCode.SUCCESS, workDeviceInfoService.create(request));
    }

    @ApiOperation(value = "현장 디바이스 정보 수정", notes = "현장 디바이스 정보 수정 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idx", value = "현장 디바이스 번호", required = true, dataType = "int", paramType = "path")
    })
    @PostMapping("update/{idx}")
    public DefaultHttpRes update(
            @Valid @RequestBody UpdateWorkDeviceInfoRequest request,
            @PathVariable(value = "idx", required = true) int idx) {
        workDeviceInfoService.update(request, idx);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "현장 디바이스 정보 삭제", notes = "현장 디바이스 정보 삭제 제공한다.")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "idx", value = "현장 디바이스 번호", required = true, dataType = "int", paramType = "path")
    })
    @PostMapping("delete/{idx}")
    public DefaultHttpRes delete(
            @PathVariable(value = "idx", required = true) int idx) {
        workDeviceInfoService.delete(idx);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "현장 디바이스 복수 삭제", notes = "현장 디바이스 복수 삭제 제공한다.")
    @PostMapping("deleteMultiple")
    public DefaultHttpRes deleteMultiple(
        @RequestParam(value = "idxs", required = true) List<Integer> idxs) {
        workDeviceInfoService.deleteMultiple(idxs);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }
}
