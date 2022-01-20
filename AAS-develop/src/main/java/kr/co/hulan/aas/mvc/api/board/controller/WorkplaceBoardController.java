package kr.co.hulan.aas.mvc.api.board.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.board.controller.request.*;
import kr.co.hulan.aas.mvc.api.board.controller.response.ListWorkplaceBoardResponse;
import kr.co.hulan.aas.mvc.api.board.dto.WpBoardDto;
import kr.co.hulan.aas.mvc.api.board.service.WorkplaceBoardService;
import kr.co.hulan.aas.mvc.model.dto.G5WriteWpboardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/board/workplace")
@Api(tags = "현장 게시판 관리")
public class WorkplaceBoardController {

    @Autowired
    private WorkplaceBoardService workplaceBoardService;

    @ApiOperation(value = "현장 게시판 리스트", notes = "현장 게시판 검색 제공한다.", produces="application/json;charset=UTF-8")
    @PostMapping("search")
    public DefaultHttpRes<ListWorkplaceBoardResponse> search(
            @Valid @RequestBody ListWorkplaceBoardRequest request) {
        return new DefaultHttpRes<ListWorkplaceBoardResponse>(BaseCode.SUCCESS, workplaceBoardService.findListPage(request));
    }

    @ApiOperation(value = "현장 게시판 데이터 Export", notes = "현장 게시판 데이터 Export 제공한다.", produces="application/json;charset=UTF-8")
    @PostMapping("export")
    public DefaultHttpRes<List<WpBoardDto>> export(
            @Valid @RequestBody ExportWorkplaceBoardRequest request) {
        return new DefaultHttpRes<List<WpBoardDto>>(BaseCode.SUCCESS, workplaceBoardService.findListByCondition(request.getConditionMap()));
    }

    @ApiOperation(value = "현장 게시판 조회", notes = "현장 게시판 정보를 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wrId", value = "현장 게시판 아이디", required = true, dataType = "int", paramType = "path")
    })
    @GetMapping("detail/{wrId}")
    public DefaultHttpRes<G5WriteWpboardDto> detail(
            @PathVariable(value = "wrId", required = true) int wrId) {
        return new DefaultHttpRes(BaseCode.SUCCESS,  workplaceBoardService.findById(wrId));
    }

    @ApiOperation(value = "현장 게시판 정보 등록", notes = "현장 게시판 정보 등록 제공한다.")
    @PostMapping("create")
    public DefaultHttpRes create(
            @Valid @RequestBody CreateWorkplaceBoardRequest request) {
        workplaceBoardService.create(request);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }


    @ApiOperation(value = "현장 게시판 정보 수정", notes = "현장 게시판 정보 수정 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wrId", value = "현장 게시판 아이디", required = true, dataType = "int", paramType = "path")
    })
    @PostMapping("update/{wrId}")
    public DefaultHttpRes update(
            @Valid @RequestBody UpdateWorkplaceBoardRequest request,
            @PathVariable(value = "wrId", required = true) int wrId) {
        workplaceBoardService.update(request, wrId);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "현장 게시판 요청에 대한 처리 ( 조치 처리, 승인/미승인 )", notes = "현장 게시판 요청에 대한 처리 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wrId", value = "현장 게시판 아이디", required = true, dataType = "int", paramType = "path")
    })
    @PostMapping("reply/{wrId}")
    public DefaultHttpRes reply(
            @Valid @RequestBody UpdateActionWorkplaceBoardRequest request,
            @PathVariable(value = "wrId", required = true) int wrId) {
        workplaceBoardService.updateAction(request, wrId);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }


    @ApiOperation(value = "현장 게시판 정보 삭제", notes = "현장 게시판 정보 삭제 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wrId", value = "현장 게시판 아이디", required = true, dataType = "int", paramType = "path")
    })
    @PostMapping("delete/{wrId}")
    public DefaultHttpRes delete(
            @PathVariable(value = "wrId", required = true) int wrId) {
        workplaceBoardService.delete(wrId);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "현장 게시판 정보 복수 삭제", notes = "현장 게시판 정보 복수 삭제 제공한다.")
    @PostMapping("deleteMultiple")
    public DefaultHttpRes deleteMultiple(
            @RequestParam(value = "wrIds", required = true) List<Integer> wrIds) {
        workplaceBoardService.deleteMultiple(wrIds);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

}
