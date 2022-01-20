package kr.co.hulan.aas.mvc.api.sensorMgr.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.request.*;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.response.ListSensorInfoResponse;
import kr.co.hulan.aas.mvc.api.sensorMgr.service.SensorManageService;
import kr.co.hulan.aas.mvc.model.dto.SensorInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/sensorMgr/manage")
@Api(tags = "안전센서 관리")
public class SensorManageController {

    @Autowired
    private SensorManageService sensorManageService;

    @ApiOperation(value = "안전센서 리스트", notes = "안전센서 리스트 검색 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("search")
    public DefaultHttpRes<ListSensorInfoResponse> search(
            @Valid @RequestBody ListSensorInfoRequest request) {
        return new DefaultHttpRes<ListSensorInfoResponse>(BaseCode.SUCCESS, sensorManageService.findListPage(request));
    }

    @ApiOperation(value = "안전센서 데이터 Export", notes = "안전센서 데이터 Export 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("export")
    public DefaultHttpRes<List<SensorInfoDto>> export(
            @Valid @RequestBody ExportSensorInfoDataRequest request) {
        return new DefaultHttpRes<List<SensorInfoDto>>(BaseCode.SUCCESS, sensorManageService.findListByCondition(request.getConditionMap()));
    }

    @ApiOperation(value = "안전센서 조회", notes = "안전센서 정보를 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "siIdx", value = "안전센서 아이디", required = true, dataType = "int", paramType = "path")
    })
    @GetMapping("detail/{siIdx}")
    public DefaultHttpRes<SensorInfoDto> detail(
            @PathVariable(value = "siIdx", required = true) int siIdx) {
        return new DefaultHttpRes(BaseCode.SUCCESS,  sensorManageService.findById(siIdx));
    }

    @ApiOperation(value = "안전센서 코드 이용가능 유무 조회", notes = "안전센서 코드 이용가능 유무 정보를 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "siCode", value = "안전센서 코드", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "siIdx", value = "센서 아이디", required = false, dataType = "int", paramType = "query")
    })
    @GetMapping("checkSiCode")
    public DefaultHttpRes checkSiCode(
            @RequestParam(value = "wpId", required = true) String wpId,
            @RequestParam(value = "siCode", required = true) String siCode,
            @RequestParam(value = "siIdx", required = false, defaultValue = "0") int siIdx) {
        if( sensorManageService.checkUsableSiCode(wpId, siCode, siIdx) ){
            return new DefaultHttpRes(BaseCode.SUCCESS);
        }
        else {
            return new DefaultHttpRes(BaseCode.ERR_ETC_EXCEPTION, "안전센서 코드가 중복되었습니다.");
        }
    }

    @ApiOperation(value = "안전센서 정보 등록", notes = "안전센서 정보 등록 제공한다.")
    @PostMapping("create")
    public DefaultHttpRes create(
            @Valid @RequestBody CreateSensorInfoRequest request) {
        sensorManageService.create(request);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }



    @ApiOperation(value = "안전센서 정보 수정", notes = "안전센서 정보 수정 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "siIdx", value = "안전센서 아이디", required = true, dataType = "int", paramType = "path")
    })
    @PostMapping("update/{siIdx}")
    public DefaultHttpRes updateWorker(
            @Valid @RequestBody UpdateSensorInfoRequest request,
            @PathVariable(value = "siIdx", required = true) int siIdx) {
        sensorManageService.update(request, siIdx);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }



    @ApiOperation(value = "안전센서 정보 삭제", notes = "안전센서 정보 삭제 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "siIdx", value = "안전센서 아이디", required = true, dataType = "int", paramType = "path")
    })
    @PostMapping("delete/{siIdx}")
    public DefaultHttpRes delete(
            @PathVariable(value = "siIdx", required = true) int siIdx) {
        sensorManageService.delete(siIdx);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "안전센서 정보 복수 삭제", notes = "안전센서 정보 복수 삭제 제공한다.")
    @PostMapping("deleteMultiple")
    public DefaultHttpRes deleteMultiple(
            @RequestParam(value = "siIdxs", required = true) List<Integer> siIdxs) {
        sensorManageService.deleteMultiple(siIdxs);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

}
