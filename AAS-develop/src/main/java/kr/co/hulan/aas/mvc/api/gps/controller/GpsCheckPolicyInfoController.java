package kr.co.hulan.aas.mvc.api.gps.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.gps.controller.request.CreateGpsCheckPolicyInfoRequest;
import kr.co.hulan.aas.mvc.api.gps.controller.request.ExportGpsCheckPolicyInfoDataRequest;
import kr.co.hulan.aas.mvc.api.gps.controller.request.ListGpsCheckPolicyInfoRequest;
import kr.co.hulan.aas.mvc.api.gps.controller.request.UpdateGpsCheckPolicyInfoRequest;
import kr.co.hulan.aas.mvc.api.gps.controller.response.ListGpsCheckPolicyInfoResponse;
import kr.co.hulan.aas.mvc.api.gps.service.GpsCheckPolicyInfoService;
import kr.co.hulan.aas.mvc.model.dto.GpsCheckPolicyInfoDto;
import kr.co.hulan.aas.mvc.model.dto.RecruitDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/gpsPolicy")
@Api(tags = "현장별 GPS 정책 관리")
public class GpsCheckPolicyInfoController {

    @Autowired
    private GpsCheckPolicyInfoService gpsCheckPolicyInfoService;

    @ApiOperation(value = "현장별 GPS 정책", notes = "현장별 GPS 정책 리스트 검색 제공한다.", produces="application/json;charset=UTF-8")
    @PostMapping("search")
    public DefaultHttpRes<ListGpsCheckPolicyInfoResponse> search(
            @Valid @RequestBody ListGpsCheckPolicyInfoRequest request) {
        return new DefaultHttpRes<ListGpsCheckPolicyInfoResponse>(BaseCode.SUCCESS, gpsCheckPolicyInfoService.findListPage(request));
    }

    @ApiOperation(value = "현장별 GPS 정책 Export", notes = "현장별 GPS 정책 Export 제공한다.", produces="application/json;charset=UTF-8")
    @PostMapping("export")
    public DefaultHttpRes<List<GpsCheckPolicyInfoDto>> export(
            @Valid @RequestBody ExportGpsCheckPolicyInfoDataRequest request) {
        return new DefaultHttpRes<List<GpsCheckPolicyInfoDto>>(BaseCode.SUCCESS, gpsCheckPolicyInfoService.findListByCondition(request.getConditionMap()));
    }

    @ApiOperation(value = "현장별 GPS 정책 조회", notes = "현장별 GPS 정책 정보를 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idx", value = "GPS 정책 아이디", required = true, dataType = "int", paramType = "path")
    })
    @GetMapping("detail/{idx}")
    public DefaultHttpRes<RecruitDto> detail(
            @PathVariable(value = "idx", required = true) int idx) {
        return new DefaultHttpRes(BaseCode.SUCCESS,  gpsCheckPolicyInfoService.findById(idx));
    }


    @ApiOperation(value = "현장별 GPS 정책 등록", notes = "현장별 GPS 정책 등록 제공한다.")
    @PostMapping("create")
    public DefaultHttpRes create(
            @Valid @RequestBody CreateGpsCheckPolicyInfoRequest request) {
        return new DefaultHttpRes(BaseCode.SUCCESS, gpsCheckPolicyInfoService.create(request));
    }

    @ApiOperation(value = "현장별 GPS 정책 수정", notes = "현장별 GPS 정책 수정 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idx", value = "GPS 정책 아이디", required = true, dataType = "int", paramType = "path")
    })
    @PostMapping("update/{idx}")
    public DefaultHttpRes update(
            @Valid @RequestBody UpdateGpsCheckPolicyInfoRequest request,
            @PathVariable(value = "idx", required = true) int idx) {
        gpsCheckPolicyInfoService.update(request, idx);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "현장별 GPS 정책 삭제", notes = "현장별 GPS 정책 삭제 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idx", value = "GPS 정책 아이디", required = true, dataType = "int", paramType = "path")
    })
    @PostMapping("delete/{idx}")
    public DefaultHttpRes delete(
            @PathVariable(value = "idx", required = true) int idx) {
        gpsCheckPolicyInfoService.delete(idx);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "현장별 GPS 정책 복수 삭제", notes = "현장별 GPS 정책 복수 삭제 제공한다.")
    @PostMapping("deleteMultiple")
    public DefaultHttpRes deleteMultiple(
        @RequestParam(value = "idxs", required = true) List<Integer> idxs) {
        gpsCheckPolicyInfoService.deleteMultiple(idxs);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }
}
