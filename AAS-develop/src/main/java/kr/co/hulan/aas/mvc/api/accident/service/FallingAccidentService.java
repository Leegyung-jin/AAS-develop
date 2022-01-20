package kr.co.hulan.aas.mvc.api.accident.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.mvc.api.accident.controller.request.ListFallingAccidentRequest;
import kr.co.hulan.aas.mvc.api.accident.controller.request.UpdateFallingAccidentPopupRequest;
import kr.co.hulan.aas.mvc.api.accident.controller.response.ListFallingAccidentResponse;
import kr.co.hulan.aas.mvc.dao.mapper.FallingAccidentDao;
import kr.co.hulan.aas.mvc.dao.repository.FallingAccidentRecentRepository;
import kr.co.hulan.aas.mvc.model.domain.FallingAccidentRecent;
import kr.co.hulan.aas.mvc.model.dto.FallingAccidentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FallingAccidentService {

  @Autowired
  private FallingAccidentDao fallingAccidentDao;

  @Autowired
  private FallingAccidentRecentRepository fallingAccidentRecentRepository;


  public ListFallingAccidentResponse findListPage(ListFallingAccidentRequest request) {
    return new ListFallingAccidentResponse(
        findListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(request.getConditionMap())),
        findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(request.getConditionMap()))
    );
  }

  public List<FallingAccidentDto> findListByCondition(Map<String,Object> conditionMap) {
    return fallingAccidentDao.findListByCondition(conditionMap);
  }

  private Long findListCountByCondition(Map<String,Object> conditionMap) {
    return fallingAccidentDao.findListCountByCondition(conditionMap);
  }

  public List<FallingAccidentDto> findFallingAccidentRecentListByWpId(String wpId) {
    List<FallingAccidentDto> list = fallingAccidentDao.findFallingAccidentRecentListByWpId(wpId);
    return list == null ? Collections.emptyList() : list;
  }

  public List<FallingAccidentDto> findFallingAccidentListByWpId(String wpId) {
    List<FallingAccidentDto> list = fallingAccidentDao.findFallingAccidentListByWpId(wpId);
    return list == null ? Collections.emptyList() : list;
  }

  public FallingAccidentDto findById(String mbId){
    FallingAccidentDto dto = fallingAccidentDao.findById(mbId);
    if (dto != null ) {
      return dto;
    } else {
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
    }
  }

  @Transactional("transactionManager")
  public void updateFallingAccidentPopup(String mbId, UpdateFallingAccidentPopupRequest request){
    FallingAccidentRecent recent = fallingAccidentRecentRepository.findById(mbId).orElse(null);
    if( recent != null ){
      recent.setDashboardPopup(request.getDashboardPopup());
      fallingAccidentRecentRepository.save(recent);
    }
    else {
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
    }
  }
}
