package kr.co.hulan.aas.mvc.api.workplace.service;


import java.util.HashMap;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.MemberLevel;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.common.utils.GenerateIdUtils;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.workplace.controller.request.*;
import kr.co.hulan.aas.mvc.api.workplace.controller.response.ListWorkplaceCooperativeCompanyResponse;
import kr.co.hulan.aas.mvc.dao.mapper.WorkPlaceCoopDao;
import kr.co.hulan.aas.mvc.dao.mapper.WorkPlaceDao;
import kr.co.hulan.aas.mvc.dao.repository.*;
import kr.co.hulan.aas.mvc.model.domain.*;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceCoopDto;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceDto;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class WorkplaceCooperativeCompanyService {

    @Autowired
    G5MemberRepository g5MemberRepository;

    @Autowired
    private WorkPlaceCoopRepository workPlaceCoopRepository;

    @Autowired
    private WorkPlaceCoopDao workPlaceCoopDao;

    @Autowired
    private WorkPlaceWorkerRepository workPlaceWorkerRepository;

    @Autowired
    private WorkPlaceRepository workPlaceRepository;

    @Autowired
    private WorkSectionRepository workSectionRepository;

    @Autowired
    private ConstructionSiteRepository constructionSiteRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    @Qualifier("entityManagerFactory")
    EntityManager entityManager;

    public ListWorkplaceCooperativeCompanyResponse findListPage(ListWorkplaceCooperativeCompanyRequest request) {

        return new ListWorkplaceCooperativeCompanyResponse(
                findListCountByCondition(request.getConditionMap()),
                findListByCondition(request.getConditionMap())
        );
    }


    public List<WorkPlaceCoopDto> findListByCondition(Map<String,Object> conditionMap) {
        Map<String,Object> condition = AuthenticationHelper.addAdditionalConditionByLevel(new HashMap<String,Object>());
        condition.putAll(conditionMap);
        return workPlaceCoopDao.findSafetySituationListByCondition(condition);
    }

    private Long findListCountByCondition(Map<String,Object> conditionMap) {
        Map<String,Object> condition = AuthenticationHelper.addAdditionalConditionByLevel(new HashMap<String,Object>());
        condition.putAll(conditionMap);
        return workPlaceCoopDao.findSafetySituationListCountByCondition(condition);
    }


    public WorkPlaceCoopDto findById(String wpcId){
        WorkPlaceCoopDto workPlaceOp = workPlaceCoopDao.findInfo(wpcId);
        if ( workPlaceOp != null ) {
            return workPlaceOp;
        } else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }
    }

    public WorkPlaceCoopDto findByWpIdAndCoopMbId(String wpId, String coopMbId){
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("wpId", wpId);
        condition.put("coopMbId", coopMbId);
        WorkPlaceCoopDto workPlaceOp = workPlaceCoopDao.findByWpIdAndCoopMbId(condition);
        if (workPlaceOp == null ) {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }
        return workPlaceOp;
    }

    @Transactional("transactionManager")
    public void create(CreateWorkplaceCooperativeCompanyRequest request) {
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        WorkPlaceCoop saveDto = modelMapper.map(request, WorkPlaceCoop.class);

        Optional<WorkPlace> companyOp = workPlaceRepository.findById(saveDto.getWpId());
        if( companyOp.isPresent()){
            saveDto.setWpName( companyOp.get().getWpName());
        }
        else {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 현장입니다.");
        }

        Optional<G5Member> memberOp = g5MemberRepository.findByMbId(saveDto.getCoopMbId());
        if( memberOp.isPresent() && memberOp.get().getMbLevel() == MemberLevel.COOPERATIVE_COMPANY.getCode()){
            saveDto.setCoopMbName( memberOp.get().getName());
        }
        else {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 협력사입니다.");
        }

        ConstructionSite constructionSite = constructionSiteRepository.findById(ConstructionSiteKey.builder()
            .ccId(saveDto.getCcId())
            .wpId(saveDto.getWpId())
            .build()).orElseThrow( () ->
            new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "해당 현장에 편입된 건설사가 아닙니다.")
        );

        long existsCoopCount = workPlaceCoopRepository.countByWpIdAndCoopMbId(saveDto.getWpId(), saveDto.getCoopMbId());
        if( existsCoopCount > 0 ){
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "이미 해당 현장에 편입된 협력사입니다.");
        }

        Optional<WorkSection> workSectionOp = workSectionRepository.findById(saveDto.getWorkSectionA());
        if( workSectionOp.isPresent() ){
            WorkSection section = workSectionOp.get();
            if( StringUtils.isNotEmpty(section.getParentSectionCd()) ){
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "상위공종(공종A)을 설정하셔야 합니다.");
            }
            saveDto.setWpcWork( section.getSectionName() );
        }
        else {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 공정입니다.");
        }

        saveDto.setWpcId(GenerateIdUtils.getUuidKey());
        workPlaceCoopRepository.save(saveDto);
    }


    @Transactional("transactionManager")
    public void update(UpdateWorkplaceCooperativeCompanyRequest request, String wpcId) {
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        if (!StringUtils.equals(request.getWpcId(), wpcId)) {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "아이디가 일치하지 않습니다.");
        }

        Optional<WorkPlaceCoop> duplicated = workPlaceCoopRepository.findById(wpcId);
        if (duplicated.isPresent()) {

            WorkPlaceCoop saveDto = duplicated.get();

            ConstructionSite constructionSite = constructionSiteRepository.findById(ConstructionSiteKey.builder()
                .ccId(request.getCcId())
                .wpId(saveDto.getWpId())
                .build()).orElseThrow( () ->
                new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "해당 현장에 편입된 건설사가 아닙니다.")
            );
            saveDto.setCcId(request.getCcId());

            if( !StringUtils.equals( saveDto.getWorkSectionA(), request.getWorkSectionA())){
                modelMapper.map(request, saveDto);
                Optional<WorkSection> workSectionOp = workSectionRepository.findById(saveDto.getWorkSectionA());
                if( workSectionOp.isPresent() ){
                    WorkSection section = workSectionOp.get();
                    if( StringUtils.isNotEmpty(section.getParentSectionCd()) ){
                        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "상위공종(공종A)을 설정하셔야 합니다.");
                    }
                    saveDto.setWpcWork( section.getSectionName() );
                }
                else {
                    throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 공정입니다.");
                }
                workPlaceWorkerRepository.updateResetWorkerSectionsByCoopMbId(saveDto.getWpId(), saveDto.getCoopMbId());
                workPlaceCoopRepository.save(saveDto);
            }
        }
        else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }
    }


    @Transactional("transactionManager")
    public int delete(String wpcId) {
        workPlaceCoopRepository.deleteById(wpcId);
        return 1;
    }

    @Transactional("transactionManager")
    public int deleteMultiple(List<String> wpcIds) {
        int deleteCnt = 0;
        for (String wpcId : wpcIds) {
            deleteCnt += delete(wpcId);
        }
        return deleteCnt;
    }


}
