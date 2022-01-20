package kr.co.hulan.aas.mvc.api.workplace.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.EnableCode;
import kr.co.hulan.aas.common.code.UiComponentCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.dao.mapper.WorkPlaceUiComponentDao;
import kr.co.hulan.aas.mvc.dao.repository.EntrGateWorkplaceRepository;
import kr.co.hulan.aas.mvc.dao.repository.UiComponentRepository;
import kr.co.hulan.aas.mvc.dao.repository.WorkPlaceCctvRepository;
import kr.co.hulan.aas.mvc.dao.repository.WorkPlaceRepository;
import kr.co.hulan.aas.mvc.dao.repository.WorkPlaceUiComponentRepository;
import kr.co.hulan.aas.mvc.model.domain.EntrGateWorkplace;
import kr.co.hulan.aas.mvc.model.domain.UiComponent;
import kr.co.hulan.aas.mvc.model.domain.WorkPlace;
import kr.co.hulan.aas.mvc.model.domain.WorkPlaceUiComponent;
import kr.co.hulan.aas.mvc.model.domain.WorkPlaceUiComponentCompositeKey;
import kr.co.hulan.aas.mvc.model.dto.UiComponentDto;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceUiComponentDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WorkPlaceUiComponentService {

  @Autowired
  WorkPlaceRepository workPlaceRepository;

  @Autowired
  private UiComponentRepository uiComponentRepository;

  @Autowired
  private WorkPlaceUiComponentDao workPlaceUiComponentDao;

  @Autowired
  private WorkPlaceUiComponentRepository workPlaceUiComponentRepository;

  @Autowired
  private EntrGateWorkplaceRepository entrGateWorkplaceRepository;

  @Autowired
  private WorkPlaceCctvRepository workPlaceCctvRepository;

  public List<WorkPlaceUiComponentDto> findListByWpId(String wpId) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser(true);
    Map<String,Object> condition = new HashMap<String,Object>();
    condition.put("wpId", wpId);
    condition.put("creator", loginUser.getMbId());
    return findListByCondition(condition);
  }

  public List<WorkPlaceUiComponentDto> findListByCondition(Map<String,Object> conditionMap) {
    return workPlaceUiComponentDao.findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
  }

  public WorkPlaceUiComponentDto findById(String wpId, int location){
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser(true);
    return workPlaceUiComponentDao.findById(WorkPlaceUiComponentCompositeKey.builder()
        .wpId(wpId)
        .location(location)
        .creator(loginUser.getMbId())
        .build());
  }

  public List<UiComponentDto> findSupportedListById(String wpId, int location){
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser(true);
    return workPlaceUiComponentDao.findSupportedListById(WorkPlaceUiComponentCompositeKey.builder()
        .wpId(wpId)
        .location(location)
        .creator(loginUser.getMbId())
        .build());
  }

  @Transactional("transactionManager")
  public void assignWorkplaceUiComponent(String wpId, int location, String cmptId){
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }

    WorkPlace workplace = workPlaceRepository.findById(wpId).orElse(null);
    if( workplace == null ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 현장입니다.");
    }

    UiComponent uiComponent = uiComponentRepository.findById(cmptId).orElse(null);
    if( uiComponent == null || uiComponent.getStatus() != EnableCode.ENABLED.getCode()){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "지원하지 않는 컴포넌트입니다.");
    }

    UiComponentCode compCode = UiComponentCode.get(uiComponent.getCmptId());
    if( compCode == UiComponentCode.CCTV ){
      long cctvCount = workPlaceCctvRepository.countByWpId(wpId);
      if( cctvCount < 1 ){
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "해당 현장에서는 지원되지 않는 컴포넌트입니다");
      }
    }
    else if( compCode == UiComponentCode.ENTER_GATE ){
      EntrGateWorkplace wgate = entrGateWorkplaceRepository.findById(wpId).orElse(null);
      if( wgate == null || EnableCode.get(wgate.getStatus()) != EnableCode.ENABLED){
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "해당 현장에서는 지원되지 않는 컴포넌트입니다");
      }
    }

    WorkPlaceUiComponent existsComponent = workPlaceUiComponentRepository.findById(WorkPlaceUiComponentCompositeKey.builder()
        .wpId(wpId)
        .location(location)
        .creator(loginUser.getMbId())
        .build()).orElse(null);
    if( existsComponent == null ){
      existsComponent = new WorkPlaceUiComponent();
      existsComponent.setWpId(wpId);
      existsComponent.setLocation(location);
      existsComponent.setCmptId(cmptId);
      existsComponent.setCreator(loginUser.getMbId());
      workPlaceUiComponentRepository.save(existsComponent);
    }
    else if( !StringUtils.equals(existsComponent.getCmptId(), cmptId) ){
      existsComponent.setCmptId(cmptId);
      existsComponent.setCreateDate(new Date());
      workPlaceUiComponentRepository.save(existsComponent);
    }
  }

  @Transactional("transactionManager")
  public void unassignWorkplaceUiComponent(String wpId, int location){
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }

    WorkPlace workplace = workPlaceRepository.findById(wpId).orElse(null);
    if( workplace == null ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 현장입니다.");
    }

    WorkPlaceUiComponent existsComponent = workPlaceUiComponentRepository.findById(WorkPlaceUiComponentCompositeKey.builder()
        .wpId(wpId)
        .location(location)
        .creator(loginUser.getMbId())
        .build()).orElse(null);
    if( existsComponent != null ){
      workPlaceUiComponentRepository.delete(existsComponent);
    }
  }
}
