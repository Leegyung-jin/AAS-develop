package kr.co.hulan.aas.mvc.api.authority.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.mvc.api.authority.controller.request.AuthorityManagerListRequest;
import kr.co.hulan.aas.mvc.api.authority.model.dto.AuthorityDto;
import kr.co.hulan.aas.mvc.api.authority.model.dto.AuthorityManagerDto;
import kr.co.hulan.aas.mvc.api.authority.service.AuthorityManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/authorityMng")
@Api(tags = "권한 관리 사용자 탭 API")
public class AuthorityManagerController {

    @Autowired
    private AuthorityManagerService authorityManagerService;

//    @ApiOperation(value = "사용자 리스트", notes = "등록 가능한 사용자 검색을 제공한다.", consumes="application/json;charset=UTF-8")
//    @PostMapping("/search")
//    public DefaultHttpRes<DefaultPageResult<AuthorityManagerDto>> search(
//            @Valid AuthorityManagerListRequest request) {
//        return new DefaultHttpRes<DefaultPageResult<AuthorityManagerDto>>(BaseCode.SUCCESS, authorityManagerService.findListPage(request));
//    }

//    @ApiOperation(value = "권한 Export", notes = "권한을 Export 제공한다.", produces = "application/json;charset=UTF-8")
//    @PostMapping(value = "/export", produces = "application/json;charset=UTF-8")
//    public DefaultHttpRes<List<AuthorityUserDto>> export(
//            @Valid @ModelAttribute AuthorityUserExportRequest request) {
//        return new DefaultHttpRes<List<AuthorityUserDto>>(BaseCode.SUCCESS, authorityUserService.findListByCondition(request.getConditionMap()));
//    }

    @ApiOperation(value = "권한 조회", notes = "권한 정보를 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mbId", value = "사용자 아이디", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value="/{mbId}", produces="application/json;charset=UTF-8")
    public DefaultHttpRes<AuthorityManagerDto> detail(
            @PathVariable(value = "mbId", required = true) String mbId){
        return new DefaultHttpRes(BaseCode.SUCCESS,  authorityManagerService.findInfo(mbId));
    }

}
