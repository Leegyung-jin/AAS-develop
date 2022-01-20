package kr.co.hulan.aas.mvc.api.member.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.member.controller.response.ListFieldManagerResponse;
import kr.co.hulan.aas.mvc.api.member.service.G5MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
@Api(tags = "사용자 관리")
public class MemberController {

  @Autowired
  private G5MemberService g5MemberService;

  @ApiOperation(value = "사용자 계정 정상화", notes = "사용자 계정을 정상화한다 ( 패스워드 만료, 계정 잠김 등을 정상화 )")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "mbId", value = "회원 아이디", required = true, dataType = "string", paramType = "path")
  })
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success"),
      @ApiResponse(code = 400, message = "[10001] 파라미터 오류, [40008] 존재하지 않는 사용자 ", response = DefaultHttpRes.class)
  })
  @PostMapping("{mbId}/normalize")
  public DefaultHttpRes<Object> normalizeUser(
      @PathVariable(value = "mbId", required = true) String mbId) {
    g5MemberService.normalizeUserByMbId(mbId);
    return new DefaultHttpRes<Object>(BaseCode.SUCCESS );
  }
}
