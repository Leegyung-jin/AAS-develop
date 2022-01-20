package kr.co.hulan.aas.mvc.api.workplace.service;

import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.MemberLevel;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.workplace.controller.request.*;
import kr.co.hulan.aas.mvc.api.workplace.controller.response.ListWorkerCheckResponse;
import kr.co.hulan.aas.mvc.api.workplace.controller.response.ListWorkerWarnResponse;
import kr.co.hulan.aas.mvc.dao.mapper.WorkerCheckDao;
import kr.co.hulan.aas.mvc.dao.mapper.WorkerWarnDao;
import kr.co.hulan.aas.mvc.dao.repository.*;
import kr.co.hulan.aas.mvc.model.domain.*;
import kr.co.hulan.aas.mvc.model.dto.WorkerCheckDto;
import kr.co.hulan.aas.mvc.model.dto.WorkerWarnDto;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class WorkerWarnService {

    @Autowired
    private G5MemberRepository g5MemberRepository;

    @Autowired
    private WorkPlaceWorkerRepository workPlaceWorkerRepository;

    @Autowired
    private WorkPlaceCoopRepository workPlaceCoopRepository;

    @Autowired
    private WorkPlaceRepository workPlaceRepository;

    @Autowired
    private WorkerWarnRepository workerWarnRepository;

    @Autowired
    private WorkerWarnDao workerWarnDao;

    @Autowired
    @Qualifier("entityManagerFactory")
    EntityManager entityManager;

    public ListWorkerWarnResponse findListPage(ListWorkerWarnRequest request) {
        return new ListWorkerWarnResponse(
                findListCountByCondition(request.getConditionMap()),
                findListByCondition(request.getConditionMap())
        );
    }


    public List<WorkerWarnDto> findListByCondition(Map<String,Object> conditionMap) {
        return workerWarnDao.findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }

    private Long findListCountByCondition(Map<String,Object> conditionMap) {
        return workerWarnDao.findListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }


    public WorkerWarnDto findById(int wcIdx){
        WorkerWarnDto workerWarnDto = workerWarnDao.findById(wcIdx);
        if ( workerWarnDto != null ) {
            return workerWarnDto;
        } else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }
    }


    @Transactional("mybatisTransactionManager")
    public void create(CreateWorkerWarnRequest request) {
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        WorkerWarnDto saveDto = modelMapper.map(request, WorkerWarnDto.class);

        Optional<G5Member> loginUserOp = g5MemberRepository.findByMbId(loginUser.getUsername());
        if( loginUserOp.isPresent() ){
            saveDto.setMbId(loginUserOp.get().getMbId());
            saveDto.setMbName(loginUserOp.get().getName());
        }
        else {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 사용자입니다.");
        }


        Optional<WorkPlace> workplaceOp = workPlaceRepository.findById(saveDto.getWpId());
        if( workplaceOp.isPresent()){
            saveDto.setWpName( workplaceOp.get().getWpName());
        }
        else {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 현장입니다.");
        }

        if( StringUtils.isNotBlank(request.getCoopMbId())){
            Optional<WorkPlaceCoop> coopOp = workPlaceCoopRepository.findByWpIdAndCoopMbId(saveDto.getWpId(), saveDto.getCoopMbId());
            if( coopOp.isPresent()){
                saveDto.setCoopMbId( coopOp.get().getCoopMbId());
                saveDto.setCoopMbName( coopOp.get().getCoopMbName());
            }
            else {
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "해당 현장에 폅입되지 않은 협력사 정보입니다.");
            }
        }
        else {
            saveDto.setCoopMbId( "");
            saveDto.setCoopMbName( "");
        }


        Optional<G5Member> workerOp = g5MemberRepository.findByMbId(saveDto.getWorkerMbId());
        if( workerOp.isPresent() && workerOp.get().getMbLevel() == MemberLevel.WORKER.getCode()){
            saveDto.setWorkerMbName( workerOp.get().getName());
        }
        else {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 근로자입니다.");
        }

        long existsWorkerCount = workPlaceWorkerRepository.countByWpIdAndWorkerMbId(saveDto.getWpId(), saveDto.getWorkerMbId());
        if ( existsWorkerCount > 0 ) {
            workerWarnDao.create(saveDto);
        }
        else {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "해당 현장에 편입되지 않은 근로자입니다.");
        }
    }


    @Transactional("mybatisTransactionManager")
    public void update(UpdateWorkerWarnRequest request, int wwIdx) {
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        if ( request.getWwIdx() != wwIdx) {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "아이디가 일치하지 않습니다.");
        }

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        WorkerWarnDto saveDto = modelMapper.map(request, WorkerWarnDto.class);

        Optional<WorkerWarn> duplicated = workerWarnRepository.findById(wwIdx);
        if (duplicated.isPresent()) {
            workerWarnDao.update(saveDto);
        }
        else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }
    }


    @Transactional("transactionManager")
    public int delete(int wcIdx) {
        workerWarnRepository.deleteById(wcIdx);
        return 1;
    }

    @Transactional("transactionManager")
    public int deleteMultiple(List<Integer> wcIdxs) {
        int deleteCnt = 0;
        for (Integer wcIdx : wcIdxs) {
            deleteCnt += delete(wcIdx);
        }
        return deleteCnt;
    }
}
