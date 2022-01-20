package kr.co.hulan.aas.mvc.api.gas.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.config.properties.AasConfigProperties;
import kr.co.hulan.aas.mvc.api.device.controller.request.ExportWorkDeviceInfoDataRequest;
import kr.co.hulan.aas.mvc.api.device.controller.request.ListWorkDeviceInfoRequest;
import kr.co.hulan.aas.mvc.api.device.controller.response.ListWorkDeviceInfoResponse;
import kr.co.hulan.aas.mvc.api.gas.controller.request.ExportGasLogRequest;
import kr.co.hulan.aas.mvc.api.gas.controller.request.ListGasLogRequest;
import kr.co.hulan.aas.mvc.api.gas.controller.response.ListGasLogResponse;
import kr.co.hulan.aas.mvc.api.gas.service.GasService;
import kr.co.hulan.aas.mvc.model.dto.GasLogDto;
import kr.co.hulan.aas.mvc.model.dto.WorkDeviceInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gas")
@Api(tags = "유해물질 측정 정보 관리")
public class GasController {

  @Autowired
  private AasConfigProperties aasConfigProperties;

  @Autowired
  private GasService gasService;


  @ApiOperation(value = "유해물질 측정 정보 검색", notes = "유해물질 측정 정보 검색 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping("search")
  public DefaultHttpRes<ListGasLogResponse> search(
      @RequestBody @Valid ListGasLogRequest request) {
    return new DefaultHttpRes<ListGasLogResponse>(BaseCode.SUCCESS, gasService.findListPage(request));
  }

  @ApiOperation(value = "유해물질 측정 정보 Export", notes = "유해물질 측정 정보 검색 Export 제공한다.", produces="application/json;charset=UTF-8")
  @PostMapping("export")
  public DefaultHttpRes<List<GasLogDto>> export(
      @RequestBody @Valid ExportGasLogRequest request) {
    return new DefaultHttpRes<List<GasLogDto>>(BaseCode.SUCCESS, gasService.findListByCondition(request.getConditionMap()));
  }
}
