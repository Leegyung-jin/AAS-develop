package kr.co.hulan.aas.mvc.api.worksection.service;

import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.code.dto.CodeDto;
import kr.co.hulan.aas.mvc.api.worksection.controller.request.CreateWorkSectionRequest;
import kr.co.hulan.aas.mvc.api.worksection.controller.request.ListWorkSectionRequest;
import kr.co.hulan.aas.mvc.api.worksection.controller.request.UpdateWorkSectionRequest;
import kr.co.hulan.aas.mvc.api.worksection.controller.response.ListWorkSectionResponse;
import kr.co.hulan.aas.mvc.api.worksection.controller.response.WorkSectionDetailResponse;
import kr.co.hulan.aas.mvc.dao.mapper.WorkSectionDao;
import kr.co.hulan.aas.mvc.dao.repository.WorkSectionRepository;
import kr.co.hulan.aas.mvc.model.domain.WorkSection;
import kr.co.hulan.aas.mvc.model.dto.WorkSectionDto;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class WorkSectionService {

    @Autowired
    private WorkSectionRepository workSectionRepository;

    @Autowired
    private WorkSectionDao workSectionDao;

    @Autowired
    ModelMapper modelMapper;

    public List<CodeDto> findTopLevelList(){
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("parentSectionCd", "TOPLEVEL");
        return workSectionDao.findListByCondition(condition)
                .stream().map(section -> { return new CodeDto(section.getSectionCd(), section.getSectionName()); } ).collect(Collectors.toList());

    }

    public ListWorkSectionResponse findListPage(ListWorkSectionRequest request) {
        return new ListWorkSectionResponse(
                findListCountByCondition(request.getConditionMap()),
                findListByCondition(request.getConditionMap())
        );
    }

    public List<WorkSectionDto> findListByCondition(Map<String,Object> conditionMap) {
        return workSectionDao.findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }

    private Long findListCountByCondition(Map<String,Object> conditionMap) {
        return workSectionDao.findListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }

    public WorkSectionDetailResponse findById(String sectionCd) {
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("parentSectionCd", sectionCd);
        return new WorkSectionDetailResponse(
                workSectionDao.findById(sectionCd),
                findListByCondition(condition)
        );
    }

    @Transactional("transactionManager")
    public void create(CreateWorkSectionRequest request){
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        WorkSection saveDto = modelMapper.map(request, WorkSection.class);

        Optional<WorkSection> workSectionOp = workSectionRepository.findById(saveDto.getSectionCd());
        if( workSectionOp.isPresent()){
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "이미 존재하는 공종 코드입니다.");
        }
        else {
            if( StringUtils.isEmpty(saveDto.getParentSectionCd())){
                saveDto.setParentSectionCd(null);
            }
            else {
                Optional<WorkSection> parentWorkSectionOp = workSectionRepository.findById(saveDto.getParentSectionCd());
                if( parentWorkSectionOp.isPresent() ){
                    if( StringUtils.isNotEmpty(parentWorkSectionOp.get().getParentSectionCd()) ){
                        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "최상위 공종 코드가 아닙니다.");
                    }
                }
                else {
                    throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 상위 공종 코드입니다.");
                }
            }
            saveDto.setCreateDate(new Date());
            saveDto.setCreator(loginUser.getUsername());
            saveDto.setUpdateDate(new Date());
            saveDto.setUpdater(loginUser.getUsername());
            workSectionRepository.save(saveDto);
        }
    }

    @Transactional("transactionManager")
    public void update(UpdateWorkSectionRequest request, String sectionCd){
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        if (!StringUtils.equals(request.getSectionCd(), sectionCd)) {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "공종 코드가 일치하지 않습니다.");
        }

        Optional<WorkSection> workSectionOp = workSectionRepository.findById(sectionCd);
        if( workSectionOp.isPresent()){
            WorkSection saveDto = workSectionOp.get();
            modelMapper.map(request, saveDto);

            if( StringUtils.isEmpty(saveDto.getParentSectionCd())){
                saveDto.setParentSectionCd(null);
            }
            else {
                if( workSectionRepository.countByParentSectionCd(saveDto.getSectionCd()) > 0 ){
                    throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "하위 공종 코드가 존재하는 경우 상위 공종코드를 지정할 수 없습니다.");
                }
                Optional<WorkSection> parentWorkSectionOp = workSectionRepository.findById(saveDto.getParentSectionCd());
                if( parentWorkSectionOp.isPresent() ){
                    if( StringUtils.isNotEmpty(parentWorkSectionOp.get().getParentSectionCd()) ){
                        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "최상위 공종 코드가 아닙니다.");
                    }
                }
                else {
                    throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 상위 공종 코드입니다.");
                }
            }
            saveDto.setUpdateDate(new Date());
            saveDto.setUpdater(loginUser.getUsername());
            workSectionRepository.save(saveDto);
        }
        else {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 공종 코드입니다.");
        }
    }

    @Transactional("transactionManager")
    public void delete(String sectionCd){
        Optional<WorkSection> workSectionOp = workSectionRepository.findById(sectionCd);
        if( workSectionOp.isPresent()){
            workSectionRepository.delete(workSectionOp.get());
        }
    }

    @Transactional("transactionManager")
    public void deleteMultiple(List<String> sectionCds){
        for (String sectionCd : sectionCds) {
            delete(sectionCd);
        }
    }

}
