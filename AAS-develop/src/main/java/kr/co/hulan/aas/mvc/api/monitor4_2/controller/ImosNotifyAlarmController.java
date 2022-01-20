package kr.co.hulan.aas.mvc.api.monitor4_2.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.AttendantVo;
import kr.co.hulan.aas.mvc.api.monitor4_1.controller.request.EntergateWorkerHistoryPagingListRequest;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.request.ImosNotifyAlarmRequest;
import kr.co.hulan.aas.mvc.api.monitor4_2.service.ImosNotifyAlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/monitor/4.2/workplace/{wpId}/notify")
@Api(tags = "[4.2]IMOS 알림 발송 API")
public class ImosNotifyAlarmController {

  @Autowired
  private ImosNotifyAlarmService imosNotifyAlarmService;

  @ApiOperation(value = "알림 발송 요청", notes = "알림 발송 요청 제공한다.", produces="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @PostMapping(produces="application/json;charset=UTF-8")
  public DefaultHttpRes notifyAlarm(
      @PathVariable(value = "wpId", required = true) String wpId,
      @Valid @RequestBody ImosNotifyAlarmRequest request
  ) {
    imosNotifyAlarmService.sendAlarm(wpId, request);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

}
