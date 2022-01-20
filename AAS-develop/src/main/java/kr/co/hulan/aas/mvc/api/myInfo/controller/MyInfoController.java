package kr.co.hulan.aas.mvc.api.myInfo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import javax.validation.Valid;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.config.security.oauth.model.SecurityMemberResponse;
import kr.co.hulan.aas.mvc.api.myInfo.controller.request.ChangePasswordRequest;
import kr.co.hulan.aas.mvc.api.myInfo.service.MyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/myInfo")
@Api(tags = "내정보 관리")
public class MyInfoController {

    @Autowired
    private MyInfoService myInfoService;

    @ApiOperation(value = "내정보 조회", notes = "내정보를 제공한다.")
    @GetMapping("detail")
    public DefaultHttpRes<SecurityMemberResponse> detail() {
        return new DefaultHttpRes(BaseCode.SUCCESS,  myInfoService.getDetail());
    }


    @ApiOperation(value = "패스워드 변경", notes = "패스워드 변경을 제공한다.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 400, message = "[10001] 파라미터 오류, [40007] 비밀번호 불일치, [40008] 존재하지 않는 사용자 ", response = DefaultHttpRes.class)
    })
    @PostMapping("password/change")
    public DefaultHttpRes<Object> changePassword(
        @Valid @RequestBody ChangePasswordRequest changeRequest
    ) {
        myInfoService.changePassword(changeRequest);
        return new DefaultHttpRes<Object>(BaseCode.SUCCESS);
    }


    @ApiOperation(value = "패스워드 변경 연기", notes = "내정보를 제공한다.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 400, message = "[10001] 파라미터 오류, [40008] 존재하지 않는 사용자 ", response = DefaultHttpRes.class)
    })
    @PostMapping("password/postpone")
    public DefaultHttpRes<Object> postponeChangingPassword() {
        myInfoService.postponeChangingPassword();
        return new DefaultHttpRes<Object>(BaseCode.SUCCESS);
    }
}
