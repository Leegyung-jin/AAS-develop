package kr.co.hulan.aas.mvc.api.monitor4_2.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.DeviceType;
import kr.co.hulan.aas.common.code.EnableCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.request.ImosMagneticOpeningPagingListRequest;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.request.ImosTiltDeviceStatePagingListRequest;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.response.ImosMagneticOpeningStateResponse;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.ImosMagneticOpeningStateVo;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.ImosTiltDeviceStateVo;
import kr.co.hulan.aas.mvc.dao.mapper.MagneticOpeningDao;
import kr.co.hulan.aas.mvc.dao.repository.MagneticOpenRecentRepository;
import kr.co.hulan.aas.mvc.dao.repository.WorkDeviceInfoRepository;
import kr.co.hulan.aas.mvc.model.domain.MagneticOpenRecent;
import kr.co.hulan.aas.mvc.model.domain.TiltLogRecent;
import kr.co.hulan.aas.mvc.model.domain.WorkDeviceInfo;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ImosMagneticOpeningService {

  @Autowired
  private MagneticOpeningDao magneticOpeningDao;

  @Autowired
  private MagneticOpenRecentRepository magneticOpenRecentRepository;

  @Autowired
  private WorkDeviceInfoRepository workDeviceInfoRepository;


  public ImosMagneticOpeningStateResponse findCurrentState(String wpId){
    ImosMagneticOpeningStateResponse res = magneticOpeningDao.findCurrentState(wpId);
    if( res == null ){
      return ImosMagneticOpeningStateResponse.builder()
          .totalCnt(0)
          .build();
    }

    res.setOpenDeviceList(findDeviceStateList(wpId, EnableCode.ENABLED.getCode()));
    return res;
  }



  public DefaultPageResult<ImosMagneticOpeningStateVo> findPagingList(String wpId, ImosMagneticOpeningPagingListRequest request){
    Map<String,Object> condition = AuthenticationHelper.addAdditionalConditionByLevel(request.getConditionMap());
    condition.put("wpId", wpId);
    return DefaultPageResult.<ImosMagneticOpeningStateVo>builder()
        .pageSize(request.getPageSize())
        .currentPage(request.getPage())
        .list(findDeviceStateList(condition))
        .totalCount(magneticOpeningDao.countDeviceStateList(condition))
        .build();
  }


  public List<ImosMagneticOpeningStateVo> findDeviceStateList(String wpId, int status){
    Map<String,Object> condition = new HashMap<String,Object>();
    condition.put("wpId", wpId);
    condition.put("status", status);
    return findDeviceStateList(condition);
  }

  public List<ImosMagneticOpeningStateVo> findDeviceStateList(Map<String,Object> condition){
    return ObjectUtils.defaultIfNull(magneticOpeningDao.findDeviceStateList(condition), Collections.emptyList());
  }

  @Transactional("transactionManager")
  public void updateDeviceAlarm(String wpId, int idx, int alarmFlag){
    WorkDeviceInfo deviceInfo = workDeviceInfoRepository.findById(idx).orElse(null);
    if( deviceInfo != null ){
      if(!StringUtils.equals( deviceInfo.getWpId(), wpId)){
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "해당 현장에 할당된 개구부가 아닙니다.");
      }
      /*
      DeviceType dtype = DeviceType.get(deviceInfo.getDeviceType());
      if( dtype != DeviceType.OPENING_SENSOR ){
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "해당 디바이스는 개구부 센서가 아닙니다.");
      }
       */
      MagneticOpenRecent recent = magneticOpenRecentRepository.findById(deviceInfo.getMacAddress()).orElse(null);
      if( recent != null ){
        recent.setDashboardPopup(alarmFlag);
        magneticOpenRecentRepository.save(recent);
      }
    }
    else {
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "디바이스 정보를 찾을 수 없습니다.");
    }

  }


}
