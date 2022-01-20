package kr.co.hulan.aas.mvc.api.jobMgr.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.jobMgr.controller.request.CreateRecruitRequest;
import kr.co.hulan.aas.mvc.api.jobMgr.controller.request.ExportRecruitDataRequest;
import kr.co.hulan.aas.mvc.api.jobMgr.controller.request.ListRecruitRequest;
import kr.co.hulan.aas.mvc.api.jobMgr.controller.request.UpdateRecruitRequest;
import kr.co.hulan.aas.mvc.api.jobMgr.controller.response.ListRecruitResponse;
import kr.co.hulan.aas.mvc.api.jobMgr.service.RecruitService;
import kr.co.hulan.aas.mvc.model.dto.RecruitDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/jobMgr/recruit")
@Api(tags = "구인 관리")
public class RecruitController {

    @Autowired
    private RecruitService recruitService;

    @ApiOperation(value = "구인 리스트", notes = "구인 리스트 검색 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("search")
    public DefaultHttpRes<ListRecruitResponse> search(
            @Valid @RequestBody ListRecruitRequest request) {
        return new DefaultHttpRes<ListRecruitResponse>(BaseCode.SUCCESS, recruitService.findListPage(request));
    }

    @ApiOperation(value = "구인 데이터 Export", notes = "구인 데이터 Export 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("export")
    public DefaultHttpRes<List<RecruitDto>> export(
            @Valid @RequestBody ExportRecruitDataRequest request) {
        return new DefaultHttpRes<List<RecruitDto>>(BaseCode.SUCCESS, recruitService.findListByCondition(request.getConditionMap()));
    }

    @ApiOperation(value = "구인 조회", notes = "구인 정보를 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rcIdx", value = "구인 아이디", required = true, dataType = "int", paramType = "path")
    })
    @GetMapping("detail/{rcIdx}")
    public DefaultHttpRes<RecruitDto> detail(
            @PathVariable(value = "rcIdx", required = true) int rcIdx) {
        return new DefaultHttpRes(BaseCode.SUCCESS,  recruitService.findById(rcIdx));
    }


    @ApiOperation(value = "구인 정보 등록", notes = "구인 정보 등록 제공한다.")
    @PostMapping("create")
    public DefaultHttpRes create(
            @Valid @RequestBody CreateRecruitRequest request) {
        recruitService.create(request);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }



    @ApiOperation(value = "구인 정보 수정", notes = "구인 정보 수정 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rcIdx", value = "구인 아이디", required = true, dataType = "int", paramType = "path")
    })
    @PostMapping("update/{rcIdx}")
    public DefaultHttpRes update(
            @Valid @RequestBody UpdateRecruitRequest request,
            @PathVariable(value = "rcIdx", required = true) int rcIdx) {
        recruitService.update(request, rcIdx);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "구인 정보 올리기", notes = "구인 정보 올리기 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rcIdx", value = "구인 아이디", required = true, dataType = "int", paramType = "path")
    })
    @PostMapping("updateExpose/{rcIdx}")
    public DefaultHttpRes updateExpose(
            @PathVariable(value = "rcIdx", required = true) int rcIdx) {
        recruitService.updateExpose(rcIdx);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }



    @ApiOperation(value = "구인 정보 삭제", notes = "구인 정보 삭제 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rcIdx", value = "구인 아이디", required = true, dataType = "int", paramType = "path")
    })
    @PostMapping("delete/{rcIdx}")
    public DefaultHttpRes delete(
            @PathVariable(value = "rcIdx", required = true) int rcIdx) {
        recruitService.delete(rcIdx);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "구인 정보 복수 삭제", notes = "구인 정보 복수 삭제 제공한다.")
    @PostMapping("deleteMultiple")
    public DefaultHttpRes deleteMultiple(
            @RequestParam(value = "rcIdxs", required = true) List<Integer> rcIdxs) {
        recruitService.deleteMultiple(rcIdxs);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

}
