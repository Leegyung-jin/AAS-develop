package kr.co.hulan.aas.mvc.api.push.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.push.controller.request.SendPushRequest;
import kr.co.hulan.aas.mvc.api.push.service.PushMgrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/push")
@Api(tags = "Push 전송 관리")
public class PushMgrController {

  @Autowired
  private PushMgrService pushMgrService;

  @ApiOperation(value = "Push 요청", notes = "Push 요청 API 제공한다.", consumes="application/json;charset=UTF-8")
  @PostMapping("send")
  public DefaultHttpRes request(
      @Valid @RequestBody SendPushRequest request
      ) {

    pushMgrService.sendPush(request);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }
}
