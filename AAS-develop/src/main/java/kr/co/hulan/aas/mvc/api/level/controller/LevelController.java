package kr.co.hulan.aas.mvc.api.level.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.mvc.api.level.controller.request.LevelCreateRequest;
import kr.co.hulan.aas.mvc.api.level.controller.request.LevelExportRequest;
import kr.co.hulan.aas.mvc.api.level.controller.request.LevelListRequest;
import kr.co.hulan.aas.mvc.api.level.controller.request.LevelUpdateRequest;
import kr.co.hulan.aas.mvc.api.level.controller.response.LevelAuthorityResponse;
import kr.co.hulan.aas.mvc.api.level.service.LevelService;
import kr.co.hulan.aas.mvc.api.level.model.dto.LevelDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/level")
@Api(tags = "등급 권한 API")
public class LevelController {

    @Autowired
    private LevelService levelService;

    @ApiOperation(value = "등급 검색", notes = "등급 정보 검색을 제공한다.", produces = "application/json;charset=UTF-8")
    @PostMapping(value = "/search", produces = "application/json;charset=UTF-8")
    public DefaultHttpRes<DefaultPageResult<LevelDto>> search(@RequestBody @Valid LevelListRequest request) {
        return new DefaultHttpRes(BaseCode.SUCCESS, levelService.findListPage(request));
    }

    @ApiOperation(value = "계정 등급 정보 Export", notes = "계정 등급 정보를 Export 제공한다.", produces = "application/json;charset=UTF-8")
    @PostMapping(value = "/export", produces = "application/json;charset=UTF-8")
    public DefaultHttpRes<List<LevelDto>> export(
            @Valid @RequestBody LevelExportRequest request) {
        return new DefaultHttpRes<List<LevelDto>>(BaseCode.SUCCESS, levelService.findListByCondition(request.getConditionMap()));
    }

    @ApiOperation(value = "계정 등급 정보 조회", notes = "계정 등급 상세 정보를 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mbLevel", value = "계정 레벨(등급)", required = true, dataType = "Integer", paramType = "path")
    })
    @GetMapping(value="/{mbLevel}", produces="application/json;charset=UTF-8")
    public DefaultHttpRes<LevelAuthorityResponse> detail(
            @PathVariable(value = "mbLevel", required = true) Integer mbLevel){
        return new DefaultHttpRes(BaseCode.SUCCESS,  levelService.findInfo(mbLevel));
    }

    @ApiOperation(value = "계정 등급 정보 등록", notes = "계정 등급을 등록한다.")
    @PostMapping(produces="application/json;charset=UTF-8")
    public DefaultHttpRes create(
            @Valid @RequestBody LevelCreateRequest request) {
        return new DefaultHttpRes(BaseCode.SUCCESS, levelService.create(request));
    }

    @ApiOperation(value = "계정 등급 정보 수정", notes = "계정 등급을 수정한다.")
    @ApiImplicitParams({ @ApiImplicitParam(name = "mbLevel", value = "계정 레벨(등급)", required = true, dataType = "Integer", paramType = "path") })
    @PutMapping(value="/{mbLevel}", produces="application/json;charset=UTF-8")
    public DefaultHttpRes update(@Valid @RequestBody LevelUpdateRequest request,
                                 @PathVariable(value = "mbLevel", required = true) Integer mbLevel) {
        levelService.update(request, mbLevel);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }

    @ApiOperation(value = "계정 등급 정보 삭제", notes = "계정 등급을 삭제한다.")
    @ApiImplicitParams({ @ApiImplicitParam(name = "mbLevel", value = "계정 레벨(등급)", required = true, dataType = "Integer", paramType = "path") })
    @DeleteMapping(value = "/{mbLevel}", produces="application/json;charset=UTF-8")
    public DefaultHttpRes delete(@PathVariable(value = "mbLevel", required = true) Integer mbLevel) {
        levelService.delete(mbLevel);
        return new DefaultHttpRes(BaseCode.SUCCESS);
    }
}
