package kr.co.hulan.aas.mvc.api.openapi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.infra.airkorea.station.dto.NearStationItem;
import kr.co.hulan.aas.mvc.api.openapi.service.AirkoreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/openapi/airkorea")
@Api(tags = "Airkorea 연동")
public class AirkoreaController {

  @Autowired
  private AirkoreaService airkoreaService;

  @ApiOperation(value = "근접 측정소 조회", notes = "근접 측정소 조회 검색 제공한다.", consumes="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "sigunguName", value = "법정시군구명", required = true, dataType = "string", paramType = "query"),
      @ApiImplicitParam(name = "dongName", value = "법정동명", required = true, dataType = "string", paramType = "query")
  })
  @GetMapping(value="/nearStation")
  public DefaultHttpRes<List<NearStationItem>> search(
      @RequestParam String sigunguName, @RequestParam String dongName) {
    return new DefaultHttpRes<List<NearStationItem>>(BaseCode.SUCCESS, airkoreaService.findNearStationList(sigunguName, dongName));
  }
}
