package kr.co.hulan.aas.mvc.api.sensorMgr.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.request.ExportSensorPolicyInfoDataRequest;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.request.ListSensorPolicyInfoRequest;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.request.UpdateSensorPolicyInfoRequest;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.response.ListSensorPolicyInfoResponse;
import kr.co.hulan.aas.mvc.api.sensorMgr.service.SensorPolicyService;
import kr.co.hulan.aas.mvc.model.dto.SensorPolicyInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/sensorMgr/policy")
@Api(tags = "안전센서 정책 관리")
public class SensorPolicyController {

    @Autowired
    private SensorPolicyService sensorPolicyService;

    @ApiOperation(value = "안전센서 정책 리스트", notes = "안전센서 정책 리스트 검색 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("search")
    public DefaultHttpRes<ListSensorPolicyInfoResponse> search(
            @Valid @RequestBody ListSensorPolicyInfoRequest request) {
        return new DefaultHttpRes<ListSensorPolicyInfoResponse>(BaseCode.SUCCESS, sensorPolicyService.findListPage(request));
    }

    @ApiOperation(value = "안전센서 정책 데이터 Export", notes = "안전센서 정책 데이터 Export 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("export")
    public DefaultHttpRes<List<SensorPolicyInfoDto>> export(
            @Valid @RequestBody ExportSensorPolicyInfoDataRequest request) {
        return new DefaultHttpRes<List<SensorPolicyInfoDto>>(BaseCode.SUCCESS, sensorPolicyService.findListByCondition(request.getConditionMap()));
    }

    @ApiOperation(value = "안전센서 정책 조회", notes = "안전센서 정책 정보를 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "spIdx", value = "안전센서 정책 아이디", required = true, dataType = "int", paramType = "path")
    })
    @GetMapping("detail/{spIdx}")
    public DefaultHttpRes<SensorPolicyInfoDto> detail(
            @PathVariable(value = "spIdx", required = true) int spIdx
    ) {
        return new DefaultHttpRes(BaseCode.SUCCESS,  sensorPolicyService.findById(spIdx));
    }

    @ApiOperation(value = "안전센서 정책 정보 수정", notes = "안전센서 정책 정보 수정 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "spIdx", value = "안전센서 정책 아이디", required = true, dataType = "int", paramType = "path")
    })
    @PostMapping("update/{spIdx}")
    public DefaultHttpRes update(
            @Valid @RequestBody UpdateSensorPolicyInfoRequest request,
            @PathVariable(value = "spIdx", required = true) int spIdx) {
        sensorPolicyService.update(request, spIdx);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }



    @ApiOperation(value = "안전센서 정책 정보 Reset", notes = "안전센서 정책 정보 Reset 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "spIdx", value = "안전센서 정책 아이디", required = true, dataType = "int", paramType = "path")
    })
    @PostMapping("Reset/{spIdx}")
    public DefaultHttpRes reset(
            @PathVariable(value = "spIdx", required = true) int spIdx) {
        sensorPolicyService.reset(spIdx);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }


}
