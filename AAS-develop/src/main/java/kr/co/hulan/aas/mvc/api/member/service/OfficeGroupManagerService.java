package kr.co.hulan.aas.mvc.api.member.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.MemberLevel;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.common.utils.MysqlPasswordEncoder;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.member.controller.request.OfficeGroupManagerCreateRequest;
import kr.co.hulan.aas.mvc.api.member.controller.request.OfficeGroupManagerPagingListRequest;
import kr.co.hulan.aas.mvc.api.member.controller.request.OfficeGroupManagerUpdateRequest;
import kr.co.hulan.aas.mvc.api.member.dto.OfficeGroupManagerDto;
import kr.co.hulan.aas.mvc.api.orderoffice.service.OrderingOfficeMgrService;
import kr.co.hulan.aas.mvc.dao.mapper.G5MemberDao;
import kr.co.hulan.aas.mvc.dao.repository.G5MemberRepository;
import kr.co.hulan.aas.mvc.dao.repository.OfficeWorkplaceGrpRepository;
import kr.co.hulan.aas.mvc.dao.repository.OfficeWorkplaceManagerRepository;
import kr.co.hulan.aas.mvc.dao.repository.OrderingOfficeRepository;
import kr.co.hulan.aas.mvc.model.domain.G5Member;
import kr.co.hulan.aas.mvc.model.domain.OfficeWorkplaceGrp;
import kr.co.hulan.aas.mvc.model.domain.OfficeWorkplaceManager;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OfficeGroupManagerService {

  private Logger logger = LoggerFactory.getLogger(OrderingOfficeMgrService.class);

  @Autowired
  MbKeyGenerateService mbKeyGenerateService;

  @Autowired
  MysqlPasswordEncoder mysqlPasswordEncoder;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  G5MemberDao g5MemberDao;

  @Autowired
  G5MemberRepository g5MemberRepository;

  @Autowired
  private OrderingOfficeRepository orderingOfficeRepository;

  @Autowired
  private OfficeWorkplaceGrpRepository officeWorkplaceGrpRepository;

  @Autowired
  private OfficeWorkplaceManagerRepository officeWorkplaceManagerRepository;


  public DefaultPageResult<OfficeGroupManagerDto> findListPage(
      OfficeGroupManagerPagingListRequest request) {
    return DefaultPageResult.<OfficeGroupManagerDto>builder()
        .currentPage(request.getPage())
        .pageSize(request.getPageSize())
        .totalCount(countListByCondition(request.getConditionMap()))
        .list(findListByCondition(request.getConditionMap()))
        .build();
  }

  public List<OfficeGroupManagerDto> findListByCondition(Map<String,Object> conditionMap) {
    return g5MemberDao.findOfficeGroupManagerListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
  }

  private Long countListByCondition(Map<String,Object> conditionMap) {
    return g5MemberDao.countOfficeGroupManagerListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
  }

  public OfficeGroupManagerDto findInfo(String mbId){
    OfficeGroupManagerDto currentVo = g5MemberDao.findOfficeGroupManagerInfo(mbId);
    if (currentVo != null ) {
      return currentVo;
    } else {
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
    }
  }

  @Transactional("transactionManager")
  public void create(OfficeGroupManagerCreateRequest request) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }

    G5Member saveEntity = modelMapper.map(request, G5Member.class);

    if( g5MemberRepository.existsByMbId(saveEntity.getMbId()) ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "이미 등록된 아이디가 존재합니다.");
    }

    if( g5MemberRepository.countDuplicatedMemberCount(saveEntity.getMbId()) > 0 ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "아이디는 기등록된 전화번호, 사업자등록번호와 겹쳐서는 안됩니다.");
    }

    if( StringUtils.isNotBlank(saveEntity.getTelephone())){
      if ( g5MemberRepository.countDuplicatedMemberCount(saveEntity.getTelephone()) > 0) {
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "이미 등록된 전화번호가 존재합니다.");
      }
    }
    if( !orderingOfficeRepository.existsById( saveEntity.getOfficeNo())){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "발주사 정보가 존재하지 않습니다.");
    }

    if (StringUtils.isNotBlank(saveEntity.getMbPassword())) {
      saveEntity.setMbPassword(mysqlPasswordEncoder.encode(saveEntity.getMbPassword()));
    }
    saveEntity.setMbLevel(MemberLevel.WP_GROUP_MANAGER.getCode());
    saveEntity.setRegistDate(new Date());
    saveEntity.setMemberShipNo(mbKeyGenerateService.generateKey());
    g5MemberRepository.save(saveEntity);
  }

  @Transactional("transactionManager")
  public void update(OfficeGroupManagerUpdateRequest request, String mbId) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }

    G5Member saveEntity =  g5MemberRepository.findByMbId(mbId).orElseThrow(() ->
      new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), "회원 정보가 존재하지 않습니다.")
    );
    
    if( MemberLevel.WP_GROUP_MANAGER != MemberLevel.get(saveEntity.getMbLevel()) ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "["+mbId+"] 회원은 발주사 현장그룹 매니저가 아닙니다.");
    }

    String oldPwd = saveEntity.getMbPassword();
    modelMapper.map(request, saveEntity);

    if( StringUtils.isNotBlank(saveEntity.getTelephone())){
      if ( g5MemberRepository.countDuplicatedMemberAndNotMbIdCount(saveEntity.getTelephone(), saveEntity.getMbId()) > 0) {
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "이미 등록된 전화번호가 존재합니다.");
      }
    }

    if (StringUtils.isNotBlank(saveEntity.getMbPassword())) {
      saveEntity.setMbPassword(mysqlPasswordEncoder.encode(saveEntity.getMbPassword()));
    }
    else {
      saveEntity.setMbPassword(oldPwd);
    }
    g5MemberRepository.save(saveEntity);

  }


  @Transactional("transactionManager")
  public int delete(String mbId) {
    G5Member saveEntity =  g5MemberRepository.findByMbId(mbId).orElse(null);
    if( saveEntity != null ){
      if( MemberLevel.WP_GROUP_MANAGER != MemberLevel.get(saveEntity.getMbLevel()) ){
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "["+mbId+"] 회원은 발주사 현장그룹 매니저가 아닙니다.");
      }
      g5MemberRepository.delete(saveEntity);
      return 1;
    }
    return 0;
  }

  @Transactional("transactionManager")
  public int deleteMultiple(List<String> mbIdList) {
    int deleteCnt = 0;
    for (String mbId : mbIdList) {
      deleteCnt += delete(mbId);
    }
    return deleteCnt;
  }

  @Transactional("transactionManager")
  public void assignGroup(String mbId, long wpGrpNo){
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser(true);
    OfficeWorkplaceGrp grp = officeWorkplaceGrpRepository.findById(wpGrpNo).orElseThrow( () ->
       new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 현장그룹입니다.")
    );
    if( grp.containMember(mbId) ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "["+mbId+"] 회원은 이미 해당 현장그룹의 멤버입니다.");
    }
    G5Member member = g5MemberRepository.findByMbId(mbId).orElseThrow( () ->
        new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 사용자입니다.")
    );

    if( MemberLevel.get(member.getMbLevel()) != MemberLevel.WP_GROUP_MANAGER ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "해당 사용자는 발주사 현장그룹 매니저가 아닙니다.");
    }

    if( !grp.getOfficeNo().equals(member.getOfficeNo()) ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "해당 사용자는 해당 발주사의 현장그룹 매니저가 아닙니다.");
    }

    OfficeWorkplaceManager manager = new OfficeWorkplaceManager();
    manager.setMbId(mbId);
    manager.setWpGrpNo(wpGrpNo);
    manager.setCreator(loginUser.getMbId());
    manager.setCreateDate(new Date());
    officeWorkplaceManagerRepository.save(manager);

  }

  @Transactional("transactionManager")
  public void dismissGroup(String mbId, long wpGrpNo){
    OfficeWorkplaceGrp grp = officeWorkplaceGrpRepository.findById(wpGrpNo).orElseThrow( () ->
        new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 현장그룹입니다.")
    );
    if( grp.containMember(mbId) ){
      grp.getMember(mbId).ifPresent( (member) -> officeWorkplaceManagerRepository.delete(member));
    }
  }
}
