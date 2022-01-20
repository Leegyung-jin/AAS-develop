package kr.co.hulan.aas.mvc.api.hicc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.hicc.service.HiccCctvService;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceSummaryVo;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceCctvDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/hicc/v1/component/cctv")
@Api(tags = "Hulan Integrated Control Center CCTV API")
public class HiccCctvCompController {

  @Autowired
  private HiccCctvService hiccCctvService;

  @ApiOperation(value = "CCTV 지원 현장 리스트 조회", notes = "CCTV 지원 현장 리스트 조회", produces="application/json;charset=UTF-8")
  @GetMapping(value="/workplace", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<HiccWorkplaceSummaryVo>> workplaceSupportCctv(){
    return new DefaultHttpRes<List<HiccWorkplaceSummaryVo>>(
        BaseCode.SUCCESS, hiccCctvService.findWorkplaceSupportCctv());
  }

  @ApiOperation(value = "현장 CCTV 리스트 정보", notes = "현장 CCTV 리스트제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping(value="/workplace/{wpId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<List<WorkPlaceCctvDto>> export(
      @PathVariable(value = "wpId", required = true) String wpId
  ) {
    return new DefaultHttpRes<List<WorkPlaceCctvDto>>(BaseCode.SUCCESS, hiccCctvService.findCctvList(wpId));
  }
}
