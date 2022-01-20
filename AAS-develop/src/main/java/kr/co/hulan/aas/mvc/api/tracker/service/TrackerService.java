package kr.co.hulan.aas.mvc.api.tracker.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.tracker.controller.request.AssignTrackerRequest;
import kr.co.hulan.aas.mvc.api.tracker.controller.request.CollectTrackerRequest;
import kr.co.hulan.aas.mvc.api.tracker.controller.request.CreateTrackerRequest;
import kr.co.hulan.aas.mvc.api.tracker.controller.request.ListTrackerRequest;
import kr.co.hulan.aas.mvc.api.tracker.controller.request.UpdateTrackerRequest;
import kr.co.hulan.aas.mvc.api.tracker.controller.response.ListTrackerResponse;
import kr.co.hulan.aas.mvc.dao.mapper.TrackerDao;
import kr.co.hulan.aas.mvc.dao.repository.TrackerAssignLogRepository;
import kr.co.hulan.aas.mvc.dao.repository.TrackerAssignRepository;
import kr.co.hulan.aas.mvc.dao.repository.TrackerRepository;
import kr.co.hulan.aas.mvc.dao.repository.WorkPlaceRepository;
import kr.co.hulan.aas.mvc.dao.repository.WorkPlaceWorkerRepository;
import kr.co.hulan.aas.mvc.model.domain.Tracker;
import kr.co.hulan.aas.mvc.model.domain.TrackerAssign;
import kr.co.hulan.aas.mvc.model.domain.TrackerAssignLog;
import kr.co.hulan.aas.mvc.model.domain.WorkPlace;
import kr.co.hulan.aas.mvc.model.dto.TrackerDto;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TrackerService {

  @Autowired
  TrackerRepository trackerRepository;

  @Autowired
  TrackerAssignRepository trackerAssignRepository;

  @Autowired
  TrackerAssignLogRepository trackerAssignLogRepository;

  @Autowired
  WorkPlaceRepository workPlaceRepository;

  @Autowired
  WorkPlaceWorkerRepository workPlaceWorkerRepository;

  @Autowired
  TrackerDao trackerDao;

  @Autowired
  ModelMapper modelMapper;

  public ListTrackerResponse findListPage(ListTrackerRequest request) {
    return new ListTrackerResponse(
        findListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(request.getConditionMap())),
        findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(request.getConditionMap()))
    );
  }

  public List<TrackerDto> findListByCondition(Map<String,Object> conditionMap) {
    return trackerDao.findListByCondition(conditionMap);
  }

  private Long findListCountByCondition(Map<String,Object> conditionMap) {
    return trackerDao.findListCountByCondition(conditionMap);
  }

  public TrackerDto findById(String trackerId){
    TrackerDto trackerDto = trackerDao.findById(trackerId);
    if (trackerDto != null ) {
      return trackerDto;
    } else {
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
    }
  }


  @Transactional("transactionManager")
  public void create(CreateTrackerRequest request) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }

    Tracker tracker = modelMapper.map(request, Tracker.class);

    Optional<Tracker> existsTrackerOp = trackerRepository.findById(tracker.getTrackerId());
    if( existsTrackerOp.isPresent()){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "이미 등록된 트랙커 아이디입니다.");
    }

    if( StringUtils.isNotEmpty( tracker.getWpId())){
      Optional<WorkPlace> existsWorkplaceOp = workPlaceRepository.findById(tracker.getWpId());
      if( !existsWorkplaceOp.isPresent()){
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 현장입니다.");
      }
    }
    tracker.setCreateDate(new Date());
    tracker.setCreator(loginUser.getUsername());
    tracker.setUpdateDate(new Date());
    tracker.setUpdater(loginUser.getUsername());
    trackerRepository.save(tracker);
  }


  @Transactional("transactionManager")
  public void update(UpdateTrackerRequest request, String trackerId) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }

    if (!StringUtils.equals(request.getTrackerId(), trackerId)) {
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "트랙커 아이디가 일치하지 않습니다.");
    }

    Optional<Tracker> existsTrackerOp = trackerRepository.findById(request.getTrackerId());
    if( existsTrackerOp.isPresent()){
      Tracker existsTracker = existsTrackerOp.get();

      if( !StringUtils.equals( request.getWpId(), existsTracker.getWpId())){
        Optional<TrackerAssign> existsTrackerAssignOp = trackerAssignRepository.findById(existsTracker.getTrackerId());
        if( existsTrackerAssignOp.isPresent()){
          throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "반납되지 않은 트랙커입니다. 반납 후 변경하여주세요.");
        }

        if( StringUtils.isNotEmpty( request.getWpId())){
          Optional<WorkPlace> existsWorkplaceOp = workPlaceRepository.findById(request.getWpId());
          if( !existsWorkplaceOp.isPresent()){
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 현장입니다.");
          }
        }
      }
      modelMapper.map(request, existsTracker);
      existsTracker.setUpdateDate(new Date());
      existsTracker.setUpdater(loginUser.getUsername());
      trackerRepository.save(existsTracker);
    }
    else {
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "등록되지 않은 트랙커입니다.");
    }
  }


  @Transactional("transactionManager")
  public int delete(String trackerId) {
    Optional<Tracker> existsTrackerOp = trackerRepository.findById(trackerId);
    if( existsTrackerOp.isPresent()) {
      Optional<TrackerAssign> existsTrackerAssignOp = trackerAssignRepository.findById(trackerId);
      if (existsTrackerAssignOp.isPresent()) {
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(),
            "반납되지 않은 트랙커입니다. 반납 후 변경하여주세요.");
      }
      trackerRepository.delete(existsTrackerOp.get());
    }
    return 1;
  }

  @Transactional("transactionManager")
  public int deleteMultiple(List<String> trackerIds) {
    int deleteCnt = 0;
    for (String trackerId : trackerIds) {
      deleteCnt += delete(trackerId);
    }
    return deleteCnt;
  }

  @Transactional("transactionManager")
  public void assignTrackerToWorker(AssignTrackerRequest request, String trackerId) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }

    if (!StringUtils.equals(request.getTrackerId(), trackerId)) {
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "트랙커 아이디가 일치하지 않습니다.");
    }

    TrackerAssign trackerAssign = modelMapper.map(request, TrackerAssign.class);
    Optional<Tracker> existsTrackerOp = trackerRepository.findById(trackerAssign.getTrackerId());
    if( existsTrackerOp.isPresent()){
      Tracker existsTracker = existsTrackerOp.get();

      Optional<TrackerAssign> existsTrackerAssignOp = trackerAssignRepository.findById(trackerAssign.getTrackerId());
      if( existsTrackerAssignOp.isPresent()){
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "이미 할당된 트랙커입니다. 반납 후 진행하여주세요.");
      }
      else if( !StringUtils.equals( existsTracker.getWpId(), trackerAssign.getWpId() )){
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "해당 트랙커가 배포된 현장이 아닙니다.");
      }
      else if( workPlaceWorkerRepository.countByWpIdAndCoopMbIdAndWorkerMbId(trackerAssign.getWpId(), trackerAssign.getCoopMbId(), trackerAssign.getMbId()) == 0 ){
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "현장에 편입되지 않은 근로자입니다.");
      }

      TrackerAssignLog trackerAssignLog = new TrackerAssignLog();
      modelMapper.map(trackerAssign, trackerAssignLog);
      trackerAssignLog.setAssignDate(new Date());
      trackerAssignLog.setAssigner(loginUser.getUsername());
      trackerAssignLogRepository.save(trackerAssignLog);

      trackerAssign.setTalNo(trackerAssignLog.getTalNo());
      trackerAssignRepository.save(trackerAssign);
    }
    else {
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "등록되지 않은 트랙커입니다.");
    }
  }

  @Transactional("transactionManager")
  public void collectTrackerFromWorker(CollectTrackerRequest request, String trackerId) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }

    if (!StringUtils.equals(request.getTrackerId(), trackerId)) {
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "트랙커 아이디가 일치하지 않습니다.");
    }

    Optional<Tracker> existsTrackerOp = trackerRepository.findById(trackerId);
    if( existsTrackerOp.isPresent()){
      Tracker existsTracker = existsTrackerOp.get();

      Optional<TrackerAssign> existsTrackerAssignOp = trackerAssignRepository.findById(trackerId);
      if( existsTrackerAssignOp.isPresent()){
        TrackerAssign existsTrackerAssign = existsTrackerAssignOp.get();
        if( !StringUtils.equals( existsTrackerAssign.getMbId(), request.getMbId() )){
          throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "해당 사용자에게 할당된 트랙커가 아닙니다.");
        }

        Optional<TrackerAssignLog> existsTrackerAssignLogOp = trackerAssignLogRepository.findById(existsTrackerAssign.getTalNo());
        if( existsTrackerAssignLogOp.isPresent()){
          TrackerAssignLog existsTrackerAssignLog = existsTrackerAssignLogOp.get();
          existsTrackerAssignLog.setCollectDate(new Date());
          existsTrackerAssignLog.setCollector(loginUser.getUsername());
          trackerAssignLogRepository.save(existsTrackerAssignLog);
        }
        trackerAssignRepository.delete(existsTrackerAssign);
      }
      else {
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "이미 반납된 트랙커입니다.");
      }
    }
    else {
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "등록되지 않은 트랙커입니다.");
    }
  }

}
