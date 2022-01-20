package kr.co.hulan.aas.mvc.api.msg.service;

import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.mvc.api.msg.controller.request.ListAdminMsgInfoRequest;
import kr.co.hulan.aas.mvc.api.msg.controller.response.ListAdminMsgInfoResponse;
import kr.co.hulan.aas.mvc.dao.mapper.AdminMsgInfoDao;
import kr.co.hulan.aas.mvc.model.dto.AdminMsgInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminMsgInfoService {

  @Autowired
  private AdminMsgInfoDao adminMsgInfoDao;

  public ListAdminMsgInfoResponse findListPage(ListAdminMsgInfoRequest request) {
    return new ListAdminMsgInfoResponse(
        findListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(request.getConditionMap())),
        findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(request.getConditionMap()))
    );
  }

  public List<AdminMsgInfoDto> findListByCondition(Map<String, Object> conditionMap) {
    return adminMsgInfoDao.findListByCondition(conditionMap);
  }

  private Long findListCountByCondition(Map<String, Object> conditionMap) {
    return adminMsgInfoDao.findListCountByCondition(conditionMap);
  }

  public AdminMsgInfoDto findById(int idx){
    return adminMsgInfoDao.findById(idx);
  }

}
