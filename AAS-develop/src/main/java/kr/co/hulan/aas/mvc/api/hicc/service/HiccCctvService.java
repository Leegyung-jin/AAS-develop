package kr.co.hulan.aas.mvc.api.hicc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.common.code.EnableCode;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.mvc.api.device.service.WorkPlaceCctvService;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceSummaryVo;
import kr.co.hulan.aas.mvc.dao.mapper.WorkPlaceCctvDao;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceCctvDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HiccCctvService {

  @Autowired
  private WorkPlaceCctvDao workPlaceCctvDao;

  @Autowired
  private WorkPlaceCctvService workPlaceCctvService;

  public List<HiccWorkplaceSummaryVo> findWorkplaceSupportCctv(){
    return workPlaceCctvDao.findWorkplaceSupportCctv(AuthenticationHelper.addAdditionalConditionByLevel());
  }

  public List<WorkPlaceCctvDto> findCctvList(String wpId){
    Map<String,Object> condition = new HashMap<String,Object>();
    condition.put("wpId", wpId);
    condition.put("status", EnableCode.ENABLED.getCode());
    return workPlaceCctvService.findListByCondition(condition);
  }
}
