package kr.co.hulan.aas.mvc.api.gas.service;

import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.mvc.api.device.controller.request.ListWorkDeviceInfoRequest;
import kr.co.hulan.aas.mvc.api.device.controller.response.ListWorkDeviceInfoResponse;
import kr.co.hulan.aas.mvc.api.gas.controller.request.ListGasLogRequest;
import kr.co.hulan.aas.mvc.api.gas.controller.response.ListGasLogResponse;
import kr.co.hulan.aas.mvc.dao.mapper.GasLogDao;
import kr.co.hulan.aas.mvc.model.dto.GasLogDto;
import kr.co.hulan.aas.mvc.model.dto.WorkDeviceInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GasService {

  @Autowired
  private GasLogDao gasLogDao;

  public ListGasLogResponse findListPage(ListGasLogRequest request) {
    return new ListGasLogResponse(
        findListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(request.getConditionMap())),
        findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(request.getConditionMap()))
    );
  }

  public List<GasLogDto> findListByCondition(Map<String, Object> conditionMap) {
    return gasLogDao.findListByCondition(conditionMap);
  }

  public Long findListCountByCondition(Map<String, Object> conditionMap) {
    return gasLogDao.findListCountByCondition(conditionMap);
  }
}
