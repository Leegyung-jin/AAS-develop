package kr.co.hulan.aas.mvc.api.member.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.MemberLevel;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.common.utils.MysqlPasswordEncoder;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.member.controller.request.OfficeManagerCreateRequest;
import kr.co.hulan.aas.mvc.api.member.controller.request.OfficeManagerPagingListRequest;
import kr.co.hulan.aas.mvc.api.member.controller.request.OfficeManagerUpdateRequest;
import kr.co.hulan.aas.mvc.api.member.dto.OfficeManagerDto;
import kr.co.hulan.aas.mvc.api.orderoffice.controller.request.OrderingOfficeCreateRequest;
import kr.co.hulan.aas.mvc.api.orderoffice.controller.request.OrderingOfficePagingListRequest;
import kr.co.hulan.aas.mvc.api.orderoffice.controller.request.OrderingOfficeUpdateRequest;
import kr.co.hulan.aas.mvc.api.orderoffice.service.OrderingOfficeMgrService;
import kr.co.hulan.aas.mvc.api.orderoffice.vo.OfficeWorkplaceGroupVo;
import kr.co.hulan.aas.mvc.api.orderoffice.vo.OrderingOfficeListVo;
import kr.co.hulan.aas.mvc.api.orderoffice.vo.OrderingOfficeVo;
import kr.co.hulan.aas.mvc.dao.mapper.G5MemberDao;
import kr.co.hulan.aas.mvc.dao.mapper.OrderingOfficeDao;
import kr.co.hulan.aas.mvc.dao.repository.ConCompanyRepository;
import kr.co.hulan.aas.mvc.dao.repository.G5MemberRepository;
import kr.co.hulan.aas.mvc.dao.repository.OfficeWorkplaceGrpRepository;
import kr.co.hulan.aas.mvc.dao.repository.OrderingOfficeRepository;
import kr.co.hulan.aas.mvc.model.domain.G5Member;
import kr.co.hulan.aas.mvc.model.domain.OrderingOffice;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OfficeManagerService {

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

  public DefaultPageResult<OfficeManagerDto> findListPage(
      OfficeManagerPagingListRequest request) {
    return DefaultPageResult.<OfficeManagerDto>builder()
        .currentPage(request.getPage())
        .pageSize(request.getPageSize())
        .totalCount(countListByCondition(request.getConditionMap()))
        .list(findListByCondition(request.getConditionMap()))
        .build();
  }

  public List<OfficeManagerDto> findListByCondition(Map<String,Object> conditionMap) {
    return g5MemberDao.findOfficeManagerListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
  }

  private Long countListByCondition(Map<String,Object> conditionMap) {
    return g5MemberDao.countOfficeManagerListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
  }

  public OfficeManagerDto findInfo(String mbId){
    OfficeManagerDto currentVo = g5MemberDao.findOfficeManagerInfo(mbId);
    if (currentVo != null ) {
      return currentVo;
    } else {
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
    }
  }

  @Transactional("transactionManager")
  public void create(OfficeManagerCreateRequest request) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }

    G5Member saveEntity = modelMapper.map(request, G5Member.class);

    if( g5MemberRepository.existsByMbId(saveEntity.getMbId()) ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "?????? ????????? ???????????? ???????????????.");
    }

    if( g5MemberRepository.countDuplicatedMemberCount(saveEntity.getMbId()) > 0 ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "???????????? ???????????? ?????????, ????????????, ???????????????????????? ???????????? ????????????.");
    }

    if( StringUtils.isNotBlank(saveEntity.getTelephone())){
      if ( g5MemberRepository.countDuplicatedMemberCount(saveEntity.getTelephone()) > 0) {
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "??????????????? ???????????? ?????????, ????????????, ???????????????????????? ???????????? ????????????.");
      }
    }

    if( !orderingOfficeRepository.existsById( saveEntity.getOfficeNo())){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "????????? ????????? ???????????? ????????????.");
    }

    if (StringUtils.isNotBlank(saveEntity.getMbPassword())) {
      saveEntity.setMbPassword(mysqlPasswordEncoder.encode(saveEntity.getMbPassword()));
    }
    saveEntity.setMbLevel(MemberLevel.OFFICE_MANAGER.getCode());
    saveEntity.setRegistDate(new Date());
    saveEntity.setMemberShipNo(mbKeyGenerateService.generateKey());
    g5MemberRepository.save(saveEntity);

  }

  @Transactional("transactionManager")
  public void update(OfficeManagerUpdateRequest request, String mbId) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }

    G5Member saveEntity =  g5MemberRepository.findByMbId(mbId).orElse(null);
    if( saveEntity == null ){
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), "?????? ????????? ???????????? ????????????.");
    }

    if( MemberLevel.OFFICE_MANAGER != MemberLevel.get(saveEntity.getMbLevel()) ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "["+mbId+"] ????????? ????????? ???????????? ????????????.");
    }

    String oldPwd = saveEntity.getMbPassword();
    modelMapper.map(request, saveEntity);

    if( StringUtils.isNotBlank(saveEntity.getTelephone())){
      if ( g5MemberRepository.countDuplicatedMemberAndNotMbIdCount(saveEntity.getTelephone(), saveEntity.getMbId()) > 0) {
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "??????????????? ???????????? ?????????, ????????????, ???????????????????????? ???????????? ????????????.");
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
      if( MemberLevel.OFFICE_MANAGER != MemberLevel.get(saveEntity.getMbLevel()) ){
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "["+mbId+"] ????????? ????????? ???????????? ????????????.");
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
