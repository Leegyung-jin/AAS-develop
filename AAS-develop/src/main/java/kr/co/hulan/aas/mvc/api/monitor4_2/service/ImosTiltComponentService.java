package kr.co.hulan.aas.mvc.api.monitor4_2.service;

import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.DeviceType;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.request.ImosTiltDeviceStatePagingListRequest;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.ImosTiltDeviceStateVo;
import kr.co.hulan.aas.mvc.dao.mapper.TiltLogDao;
import kr.co.hulan.aas.mvc.dao.repository.TiltLogRecentRepository;
import kr.co.hulan.aas.mvc.dao.repository.WorkDeviceInfoRepository;
import kr.co.hulan.aas.mvc.model.domain.GasLogRecent;
import kr.co.hulan.aas.mvc.model.domain.TiltLogRecent;
import kr.co.hulan.aas.mvc.model.domain.WorkDeviceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ImosTiltComponentService {

  @Autowired
  private TiltLogDao tiltLogDao;

  @Autowired
  private WorkDeviceInfoRepository workDeviceInfoRepository;

  @Autowired
  private TiltLogRecentRepository tiltLogRecentRepository;

  public DefaultPageResult<ImosTiltDeviceStateVo> findTiltDeviceStatePagingList(String wpId, ImosTiltDeviceStatePagingListRequest request){
    Map<String,Object> condition = AuthenticationHelper.addAdditionalConditionByLevel(request.getConditionMap());
    condition.put("wpId", wpId);
    return DefaultPageResult.<ImosTiltDeviceStateVo>builder()
        .pageSize(request.getPageSize())
        .currentPage(request.getPage())
        .list(findTiltDeviceStateList(condition))
        .totalCount(tiltLogDao.countTiltDeviceStateList(condition))
        .build();
  }

  public List<ImosTiltDeviceStateVo> findTiltDeviceStateList(Map<String,Object> condition){
    return tiltLogDao.findTiltDeviceStateList(condition);
  }

  public ImosTiltDeviceStateVo findTiltDeviceState(String wpId, int idx){
    ImosTiltDeviceStateVo vo = tiltLogDao.findTiltDeviceState(wpId, idx);
    if( vo == null){
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
    }
    return vo;
  }

  @Transactional("transactionManager")
  public void updateTiltDeviceAlarm(String wpId, int idx, int alarmFlag){
    WorkDeviceInfo deviceInfo = workDeviceInfoRepository.findById(idx).orElse(null);
    if( deviceInfo != null ){
      /*
      DeviceType dtype = DeviceType.get(deviceInfo.getDeviceType());
      if( dtype != DeviceType.TILT_SENSOR ){
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "해당 디바이스는 기울기 센서가 아닙니다.");
      }
       */
      TiltLogRecent recent = tiltLogRecentRepository.findById(deviceInfo.getMacAddress()).orElse(null);
      if( recent != null ){
        recent.setDashboardPopup(alarmFlag);
        tiltLogRecentRepository.save(recent);
      }
    }
    else {
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "디바이스 정보를 찾을 수 없습니다.");
    }

  }


}
