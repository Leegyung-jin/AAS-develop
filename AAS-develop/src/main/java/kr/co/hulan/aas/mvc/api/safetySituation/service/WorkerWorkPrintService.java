package kr.co.hulan.aas.mvc.api.safetySituation.service;

import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.MemberLevel;
import kr.co.hulan.aas.common.code.WorkerWorkPrintStatus;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.common.utils.JsonUtil;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.member.controller.request.CreateConstructionCompanyRequest;
import kr.co.hulan.aas.mvc.api.member.controller.request.UpdateConstructionCompanyRequest;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.request.CreateWorkerWorkPrintRequest;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.request.ListCooperativeCompanySafetySituationRequest;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.request.ListWorkerWorkPrintRequest;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.request.UpdateWorkerWorkPrintRequest;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.response.ListCooperativeCompanySafetySituationResponse;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.response.ListWorkerWorkPrintResponse;
import kr.co.hulan.aas.mvc.api.safetySituation.dto.AttendantDto;
import kr.co.hulan.aas.mvc.api.safetySituation.dto.CooperativeCompanySafetySituationDto;
import kr.co.hulan.aas.mvc.api.safetySituation.dto.WorkPrintDto;
import kr.co.hulan.aas.mvc.dao.mapper.SensorLogInoutDao;
import kr.co.hulan.aas.mvc.dao.mapper.WorkPlaceCoopDao;
import kr.co.hulan.aas.mvc.dao.mapper.WorkPlaceWorkerDao;
import kr.co.hulan.aas.mvc.dao.mapper.WorkerWorkPrintDao;
import kr.co.hulan.aas.mvc.dao.repository.ConCompanyRepository;
import kr.co.hulan.aas.mvc.dao.repository.G5MemberRepository;
import kr.co.hulan.aas.mvc.dao.repository.WorkPlaceCoopRepository;
import kr.co.hulan.aas.mvc.dao.repository.WorkPlaceRepository;
import kr.co.hulan.aas.mvc.dao.repository.WorkerWorkPrintRepository;
import kr.co.hulan.aas.mvc.model.domain.ConCompany;
import kr.co.hulan.aas.mvc.model.domain.G5Member;
import kr.co.hulan.aas.mvc.model.domain.WorkPlace;
import kr.co.hulan.aas.mvc.model.domain.WorkPlaceCoop;
import kr.co.hulan.aas.mvc.model.domain.WorkerWorkPrint;
import kr.co.hulan.aas.mvc.model.dto.ConCompanyDto;
import kr.co.hulan.aas.mvc.model.dto.WorkerWorkPrintDto;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WorkerWorkPrintService {

    @Autowired
    WorkerWorkPrintRepository workerWorkPrintRepository;

    @Autowired
    WorkPlaceRepository workPlaceRepository;

    @Autowired
    WorkPlaceCoopRepository workPlaceCoopRepository;

    @Autowired
    ConCompanyRepository conCompanyRepository;

    @Autowired
    G5MemberRepository g5MemberRepository;

    @Autowired
    WorkerWorkPrintDao workerWorkPrintDao;

    @Autowired
    WorkPlaceWorkerDao workPlaceWorkerDao;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    @Qualifier("entityManagerFactory")
    EntityManager entityManager;


    public ListWorkerWorkPrintResponse findListPage(ListWorkerWorkPrintRequest request) {
        return new ListWorkerWorkPrintResponse(
                findListCountByCondition(request.getConditionMap()),
                findListByCondition(request.getConditionMap())
        );
    }

    public List<WorkPrintDto> findListByCondition(Map<String,Object> conditionMap) {
        return workerWorkPrintDao.findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap))
                .stream().map(workerWorkPrint -> {
                    workerWorkPrint.parseWwpData();
                    WorkPrintDto dto = modelMapper.map(workerWorkPrint, WorkPrintDto.class);
                    return dto;
                } ).collect(Collectors.toList());
    }

    private Long findListCountByCondition(Map<String,Object> conditionMap) {
        return workerWorkPrintDao.findListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }


    public WorkPrintDto findById( int wwpIdx ){
        Optional<WorkerWorkPrint> currentPrint = workerWorkPrintRepository.findById(wwpIdx);
        if( currentPrint.isPresent() ){
            WorkerWorkPrint print = currentPrint.get();
            WorkerWorkPrintDto dto = modelMapper.map(print, WorkerWorkPrintDto.class);
            dto.parseWwpData();
            return modelMapper.map(dto, WorkPrintDto.class);
        }
        else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }
    }

    public WorkPrintDto findByWpIdAndCoopMbIdAndWwpDate(String wpId, String coopMbId, Date wwpDate){
        Optional<WorkerWorkPrint> currentPrint = workerWorkPrintRepository.findByWpIdAndCoopMbIdAndWwpDate(wpId, coopMbId, DateUtils.truncate(wwpDate, Calendar.DATE));
        if( currentPrint.isPresent() ){
            WorkerWorkPrint print = currentPrint.get();
            WorkerWorkPrintDto dto = modelMapper.map(print, WorkerWorkPrintDto.class);
            dto.parseWwpData();
            return modelMapper.map(dto, WorkPrintDto.class);
        }
        else {
            throw new CommonException(BaseCode.ERR_NONE_ALERT_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }
    }

    public List<AttendantDto> findAttendants(String wpId, String coopMbId, Date wwpDate){

        Map<String, Object> condition = new HashMap<String,Object>();
        condition.put("wpId", wpId);
        condition.put("coopMbId", coopMbId);
        condition.put("wwpDate", wwpDate);
        return workPlaceWorkerDao.findAttendantListByCondition(condition)
                .stream().map(workPlaceWorker -> modelMapper.map(workPlaceWorker, AttendantDto.class) ).collect(Collectors.toList());
    }

    @Transactional("mybatisTransactionManager")
    public void create(CreateWorkerWorkPrintRequest request) {
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        WorkerWorkPrintDto workerWorkPrint = modelMapper.map(request, WorkerWorkPrintDto.class);
        Optional<WorkerWorkPrint> existsWorkerWorkPrint = workerWorkPrintRepository.findByWpIdAndCoopMbIdAndWwpDate(workerWorkPrint.getWpId(), workerWorkPrint.getCoopMbId(), DateUtils.truncate( workerWorkPrint.getWwpDate(), Calendar.DATE) );
        if( existsWorkerWorkPrint.isPresent() ){
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "이미 작성된 출력일보가 존재합니다.");
        }

        Optional<WorkPlace> existsWorkPlace = workPlaceRepository.findById(workerWorkPrint.getWpId() );
        if( existsWorkPlace.isPresent() ){
            WorkPlace workPlace = existsWorkPlace.get();
            workerWorkPrint.setWpName( workPlace.getWpName());
        }
        else {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 현장입니다.");
        }

        WorkPlaceCoop workplaceCoop = workPlaceCoopRepository.findByWpIdAndCoopMbId(workerWorkPrint.getWpId(), workerWorkPrint.getCoopMbId()).orElse(null);
        if( workplaceCoop == null ){
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "해당 현장에 편입되지 않은 협력사입니다.");
        }

        if( StringUtils.isBlank(workplaceCoop.getCcId())){
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "편입 건설사 정보가 존재하지 않는 협력사입니다. 관리자에게 문의하세요");
        }
        else {
            ConCompany cc = conCompanyRepository.findById(workplaceCoop.getCcId()).orElse(null);
            if( cc == null ){
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "편입 건설사 정보가 존재하지 않는 협력사입니다. 관리자에게 문의하세요");
            }
            workerWorkPrint.setCcId( cc.getCcId());
            workerWorkPrint.setCcName( cc.getCcName());
        }

        Optional<G5Member> existsCoop = g5MemberRepository.findByMbIdAndMbLevel(workerWorkPrint.getCoopMbId(), MemberLevel.COOPERATIVE_COMPANY.getCode());
        if( existsCoop.isPresent() ){
            G5Member coop = existsCoop.get();
            workerWorkPrint.setCoopMbName( coop.getName());
        }
        else {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 협력사입니다.");
        }
        workerWorkPrint.makeWwpData();
        workerWorkPrintDao.create(workerWorkPrint);
    }


    @Transactional("mybatisTransactionManager")
    public void update(UpdateWorkerWorkPrintRequest request, int wwpIdx) {
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        if ( request.getWwpIdx() != wwpIdx ) {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "아이디가 일치하지 않습니다.");
        }

        Optional<WorkerWorkPrint> existsWorkerWorkPrint = workerWorkPrintRepository.findById(wwpIdx);
        if( existsWorkerWorkPrint.isPresent() ){
            WorkerWorkPrintDto workerWorkPrint = modelMapper.map(existsWorkerWorkPrint.get(), WorkerWorkPrintDto.class);
            modelMapper.map(request, workerWorkPrint);
            workerWorkPrint.makeWwpData();
            workerWorkPrintDao.update(workerWorkPrint);
        }
        else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }
    }

    @Transactional("mybatisTransactionManager")
    public void updateStatus(int wwpIdx, int wwpStatus) {
        if( WorkerWorkPrintStatus.get(wwpStatus) == null ){
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "상태값이 올바르지 않습니다.");
        }

        Optional<WorkerWorkPrint> existsWorkerWorkPrint = workerWorkPrintRepository.findById(wwpIdx );
        if( existsWorkerWorkPrint.isPresent() ){
            WorkerWorkPrintDto workerWorkPrint = modelMapper.map(existsWorkerWorkPrint.get(), WorkerWorkPrintDto.class);
            workerWorkPrint.setWwpStatus(wwpStatus);
            workerWorkPrintDao.updateStatus(workerWorkPrint);
        }
        else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }
    }


    @Transactional("transactionManager")
    public int delete(int wwpIdx) {
        workerWorkPrintRepository.deleteById(wwpIdx);
        return 1;
    }

    @Transactional("transactionManager")
    public int deleteMultiple(List<Integer> wwpIdxs) {
        int deleteCnt = 0;
        for (Integer wwpIdx : wwpIdxs) {
            deleteCnt += delete(wwpIdx);
        }
        return deleteCnt;
    }


}
