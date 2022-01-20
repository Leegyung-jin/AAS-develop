package kr.co.hulan.aas.mvc.api.entergate.service;

import com.google.gson.Gson;
import java.util.Date;
import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.common.utils.MysqlPasswordEncoder;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.entergate.controller.request.EntrGateAccountCreateRequest;
import kr.co.hulan.aas.mvc.api.entergate.controller.request.EntrGateAccountListRequest;
import kr.co.hulan.aas.mvc.api.entergate.controller.request.EntrGateAccountUpdateRequest;
import kr.co.hulan.aas.mvc.api.entergate.vo.EntrGateAccountDetailVo;
import kr.co.hulan.aas.mvc.api.entergate.vo.EntrGateAccountListVo;
import kr.co.hulan.aas.mvc.dao.mapper.EntrGateAccountDao;
import kr.co.hulan.aas.mvc.dao.repository.EntrGateAccountRepository;
import kr.co.hulan.aas.mvc.model.domain.EntrGateAccount;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EntrGateAccountService {

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  Gson gson;

  @Autowired
  MysqlPasswordEncoder mysqlPasswordEncoder;

  @Autowired
  private EntrGateAccountDao entrGateAccountDao;

  @Autowired
  private EntrGateAccountRepository entrGateAccountRepository;

  public DefaultPageResult<EntrGateAccountListVo> findListPage(EntrGateAccountListRequest request) {
    return DefaultPageResult.<EntrGateAccountListVo>builder()
        .pageSize(request.getPageSize())
        .currentPage(request.getPage())
        .totalCount(findListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(request.getConditionMap())))
        .list(findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(request.getConditionMap())))
        .build();
  }

  public List<EntrGateAccountListVo> findListByCondition(Map<String,Object> conditionMap) {
    return entrGateAccountDao.findListByCondition(conditionMap);
  }

  private Long findListCountByCondition(Map<String,Object> conditionMap) {
    return entrGateAccountDao.countListByCondition(conditionMap);
  }

  public EntrGateAccountDetailVo findInfo(String accountId){
    EntrGateAccountDetailVo dto = entrGateAccountDao.findInfo(accountId);
    if (dto != null ) {
      return dto;
    } else {
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
    }
  }

  @Transactional("transactionManager")
  public void create(EntrGateAccountCreateRequest request) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser(true);

    EntrGateAccount saveEntity = entrGateAccountRepository.findById(request.getAccountId()).orElse(null);
    if ( saveEntity != null ) {
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "이미 등록된 계정 아이디입니다.");
    }
    saveEntity = modelMapper.map(request, EntrGateAccount.class);
    saveEntity.setCreator(loginUser.getUsername());
    saveEntity.setUpdater(loginUser.getUsername());
    saveEntity.setCreateDate(new Date());
    saveEntity.setUpdateDate(new Date());

    saveEntity.setAccountPwd(mysqlPasswordEncoder.encode(request.getAccountPwd()));
    entrGateAccountRepository.save(saveEntity);
  }

  @Transactional("transactionManager")
  public void update(String accountId, EntrGateAccountUpdateRequest request) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser(true);

    EntrGateAccount saveEntity = entrGateAccountRepository.findById(accountId).orElse(null);
    if ( saveEntity == null ) {
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "등록된 계정이 아닙니다.");
    }

    if(StringUtils.isNotBlank(request.getAccountPwd())){
      request.setAccountPwd(mysqlPasswordEncoder.encode(request.getAccountPwd()));
    }
    else {
      request.setAccountPwd(saveEntity.getAccountPwd());
    }

    modelMapper.map(request, saveEntity);
    saveEntity.setUpdater(loginUser.getUsername());
    saveEntity.setUpdateDate(new Date());

    entrGateAccountRepository.save(saveEntity);
  }

  @Transactional("transactionManager")
  public void delete(String accountId) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser(true);

    EntrGateAccount saveEntity = entrGateAccountRepository.findById(accountId).orElse(null);
    if( saveEntity != null ){
      entrGateAccountRepository.delete(saveEntity);
    }

  }

  @Transactional("transactionManager")
  public void deleteMultiple(List<String> accountIdList) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser(true);

    for (String accountId : accountIdList) {
      delete(accountId);
    }
  }

}
