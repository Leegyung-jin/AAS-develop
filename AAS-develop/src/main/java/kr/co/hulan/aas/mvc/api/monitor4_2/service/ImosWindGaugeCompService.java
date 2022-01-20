package kr.co.hulan.aas.mvc.api.monitor4_2.service;

import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.response.ImosWindGaugeMainResponse;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.windgauge.WindGaugeStatusVo;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.windgauge.WindSpeedFigureVo;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.windgauge.WindSpeedRangeChecker;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.windgauge.WindSpeedRecentVo;
import kr.co.hulan.aas.mvc.dao.mapper.WindSpeedDao;
import kr.co.hulan.aas.mvc.dao.repository.WindSpeedRecentRepository;
import kr.co.hulan.aas.mvc.dao.repository.WorkDeviceInfoRepository;
import kr.co.hulan.aas.mvc.model.domain.TiltLogRecent;
import kr.co.hulan.aas.mvc.model.domain.WindSpeedRecent;
import kr.co.hulan.aas.mvc.model.domain.WorkDeviceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ImosWindGaugeCompService {

  @Autowired
  private WindSpeedDao windSpeedDao;

  @Autowired
  private WorkDeviceInfoRepository workDeviceInfoRepository;

  @Autowired
  private WindSpeedRecentRepository windSpeedRecentRepository;

  public List<WindGaugeStatusVo> findWindGaugeStatusList(String wpId){
    WindSpeedRangeChecker checker = new WindSpeedRangeChecker(windSpeedDao.findWindSpeedRangeListByWpId(wpId));
    List<WindGaugeStatusVo> list = windSpeedDao.findWindGaugeStatusList(wpId);
    list.stream().forEach( wind -> wind.applyWindSpeedRange(checker));
    return list;
  }

  public WindGaugeStatusVo findWindGaugeStatus(String wpId, int idx){
    WindGaugeStatusVo vo = windSpeedDao.findWindGaugeStatus(idx);
    if( vo != null ){
      vo.applyWindSpeedRange(new WindSpeedRangeChecker(windSpeedDao.findWindSpeedRangeListByWpId(wpId)));
    }
    return vo;
  }

  public List<WindSpeedFigureVo> findWindSpeedHistoryList(Map<String,Object> condition){
    return windSpeedDao.findWindSpeedHistoryList(condition);
  }

  public ImosWindGaugeMainResponse findWindGaugeMainInfo(String wpId){
    WindSpeedRangeChecker checker = new WindSpeedRangeChecker(windSpeedDao.findWindSpeedRangeListByWpId(wpId));
    List<WindSpeedRecentVo> recentList = windSpeedDao.findWindGaugeList(wpId);
    recentList.stream().forEach( recent -> recent.applyWindSpeedRange(checker));
    return ImosWindGaugeMainResponse.builder()
        .list(recentList)
        .threshold(checker.getAlertRangeList())
        .build();
  }

  @Transactional("transactionManager")
  public void updateAlarm(String wpId, int idx, int alarmFlag){
    WorkDeviceInfo deviceInfo = workDeviceInfoRepository.findById(idx).orElse(null);
    if( deviceInfo != null ){
      WindSpeedRecent recent = windSpeedRecentRepository.findById(deviceInfo.getMacAddress()).orElse(null);
      if( recent != null ){
        recent.setDashboardPopup(alarmFlag);
        windSpeedRecentRepository.save(recent);
      }
    }
    else {
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "디바이스 정보를 찾을 수 없습니다.");
    }

  }
}
