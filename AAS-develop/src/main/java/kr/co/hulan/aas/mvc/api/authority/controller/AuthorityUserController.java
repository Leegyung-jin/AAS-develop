package kr.co.hulan.aas.mvc.api.authority.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.mvc.api.authority.controller.request.AuthorityExportRequest;
import kr.co.hulan.aas.mvc.api.authority.controller.request.AuthorityUserExportRequest;
import kr.co.hulan.aas.mvc.api.authority.controller.request.AuthorityUserListRequest;
import kr.co.hulan.aas.mvc.api.authority.model.dto.AuthorityDto;
import kr.co.hulan.aas.mvc.api.authority.model.dto.AuthorityUserDto;
import kr.co.hulan.aas.mvc.api.authority.service.AuthorityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/authorityUser")
@Api(tags = "권한 관리 사용자 탭 API")
public class AuthorityUserController {

    @Autowired
    private AuthorityUserService authorityUserService;

    @ApiOperation(value = "권한 등록 가능한 사용자 리스트", notes = "등록 가능한 사용자 검색을 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("/search")
    public DefaultHttpRes<DefaultPageResult<AuthorityUserDto>> searchAuthorityUser(
            @Valid @RequestBody AuthorityUserListRequest request) {
        return new DefaultHttpRes<DefaultPageResult<AuthorityUserDto>>(BaseCode.SUCCESS, authorityUserService.findListPage(request));
    }

    @ApiOperation(value = "권한 Export", notes = "권한을 Export 제공한다.", produces = "application/json;charset=UTF-8")
    @PostMapping(value = "/export", produces = "application/json;charset=UTF-8")
    public DefaultHttpRes<List<AuthorityUserDto>> export(
            @Valid @RequestBody AuthorityUserExportRequest request) {
        return new DefaultHttpRes<List<AuthorityUserDto>>(BaseCode.SUCCESS, authorityUserService.findListByCondition(request.getConditionMap()));
    }
}
