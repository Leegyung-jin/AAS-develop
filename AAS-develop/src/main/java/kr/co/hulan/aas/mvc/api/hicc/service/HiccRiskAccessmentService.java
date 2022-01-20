package kr.co.hulan.aas.mvc.api.hicc.service;

import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.mvc.api.hicc.controller.request.RiskAccessmentInsepctPagingRequest;
import kr.co.hulan.aas.mvc.api.hicc.controller.response.HiccRiskAccessmentStateResponse;
import kr.co.hulan.aas.mvc.api.hicc.vo.risk.HiccRiskAccessmentInspectDetailVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.risk.HiccRiskAccessmentInspectVo;
import kr.co.hulan.aas.mvc.dao.mapper.RiskAccessmentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HiccRiskAccessmentService {

  @Autowired
  private RiskAccessmentDao riskAccessmentDao;

  public HiccRiskAccessmentStateResponse findHiccRiskAccessmentState(){
    Map<String,Object> condition = AuthenticationHelper.addAdditionalConditionByLevel();
    condition.put("startRow", 0);
    condition.put("pageSize", 20);

    return HiccRiskAccessmentStateResponse.builder()
        .state(riskAccessmentDao.findState(condition))
        .list(riskAccessmentDao.findInspectList(condition))
        .build();
  }

  public DefaultPageResult<HiccRiskAccessmentInspectVo> findInspectPagingList( RiskAccessmentInsepctPagingRequest request){
    Map<String,Object> condition = request.getConditionMap();
    return DefaultPageResult.<HiccRiskAccessmentInspectVo>builder()
        .pageSize(request.getPageSize())
        .currentPage(request.getPage())
        .list(riskAccessmentDao.findInspectList(condition))
        .totalCount(riskAccessmentDao.countInspectList(condition))
        .build();
  }

  public List<HiccRiskAccessmentInspectVo> findInspectList(Map<String,Object> condition){
    return riskAccessmentDao.findInspectList(condition);
  }

  public HiccRiskAccessmentInspectDetailVo findInspectInfo(long raiNo){
    HiccRiskAccessmentInspectDetailVo vo = riskAccessmentDao.findInspectInfo(raiNo);
    if( vo == null ){
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(),BaseCode.ERR_DETAIL_EXCEPTION.message());
    }
    return vo;
  }

  public List<String> findSectionList(){
    return riskAccessmentDao.findSectionList();
  }
}
