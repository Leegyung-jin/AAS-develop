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
import kr.co.hulan.aas.mvc.api.hicc.controller.request.RiskAccessmentInsepctPagingRequest;
import kr.co.hulan.aas.mvc.api.hicc.controller.request.RiskAccessmentInspectExportRequest;
import kr.co.hulan.aas.mvc.api.hicc.controller.response.HiccRiskAccessmentStateResponse;
import kr.co.hulan.aas.mvc.api.hicc.service.HiccRiskAccessmentService;
import kr.co.hulan.aas.mvc.api.hicc.vo.risk.HiccRiskAccessmentInspectDetailVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.risk.HiccRiskAccessmentInspectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hicc/v1/risk/accessment")
@Api(tags = "Hulan Integrated Control Center 위험성 평가 API")
public class RiskAccessmentController {

  @Autowired
  private HiccRiskAccessmentService hiccRiskAccessmentService;

  @ApiOperation(value = "위험성 평가 현황 조회", notes = "위험성 평가 현황 제공한다.", produces="application/json;charset=UTF-8")
  @GetMapping(value="/state", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<HiccRiskAccessmentStateResponse> state(){
    return new DefaultHttpRes<HiccRiskAccessmentStateResponse>(
        BaseCode.SUCCESS, hiccRiskAccessmentService.findHiccRiskAccessmentState());
  }

  @ApiOperation(value = "위험성 평가 공종 리스트 조회", notes = "위험성 평가 공종 리스트 제공한다.", produces="application/json;charset=UTF-8")
  @GetMapping(value="/section", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<String>> section(){
    return new DefaultHttpRes<List<String>>(
        BaseCode.SUCCESS, hiccRiskAccessmentService.findSectionList());
  }

  @ApiOperation(value = "위험성 평가 리스트(페이징) 조회", notes = "위험성 평가 리스트(페이징) 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping(value="/search", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<DefaultPageResult<HiccRiskAccessmentInspectVo>> search(
      @Valid @RequestBody RiskAccessmentInsepctPagingRequest request
  ){
    return new DefaultHttpRes<DefaultPageResult<HiccRiskAccessmentInspectVo>>(
        BaseCode.SUCCESS, hiccRiskAccessmentService.findInspectPagingList(request));
  }

  @ApiOperation(value = "위험성 평가 데이터 export 요청", notes = "위험성 평가 데이터 export 요청", produces="application/json;charset=UTF-8")
  @PostMapping(value="/export", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<HiccRiskAccessmentInspectVo>> export(
      @Valid @RequestBody RiskAccessmentInspectExportRequest request
  ){
    return new DefaultHttpRes<List<HiccRiskAccessmentInspectVo>>(
        BaseCode.SUCCESS, hiccRiskAccessmentService.findInspectList(request.getConditionMap()));
  }


  @ApiOperation(value = "위험성 평가 상세 조회", notes = "위험성 평가 상세 조회", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "rai_no", value = "위험성 평가 번호", required = true, dataType = "long", paramType = "path")
  })
  @GetMapping(value="/{rai_no}", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<HiccRiskAccessmentInspectDetailVo> detail(
      @PathVariable(value = "rai_no", required = true) long raiNo
  ){
    return new DefaultHttpRes<HiccRiskAccessmentInspectDetailVo>(
        BaseCode.SUCCESS, hiccRiskAccessmentService.findInspectInfo(raiNo));
  }
}
