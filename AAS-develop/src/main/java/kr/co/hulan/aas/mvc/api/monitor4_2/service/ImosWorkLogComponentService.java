package kr.co.hulan.aas.mvc.api.monitor4_2.service;

import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.EnableCode;
import kr.co.hulan.aas.common.code.WorkInOutMethod;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.AttendantSituationVo;
import kr.co.hulan.aas.mvc.api.monitor4_1.vo.CurrentWorkStatusSummary;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.request.ImosWorkLogWorkerExportRequest;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.response.ImosWorkStatusResponse;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.ImosCoopStatVo;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.ImosWorkingWorkerVo;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.ImosCommuteTypeStatVo;
import kr.co.hulan.aas.mvc.dao.mapper.WorkPlaceWorkerDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImosWorkLogComponentService {

  @Autowired
  private WorkPlaceWorkerDao workPlaceWorkerDao;

  public ImosWorkStatusResponse findWorkStatusSummary(String wpId, String monitorType ){
    Map<String,Object> condition = AuthenticationHelper.addAdditionalConditionByLevel();
    condition.put("wpId", wpId);
    if(StringUtils.isNotBlank(monitorType)){
      condition.put("monitorType", monitorType);
    }

    List<ImosCommuteTypeStatVo> commuteTypeList = workPlaceWorkerDao.findCommuteTypeStatList(condition);
    CurrentWorkStatusSummary summary = workPlaceWorkerDao.findCurrentWorkStatus(condition);
    if( summary == null ){
      summary = CurrentWorkStatusSummary.builder()
          .totalWorkerCount(0L)
          .currentWorkerCount(0L)
          .build();
    }
    return ImosWorkStatusResponse.builder()
        .commuteTypeList(commuteTypeList)
        .currentWorkStatus(summary)
        .build();
  }

  public List<ImosWorkingWorkerVo> findWorkerList(String wpId, String monitorType, ImosWorkLogWorkerExportRequest request ){
    Map<String,Object> condition = request.getConditionMap();
    condition.put("wpId", wpId);
    if(StringUtils.isNotBlank(monitorType)){
      condition.put("monitorType", monitorType);
    }
    return workPlaceWorkerDao.findMonitoringWorkerListByCondition(condition);
  }

  public List<ImosCoopStatVo> findCoopListByCommuteType(String wpId, String monitorType, int commuteType){
    Map<String,Object> condition = AuthenticationHelper.addAdditionalConditionByLevel();
    condition.put("wpId", wpId);
    if(StringUtils.isNotBlank(monitorType)){
      condition.put("monitorType", monitorType);
    }
    condition.put("commuteType", commuteType);
    return workPlaceWorkerDao.findCoopListByCommuteType(condition);
  }

  public List<ImosCoopStatVo> findCoopListByCurrentWorking(String wpId, String monitorType, Integer currentWorking){
    Map<String,Object> condition = AuthenticationHelper.addAdditionalConditionByLevel();
    condition.put("wpId", wpId);
    if(StringUtils.isNotBlank(monitorType)){
      condition.put("monitorType", monitorType);
    }
    condition.put("currentWorking", currentWorking);
    return workPlaceWorkerDao.findCoopListByCurrentWorking(condition);
  }

  public List<ImosCoopStatVo> findCoopList(String wpId, String monitorType){
    Map<String,Object> condition = AuthenticationHelper.addAdditionalConditionByLevel();
    condition.put("wpId", wpId);
    if(StringUtils.isNotBlank(monitorType)){
      condition.put("monitorType", monitorType);
    }
    condition.put("currentWorking", EnableCode.ENABLED.getCode());
    return workPlaceWorkerDao.findCoopListByCurrentWorking(condition);
  }


}

