package kr.co.hulan.aas.mvc.api.workplace.service;

import java.util.Date;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.MemberLevel;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.common.utils.GenerateIdUtils;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.workplace.controller.request.*;
import kr.co.hulan.aas.mvc.api.workplace.controller.response.ListWorkplaceWorkerResponse;
import kr.co.hulan.aas.mvc.dao.mapper.WorkPlaceWorkerDao;
import kr.co.hulan.aas.mvc.dao.repository.*;
import kr.co.hulan.aas.mvc.model.domain.*;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceWorkerDto;
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
public class WorkplaceWorkerService {

    @Autowired
    G5MemberRepository g5MemberRepository;

    @Autowired
    private WorkPlaceWorkerRepository workPlaceWorkerRepository;

    @Autowired
    private WorkPlaceCoopRepository workPlaceCoopRepository;

    @Autowired
    private WorkPlaceWorkerDao workPlaceWorkerDao;

    @Autowired
    private WorkPlaceRepository workPlaceRepository;

    @Autowired
    private WorkSectionRepository workSectionRepository;

    @Autowired
    private CoopWorkerRepository coopWorkerRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    @Qualifier("entityManagerFactory")
    EntityManager entityManager;

    public ListWorkplaceWorkerResponse findListPage(ListWorkplaceWorkerRequest request) {
        return new ListWorkplaceWorkerResponse(
                findListCountByCondition(request.getConditionMap()),
                findListByCondition(request.getConditionMap())
        );
    }


    public List<WorkPlaceWorkerDto> findListByCondition(Map<String,Object> conditionMap) {
        return workPlaceWorkerDao.findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }

    private Long findListCountByCondition(Map<String,Object> conditionMap) {
        return workPlaceWorkerDao.findListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }


    public WorkPlaceWorkerDto findById(String wpwId){
        WorkPlaceWorkerDto workPlaceWorkerDto = workPlaceWorkerDao.findById(wpwId);
        if ( workPlaceWorkerDto != null ) {
            return workPlaceWorkerDto;
        } else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }
    }


    @Transactional("transactionManager")
    public WorkPlaceWorkerDto create(CreateWorkplaceWorkerRequest request) {
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        WorkPlaceWorker saveDto = modelMapper.map(request, WorkPlaceWorker.class);

        Optional<WorkPlace> workplaceOp = workPlaceRepository.findById(saveDto.getWpId());
        if( workplaceOp.isPresent()){
            saveDto.setWpName( workplaceOp.get().getWpName());
        }
        else {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 현장입니다.");
        }

        Optional<G5Member> coopMemberOp = g5MemberRepository.findByMbId(saveDto.getCoopMbId());
        if( coopMemberOp.isPresent() && coopMemberOp.get().getMbLevel() == MemberLevel.COOPERATIVE_COMPANY.getCode()){
          saveDto.setCoopMbName( coopMemberOp.get().getName());
        }
        else {
          throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 협력사입니다.");
        }
        G5Member coopMember = coopMemberOp.get();

        Optional<G5Member> workerOp = g5MemberRepository.findByMbId(saveDto.getWorkerMbId());
        if( workerOp.isPresent() && workerOp.get().getMbLevel() == MemberLevel.WORKER.getCode()){
          saveDto.setWorkerMbName( workerOp.get().getName());
        }
        else {
          throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 근로자입니다.");
        }

        WorkPlaceCoop coopOp = workPlaceCoopRepository.findByWpIdAndCoopMbId(saveDto.getWpId(), saveDto.getCoopMbId()).orElse(null);
        if( coopOp == null ){
          Optional<WorkSection> wsOp = workSectionRepository.findById(coopMember.getWorkSectionA());
          coopOp = WorkPlaceCoop.builder()
              .wpcId(GenerateIdUtils.getUuidKey())
              .wpId(workplaceOp.get().getWpId())
              .wpName(workplaceOp.get().getWpName())
              .coopMbId(coopMember.getMbId())
              .coopMbName(coopMember.getName())
              .workSectionA(coopMember.getWorkSectionA())
              .wpcWork(wsOp.isPresent() ? wsOp.get().getSectionName() : "")
              .build();
          workPlaceCoopRepository.save(coopOp);
        }

        saveDto.setWpcId( coopOp.getWpcId());
        saveDto.setCoopMbId( coopOp.getCoopMbId());
        saveDto.setWpcWork( coopOp.getWpcWork());
        if( StringUtils.isNotEmpty(saveDto.getWorkSectionB())){
          Optional<WorkSection> workSectionBOp = workSectionRepository.findById(saveDto.getWorkSectionB());
          if( workSectionBOp.isPresent() ){
            WorkSection sectionB = workSectionBOp.get();
            if( StringUtils.isEmpty(coopOp.getWorkSectionA())){
              saveDto.setWorkSectionB(null);
            }
            else if( !StringUtils.equals(sectionB.getParentSectionCd(), coopOp.getWorkSectionA()) ){
              throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "협력사의 하위공종(공종B)을 설정하셔야 합니다.");
            }
          }
          else {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "하위공종(공종B)이 존재하지 않습니다.");
          }
        }
        else {
          saveDto.setWorkSectionB(null);
        }

        long existsWorkerCount = workPlaceWorkerRepository.countByWpIdAndWorkerMbId(saveDto.getWpId(), saveDto.getWorkerMbId());
        if (existsWorkerCount > 0) {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "이미 편입된 근로자입니다.");
        }

        saveDto.setWpwId(GenerateIdUtils.getUuidKey());

        workPlaceWorkerRepository.save(saveDto);

        CoopWorker coopWorker = coopWorkerRepository.findById(saveDto.getWorkerMbId()).orElse(null);
        if( coopWorker != null ){
          if( !StringUtils.equals(coopWorker.getCoopMbId(), saveDto.getCoopMbId())){
            coopWorkerRepository.delete(coopWorker);
            coopWorkerRepository.flush();
            coopWorkerRepository.save(CoopWorker.builder()
                .workerMbId(saveDto.getWorkerMbId())
                .coopMbId(saveDto.getCoopMbId())
                .build());
          }
        }
        else {
          coopWorkerRepository.save(CoopWorker.builder()
              .workerMbId(saveDto.getWorkerMbId())
              .coopMbId(saveDto.getCoopMbId())
              .build());
        }
        return modelMapper.map(saveDto, WorkPlaceWorkerDto.class);
    }


    @Transactional("transactionManager")
    public void update(UpdateWorkplaceWorkerRequest request, String wpwId) {
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        if (!StringUtils.equals(request.getWpwId(), wpwId)) {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "아이디가 일치하지 않습니다.");
        }

        Optional<WorkPlaceWorker> duplicated = workPlaceWorkerRepository.findById(wpwId);
        if (duplicated.isPresent()) {
            WorkPlaceWorker saveDto = duplicated.get();
            modelMapper.map(request, saveDto);

            Optional<WorkPlace> workplaceOp = workPlaceRepository.findById(saveDto.getWpId());
            if( workplaceOp.isPresent()){
              saveDto.setWpName( workplaceOp.get().getWpName());
            }
            else {
              throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 현장입니다.");
            }

            Optional<G5Member> coopMemberOp = g5MemberRepository.findByMbId(saveDto.getCoopMbId());
            if( coopMemberOp.isPresent() && coopMemberOp.get().getMbLevel() == MemberLevel.COOPERATIVE_COMPANY.getCode()){
              saveDto.setCoopMbName( coopMemberOp.get().getName());
            }
            else {
              throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 협력사입니다.");
            }

            Optional<G5Member> workerOp = g5MemberRepository.findByMbId(saveDto.getWorkerMbId());
            if( workerOp.isPresent() && workerOp.get().getMbLevel() == MemberLevel.WORKER.getCode()){
              saveDto.setWorkerMbName( workerOp.get().getName());
            }
            else {
              throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 근로자입니다.");
            }

            Optional<WorkPlaceCoop> coopOp = workPlaceCoopRepository.findByWpIdAndCoopMbId(saveDto.getWpId(), saveDto.getCoopMbId());
            if( coopOp.isPresent()){
              WorkPlaceCoop coop = coopOp.get();

              saveDto.setWpcId( coopOp.get().getWpcId());
              saveDto.setCoopMbId( coopOp.get().getCoopMbId());
              saveDto.setWpcWork( coopOp.get().getWpcWork());
              if( StringUtils.isNotBlank(saveDto.getWorkSectionB())){
                Optional<WorkSection> workSectionBOp = workSectionRepository.findById(saveDto.getWorkSectionB());
                if( workSectionBOp.isPresent() ){
                  WorkSection sectionB = workSectionBOp.get();
                  if( StringUtils.isBlank(coop.getWorkSectionA())){
                    saveDto.setWorkSectionB(null);
                  }
                  else if( !StringUtils.equals(sectionB.getParentSectionCd(), coop.getWorkSectionA()) ){
                    throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "협력사의 하위공종(공종B)을 설정하셔야 합니다.");
                  }
                }
                else {
                  throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "하위공종(공종B)이 존재하지 않습니다.");
                }
              }
              else {
                saveDto.setWorkSectionB(null);
              }
              workPlaceWorkerRepository.save(saveDto);

              CoopWorker newCoopWorker = CoopWorker.builder()
                  .workerMbId(saveDto.getWorkerMbId())
                  .coopMbId(saveDto.getCoopMbId())
                  .createDatetime(new Date())
                  .updateDatetime(new Date())
                  .build();

              CoopWorker coopWorker = coopWorkerRepository.findById(saveDto.getWorkerMbId()).orElse(null);
              if( coopWorker != null ){
                if( !StringUtils.equals(coop.getCoopMbId(), coopWorker.getCoopMbId())){

                  newCoopWorker.setCreateDatetime(coopWorker.getCreateDatetime());
                  newCoopWorker.setUpdateDatetime(coopWorker.getUpdateDatetime());

                  coopWorkerRepository.delete(coopWorker);
                  coopWorkerRepository.flush();

                  coopWorkerRepository.save(newCoopWorker);
                }
              }
              else {
                coopWorkerRepository.save(newCoopWorker);
              }
            }
            else {
              throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "해당 현장에 편입되지 않은 협력사 정보입니다.");
            }
        }
        else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }
    }


    @Transactional("transactionManager")
    public int delete(String wpwId) {
        workPlaceWorkerRepository.deleteById(wpwId);
        return 1;
    }

    @Transactional("transactionManager")
    public int deleteMultiple(List<String> wpwIds) {
        int deleteCnt = 0;
        for (String wpwId : wpwIds) {
            deleteCnt += delete(wpwId);
        }
        return deleteCnt;
    }
}
