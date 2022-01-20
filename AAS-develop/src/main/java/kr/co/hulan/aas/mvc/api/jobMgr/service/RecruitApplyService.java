package kr.co.hulan.aas.mvc.api.jobMgr.service;

import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.MemberLevel;
import kr.co.hulan.aas.common.code.RecruitApplyStatus;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.common.utils.GenerateIdUtils;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.jobMgr.controller.request.*;
import kr.co.hulan.aas.mvc.api.jobMgr.controller.response.ListRecruitApplyResponse;
import kr.co.hulan.aas.mvc.api.sensorLog.controller.request.NotifyAlarmRequest;
import kr.co.hulan.aas.mvc.dao.mapper.RecruitApplyDao;
import kr.co.hulan.aas.mvc.dao.mapper.RecruitDao;
import kr.co.hulan.aas.mvc.dao.mapper.WorkPlaceWorkerDao;
import kr.co.hulan.aas.mvc.dao.repository.*;
import kr.co.hulan.aas.mvc.model.domain.G5Member;
import kr.co.hulan.aas.mvc.model.domain.Recruit;
import kr.co.hulan.aas.mvc.model.domain.RecruitApply;
import kr.co.hulan.aas.mvc.model.domain.WorkPlace;
import kr.co.hulan.aas.mvc.model.domain.WorkPlaceCoop;
import kr.co.hulan.aas.mvc.model.dto.*;
import kr.co.hulan.aas.mvc.service.push.PushData;
import kr.co.hulan.aas.mvc.service.push.PushService;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RecruitApplyService {

    private Logger logger = LoggerFactory.getLogger(RecruitApplyService.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private WorkPlaceCoopRepository workPlaceCoopRepository;

    @Autowired
    private WorkPlaceWorkerRepository workPlaceWorkerRepository;

    @Autowired
    private WorkPlaceWorkerDao workPlaceWorkerDao;

    @Autowired
    private G5MemberRepository g5MemberRepository;

    @Autowired
    private RecruitRepository recruitRepository;

    @Autowired
    private PushService pushService;

    @Autowired
    private RecruitDao recruitDao;

    @Autowired
    private RecruitApplyRepository recruitApplyRepository;

    @Autowired
    private RecruitApplyDao recruitApplyDao;

    @Autowired
    private WorkPlaceRepository workPlaceRepository;

    public ListRecruitApplyResponse findListPage(ListRecruitApplyRequest request) {
        return new ListRecruitApplyResponse(
                findListCountByCondition(request.getConditionMap()),
                findListByCondition(request.getConditionMap())
        );
    }


    public List<RecruitApplyDto> findListByCondition(Map<String, Object> conditionMap) {
        return recruitApplyDao.findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }

    private Long findListCountByCondition(Map<String, Object> conditionMap) {
        return recruitApplyDao.findListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }


    public RecruitApplyDto findById(int raIdx) {
        RecruitApplyDto RecruitDto = recruitApplyDao.findById(raIdx);
        if (RecruitDto != null) {
            return RecruitDto;
        } else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }
    }

    @Transactional("transactionManager")
    public Integer create(CreateRecruitApplyRequest request){
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        G5Member g5Member = g5MemberRepository.findByMbId(request.getMbId()).orElse(null);
        if( g5Member == null ){
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 근로자입니다.");
        }

        RecruitApply recruitApply = modelMapper.map(request, RecruitApply.class);
        recruitApply.setRcIdx(0);
        recruitApply.setMbId(g5Member.getMbId());
        recruitApply.setMbName(g5Member.getName());
        recruitApply.setMbBirth(g5Member.getBirthday());

        if (MemberLevel.get(loginUser.getMbLevel()) == MemberLevel.COOPERATIVE_COMPANY ) {
            if( StringUtils.isBlank(recruitApply.getCoopMbId())){
                recruitApply.setCoopMbId( loginUser.getMbId() );
            }
            else if( !StringUtils.equals(recruitApply.getCoopMbId(), loginUser.getMbId() ) ){
                throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), "협력사가 일치하지 않습니다.");
            }
        }
        else if (MemberLevel.get(loginUser.getMbLevel()) == MemberLevel.FIELD_MANAGER ) {
            if( StringUtils.isBlank(recruitApply.getWpId())){
                recruitApply.setWpId( loginUser.getWpId() );
            }
            else if( !StringUtils.equals(recruitApply.getWpId(), loginUser.getWpId() ) ){
                logger.warn(this.getClass().getSimpleName()+"|NOT_EQUAL_WP_ID|"+loginUser.getWpId()+"|"+recruitApply.getWpId());
                throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), "현장 정보가 일치하지 않습니다.");
            }
        }


        if( StringUtils.isNotBlank(recruitApply.getWpId())){
            Optional<WorkPlace> workplaceOp = workPlaceRepository.findById(recruitApply.getWpId());
            if( workplaceOp.isPresent()){
                recruitApply.setWpName( workplaceOp.get().getWpName());
            }
            else {
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 현장입니다.");
            }
        }
        else {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "현장 정보는 필수입니다");
        }

        if( StringUtils.isNotBlank(recruitApply.getCoopMbId())){
            Optional<WorkPlaceCoop> coopOp = workPlaceCoopRepository.findByWpIdAndCoopMbId(recruitApply.getWpId(), recruitApply.getCoopMbId());
            if( coopOp.isPresent()){
                recruitApply.setCoopMbName( coopOp.get().getCoopMbName());
            }
            else {
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "해당 현장에 폅입되지 않은 협력사 정보입니다.");
            }
        }
        else {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "협력사 정보는 필수입니다");
        }

        long count = workPlaceWorkerRepository.countByWpIdAndWorkerMbId(recruitApply.getWpId(), recruitApply.getMbId());
        if( count > 0 ) {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "이미 해당 현장에 등록된 근로자입니다.");
        }

        long requestCount = recruitApplyRepository.countByWpIdAndCoopMbIdAndMbIdAndRaStatusNot(recruitApply.getWpId(), recruitApply.getCoopMbId(), recruitApply.getMbId(), RecruitApplyStatus.TRANSFER_WORKPLACE.getCode());;
        if( requestCount > 0 ) {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "이미 해당 현장에 편입 요청된 근로자입니다.");
        }

        recruitApply.setRaStatus(RecruitApplyStatus.UNDER_SCREENING.getCode());
        recruitApplyRepository.saveAndFlush(recruitApply);
        return recruitApply.getRaIdx();
    }



    @Transactional("mybatisTransactionManager")
    public void askRecruiteApply(int raIdx) {
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        RecruitApplyDto saveDto = recruitApplyDao.findById(raIdx);
        if( saveDto != null ){
            if( RecruitApplyStatus.get(saveDto.getRaStatus()) == RecruitApplyStatus.READY ){
                saveDto.setRaStatus(RecruitApplyStatus.UNDER_SCREENING.getCode());
                recruitApplyDao.updateStatus(saveDto);
            }
            else {
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "해당 구인신청은 현재 ["+RecruitApplyStatus.READY.getName()+"] 상태가 아닙니다.");
            }
        }
        else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }

    }

    @Transactional("mybatisTransactionManager")
    public void transferRecruiteApply(int raIdx) {
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        RecruitApplyDto saveDto = recruitApplyDao.findById(raIdx);
        if( saveDto != null ){
            if( RecruitApplyStatus.get(saveDto.getRaStatus()) == RecruitApplyStatus.UNDER_SCREENING ){

                ModelMapper modelMapper = new ModelMapper();
                modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
                WorkPlaceWorkerDto saveWpWDto = modelMapper.map(saveDto, WorkPlaceWorkerDto.class);

                Optional<WorkPlace> workplaceOp = workPlaceRepository.findById(saveDto.getWpId());
                if( workplaceOp.isPresent()){
                    saveDto.setWpName( workplaceOp.get().getWpName());
                }
                else {
                    throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 현장입니다.");
                }


                if( StringUtils.isNotBlank(saveDto.getCoopMbId())){
                    Optional<WorkPlaceCoop> coopOp = workPlaceCoopRepository.findByWpIdAndCoopMbId(saveWpWDto.getWpId(), saveWpWDto.getCoopMbId());
                    if( coopOp.isPresent()){
                        saveWpWDto.setWpcId( coopOp.get().getWpcId());
                        saveWpWDto.setCoopMbId( coopOp.get().getCoopMbId());
                        saveWpWDto.setCoopMbName( coopOp.get().getCoopMbName());
                        saveWpWDto.setWpcWork( coopOp.get().getWpcWork());
                    }
                    else {
                        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "해당 현장에 폅입되지 않은 협력사 정보입니다.");
                    }
                }
                else {
                    saveWpWDto.setWpcId("");
                    saveWpWDto.setCoopMbId("");
                    saveWpWDto.setCoopMbName("");
                    saveWpWDto.setWpcWork("");
                }

                Optional<G5Member> workerOp = g5MemberRepository.findByMbId(saveDto.getMbId());
                if( workerOp.isPresent() && workerOp.get().getMbLevel() == MemberLevel.WORKER.getCode()){
                    saveWpWDto.setWorkerMbId( workerOp.get().getMbId());
                    saveWpWDto.setWorkerMbName( workerOp.get().getName());
                }
                else {
                    throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 근로자입니다.");
                }

                /*
                RecruitDto rDto = recruitDao.findById(saveDto.getRcIdx());
                if( rDto == null ){
                    throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 구인정보입니다.");
                }
                 */

                long count = workPlaceWorkerRepository.countByWpIdAndWorkerMbId(saveDto.getWpId(), saveDto.getMbId());
                if( count == 0 ){
                    saveDto.setRaStatus(RecruitApplyStatus.TRANSFER_WORKPLACE.getCode());
                    recruitApplyDao.updateStatus(saveDto);

                    RecruitDto rDto = recruitDao.findById(saveDto.getRcIdx());
                    if( rDto == null ){
                        saveWpWDto.setWorkSectionB(null);
                    }
                    else {
                        saveWpWDto.setWorkSectionB(StringUtils.defaultIfEmpty(rDto.getWorkSectionB(), null));
                    }
                    saveWpWDto.setWpwOut(0);
                    saveWpWDto.setWpwOutMemo("");
                    saveWpWDto.setWpwBp(0);
                    saveWpWDto.setWpwOper(0);
                    saveWpWDto.setWpwDis1(0);
                    saveWpWDto.setWpwDis2(0);
                    saveWpWDto.setWpwDis3(0);
                    saveWpWDto.setWpwDis4(0);
                    saveWpWDto.setWpwShow(1);
                    saveWpWDto.setWpwStatus(0);
                    saveWpWDto.setWpwId(GenerateIdUtils.getUuidKey());
                    workPlaceWorkerDao.create(saveWpWDto);

                    G5Member workerInfo = workerOp.get();

                    if(StringUtils.isNotBlank(workerInfo.getDeviceId())){
                        PushData push = new PushData();
                        push.setSubject( "근로자 현장편입 승인" );
                        StringBuilder content = new StringBuilder();
                        content.append(saveDto.getMbName()).append("님은 ");
                        content.append(saveDto.getWpName()).append(" 현장에 편입되었습니다.");
                        content.append(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
                        push.setBody( content.toString());
                        push.setCode("working");
                        push.addTargetInfo(workerInfo.getMbId(), workplaceOp.get().getWpId() ,workerInfo.getDeviceId(), workerInfo.getAppVersion());

                        pushService.sendPush(push);
                    }

                }
                else {
                    throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "이미 해당 현장에 등록된 근로자입니다.");
                }
            }
            else {
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "해당 구인신청은 현재 ["+RecruitApplyStatus.UNDER_SCREENING.getName()+"] 상태가 아닙니다.");
            }
        }
        else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }
    }

    

    @Transactional("transactionManager")
    public int delete(int raIdx) {
        RecruitApplyDto savedDto = recruitApplyDao.findById(raIdx);
        if( savedDto != null  ){
            recruitApplyRepository.deleteById(raIdx);
            sendAlarm(savedDto);
        }
        return 1;
    }

    @Transactional("transactionManager")
    public int deleteMultiple(List<Integer> raIdxs) {
        int deleteCnt = 0;
        List<RecruitApplyDto> dtoList = new ArrayList<RecruitApplyDto>();
        for (Integer raIdx : raIdxs) {
            RecruitApplyDto savedDto = recruitApplyDao.findById(raIdx);
            if( savedDto != null  ){
                recruitApplyRepository.deleteById(raIdx);
                dtoList.add(savedDto);
                deleteCnt ++;
            }
        }
        for( RecruitApplyDto dto : dtoList){
            sendAlarm(dto);
        }
        return deleteCnt;
    }

    private void sendAlarm(RecruitApplyDto recruitApply){
        if( StringUtils.isNotBlank(recruitApply.getDeviceId())){
            PushData push = new PushData();
            push.setSubject("근로자 현장편입 거절");
            StringBuilder content = new StringBuilder();
            content.append(recruitApply.getMbName()).append("님은 ");
            content.append(recruitApply.getWpName()).append(" 현장에 거절되었습니다.");
            content.append(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
            push.setBody( content.toString());
            push.setCode("working_deny");

            push.addTargetInfo(recruitApply.getMbId(), recruitApply.getWpId() ,recruitApply.getDeviceId(), recruitApply.getAppVersion());
            pushService.sendPush(push);
        }


    }
}
