package kr.co.hulan.aas.mvc.api.member.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.MemberLevel;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.model.req.Condition;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.common.utils.JpaCriteriaUtils;
import kr.co.hulan.aas.common.utils.MysqlPasswordEncoder;
import kr.co.hulan.aas.config.properties.LoginPolicyProperties;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.infra.StorageClient;
import kr.co.hulan.aas.infra.storage.DeleteFileCommand;
import kr.co.hulan.aas.mvc.api.file.service.FileService;
import kr.co.hulan.aas.mvc.api.member.controller.request.CreateWorkerRequest;
import kr.co.hulan.aas.mvc.api.member.controller.request.ListWorkerRequest;
import kr.co.hulan.aas.mvc.api.member.controller.request.UpdateWorkerRequest;
import kr.co.hulan.aas.mvc.api.member.controller.response.ListWorkerResponse;
import kr.co.hulan.aas.mvc.api.member.dto.SuperAdminDto;
import kr.co.hulan.aas.mvc.api.member.dto.WorkerDto;
import kr.co.hulan.aas.mvc.dao.mapper.*;
import kr.co.hulan.aas.mvc.dao.repository.CoopWorkerRepository;
import kr.co.hulan.aas.mvc.dao.repository.G5MemberRepository;
import kr.co.hulan.aas.mvc.model.domain.CoopWorker;
import kr.co.hulan.aas.mvc.model.domain.G5Member;
import kr.co.hulan.aas.mvc.model.dto.CoopWorkerDto;
import kr.co.hulan.aas.mvc.model.dto.G5MemberDto;
import kr.co.hulan.aas.mvc.model.dto.HulanSequenceDto;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WorkerService {

  @Autowired
  MbKeyGenerateService mbKeyGenerateService;

  @Autowired
  G5MemberRepository g5MemberRepository;

  @Autowired
  CoopWorkerRepository coopWorkerRepository;

  @Autowired
  G5MemberDao g5MemberDao;

  @Autowired
  CoopWorkerDao coopWorkerDao;

  @Autowired
  WorkPlaceWorkerDao workPlaceWorkerDao;

  @Autowired
  WorkerCheckDao workerCheckDao;

  @Autowired
  RecruitApplyDao recruitApplyDao;

  @Autowired
  FileService fileService;

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  @Qualifier("entityManagerFactory")
  EntityManager entityManager;

  @Autowired
  MysqlPasswordEncoder mysqlPasswordEncoder;

  @Autowired
  StorageClient storageClient;

  @Autowired
  LoginPolicyProperties loginPolicyProperties;

  @Transactional("mybatisTransactionManager")
  public WorkerDto createWorker(CreateWorkerRequest request) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }

    G5MemberDto worker = modelMapper.map(request, G5MemberDto.class);
    Optional<G5Member> duplicatedWorker = g5MemberRepository.findByMbId(worker.getMbId());
    if (duplicatedWorker.isPresent()) {
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "이미 존재하는 아이디입니다.");
    }

    Map<String,Object> condition = new HashMap<String,Object>();
    condition.put("mb_key", worker.getMbId());
    if (g5MemberDao.findDuplicatedMemberCount(condition) > 0) {
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "휴대전화번호(사업자등록번호), 연락처는 하나라도 중복등록이 불가능합니다.");
    }

    worker.setMbLevel(MemberLevel.WORKER.getCode());
    worker.setMbIp(AuthenticationHelper.getRemoteIp());

    if (StringUtils.isNotEmpty(worker.getMbPassword())) {
      worker.setMbPassword(mysqlPasswordEncoder.encode(worker.getMbPassword()));
    }

    if (StringUtils.isNotEmpty(request.getSafetyEducationFile())) {
      fileService.moveTempFile(request.getSafetyEducationFile(), fileService.getSafeFilePath());
    }

    worker.setMemberShipNo(mbKeyGenerateService.generateKey());

    g5MemberDao.createWorker(worker);

    if( StringUtils.isNotEmpty( request.getRegCoopMbId() )){
      CoopWorkerDto coopWorkerDto = coopWorkerDao.findById(worker.getMbId());
      if( coopWorkerDto == null ){
        coopWorkerDao.create(CoopWorkerDto.builder().workerMbId(worker.getMbId()).coopMbId(request.getRegCoopMbId()).build());
      }
      else if( !StringUtils.equals(coopWorkerDto.getCoopMbId() , request.getRegCoopMbId())){
        coopWorkerDao.delete(worker.getMbId());
        coopWorkerDao.create(CoopWorkerDto.builder().workerMbId(worker.getMbId()).coopMbId(request.getRegCoopMbId()).build());
      }
    }
    else {
      coopWorkerDao.delete(worker.getMbId());
    }

    return modelMapper.map(worker, WorkerDto.class);
    // return findWorkerByMbId( worker.getMbId());
  }

  @Transactional("mybatisTransactionManager")
  public void updateWorker(UpdateWorkerRequest request, String mbId) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }

    if (!StringUtils.equals(request.getMbId(), mbId)) {
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "아이디가 일치하지 않습니다.");
    }

    G5MemberDto worker = modelMapper.map(request, G5MemberDto.class);
    if( StringUtils.isNotBlank(worker.getTelephone())){
      Map<String,Object> condition = new HashMap<String,Object>();
      condition.put("mb_key", worker.getTelephone());
      condition.put("current_mb_id", worker.getMbId());
      if (g5MemberDao.findDuplicatedMemberCount(condition) > 0) {
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "휴대전화번호(사업자등록번호), 연락처는 하나라도 중복등록이 불가능합니다.");
      }
    }

    Optional<G5Member> existWorker = g5MemberRepository.findByMbId(worker.getMbId());
    if (existWorker.isPresent()) {
      G5Member origin = existWorker.get();

      DeleteFileCommand deleteCommand = new DeleteFileCommand();

      if( StringUtils.isNotBlank(existWorker.get().getSafetyEducationFile())){
        if( StringUtils.isBlank( worker.getSafetyEducationFile() ) ){
          deleteCommand.addFilePath(fileService.getSafeFilePath()+ File.separator+existWorker.get().getSafetyEducationFile());
        }
        else if( !StringUtils.equals( worker.getSafetyEducationFile(), existWorker.get().getSafetyEducationFile()) ){
          deleteCommand.addFilePath(fileService.getSafeFilePath()+ File.separator+existWorker.get().getSafetyEducationFile());
          fileService.moveTempFile(worker.getSafetyEducationFile(), fileService.getSafeFilePath());
        }
      }
      else {
        if (StringUtils.isNotBlank(worker.getSafetyEducationFile())) {
          fileService.moveTempFile(worker.getSafetyEducationFile(), fileService.getSafeFilePath());
        }
      }

      if (StringUtils.isNotEmpty(worker.getMbPassword())) {
        worker.setMbPassword(mysqlPasswordEncoder.encode(worker.getMbPassword()));
      }
      worker.setMbLevel(MemberLevel.WORKER.getCode());

      g5MemberDao.updateWorker(worker);
      workPlaceWorkerDao.updateWorkerMbName(worker);
      workerCheckDao.updateWorkerMbName(worker);
      recruitApplyDao.updateMbName(worker);

      /*
      if( StringUtils.isNotEmpty( request.getRegCoopMbId() )){
        CoopWorkerDto coopWorkerDto = coopWorkerDao.findById(worker.getMbId());
        if( coopWorkerDto == null ){
          coopWorkerDao.create(CoopWorkerDto.builder().workerMbId(worker.getMbId()).coopMbId(request.getRegCoopMbId()).build());
        }
        else if( !StringUtils.equals(coopWorkerDto.getCoopMbId() , request.getRegCoopMbId())){
          coopWorkerDao.delete(worker.getMbId());
          coopWorkerDao.create(CoopWorkerDto.builder().workerMbId(worker.getMbId()).coopMbId(request.getRegCoopMbId()).build());
        }
      }
      else {
        coopWorkerDao.delete(worker.getMbId());
      }
       */

      if( deleteCommand.existsDeleteFiles() ){
        storageClient.requestDeleteFiles(deleteCommand);
      }
    } else {
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
    }
  }

  @Transactional("transactionManager")
  public int deleteWorker(String mbId) {
    DeleteFileCommand deleteCommand = new DeleteFileCommand();
    int deleteCnt =deleteWorkerAction(mbId, deleteCommand);
    if( deleteCommand.existsDeleteFiles() ){
      storageClient.requestDeleteFiles(deleteCommand);
    }
    return deleteCnt;
  }

  @Transactional("transactionManager")
  public int deleteWorkers(List<String> mbIds) {
    int deleteCnt = 0;
    DeleteFileCommand deleteCommand = new DeleteFileCommand();
    for (String mbId : mbIds) {
      deleteCnt += deleteWorkerAction(mbId, deleteCommand);
    }
    if( deleteCommand.existsDeleteFiles() ){
      storageClient.requestDeleteFiles(deleteCommand);
    }
    return deleteCnt;
  }

  private int deleteWorkerAction(String mbId, DeleteFileCommand deleteCommand){
    Optional<G5Member> existWorker = g5MemberRepository.findByMbId(mbId);
    if (existWorker.isPresent()) {
      if( StringUtils.isNotBlank(existWorker.get().getSafetyEducationFile())){
        deleteCommand.addFilePath(fileService.getSafeFilePath()+ File.separator+existWorker.get().getSafetyEducationFile());
      }
      coopWorkerDao.delete(mbId);
      return g5MemberRepository.deleteByMbId(mbId);
    }
    else {
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
    }
  }

  // g5_XXXX 테이블 중 사용하지 않는 것은 조사 제외.

  /* 근로자 삭제시 삭제 필요 테이블 ( mb_id 가 있는 테이블 )
  // alarm_msg_info
  // device_model_policy_info
  // g5_board_new
  // g5_member
  // g5_write_notice
  // g5_write_wpboard
  // gps_device_info
  // gps_location
  // manager_token
  // recruit_apply
  // recruit_wish
  // work_equipment_info
  // work_place_worker
  // worker_check
  // worker_event_hist
  // worker_msg_info
  // worker_warn
  // worker_work_print

  */
  /* 협력사 삭제시 삭제 필요 테이블 ( coop_mb_id 가 있는 테이블 )

  // recruit
  // recruit_apply
  // work_equipment_info
  // work_place_coop
  // work_place_worker
  // worker_check
  // worker_event_hist
  // worker_msg_info
  // worker_warn
  // worker_work_print
   */

  /*  현장 삭제시 삭제 필요 테이블 ( wp_id 가 있는 테이블 )
  // work_geofence_limit
  // work_tag_info
  // worker_check
  // worker_event_hist
  // worker_msg_info
  // worker_warn
  // worker_work_print
   */

  public WorkerDto findWorkerByMbId(String mbId) {
    Optional<G5Member> member = g5MemberRepository.findByMbId(mbId);
    if (member.isPresent()) {
      WorkerDto worker = modelMapper.map(member.get(), WorkerDto.class);
      CoopWorker coopWorker = coopWorkerRepository.findById(mbId).orElse(null);
      if( coopWorker != null ){
        worker.setRegCoopMbId(coopWorker.getCoopMbId());
      }
      return worker;
    } else {
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
    }
  }


  public ListWorkerResponse findWorkerListPage(ListWorkerRequest request) {
    return new ListWorkerResponse(
        findWorkerListCount(request.getConditionMap()),
        findWorkerWithdrawListCount(request),
        findWorkerBlockListCount(request),
        findWorkerList(request.getConditionMap())
    );
  }

  public List<WorkerDto> findWorkerList(Map<String, Object> conditionMap) {
    return g5MemberDao.findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap))
        .stream()
        .map(g5MemberDto -> {
          WorkerDto dto = modelMapper.map(g5MemberDto, WorkerDto.class);
          dto.setLocked( loginPolicyProperties.isLocked(g5MemberDto) );
          dto.setPasswordExpired( loginPolicyProperties.isPasswordExpired(g5MemberDto) );
          return dto;
        })
        .collect(Collectors.toList());
  }

  private Long findWorkerListCount(Map<String, Object> conditionMap) {
    return g5MemberDao.findListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
  }

  private long findWorkerWithdrawListCount(ListWorkerRequest request) {
    Map<String, Object> conditionMap = request.getConditionMap();
    conditionMap.put("withdraw", "withdraw");
    return findWorkerListCount(conditionMap);

  }

  private long findWorkerBlockListCount(ListWorkerRequest request) {
    Map<String, Object> conditionMap = request.getConditionMap();
    conditionMap.put("block", "block");
    return findWorkerListCount(conditionMap);
  }

  /*
  private long findWorkerCount(List<Condition> condition) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

    CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
    Root<G5Member> root = countQuery.from(G5Member.class);
    countQuery.select(criteriaBuilder.count(root));

    Map<ParameterExpression, Object> paramsMap = JpaCriteriaUtils.setPredicate(criteriaBuilder, countQuery, root, condition);

    TypedQuery<Long> listTypedQuery = entityManager.createQuery(countQuery);
    for (Map.Entry<ParameterExpression, Object> entry : paramsMap.entrySet()) {
      listTypedQuery.setParameter(entry.getKey(), entry.getValue());
    }
    return listTypedQuery.getSingleResult();

  }
   */
}
