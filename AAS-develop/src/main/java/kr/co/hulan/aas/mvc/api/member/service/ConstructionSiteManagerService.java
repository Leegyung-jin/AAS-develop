package kr.co.hulan.aas.mvc.api.member.service;

import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.common.utils.MysqlPasswordEncoder;
import kr.co.hulan.aas.mvc.api.member.controller.request.ConstructionSiteManagerPagingListRequest;
import kr.co.hulan.aas.mvc.api.member.controller.request.OfficeGroupManagerPagingListRequest;
import kr.co.hulan.aas.mvc.api.member.dto.ConstructionSiteManagerDto;
import kr.co.hulan.aas.mvc.api.member.dto.OfficeGroupManagerDto;
import kr.co.hulan.aas.mvc.api.orderoffice.service.OrderingOfficeMgrService;
import kr.co.hulan.aas.mvc.dao.mapper.G5MemberDao;
import kr.co.hulan.aas.mvc.dao.repository.G5MemberRepository;
import kr.co.hulan.aas.mvc.dao.repository.OfficeWorkplaceGrpRepository;
import kr.co.hulan.aas.mvc.dao.repository.OfficeWorkplaceManagerRepository;
import kr.co.hulan.aas.mvc.dao.repository.OrderingOfficeRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConstructionSiteManagerService {

  private Logger logger = LoggerFactory.getLogger(OrderingOfficeMgrService.class);

  @Autowired
  G5MemberDao g5MemberDao;

  public DefaultPageResult<ConstructionSiteManagerDto> findListPage(
      ConstructionSiteManagerPagingListRequest request) {
    return DefaultPageResult.<ConstructionSiteManagerDto>builder()
        .currentPage(request.getPage())
        .pageSize(request.getPageSize())
        .totalCount(countListByCondition(request.getConditionMap()))
        .list(findListByCondition(request.getConditionMap()))
        .build();
  }

  public List<ConstructionSiteManagerDto> findListByCondition(Map<String,Object> conditionMap) {
    return g5MemberDao.findConstructionSiteManagerListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
  }

  private Long countListByCondition(Map<String,Object> conditionMap) {
    return g5MemberDao.countConstructionSiteManagerListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
  }

}
