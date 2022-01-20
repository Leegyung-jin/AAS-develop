package kr.co.hulan.aas.mvc.api.jobMgr.service;

import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.jobMgr.controller.request.CreateRecruitRequest;
import kr.co.hulan.aas.mvc.api.jobMgr.controller.request.ListRecruitRequest;
import kr.co.hulan.aas.mvc.api.jobMgr.controller.request.UpdateRecruitRequest;
import kr.co.hulan.aas.mvc.api.jobMgr.controller.response.ListRecruitResponse;
import kr.co.hulan.aas.mvc.dao.mapper.RecruitDao;
import kr.co.hulan.aas.mvc.dao.repository.RecruitRepository;
import kr.co.hulan.aas.mvc.dao.repository.WorkPlaceCoopRepository;
import kr.co.hulan.aas.mvc.dao.repository.WorkPlaceRepository;
import kr.co.hulan.aas.mvc.dao.repository.WorkSectionRepository;
import kr.co.hulan.aas.mvc.model.domain.Recruit;
import kr.co.hulan.aas.mvc.model.domain.WorkPlace;
import kr.co.hulan.aas.mvc.model.domain.WorkPlaceCoop;
import kr.co.hulan.aas.mvc.model.domain.WorkSection;
import kr.co.hulan.aas.mvc.model.dto.RecruitDto;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RecruitService {

    @Autowired
    private WorkPlaceRepository workPlaceRepository;

    @Autowired
    private WorkPlaceCoopRepository workPlaceCoopRepository;

    @Autowired
    private RecruitRepository recruitRepository;

    @Autowired
    private WorkSectionRepository workSectionRepository;

    @Autowired
    private RecruitDao recruitDao;

    public ListRecruitResponse findListPage(ListRecruitRequest request) {
        return new ListRecruitResponse(
                findListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(request.getConditionMap())),
                findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(request.getConditionMap()))
        );
    }


    public List<RecruitDto> findListByCondition(Map<String, Object> conditionMap) {
        return recruitDao.findListByCondition(conditionMap);
    }

    private Long findListCountByCondition(Map<String, Object> conditionMap) {
        return recruitDao.findListCountByCondition(conditionMap);
    }


    public RecruitDto findById(int rcIdx) {
        RecruitDto RecruitDto = recruitDao.findById(rcIdx);
        if (RecruitDto != null) {
            return RecruitDto;
        } else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }
    }

    @Transactional("mybatisTransactionManager")
    public void create(CreateRecruitRequest request) {
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        RecruitDto saveDto = modelMapper.map(request, RecruitDto.class);

        Optional<WorkPlace>  workplaceOp = workPlaceRepository.findById(saveDto.getWpId());
        if( workplaceOp.isPresent() ){
            saveDto.setWpName(workplaceOp.get().getWpName());
        }
        else {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 현장입니다.");
        }

        Optional<WorkPlaceCoop> workPlaceCoopOp = workPlaceCoopRepository.findByWpIdAndCoopMbId(saveDto.getWpId(), saveDto.getCoopMbId());
        if( workPlaceCoopOp.isPresent()){
            saveDto.setWpcId( workPlaceCoopOp.get().getWpcId());
            saveDto.setCoopMbName( workPlaceCoopOp.get().getCoopMbName());
        }
        else {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "해당 현장에 편입되지 않은 협력사입니다.");
        }

        Optional<WorkSection> workSectionAOp = workSectionRepository.findById(saveDto.getWorkSectionA());
        if( workSectionAOp.isPresent()){
            if(StringUtils.isNotEmpty(workSectionAOp.get().getParentSectionCd()) ){
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "선택된 공정A 는 하위 공정입니다.");
            }
            saveDto.setRcWork1( workSectionAOp.get().getSectionName());

            if(StringUtils.isNotEmpty(saveDto.getWorkSectionB()) ){

                Optional<WorkSection> workSectionBOp = workSectionRepository.findById(saveDto.getWorkSectionB());
                if( workSectionBOp.isPresent()){
                    if(StringUtils.isEmpty(workSectionBOp.get().getParentSectionCd()) ){
                        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "선택된 공정B 는 상위공정입니다.");
                    }
                    saveDto.setRcWork2( workSectionAOp.get().getSectionName());
                }
                else {
                    throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 공정B 입니다.");
                }
            }

        }
        else {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 공정A 입니다.");
        }

        recruitDao.create(saveDto);
    }


    @Transactional("mybatisTransactionManager")
    public void update(UpdateRecruitRequest request, int rcIdx) {
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        if ( request.getRcIdx() != rcIdx) {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "아이디가 일치하지 않습니다.");
        }

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        RecruitDto saveDto = modelMapper.map(request, RecruitDto.class);

        Optional<WorkPlace>  workplaceOp = workPlaceRepository.findById(saveDto.getWpId());
        if( workplaceOp.isPresent() ){
            saveDto.setWpName(workplaceOp.get().getWpName());
        }
        else {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 현장입니다.");
        }

        Optional<WorkPlaceCoop> workPlaceCoopOp = workPlaceCoopRepository.findByWpIdAndCoopMbId(saveDto.getWpId(), saveDto.getCoopMbId());
        if( workPlaceCoopOp.isPresent()){
            saveDto.setWpcId( workPlaceCoopOp.get().getWpcId());
            saveDto.setCoopMbName( workPlaceCoopOp.get().getCoopMbName());
        }
        else {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "해당 현장에 편입되지 않은 협력사입니다.");
        }

        Optional<WorkSection> workSectionAOp = workSectionRepository.findById(saveDto.getWorkSectionA());
        if( workSectionAOp.isPresent()){
            if(StringUtils.isNotEmpty(workSectionAOp.get().getParentSectionCd()) ){
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "선택된 공정A 는 하위 공정입니다.");
            }
            saveDto.setRcWork1( workSectionAOp.get().getSectionName());

            if(StringUtils.isNotEmpty(saveDto.getWorkSectionB()) ){

                Optional<WorkSection> workSectionBOp = workSectionRepository.findById(saveDto.getWorkSectionB());
                if( workSectionBOp.isPresent()){
                    if(StringUtils.isEmpty(workSectionBOp.get().getParentSectionCd()) ){
                        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "선택된 공정B 는 상위공정입니다.");
                    }
                    saveDto.setRcWork2( workSectionAOp.get().getSectionName());
                }
                else {
                    throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 공정B 입니다.");
                }
            }
        }
        else {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 공정A 입니다.");
        }

        Optional<Recruit> existsInfo = recruitRepository.findById(rcIdx);
        if (existsInfo.isPresent()) {
            recruitDao.update(saveDto);
        }
        else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }


    }
    @Transactional("mybatisTransactionManager")
    public void updateExpose(int rcIdx) {
        Optional<Recruit> existsInfo = recruitRepository.findById(rcIdx);
        if (existsInfo.isPresent()) {
            Recruit recruit = existsInfo.get();
            if( System.currentTimeMillis() - recruit.getRcUpdatetime().getTime() < 24 * 60 * 60 * 1000 ){
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "구인 등록 후 24시간이 지나지 않았습니다.");
            }
            recruitDao.updateExposeTime(rcIdx);
        }
        else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }
    }

    @Transactional("transactionManager")
    public int delete(int rcIdx) {
        recruitRepository.deleteById(rcIdx);
        return 1;
    }

    @Transactional("transactionManager")
    public int deleteMultiple(List<Integer> rcIdxs) {
        int deleteCnt = 0;
        for (Integer rcIdx : rcIdxs) {
            deleteCnt += delete(rcIdx);
        }
        return deleteCnt;
    }
}
