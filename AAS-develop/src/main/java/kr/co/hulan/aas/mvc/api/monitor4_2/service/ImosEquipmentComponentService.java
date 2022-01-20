package kr.co.hulan.aas.mvc.api.monitor4_2.service;

import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.mvc.api.monitor4_1.controller.response.OpeEquipmentStatusResponse;
import kr.co.hulan.aas.mvc.api.monitor4_1.vo.OpeEquipmentVo;
import kr.co.hulan.aas.mvc.dao.mapper.WorkEquipmentInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImosEquipmentComponentService {

  @Autowired
  private WorkEquipmentInfoDao workEquipmentInfoDao;


  public OpeEquipmentStatusResponse findEquipmentStatus(String wpId){
    Map<String,Object> condition = AuthenticationHelper.addAdditionalConditionByLevel();
    condition.put("wpId", wpId);
    return OpeEquipmentStatusResponse.builder()
        .equipmentList(workEquipmentInfoDao.findOpeEquipmentStatus(condition))
        .build();
  }

  public List<OpeEquipmentVo> findEquipmentListByType(String wpId, int equipmentType){
    Map<String,Object> condition = AuthenticationHelper.addAdditionalConditionByLevel();
    condition.put("wpId", wpId);
    condition.put("equipmentType", equipmentType);
    return workEquipmentInfoDao.findRegEquipmentList(condition);
  }
}
