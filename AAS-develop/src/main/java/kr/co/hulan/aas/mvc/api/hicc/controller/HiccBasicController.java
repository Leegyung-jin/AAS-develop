package kr.co.hulan.aas.mvc.api.hicc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.hicc.controller.response.HiccBaseMainResponse;
import kr.co.hulan.aas.mvc.api.hicc.service.HiccBaseService;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.WorkplaceSupportMonitoringResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hicc/v1/base")
@Api(tags = "Hulan Integrated Control Center 기본 API")
public class HiccBasicController {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private HiccBaseService hiccBaseService;

  @ApiOperation(value = "메인 정보( UI 구성정보 )", notes = "메인 정보( UI 구성정보 )를 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "deploy_page", value = "구성 페이지 정보", required = false, defaultValue = "1", dataType = "int", paramType = "query")
  })
  @GetMapping(value="/main", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<HiccBaseMainResponse> main(
      @RequestParam(value="deploy_page", defaultValue ="1", required = false) int deployPage
  ){
    return new DefaultHttpRes<HiccBaseMainResponse>(BaseCode.SUCCESS, hiccBaseService.findBaseMain(deployPage));
  }

}
