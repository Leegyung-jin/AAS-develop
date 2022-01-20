package kr.co.hulan.aas.mvc.api.openapi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.infra.vworld.VworldClient;
import kr.co.hulan.aas.infra.vworld.response.Vworld;
import kr.co.hulan.aas.infra.vworld.response.Vworld_point;
import kr.co.hulan.aas.mvc.api.gps.controller.request.ListGpsCheckPolicyInfoRequest;
import kr.co.hulan.aas.mvc.api.gps.controller.response.GpsLocation;
import kr.co.hulan.aas.mvc.api.gps.controller.response.ListGpsCheckPolicyInfoResponse;
import kr.co.hulan.aas.mvc.api.gps.service.GpsCheckPolicyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/openapi/gps")
@Api(tags = "GPS 관련 외부 API 연동")
public class GpsUtilsController {

  @Autowired
  private VworldClient vworldClient;

  @ApiOperation(value = "주소기반 GPS 찾기", notes = "시도명과 시군구명을 이용하여 GPS 좌표 찾기 제공", produces=MediaType.APPLICATION_JSON_VALUE)
  @GetMapping(value="convertAddress", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<GpsLocation> convertFromAddress(
      @RequestParam(value = "sido", required = true) String sido,
      @RequestParam(value = "sigungu", required = true) String sigungu
  ) {
    Vworld vworld = vworldClient.convertAddressToGps(sido+" "+sigungu);
    if( vworld != null && vworld.getResponse() != null && vworld.getResponse().isOk()){
      Vworld_point vpoint = vworld.getResponse().getResult().getPoint();
      double longi = Double.parseDouble(vpoint.getX());
      double latit = Double.parseDouble(vpoint.getY());
      return new DefaultHttpRes<GpsLocation>(BaseCode.SUCCESS, new GpsLocation(latit, longi));
    }
    throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "해당 주소로 GPS 좌표를 찾을 수 없습니다.");
  }
}
