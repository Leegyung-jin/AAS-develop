package kr.co.hulan.aas.mvc.api.region.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.region.service.RegionService;
import kr.co.hulan.aas.mvc.api.region.vo.RegionSidoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/region/v1/")
@Api(tags = "지역 API")
public class RegionController {

  @Autowired
  private RegionService regionService;

  @ApiOperation(value = "전국 시도 코드 리스트 조회", notes = "전국 시도 코드 리스트 제공한다.", produces="application/json;charset=UTF-8")
  @GetMapping(produces="application/json;charset=UTF-8")
  public DefaultHttpRes<List<RegionSidoVo>> sidoList(){
    return new DefaultHttpRes<List<RegionSidoVo>>(
        BaseCode.SUCCESS, regionService.findSidoList());
  }
}
