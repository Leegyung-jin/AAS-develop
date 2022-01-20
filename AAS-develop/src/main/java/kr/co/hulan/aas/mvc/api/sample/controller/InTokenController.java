package kr.co.hulan.aas.mvc.api.sample.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.mvc.service.token.TokenService;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/token")
@Api(tags = "토큰 관리")
public class InTokenController {

  @Autowired
  TokenService tokenService;

  @ApiOperation(value = "만료 토큰 삭제", notes = "만료된 토큰을 수동으로 삭제한다.")
  @PostMapping("expired")
  public DefaultHttpRes expiredToken() {
    tokenService.deleteExpiredToken();
    return new DefaultHttpRes<>(BaseCode.SUCCESS);
  }
}
