package kr.co.hulan.aas.mvc.api.workplace.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.workplace.controller.request.*;
import kr.co.hulan.aas.mvc.api.workplace.controller.response.CreateMultiWorkplaceWorkerResultResponse;
import kr.co.hulan.aas.mvc.api.workplace.controller.response.ListWorkplaceCooperativeCompanyResponse;
import kr.co.hulan.aas.mvc.api.workplace.controller.response.ListWorkplaceWorkerResponse;
import kr.co.hulan.aas.mvc.api.workplace.service.WorkplaceCooperativeCompanyService;
import kr.co.hulan.aas.mvc.api.workplace.service.WorkplaceWorkerService;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceCoopDto;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceWorkerDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/workplace/worker")
@Api(tags = "근로자 현장 편입 정보 관리")
public class WorkplaceWorkerController {

    private Logger logger = LoggerFactory.getLogger(WorkplaceWorkerController.class);

    @Autowired
    private WorkplaceWorkerService workplaceWorkerService;

    @ApiOperation(value = "근로자 현장 편입 정보 리스트", notes = "근로자 현장 편입 정보 검색 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("search")
    public DefaultHttpRes<ListWorkplaceWorkerResponse> search(
            @Valid @RequestBody ListWorkplaceWorkerRequest request) {
        return new DefaultHttpRes<ListWorkplaceWorkerResponse>(BaseCode.SUCCESS, workplaceWorkerService.findListPage(request));
    }


    @ApiOperation(value = "근로자 현장 편입 정보 데이터 Export", notes = "근로자 현장 편입 정보 데이터 Export 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("export")
    public DefaultHttpRes<List<WorkPlaceWorkerDto>> export(
            @Valid @RequestBody ExportWorkplaceWorkerDataRequest request) {
        return new DefaultHttpRes<List<WorkPlaceWorkerDto>>(BaseCode.SUCCESS, workplaceWorkerService.findListByCondition(request.getConditionMap()));
    }

    @ApiOperation(value = "근로자 현장 편입 정보 조회", notes = "근로자 현장 편입 정보를 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wpwId", value = "근로자 현장 편입 아이디", required = true, dataType = "string", paramType = "path")
    })
    @GetMapping("detail/{wpwId}")
    public DefaultHttpRes<WorkPlaceWorkerDto> detail(
            @PathVariable(value = "wpwId", required = true) String wpwId) {
        return new DefaultHttpRes(BaseCode.SUCCESS,  workplaceWorkerService.findById(wpwId));
    }

    @ApiOperation(value = "근로자 현장 편입 정보 등록", notes = "근로자 현장 편입 정보 등록 제공한다.")
    @PostMapping("create")
    public DefaultHttpRes create(
            @Valid @RequestBody CreateWorkplaceWorkerRequest request) {
        workplaceWorkerService.create(request);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "근로자 현장 편입 정보 멀티 등록", notes = "다수의 근로자 현장 편입 정보 등록 제공한다.")
    @PostMapping("createMulti")
    public DefaultHttpRes<CreateMultiWorkplaceWorkerResultResponse> createMulti(
        @Valid @RequestBody CreateMultiWorkplaceWorkerRequest request) {
        ModelMapper mapper = new ModelMapper();

        CreateMultiWorkplaceWorkerResultResponse response = new CreateMultiWorkplaceWorkerResultResponse();
        for( CreateMultiWorkplaceWorkerRequest.WorkerInfo workerInfo : request.getWorkerList()){
            try{
                CreateWorkplaceWorkerRequest copyedRequest = mapper.map(request, CreateWorkplaceWorkerRequest.class);
                mapper.map(workerInfo, copyedRequest);
                WorkPlaceWorkerDto workerDto = workplaceWorkerService.create(copyedRequest);
                if( workerDto != null ){
                    response.addResult(0, "success", workerInfo.getWorkerMbId(), workerDto);
                }
                else {
                    response.addResult(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message(), workerInfo.getWorkerMbId(), null);
                }
            }
            catch(CommonException e){
                response.addResult(e.getReturnCode(), e.getReturnMessage(), workerInfo.getWorkerMbId(), null);
            }
            catch(Exception e){
                logger.error(e.getMessage(), e);
                response.addResult(BaseCode.ERR_ETC_EXCEPTION.code(), "알수 없는 오류가 발생하였습니다.", workerInfo.getWorkerMbId(), null);
            }

        }
        return new DefaultHttpRes(BaseCode.SUCCESS, response);
    }





    @ApiOperation(value = "근로자 현장 편입 정보 수정", notes = "근로자 현장 편입 정보 수정 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wpwId", value = "근로자 현장 편입 아이디", required = true, dataType = "string", paramType = "path")
    })
    @PostMapping("update/{wpwId}")
    public DefaultHttpRes updateWorker(
            @Valid @RequestBody UpdateWorkplaceWorkerRequest request,
            @PathVariable(value = "wpwId", required = true) String wpwId) {
        workplaceWorkerService.update(request, wpwId);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }


    @ApiOperation(value = "근로자 현장 편입 정보 삭제", notes = "근로자 현장 편입 정보 삭제 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wpwId", value = "근로자 현장 편입 아이디", required = true, dataType = "string", paramType = "path")
    })
    @PostMapping("delete/{wpwId}")
    public DefaultHttpRes deleteWorker(
            @PathVariable(value = "wpwId", required = true) String wpwId) {
        workplaceWorkerService.delete(wpwId);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "근로자 현장 편입 정보 복수 삭제", notes = "근로자 현장 편입 정보 복수 삭제 제공한다.")
    @PostMapping("deleteMultiple")
    public DefaultHttpRes deleteWorkers(
            @RequestParam(value = "wpwIds", required = true) List<String> wpwIds) {
        workplaceWorkerService.deleteMultiple(wpwIds);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }
}
