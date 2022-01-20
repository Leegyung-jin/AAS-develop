package kr.co.hulan.aas.mvc.api.openapi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.infra.weather.WeatherClient;
import kr.co.hulan.aas.infra.weather.WeatherGridXY;
import kr.co.hulan.aas.infra.weather.WeatherProperties;
import kr.co.hulan.aas.infra.weather.request.WeatherForecastRequest;
import kr.co.hulan.aas.infra.weather.request.WeatherGridXYRequest;
import kr.co.hulan.aas.infra.weather.response.KmaResponse;
import kr.co.hulan.aas.infra.weather.response.KmaWeatherLandFcstItem;
import kr.co.hulan.aas.infra.weather.response.KmaWeatherMidLandFcstItem;
import kr.co.hulan.aas.infra.weather.response.KmaWeatherMidTaItem;
import kr.co.hulan.aas.infra.weather.response.KmaWeatherVilageFcstItem;
import kr.co.hulan.aas.infra.weather.response.KmaWetherBodyItemResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/openapi/weather")
@Api(tags = "Airkorea 연동")
public class WeatherController {

  @Autowired
  private WeatherProperties weatherProperties;

  @Autowired
  private WeatherClient client;


  @ApiOperation(value = "동네예보 초단기실황", notes = "동네예보 초단기실황 제공한다.", consumes="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "base_date", value = "발표일자", required = false, dataType = "string", paramType = "query"),
      @ApiImplicitParam(name = "base_time", value = "발표시각", required = false, dataType = "string", paramType = "query"),
      @ApiImplicitParam(name = "nx", value = "nx", required = true, dataType = "int", paramType = "query"),
      @ApiImplicitParam(name = "ny", value = "ny", required = true, dataType = "int", paramType = "query")
  })
  @GetMapping(value="/ultraSrtNcst")
  public DefaultHttpRes<KmaResponse<KmaWetherBodyItemResponse>> ultraSrtNcst(
      @RequestParam(required = false) String base_date,
      @RequestParam(required = false) String base_time,
      @RequestParam Integer nx,
      @RequestParam Integer ny
  ) {
    WeatherGridXYRequest gridXY = null;
    if(StringUtils.isNotBlank(base_date) && StringUtils.isNotBlank(base_time) ){
      gridXY = new WeatherGridXYRequest(base_date, base_time, nx, ny);
    }
    else {
      gridXY = new WeatherGridXYRequest(nx, ny);
    }
    return new DefaultHttpRes<KmaResponse<KmaWetherBodyItemResponse>>( BaseCode.SUCCESS, client.findWeatherUltraNcst(gridXY));
  }

  @ApiOperation(value = "동네예보 초단기예보", notes = "동네예보 초단기예보 제공한다.", consumes="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "base_date", value = "발표일자", required = false, dataType = "string", paramType = "query"),
      @ApiImplicitParam(name = "base_time", value = "발표시각", required = false, dataType = "string", paramType = "query"),
      @ApiImplicitParam(name = "nx", value = "nx", required = true, dataType = "int", paramType = "query"),
      @ApiImplicitParam(name = "ny", value = "ny", required = true, dataType = "int", paramType = "query")
  })
  @GetMapping(value="/ultraSrtFcst")
  public DefaultHttpRes<KmaResponse<KmaWeatherVilageFcstItem>> ultraSrtFcst(
      @RequestParam(required = false) String base_date,
      @RequestParam(required = false) String base_time,
      @RequestParam Integer nx,
      @RequestParam Integer ny
  ) {
    WeatherGridXYRequest gridXY = null;
    if(StringUtils.isNotBlank(base_date) && StringUtils.isNotBlank(base_time) ){
      gridXY = new WeatherGridXYRequest(base_date, base_time, nx, ny);
    }
    else {
      gridXY = new WeatherGridXYRequest(nx, ny);
    }
    return new DefaultHttpRes<KmaResponse<KmaWeatherVilageFcstItem>>( BaseCode.SUCCESS, client.findWeatherUltraFcst(gridXY));
  }

  @ApiOperation(value = "동네예보 단기예보", notes = "동네예보 단기예보 제공한다.", consumes="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "base_date", value = "발표일자", required = false, dataType = "string", paramType = "query"),
      @ApiImplicitParam(name = "base_time", value = "발표시각", required = false, dataType = "string", paramType = "query"),
      @ApiImplicitParam(name = "nx", value = "nx", required = true, dataType = "int", paramType = "query"),
      @ApiImplicitParam(name = "ny", value = "ny", required = true, dataType = "int", paramType = "query")
  })
  @GetMapping(value="/vilageFcst")
  public DefaultHttpRes<KmaResponse<KmaWeatherVilageFcstItem>> vilageFcst(
      @RequestParam(required = false) String base_date,
      @RequestParam(required = false) String base_time,
      @RequestParam Integer nx,
      @RequestParam Integer ny
  ) {
    WeatherGridXYRequest gridXY = null;
    if(StringUtils.isNotBlank(base_date) && StringUtils.isNotBlank(base_time) ){
      gridXY = new WeatherGridXYRequest(base_date, base_time, nx, ny);
    }
    else {
      gridXY = new WeatherGridXYRequest(nx, ny);
    }
    return new DefaultHttpRes<KmaResponse<KmaWeatherVilageFcstItem>>( BaseCode.SUCCESS, client.findWeatherVilageFcst(gridXY));
  }


  @ApiOperation(value = "중기 육상예보조회", notes = "중기 육상예보조회 제공한다.", consumes="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "pageNo", value = "페이지번호", required = true, dataType = "int", paramType = "query"),
      @ApiImplicitParam(name = "numberOfRow", value = "페이지결과수", required = true, dataType = "int", paramType = "query"),
      @ApiImplicitParam(name = "regId", value = "예보구역코드", required = true, dataType = "string", paramType = "query"),
      @ApiImplicitParam(name = "tmFc", value = "발표시각", required = true, dataType = "string", paramType = "query"),
  })
  @GetMapping(value="/midLandFcst")
  public DefaultHttpRes<KmaResponse<KmaWeatherMidLandFcstItem>> midLandFcst(
      @RequestParam Integer pageNo,
      @RequestParam Integer numberOfRow,
      @RequestParam String regId,
      @RequestParam String tmFc
  ) {

    WeatherForecastRequest request = new WeatherForecastRequest(regId, tmFc);
    request.setPageNo(pageNo);
    request.setNumOfRows(numberOfRow);
    return new DefaultHttpRes<KmaResponse<KmaWeatherMidLandFcstItem>>( BaseCode.SUCCESS, client.findWeatherMidLandFcst(request));
  }


  @ApiOperation(value = "중기 기온예보 조회", notes = "중기 기온예보 조회 제공한다.", consumes="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "pageNo", value = "페이지번호", required = true, dataType = "int", paramType = "query"),
      @ApiImplicitParam(name = "numberOfRow", value = "페이지결과수", required = true, dataType = "int", paramType = "query"),
      @ApiImplicitParam(name = "regId", value = "예보구역코드", required = true, dataType = "string", paramType = "query"),
      @ApiImplicitParam(name = "tmFc", value = "발표시각", required = true, dataType = "string", paramType = "query"),
  })
  @GetMapping(value="/midTa")
  public DefaultHttpRes<KmaResponse<KmaWeatherMidTaItem>> midTa(
      @RequestParam Integer pageNo,
      @RequestParam Integer numberOfRow,
      @RequestParam String regId,
      @RequestParam String tmFc
  ) {

    WeatherForecastRequest request = new WeatherForecastRequest(regId, tmFc);
    request.setPageNo(pageNo);
    request.setNumOfRows(numberOfRow);
    return new DefaultHttpRes<KmaResponse<KmaWeatherMidTaItem>>( BaseCode.SUCCESS, client.findWeatherMidTa(request));
  }



  @ApiOperation(value = "동네예보통보문 (육상예보조회) 조회", notes = "동네예보통보문 (육상예보조회) 조회 제공한다.", consumes="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "pageNo", value = "페이지번호", required = true, dataType = "int", paramType = "query"),
      @ApiImplicitParam(name = "numberOfRow", value = "페이지결과수", required = true, dataType = "int", paramType = "query"),
      @ApiImplicitParam(name = "regId", value = "예보구역코드", required = true, dataType = "string", paramType = "query")
  })
  @GetMapping(value="/landFcst")
  public DefaultHttpRes<KmaResponse<KmaWeatherLandFcstItem>> landFcst(
      @RequestParam Integer pageNo,
      @RequestParam Integer numberOfRow,
      @RequestParam String regId
  ) {

    WeatherForecastRequest request = new WeatherForecastRequest(regId, "");
    request.setPageNo(pageNo);
    request.setNumOfRows(numberOfRow);
    return new DefaultHttpRes<KmaResponse<KmaWeatherLandFcstItem>>( BaseCode.SUCCESS, client.findWeatherLandFcst(request));
  }
}
