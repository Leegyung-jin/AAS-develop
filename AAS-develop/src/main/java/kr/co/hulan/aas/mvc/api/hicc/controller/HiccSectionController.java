package kr.co.hulan.aas.mvc.api.hicc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.hicc.controller.request.CooperativeAttendanceStatExportRequest;
import kr.co.hulan.aas.mvc.api.hicc.controller.request.SectionExportRequest;
import kr.co.hulan.aas.mvc.api.hicc.service.HiccSectionService;
import kr.co.hulan.aas.mvc.api.hicc.vo.HiccSectionVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.coop.CooperativeAttendanceStatVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hicc/v1/section")
@Api(tags = "Hulan Integrated Control Center 공종 데이터 API")
public class HiccSectionController {

  @Autowired
  private HiccSectionService hiccSectionService;

  @ApiOperation(value = "공종 데이터 Export", notes = "공종 데이터 Export 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping(value="/export", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<HiccSectionVo>> sectionSearch(
      @Valid @RequestBody SectionExportRequest request
  ){
    return new DefaultHttpRes<List<HiccSectionVo>>(
        BaseCode.SUCCESS, hiccSectionService.findSectionList(request.getConditionMap()));
  }

}
