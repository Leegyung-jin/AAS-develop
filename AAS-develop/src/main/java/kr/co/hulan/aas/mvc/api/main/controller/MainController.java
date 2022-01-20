package kr.co.hulan.aas.mvc.api.main.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.main.controller.response.AdminMainResponse;
import kr.co.hulan.aas.mvc.api.main.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/main")
@Api(tags = "메인")
public class MainController {

    @Autowired
    private MainService mainService;


    @ApiOperation(value = "구인 리스트", notes = "구인 리스트 검색 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("admin")
    public DefaultHttpRes<AdminMainResponse> main() {
        return new DefaultHttpRes<AdminMainResponse>(BaseCode.SUCCESS, mainService.findMainData());
    }

}
