package kr.co.hulan.aas.mvc.api.member.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.mvc.api.member.controller.request.ListVisitRequest;
import kr.co.hulan.aas.mvc.api.member.controller.response.ListVisitResponse;
import kr.co.hulan.aas.mvc.api.member.service.VisitService;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/member/visit")
@Api(tags = "접속로그 관리")
public class VisitController {

    @Autowired
    private VisitService visitService;

    @ApiOperation(value = "접속로그 검색", notes = "접속로그 검색 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("search")
    public DefaultHttpRes<ListVisitResponse> searchWorker(
            @Valid @RequestBody ListVisitRequest request) {
        return new DefaultHttpRes<ListVisitResponse>(BaseCode.SUCCESS, visitService.findListPage(request));

    }

}
