package kr.co.hulan.aas.mvc.api.msg.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.msg.controller.request.ListAdminMsgInfoRequest;
import kr.co.hulan.aas.mvc.api.msg.controller.response.ListAdminMsgInfoResponse;
import kr.co.hulan.aas.mvc.api.msg.service.AdminMsgInfoService;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.request.ExportSensorInfoDataRequest;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.response.ListSensorInfoResponse;
import kr.co.hulan.aas.mvc.model.domain.AdminMsgInfo;
import kr.co.hulan.aas.mvc.model.dto.SensorInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/msg/admin")
@Api(tags = "현장 관리자 알림 메시지 관리")
public class AdminMsgInfoController {

  @Autowired
  private AdminMsgInfoService adminMsgInfoService;

  @ApiOperation(value = "현장 관리자 알림 메시지 리스트", notes = "현장 관리자 알림 메시지 리스트 검색 제공한다.", produces=MediaType.APPLICATION_JSON_VALUE)
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<ListAdminMsgInfoResponse> search(
      @Valid @RequestBody ListAdminMsgInfoRequest request) {
    return new DefaultHttpRes<ListAdminMsgInfoResponse>(BaseCode.SUCCESS, adminMsgInfoService.findListPage(request));
  }

  @ApiOperation(value = "현장 관리자 알림 메시지 조회", notes = "현장 관리자 알림 메시지 정보를 제공한다.", produces=MediaType.APPLICATION_JSON_VALUE)
  @ApiImplicitParams({
      @ApiImplicitParam(name = "idx", value = "현장 관리자 알림 메시지 넘버", required = true, dataType = "int", paramType = "path")
  })
  @GetMapping(value="/{idx}", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultHttpRes<AdminMsgInfo> detail(
      @PathVariable(value = "idx", required = true) int idx) {
    return new DefaultHttpRes(BaseCode.SUCCESS,  adminMsgInfoService.findById(idx));
  }

}
