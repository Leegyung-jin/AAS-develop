package kr.co.hulan.aas.mvc.api.authority.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.mvc.api.authority.controller.request.AuthorityCreateRequest;
import kr.co.hulan.aas.mvc.api.authority.controller.request.AuthorityExportRequest;
import kr.co.hulan.aas.mvc.api.authority.controller.request.AuthorityListRequest;
import kr.co.hulan.aas.mvc.api.authority.controller.request.AuthorityUpdateRequest;
import kr.co.hulan.aas.mvc.api.authority.model.dto.AuthorityDto;
import kr.co.hulan.aas.mvc.api.authority.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/authority")
@Api(tags = "권한 관리 API")
public class AuthorityController {

    @Autowired
    private AuthorityService authorityService;

    @ApiOperation(value = "권한 검색", notes = "권한 정보 검색을 제공한다.", produces = "application/json;charset=UTF-8")
    @PostMapping(value = "/search", produces = "application/json;charset=UTF-8")
    public DefaultHttpRes<DefaultPageResult<AuthorityDto>> search(@RequestBody @Valid AuthorityListRequest request) {
        return new DefaultHttpRes(BaseCode.SUCCESS, authorityService.findListPage(request));
    }

    @ApiOperation(value = "권한 Export", notes = "권한을 Export 제공한다.", produces = "application/json;charset=UTF-8")
    @PostMapping(value = "/export", produces = "application/json;charset=UTF-8")
    public DefaultHttpRes<List<AuthorityDto>> export(
            @Valid @RequestBody AuthorityExportRequest request) {
        return new DefaultHttpRes<List<AuthorityDto>>(BaseCode.SUCCESS, authorityService.findListByCondition(request.getConditionMap()));
    }

    @ApiOperation(value = "권한 조회", notes = "권한 정보를 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorityId", value = "권한 아이디", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value="/{authorityId}", produces="application/json;charset=UTF-8")
    public DefaultHttpRes<AuthorityDto> detail(
            @PathVariable(value = "authorityId", required = true) String authorityId){
        return new DefaultHttpRes(BaseCode.SUCCESS,  authorityService.findInfo(authorityId));
    }

    @ApiOperation(value = "권한 등록", notes = "권한 정보 등록을 제공한다.")
    @PostMapping(produces="application/json;charset=UTF-8")
    public DefaultHttpRes<AuthorityDto> createCompany(
            @Valid @RequestBody AuthorityCreateRequest request) {
        authorityService.create(request);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "권한 수정", notes = "권한 정보를 수정한다.")
    @ApiImplicitParams({ @ApiImplicitParam(name = "authorityId", value = "권한 아이디", required = true, dataType = "Integer", paramType = "path") })
    @PutMapping(value="/{authorityId}", produces="application/json;charset=UTF-8")
    public DefaultHttpRes update(@Valid @RequestBody AuthorityUpdateRequest request,
                                 @PathVariable(value = "authorityId", required = true) String authorityId) {
        authorityService.update(request, authorityId);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "권한 삭제", notes = "권한 정보를 삭제한다.")
    @ApiImplicitParams({ @ApiImplicitParam(name = "authorityId", value = "권한 아이디", required = true, dataType = "Integer", paramType = "path") })
    @DeleteMapping(value = "/{authorityId}", produces="application/json;charset=UTF-8")
    public DefaultHttpRes delete(@PathVariable(value = "authorityId", required = true) String authorityId) {
        authorityService.delete(authorityId);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }


}
