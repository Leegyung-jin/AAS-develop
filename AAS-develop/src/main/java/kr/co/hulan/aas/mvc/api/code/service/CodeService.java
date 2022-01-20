package kr.co.hulan.aas.mvc.api.code.service;

import java.util.Collections;
import kr.co.hulan.aas.common.code.DeviceType;
import kr.co.hulan.aas.common.code.WorkplaceCategory;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.mvc.api.code.dto.CodeDto;
import kr.co.hulan.aas.mvc.api.worksection.service.WorkSectionService;
import kr.co.hulan.aas.mvc.dao.mapper.*;
import kr.co.hulan.aas.mvc.dao.repository.*;
import kr.co.hulan.aas.mvc.model.dto.MemberLevelDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CodeService {

    @Autowired
    SensorDistrictRepository sensorDistrictRepository;

    @Autowired
    SensorPolicyInfoDao sensorPolicyInfoDao;

    @Autowired
    ConCompanyDao conCompanyDao;

    @Autowired
    WorkPlaceDao workPlaceDao;

    @Autowired
    G5MemberDao g5MemberDao;

    @Autowired
    WorkSectionService workSectionService;

    @Autowired
    EquipmentCodeRepository equipmentCodeRepository;

    @Autowired
    MemberLevelDao memberLevelDao;

    /*
    final String[] WORKSPACE_CATEGORY = new String[]{
        "아파트", "주상복합", "오피스텔", "다세대,연립주택", "업무시설(관공서,사옥등)", "상업시설(상가,백화점,쇼핑몰등)",
        "숙박시설(호텔,리조트,콘도등)", "의료시설(병원,요양원등)", "창고 및 공장", "종교시설", "운동시설(체육관,운동장등)",
        "교육연구시설(학교,연구원등)", "군사시설", "운수시설(공항,철도,항만,터미널)", "기타시설"
    };
     */

    public List<CodeDto> findConstructionCompanyCodeList(String wpId, String coopMbId, String gps){
        Map<String,Object> condition = AuthenticationHelper.addAdditionalConditionByLevel(new HashMap<String,Object>());
        if(StringUtils.isNotBlank(coopMbId) && !condition.containsKey("coopMbId")){
            condition.put("coopMbId", coopMbId);
        }
        if(StringUtils.isNotBlank(wpId) && !condition.containsKey("wpId")){
            condition.put("wpId", wpId);
        }
        if(StringUtils.isNotBlank(gps)){
            condition.put("gps", gps);
        }
        return conCompanyDao.findListForCode(condition)
                .stream().map(conCompany -> { return new CodeDto(conCompany.getCcId(), conCompany.getCcName()); } ).collect(Collectors.toList());
    }

    public List<CodeDto> findWorkPlaceCodeList(String ccId, String coopMbId, String gps, String bls ){
        Map<String,Object> condition = AuthenticationHelper.addAdditionalConditionByLevel(new HashMap<String,Object>());
        if(StringUtils.isNotBlank(ccId)){
            condition.put("ccId", ccId);
        }
        if(StringUtils.isNotBlank(coopMbId) && !condition.containsKey("coopMbId")){
            condition.put("coopMbId", coopMbId);
        }
        if(StringUtils.isNotBlank(gps) ){
            condition.put("gps", gps);
        }
        if(StringUtils.isNotBlank(bls) ){
            condition.put("bls", bls);
        }
        return workPlaceDao.findListForCode(condition)
                .stream().map(workPlace -> { return new CodeDto(workPlace.getWpId(), workPlace.getWpName()); } ).collect(Collectors.toList());
    }

    public List<CodeDto> findCooperativeCompanyCodeList(Map<String,Object> conditionMap){
        Map<String,Object> condition = AuthenticationHelper.addAdditionalConditionByLevel();
        condition.putAll(conditionMap);
        return g5MemberDao.findCooperativeCompanyCodeList(condition)
                .stream().map(g5Member -> { return new CodeDto(g5Member.getMbId(), g5Member.getName()); } ).collect(Collectors.toList());
    }

    public List<CodeDto> findWorkplaceCategoryList() {
        List<CodeDto> list = new ArrayList<CodeDto>();
        for( WorkplaceCategory category : WorkplaceCategory.values() ){
            list.add(new CodeDto(category.getName(), category.getName()));
        }
        return list;
    }

    public List<CodeDto> findWorkCodeList(){
        return workSectionService.findTopLevelList();
    }

    public List<CodeDto> findWork2CodeList(String work){
        Map<String,Object> condition = new HashMap<String, Object>();
        condition.put("parentSectionCd",work);
        return workSectionService.findListByCondition(condition)
                    .stream().map(workSection -> { return new CodeDto(workSection.getSectionCd(), workSection.getSectionName()); } ).collect(Collectors.toList());
    }

    public List<CodeDto> findWorkplaceDistrictList(String wpId){
        return sensorDistrictRepository.findAllByWpIdOrderBySdNameDesc(wpId)
                .stream().map(sensorDistrict -> { return new CodeDto(""+sensorDistrict.getSdIdx(), sensorDistrict.getSdName()); } ).collect(Collectors.toList());
    }


    public List<CodeDto> findSiTypeList(){
        return sensorPolicyInfoDao.findSiTypeCodeList()
                .stream().map(sensorPolicy -> { return new CodeDto(sensorPolicy.getSiType(), sensorPolicy.getSiType()); } ).collect(Collectors.toList());
    }


    public List<CodeDto> findEquipmentTypeList(){
        return equipmentCodeRepository.findAll()
                .stream().map(eqType -> { return new CodeDto(""+eqType.getEquipmentType(), eqType.getEquipmentName()); } ).collect(Collectors.toList());
    }

    public List<MemberLevelDto> findMbLevelList(){
        return memberLevelDao.findListByCondition(Collections.emptyMap());
    }

    public List<CodeDto> findDeviceTypeList(){
        List<CodeDto> list = new ArrayList<CodeDto>();
        for( DeviceType category : DeviceType.values() ){
            list.add(new CodeDto(""+category.getCode(), category.getName()));
        }
        return list;
    }

}
