package kr.co.hulan.aas.mvc.api.code.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import java.util.Map;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.mvc.api.code.dto.CodeDto;
import kr.co.hulan.aas.mvc.api.code.service.CodeService;
import kr.co.hulan.aas.mvc.api.member.service.ConstructionCompanyService;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.model.dto.MemberLevelDto;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/common/code")
@Api(tags = "코드 관리")
public class CodeController {

    @Autowired
    private CodeService codeService;

    @Autowired
    private ModelMapper mapper;

    @ApiOperation(value = "건설사 전체 아이디/명 리스트", notes = "건설사 전체 아이디/명 제공한다.", consumes="application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "coopMbId", value = "협력사 아이디", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "gps", value = "GPS 제공 여부. '':전체, '0': 제공안함, '1': 제공", required = false, dataType = "string", defaultValue = "", paramType = "query")
    })
    @PostMapping("constructionCompany")
    public DefaultHttpRes<List<CodeDto>> constructionCompanyCodes(
            @RequestParam(value = "wpId", required = false) String wpId,
            @RequestParam(value = "coopMbId", required = false) String coopMbId,
            @RequestParam(value = "gps", required = false, defaultValue = "") String gps
    ) {
        return new DefaultHttpRes<List<CodeDto>>(BaseCode.SUCCESS, codeService.findConstructionCompanyCodeList(wpId, coopMbId, gps));
    }


    @ApiOperation(value = "현장 전체 아이디/명 리스트", notes = "현장 전체 아이디/명 제공한다.", consumes="application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cc_id", value = "건설사 아이디", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "coopMbId", value = "협력사 아이디", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "gps", value = "GPS 제공 여부. '':전체, '0': 제공안함, '1': 제공", required = false, dataType = "string", defaultValue = "", paramType = "query"),
            @ApiImplicitParam(name = "bls", value = "BLS 제공 여부. '':전체, '0': 제공안함, '1': 제공", required = false, dataType = "string", defaultValue = "", paramType = "query")
    })
    @PostMapping("workPlace")
    public DefaultHttpRes<List<CodeDto>> workPlace(
            @Valid @RequestParam(name = "cc_id",required = false) String cc_id,
            @RequestParam(value = "coopMbId", required = false) String coopMbId,
            @RequestParam(value = "gps", required = false, defaultValue = "") String gps,
            @RequestParam(value = "bls", required = false, defaultValue = "") String bls
    ) {
        return new DefaultHttpRes<List<CodeDto>>(BaseCode.SUCCESS, codeService.findWorkPlaceCodeList(cc_id, coopMbId, gps, bls));
    }

    @ApiOperation(value = "공종A 리스트", notes = "공종A 리스트 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("work")
    public DefaultHttpRes<List<CodeDto>> work() {
        return new DefaultHttpRes<List<CodeDto>>(BaseCode.SUCCESS, codeService.findWorkCodeList());
    }

    @ApiOperation(value = "공종B 리스트", notes = "공종B 리스트 제공한다.", consumes="application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "work", value = "공종A 코드", required = true, dataType = "string", paramType = "query")
    })
    @PostMapping("work2")
    public DefaultHttpRes<List<CodeDto>> work2(
            @RequestParam(value = "work", required = true) String work
    ) {
        return new DefaultHttpRes<List<CodeDto>>(BaseCode.SUCCESS, codeService.findWork2CodeList(work));
    }

    @ApiOperation(value = "협력사 전체 아이디/명 리스트", notes = "협력사 전체 아이디/명 제공한다.", consumes="application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = false, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "ccId", value = "건설사 아이디", required = false, dataType = "string", paramType = "query")
    })
    @PostMapping("cooperativeCompany")
    public DefaultHttpRes<List<CodeDto>> cooperativeCompany(
            @RequestParam(value = "wpId", required = false) String wpId,
            @RequestParam(value = "ccId", required = false) String ccId
    ) {
        Map<String,Object> condition = AuthenticationHelper.addAdditionalConditionByLevel();
        if(StringUtils.isNotBlank(wpId)){
            condition.put("wpId", wpId);
        }
        if(StringUtils.isNotBlank(ccId)){
            condition.put("ccId", ccId);
        }
        return new DefaultHttpRes<List<CodeDto>>(BaseCode.SUCCESS, codeService.findCooperativeCompanyCodeList(condition));
    }

    @ApiOperation(value = "현장 카테고리 코드 리스트", notes = "현장 카테고리 코드 리스트 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("workplaceCategory")
    public DefaultHttpRes<List<CodeDto>> workplaceCategory() {
        return new DefaultHttpRes<List<CodeDto>>(BaseCode.SUCCESS, codeService.findWorkplaceCategoryList());
    }


    @ApiOperation(value = "센서 구역 코드 리스트", notes = "센서 구역 코드 리스트 제공한다.", consumes="application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "query")
    })
    @PostMapping("workplaceDistrict")
    public DefaultHttpRes<List<CodeDto>> workplaceDistrict(
           @RequestParam(value = "wpId", required = true) String wpId
    ) {
        return new DefaultHttpRes<List<CodeDto>>(BaseCode.SUCCESS, codeService.findWorkplaceDistrictList(wpId));
    }


    @ApiOperation(value = "센서 유형 코드 리스트", notes = "센서 유형 코드 리스트 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("siType")
    public DefaultHttpRes<List<CodeDto>> siType() {
        return new DefaultHttpRes<List<CodeDto>>(BaseCode.SUCCESS, codeService.findSiTypeList());
    }

    @ApiOperation(value = "장비 유형 코드 리스트", notes = "장비 유형 코드 리스트 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("equipmentType")
    public DefaultHttpRes<List<CodeDto>> equipmentType() {
        return new DefaultHttpRes<List<CodeDto>>(BaseCode.SUCCESS, codeService.findEquipmentTypeList());
    }

    @ApiOperation(value = "계정 레벨(등급) 코드 리스트", notes = "계정 레벨(등급) 코드 리스트 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("mbLevel")
    public DefaultHttpRes<List<MemberLevelDto>> mbLevel() {
        return new DefaultHttpRes<List<MemberLevelDto>>(BaseCode.SUCCESS, codeService.findMbLevelList());
    }

    @ApiOperation(value = "디바이스 장비구분 코드 리스트", notes = "디바이스 장비구분 코드 리스트 제공한다.", consumes="application/json;charset=UTF-8")
    @PostMapping("deviceType")
    public DefaultHttpRes<List<CodeDto>> deviceType() {
        return new DefaultHttpRes<List<CodeDto>>(BaseCode.SUCCESS, codeService.findDeviceTypeList());
    }

}
