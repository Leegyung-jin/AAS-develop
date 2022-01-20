package kr.co.hulan.aas.mvc.api.entergate.service;

import com.google.gson.Gson;
import java.util.Date;
import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.entergate.controller.request.EntrGateWorkplaceCreateRequest;
import kr.co.hulan.aas.mvc.api.entergate.controller.request.EntrGateWorkplaceListRequest;
import kr.co.hulan.aas.mvc.api.entergate.controller.request.EntrGateWorkplaceUpdateRequest;
import kr.co.hulan.aas.mvc.api.entergate.vo.EntrGateWorkplaceVo;
import kr.co.hulan.aas.mvc.dao.mapper.EntrGateWorkplaceDao;
import kr.co.hulan.aas.mvc.dao.repository.EntrGateAccountRepository;
import kr.co.hulan.aas.mvc.dao.repository.EntrGateWorkplaceRepository;
import kr.co.hulan.aas.mvc.dao.repository.WorkPlaceRepository;
import kr.co.hulan.aas.mvc.model.domain.EntrGateAccount;
import kr.co.hulan.aas.mvc.model.domain.EntrGateWorkplace;
import kr.co.hulan.aas.mvc.model.domain.WorkPlace;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EntrGateWorkplaceService {

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  Gson gson;

  @Autowired
  private EntrGateWorkplaceDao entrGateWorkplaceDao;

  @Autowired
  private WorkPlaceRepository workPlaceRepository;

  @Autowired
  private EntrGateAccountRepository entrGateAccountRepository;

  @Autowired
  private EntrGateWorkplaceRepository entrGateWorkplaceRepository;

  public DefaultPageResult<EntrGateWorkplaceVo> findListPage(EntrGateWorkplaceListRequest request) {
    return DefaultPageResult.<EntrGateWorkplaceVo>builder()
        .pageSize(request.getPageSize())
        .currentPage(request.getPage())
        .totalCount(findListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(request.getConditionMap())))
        .list(findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(request.getConditionMap())))
        .build();
  }

  public List<EntrGateWorkplaceVo> findListByCondition(Map<String,Object> conditionMap) {
    return entrGateWorkplaceDao.findListByCondition(conditionMap);
  }

  private Long findListCountByCondition(Map<String,Object> conditionMap) {
    return entrGateWorkplaceDao.countListByCondition(conditionMap);
  }

  public EntrGateWorkplaceVo findInfo(String wpId){
    EntrGateWorkplaceVo dto = entrGateWorkplaceDao.findInfo(wpId);
    if (dto != null ) {
      return dto;
    } else {
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
    }
  }

  @Transactional("transactionManager")
  public void create(EntrGateWorkplaceCreateRequest request) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser(true);

    WorkPlace workplace = workPlaceRepository.findById(request.getWpId()).orElse(null);
    if( workplace == null ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "등록되지 않은 현장입니다.");
    }

    EntrGateAccount accountEntity = entrGateAccountRepository.findById(request.getAccountId()).orElse(null);
    if ( accountEntity == null ) {
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "등록된 출입게이트 계정이 아닙니다.");
    }

    EntrGateWorkplace saveEntity = entrGateWorkplaceRepository.findById(request.getWpId()).orElse(null);
    if ( saveEntity != null ) {
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "이미 등록된 출입게이트 정보가 존재합니다.");
    }
    saveEntity = entrGateWorkplaceRepository.findByAccountIdAndMappingCd(request.getAccountId(), request.getMappingCd());
    if ( saveEntity != null ) {
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "이미 등록된 맵핑코드 정보가 존재합니다.");
    }

    saveEntity = modelMapper.map(request, EntrGateWorkplace.class);
    saveEntity.setCreator(loginUser.getUsername());
    saveEntity.setUpdater(loginUser.getUsername());
    saveEntity.setCreateDate(new Date());
    saveEntity.setUpdateDate(new Date());
    entrGateWorkplaceRepository.save(saveEntity);

  }

  @Transactional("transactionManager")
  public void update(String wpId, EntrGateWorkplaceUpdateRequest request) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser(true);

    EntrGateAccount accountEntity = entrGateAccountRepository.findById(request.getAccountId()).orElse(null);
    if ( accountEntity == null ) {
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "등록된 출입게이트 계정이 아닙니다.");
    }
    EntrGateWorkplace saveEntity = entrGateWorkplaceRepository.findById(wpId).orElse(null);
    if ( saveEntity == null ) {
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "출입게이트가 등록되지 않은 현장입니다.");
    }

    if ( entrGateWorkplaceRepository.existsByAccountIdAndMappingCdAndWpIdIsNot(request.getAccountId(), request.getMappingCd(), wpId) ) {
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "이미 등록된 맵핑코드 정보가 존재합니다.");
    }

    modelMapper.map(request, saveEntity);
    saveEntity.setUpdater(loginUser.getUsername());
    saveEntity.setUpdateDate(new Date());

    entrGateWorkplaceRepository.save(saveEntity);
  }

  @Transactional("transactionManager")
  public void delete(String wpId) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser(true);

    EntrGateWorkplace saveEntity = entrGateWorkplaceRepository.findById(wpId).orElse(null);
    if( saveEntity != null ){
      entrGateWorkplaceRepository.delete(saveEntity);
    }
  }

  @Transactional("transactionManager")
  public void deleteMultiple(List<String> wpIdList) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser(true);

    for (String wpId : wpIdList) {
      delete(wpId);
    }

  }
}
