package kr.co.hulan.aas.mvc.api.board.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.board.controller.request.CreateContentBoardRequest;
import kr.co.hulan.aas.mvc.api.board.controller.request.ExportContentBoardRequest;
import kr.co.hulan.aas.mvc.api.board.controller.request.ListContentBoardRequest;
import kr.co.hulan.aas.mvc.api.board.controller.request.UpdateContentBoardRequest;
import kr.co.hulan.aas.mvc.api.board.controller.response.ListContentBoardResponse;
import kr.co.hulan.aas.mvc.api.board.service.ContentBoardService;
import kr.co.hulan.aas.mvc.model.dto.G5ContentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/board/content")
@Api(tags = "내용 관리")
public class ContentBoardController {

    @Autowired
    private ContentBoardService contentBoardService;

    @ApiOperation(value = "내용 게시판 리스트", notes = "내용 게시판 검색 제공한다.", produces="application/json;charset=UTF-8")
    @PostMapping("search")
    public DefaultHttpRes<ListContentBoardResponse> search(
            @Valid @RequestBody ListContentBoardRequest request) {
        return new DefaultHttpRes<ListContentBoardResponse>(BaseCode.SUCCESS, contentBoardService.findListPage(request));
    }

    @ApiOperation(value = "내용 게시판 데이터 Export", notes = "내용 게시판 데이터 Export 제공한다.", produces="application/json;charset=UTF-8")
    @PostMapping("export")
    public DefaultHttpRes<List<G5ContentDto>> export(
            @Valid @RequestBody ExportContentBoardRequest request) {
        return new DefaultHttpRes<List<G5ContentDto>>(BaseCode.SUCCESS, contentBoardService.findListByCondition(request.getConditionMap()));
    }

    @ApiOperation(value = "내용 게시판 조회", notes = "내용 게시판 정보를 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "coId", value = "내용 게시판 아이디", required = true, dataType = "string", paramType = "path")
    })
    @GetMapping("detail/{coId}")
    public DefaultHttpRes<G5ContentDto> detail(
            @PathVariable(value = "coId", required = true) String coId) {
        return new DefaultHttpRes(BaseCode.SUCCESS,  contentBoardService.findById(coId));
    }

    @ApiOperation(value = "내용 게시판 정보 등록", notes = "내용 게시판 정보 등록 제공한다.")
    @PostMapping("create")
    public DefaultHttpRes create(
            @Valid @RequestBody CreateContentBoardRequest request) {
        contentBoardService.create(request);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }


    @ApiOperation(value = "내용 게시판 정보 수정", notes = "내용 게시판 정보 수정 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "coId", value = "내용 게시판 아이디", required = true, dataType = "string", paramType = "path")
    })
    @PostMapping("update/{coId}")
    public DefaultHttpRes updateWorker(
            @Valid @RequestBody UpdateContentBoardRequest request,
            @PathVariable(value = "coId", required = true) String coId) {
        contentBoardService.update(request, coId);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }


    @ApiOperation(value = "내용 게시판 정보 삭제", notes = "내용 게시판 정보 삭제 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "coId", value = "내용 게시판 아이디", required = true, dataType = "string", paramType = "path")
    })
    @PostMapping("delete/{coId}")
    public DefaultHttpRes delete(
            @PathVariable(value = "coId", required = true) String coId) {
        contentBoardService.delete(coId);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "내용 게시판 정보 복수 삭제", notes = "내용 게시판 정보 복수 삭제 제공한다.")
    @PostMapping("deleteMultiple")
    public DefaultHttpRes deleteMultiple(
            @RequestParam(value = "coIds", required = true) List<String> coIds) {
        contentBoardService.deleteMultiple(coIds);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }
}
