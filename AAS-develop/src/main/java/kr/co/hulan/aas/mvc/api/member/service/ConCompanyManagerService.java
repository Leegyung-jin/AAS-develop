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
import kr.co.hulan.aas.mvc.api.member.controller.request.ConCompanyManagerCreateRequest;
import kr.co.hulan.aas.mvc.api.member.controller.request.ConCompanyManagerPagingListRequest;
import kr.co.hulan.aas.mvc.api.member.controller.request.ConCompanyManagerUpdateRequest;
import kr.co.hulan.aas.mvc.api.member.dto.ConCompanyManagerDto;
import kr.co.hulan.aas.mvc.dao.mapper.G5MemberDao;
import kr.co.hulan.aas.mvc.dao.repository.ConCompanyRepository;
import kr.co.hulan.aas.mvc.dao.repository.G5MemberRepository;
import kr.co.hulan.aas.mvc.model.domain.G5Member;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConCompanyManagerService {

  private Logger logger = LoggerFactory.getLogger(ConCompanyManagerService.class);

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
  private ConCompanyRepository conCompanyRepository;

  public DefaultPageResult<ConCompanyManagerDto> findListPage(
      ConCompanyManagerPagingListRequest request) {
    return DefaultPageResult.<ConCompanyManagerDto>builder()
        .currentPage(request.getPage())
        .pageSize(request.getPageSize())
        .totalCount(countListByCondition(request.getConditionMap()))
        .list(findListByCondition(request.getConditionMap()))
        .build();
  }

  public List<ConCompanyManagerDto> findListByCondition(Map<String,Object> conditionMap) {
    return g5MemberDao.findConCompanyManagerListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
  }

  private Long countListByCondition(Map<String,Object> conditionMap) {
    return g5MemberDao.countConCompanyManagerListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
  }

  public ConCompanyManagerDto findInfo(String mbId){
    ConCompanyManagerDto currentVo = g5MemberDao.findConCompanyManagerInfo(mbId);
    if (currentVo != null ) {
      return currentVo;
    } else {
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
    }
  }

  @Transactional("transactionManager")
  public ConCompanyManagerDto create(ConCompanyManagerCreateRequest request) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }

    G5Member saveEntity = modelMapper.map(request, G5Member.class);

    if( g5MemberRepository.existsByMbId(saveEntity.getMbId()) ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "이미 등록된 아이디가 존재합니다.");
    }

    if( g5MemberRepository.countDuplicatedMemberCount(saveEntity.getMbId()) > 0 ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "아이디는 기등록된 아이디, 전화번호, 사업자등록번호와 겹쳐서는 안됩니다.");
    }

    if( StringUtils.isNotBlank(saveEntity.getTelephone())){
      if ( g5MemberRepository.countDuplicatedMemberCount(saveEntity.getTelephone()) > 0) {
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "전화번호는 기등록된 아이디, 전화번호, 사업자등록번호와 겹쳐서는 안됩니다.");
      }
    }

    if( !conCompanyRepository.existsById( saveEntity.getCcId())){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "건설사 정보가 존재하지 않습니다.");
    }

    if (StringUtils.isNotBlank(saveEntity.getMbPassword())) {
      saveEntity.setMbPassword(mysqlPasswordEncoder.encode(saveEntity.getMbPassword()));
    }
    saveEntity.setMbLevel(MemberLevel.CONSTRUNCTION_COMPANY_MANAGER.getCode());
    saveEntity.setRegistDate(new Date());
    saveEntity.setMemberShipNo(mbKeyGenerateService.generateKey());
    g5MemberRepository.save(saveEntity);

    return modelMapper.map(saveEntity, ConCompanyManagerDto.class);
  }

  @Transactional("transactionManager")
  public void update(ConCompanyManagerUpdateRequest request, String mbId) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }

    G5Member saveEntity =  g5MemberRepository.findByMbId(mbId).orElse(null);
    if( saveEntity == null ){
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), "회원 정보가 존재하지 않습니다.");
    }

    if( MemberLevel.CONSTRUNCTION_COMPANY_MANAGER != MemberLevel.get(saveEntity.getMbLevel()) ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "["+mbId+"] 회원은 건설사 관리자가 아닙니다.");
    }

    String oldPwd = saveEntity.getMbPassword();
    modelMapper.map(request, saveEntity);

    if( StringUtils.isNotBlank(saveEntity.getTelephone())){
      if ( g5MemberRepository.countDuplicatedMemberAndNotMbIdCount(saveEntity.getTelephone(), saveEntity.getMbId()) > 0) {
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "전화번호는 기등록된 아이디, 전화번호, 사업자등록번호와 겹쳐서는 안됩니다.");
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
      if( MemberLevel.CONSTRUNCTION_COMPANY_MANAGER != MemberLevel.get(saveEntity.getMbLevel()) ){
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "["+mbId+"] 회원은 건설사 관리자가 아닙니다.");
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
}
