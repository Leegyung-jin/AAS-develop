package kr.co.hulan.aas.mvc.api.hicc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.mvc.api.hicc.controller.request.HiccImosNoticeCreateRequest;
import kr.co.hulan.aas.mvc.api.hicc.controller.request.HiccImosNoticeExportRequest;
import kr.co.hulan.aas.mvc.api.hicc.controller.request.HiccImosNoticePagingRequest;
import kr.co.hulan.aas.mvc.api.hicc.controller.request.HiccImosNoticeUpdateRequest;
import kr.co.hulan.aas.mvc.api.hicc.service.HiccImosNoticeCompService;
import kr.co.hulan.aas.mvc.api.hicc.vo.ImosNoticeVo;
import kr.co.hulan.aas.mvc.model.dto.ImosNoticeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hicc/v2/notice")
@Api(tags = "[IMOC-C 2.0] 현장 공지사항 API")
public class HiccImosNoticeCompController {

  @Autowired
  private HiccImosNoticeCompService hiccImosNoticeCompService;

  @ApiOperation(value = "현장 공지사항 리스트", notes = "현장 공지사항 검색 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping(value="search", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<DefaultPageResult<ImosNoticeDto>> search(
      @Valid @RequestBody HiccImosNoticePagingRequest request) {
    return new DefaultHttpRes<DefaultPageResult<ImosNoticeDto>>(BaseCode.SUCCESS, hiccImosNoticeCompService.findListPage(request));
  }

  @ApiOperation(value = "현장 공지사항 데이터 Export", notes = "현장 공지사항 데이터 Export 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping(value="export",produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<List<ImosNoticeDto>> export(
      @Valid @RequestBody HiccImosNoticeExportRequest request) {
    return new DefaultHttpRes<List<ImosNoticeDto>>(BaseCode.SUCCESS, hiccImosNoticeCompService.findListByCondition(request.getConditionMap()));
  }

  @ApiOperation(value = "현장 공지사항 조회", notes = "현장 공지사항 정보를 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "imntNo", value = "현장 공지사항 번호", required = true, dataType = "long", paramType = "path")
  })
  @GetMapping(value="/{imntNo}", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<ImosNoticeVo> detail(
      @PathVariable(value = "imntNo", required = true) long imntNo) {
    return new DefaultHttpRes(BaseCode.SUCCESS,  hiccImosNoticeCompService.findDetailInfo(imntNo));
  }



  @ApiOperation(value = "현장 공지사항 정보 등록", notes = "현장 공지사항 정보 등록 제공한다.")
  @PostMapping(value="create", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes create(
      @Valid @RequestBody HiccImosNoticeCreateRequest request) {

    return new DefaultHttpRes(BaseCode.SUCCESS, hiccImosNoticeCompService.create(request));
  }


  @ApiOperation(value = "현장 공지사항 정보 수정", notes = "현장 공지사항 정보 수정 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "imntNo", value = "현장 공지사항 번호", required = true, dataType = "long", paramType = "path")
  })
  @PutMapping(value="/{imntNo}", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes updateWorker(
      @Valid @RequestBody HiccImosNoticeUpdateRequest request,
      @PathVariable(value = "imntNo", required = true) long imntNo) {
    hiccImosNoticeCompService.update(imntNo, request);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  @ApiOperation(value = "현장 공지사항 정보 삭제", notes = "현장 공지사항 정보 삭제 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "imntNo", value = "현장 공지사항 번호", required = true, dataType = "long", paramType = "path")
  })
  @DeleteMapping(value="/{imntNo}", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes delete(
      @PathVariable(value = "imntNo", required = true) long imntNo) {
    hiccImosNoticeCompService.delete(imntNo);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  @ApiOperation(value = "현장 공지사항 정보 복수 삭제", notes = "현장 공지사항 정보 복수 삭제 제공한다.")
  @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes deleteMultiple(
      @RequestParam(value = "imntNo", required = true) List<Long> imntNoList) {
    hiccImosNoticeCompService.deleteMultiple(imntNoList);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  @ApiOperation(value = "현장 공지사항 첨부 파일 삭제", notes = "현장 공지사항 첨부 파일 삭제 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "imntNo", value = "현장 공지사항 번호", required = true, dataType = "long", paramType = "path"),
      @ApiImplicitParam(name = "fileNo", value = "현장 공지사항 첨부파일 번호", required = true, dataType = "long", paramType = "path")
  })
  @DeleteMapping(value="/{imntNo}/file/{fileNo}", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes delete(
      @PathVariable(value = "imntNo", required = true) long imntNo,
      @PathVariable(value = "fileNo", required = true) long fileNo
  ) {
    hiccImosNoticeCompService.deleteFile(imntNo, fileNo);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }
}
