package kr.co.hulan.aas.mvc.api.board.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.board.controller.request.CreateNoticeBoardRequest;
import kr.co.hulan.aas.mvc.api.board.controller.request.ExportNoticeBoardRequest;
import kr.co.hulan.aas.mvc.api.board.controller.request.ListNoticeBoardRequest;
import kr.co.hulan.aas.mvc.api.board.controller.request.UpdateNoticeBoardRequest;
import kr.co.hulan.aas.mvc.api.board.controller.response.ListNoticeBoardResponse;
import kr.co.hulan.aas.mvc.api.board.dto.NoticeDto;
import kr.co.hulan.aas.mvc.api.board.service.AttachedFileService;
import kr.co.hulan.aas.mvc.api.board.service.NoticeBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/board/notice")
@Api(tags = "근로자 공지사항 관리")
public class NoticeBoardController {

    @Autowired
    private NoticeBoardService noticeBoardService;

    @ApiOperation(value = "근로자 공지사항 리스트", notes = "근로자 공지사항 검색 제공한다.", produces="application/json;charset=UTF-8")
    @PostMapping("search")
    public DefaultHttpRes<ListNoticeBoardResponse> search(
            @Valid @RequestBody ListNoticeBoardRequest request) {
        return new DefaultHttpRes<ListNoticeBoardResponse>(BaseCode.SUCCESS, noticeBoardService.findListPage(request));
    }

    @ApiOperation(value = "근로자 공지사항 데이터 Export", notes = "근로자 공지사항 데이터 Export 제공한다.", produces="application/json;charset=UTF-8")
    @PostMapping("export")
    public DefaultHttpRes<List<NoticeDto>> export(
            @Valid @RequestBody ExportNoticeBoardRequest request) {
        return new DefaultHttpRes<List<NoticeDto>>(BaseCode.SUCCESS, noticeBoardService.findListByCondition(request.getConditionMap()));
    }

    @ApiOperation(value = "근로자 공지사항 조회", notes = "근로자 공지사항 정보를 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wrId", value = "근로자 공지사항 아이디", required = true, dataType = "int", paramType = "path")
    })
    @GetMapping("detail/{wrId}")
    public DefaultHttpRes<NoticeDto> detail(
            @PathVariable(value = "wrId", required = true) int wrId) {
        return new DefaultHttpRes(BaseCode.SUCCESS,  noticeBoardService.findById(wrId));
    }

    @ApiOperation(value = "근로자 공지사항 정보 등록", notes = "근로자 공지사항 정보 등록 제공한다.")
    @PostMapping("create")
    public DefaultHttpRes create(
            @Valid @RequestBody CreateNoticeBoardRequest request) {
        noticeBoardService.create(request);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }


    @ApiOperation(value = "근로자 공지사항 정보 수정", notes = "근로자 공지사항 정보 수정 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wrId", value = "근로자 공지사항 아이디", required = true, dataType = "int", paramType = "path")
    })
    @PostMapping("update/{wrId}")
    public DefaultHttpRes updateWorker(
            @Valid @RequestBody UpdateNoticeBoardRequest request,
            @PathVariable(value = "wrId", required = true) int wrId) {
        noticeBoardService.update(request, wrId);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }


    @ApiOperation(value = "근로자 공지사항 정보 삭제", notes = "근로자 공지사항 정보 삭제 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wrId", value = "근로자 공지사항 아이디", required = true, dataType = "int", paramType = "path")
    })
    @PostMapping("delete/{wrId}")
    public DefaultHttpRes delete(
            @PathVariable(value = "wrId", required = true) int wrId) {
        noticeBoardService.delete(wrId);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "근로자 공지사항 정보 복수 삭제", notes = "근로자 공지사항 정보 복수 삭제 제공한다.")
    @PostMapping("deleteMultiple")
    public DefaultHttpRes deleteMultiple(
            @RequestParam(value = "wrIds", required = true) List<Integer> wrIds) {
        noticeBoardService.deleteMultiple(wrIds);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }


}
