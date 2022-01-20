package kr.co.hulan.aas.mvc.api.jobMgr.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.jobMgr.controller.request.CreateRecruitApplyMultiRequest;
import kr.co.hulan.aas.mvc.api.jobMgr.controller.request.CreateRecruitApplyMultiResponse;
import kr.co.hulan.aas.mvc.api.jobMgr.controller.request.CreateRecruitApplyRequest;
import kr.co.hulan.aas.mvc.api.jobMgr.controller.request.ExportRecruitApplyDataRequest;
import kr.co.hulan.aas.mvc.api.jobMgr.controller.request.ListRecruitApplyRequest;
import kr.co.hulan.aas.mvc.api.jobMgr.controller.request.UpdateRecruitApplyStatusRequest;
import kr.co.hulan.aas.mvc.api.jobMgr.controller.response.ListRecruitApplyResponse;
import kr.co.hulan.aas.mvc.api.jobMgr.service.RecruitApplyService;
import kr.co.hulan.aas.mvc.api.workplace.controller.request.CreateMultiWorkplaceWorkerRequest;
import kr.co.hulan.aas.mvc.api.workplace.controller.request.CreateWorkplaceWorkerRequest;
import kr.co.hulan.aas.mvc.api.workplace.controller.response.CreateMultiWorkplaceWorkerResultResponse;
import kr.co.hulan.aas.mvc.model.dto.RecruitApplyDto;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceWorkerDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/jobMgr/recruitApply")
@Api(tags = "구직 관리")
public class RecruitApplyController {

    private Logger logger = LoggerFactory.getLogger(RecruitApplyController.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RecruitApplyService recruitApplyService;

    @ApiOperation(value = "구직 리스트", notes = "구직 리스트 검색 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("search")
    public DefaultHttpRes<ListRecruitApplyResponse> search(
            @Valid @RequestBody ListRecruitApplyRequest request) {
        return new DefaultHttpRes<ListRecruitApplyResponse>(BaseCode.SUCCESS, recruitApplyService.findListPage(request));
    }

    @ApiOperation(value = "구직 데이터 Export", notes = "구직 데이터 Export 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("export")
    public DefaultHttpRes<List<RecruitApplyDto>> export(
            @Valid @RequestBody ExportRecruitApplyDataRequest request) {
        return new DefaultHttpRes<List<RecruitApplyDto>>(BaseCode.SUCCESS, recruitApplyService.findListByCondition(request.getConditionMap()));
    }

    @ApiOperation(value = "구직 조회", notes = "구직 정보를 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "raIdx", value = "구직 아이디", required = true, dataType = "int", paramType = "path")
    })
    @GetMapping("detail/{raIdx}")
    public DefaultHttpRes<RecruitApplyDto> detail(
            @PathVariable(value = "raIdx", required = true) int raIdx) {
        return new DefaultHttpRes(BaseCode.SUCCESS,  recruitApplyService.findById(raIdx));
    }

    @ApiOperation(value = "구직 정보 등록", notes = "구직 정보 등록 제공한다.")
    @PostMapping("create")
    public DefaultHttpRes<Integer> create(
            @Valid @RequestBody CreateRecruitApplyRequest request) {
        return new DefaultHttpRes(BaseCode.SUCCESS, recruitApplyService.create(request));
    }

    @ApiOperation(value = "구직 정보 등록", notes = "구직 정보 등록 제공한다.")
    @PostMapping("createMulti")
    public DefaultHttpRes<List<CreateRecruitApplyMultiResponse>> createMulti(
        @Valid @RequestBody CreateRecruitApplyMultiRequest request) {
        List<CreateRecruitApplyMultiResponse> response = new ArrayList<CreateRecruitApplyMultiResponse>();
        for( CreateRecruitApplyRequest requestSingle : request.getWorkerList()){
            try{
                Integer recruitApplyIdx = recruitApplyService.create(requestSingle);
                if( recruitApplyIdx != null ){
                    response.add(new CreateRecruitApplyMultiResponse(0, "success", requestSingle));
                }
                else {
                    response.add(new CreateRecruitApplyMultiResponse(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message(), requestSingle));
                }
            }
            catch(CommonException e){
                response.add(new CreateRecruitApplyMultiResponse(e.getReturnCode(), e.getReturnMessage(), requestSingle));
            }
            catch(Exception e){
                logger.error(e.getMessage(), e);
                response.add(new CreateRecruitApplyMultiResponse(BaseCode.ERR_ETC_EXCEPTION.code(), "알수 없는 오류가 발생하였습니다.", requestSingle));
            }

        }
        return new DefaultHttpRes(BaseCode.SUCCESS, response);
    }

    @ApiOperation(value = "구직 신청(심사요청)", notes = "구직 신청(심사요청) 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "raIdx", value = "구직 아이디", required = true, dataType = "int", paramType = "path")
    })
    @PostMapping("ask/{raIdx}")
    public DefaultHttpRes ask(
            @PathVariable(value = "raIdx", required = true) int raIdx) {
        recruitApplyService.askRecruiteApply(raIdx);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "현장 편입 요청", notes = "현장 편입 요청 처리 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "raIdx", value = "구직 아이디", required = true, dataType = "int", paramType = "path")
    })
    @PostMapping("transfer/{raIdx}")
    public DefaultHttpRes transfer(
            @PathVariable(value = "raIdx", required = true) int raIdx) {
        recruitApplyService.transferRecruiteApply(raIdx);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "구직 정보 삭제", notes = "구직 정보 삭제 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "raIdx", value = "구직 아이디", required = true, dataType = "int", paramType = "path")
    })
    @PostMapping("delete/{raIdx}")
    public DefaultHttpRes delete(
            @PathVariable(value = "raIdx", required = true) int raIdx) {
        recruitApplyService.delete(raIdx);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "구직 정보 복수 삭제", notes = "구직 정보 복수 삭제 제공한다.")
    @PostMapping("deleteMultiple")
    public DefaultHttpRes deleteMultiple(
            @RequestParam(value = "raIdxs", required = true) List<Integer> raIdxs) {
        recruitApplyService.deleteMultiple(raIdxs);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }


}
