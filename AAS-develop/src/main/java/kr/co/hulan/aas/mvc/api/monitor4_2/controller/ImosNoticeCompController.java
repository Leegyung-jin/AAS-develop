package kr.co.hulan.aas.mvc.api.monitor4_2.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.mvc.api.hicc.vo.ImosNoticeVo;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.request.ImosNoticeExportRequest;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.request.ImosNoticePagingRequest;
import kr.co.hulan.aas.mvc.api.monitor4_2.service.ImosNoticeCompService;
import kr.co.hulan.aas.mvc.model.dto.ImosNoticeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/monitor/4.2/workplace/{wpId}/notice")
@Api(tags = "[4.2]IMOS 현장 공지사항(From 통합관제) API")
public class ImosNoticeCompController {

  @Autowired
  private ImosNoticeCompService imosNoticeCompService;

  @ApiOperation(value = "현장 공지사항 리스트", notes = "현장 공지사항 검색 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping(value="search", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<DefaultPageResult<ImosNoticeDto>> search(
      @Valid @RequestBody ImosNoticePagingRequest request) {
    return new DefaultHttpRes<DefaultPageResult<ImosNoticeDto>>(BaseCode.SUCCESS, imosNoticeCompService
        .findListPage(request));
  }

  @ApiOperation(value = "현장 공지사항 데이터 Export", notes = "현장 공지사항 데이터 Export 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping(value="export",produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<List<ImosNoticeDto>> export(
      @Valid @RequestBody ImosNoticeExportRequest request) {
    return new DefaultHttpRes<List<ImosNoticeDto>>(BaseCode.SUCCESS, imosNoticeCompService.findListByCondition(request.getConditionMap()));
  }

  @ApiOperation(value = "현장 공지사항 조회", notes = "현장 공지사항 정보를 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "imntNo", value = "현장 공지사항 번호", required = true, dataType = "long", paramType = "path")
  })
  @GetMapping(value="/{imntNo}", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<ImosNoticeVo> detail(
      @PathVariable(value = "imntNo", required = true) long imntNo) {
    return new DefaultHttpRes(BaseCode.SUCCESS,  imosNoticeCompService.findDetailInfo(imntNo));
  }

}
