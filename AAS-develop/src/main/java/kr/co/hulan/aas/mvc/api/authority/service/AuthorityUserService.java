package kr.co.hulan.aas.mvc.api.authority.service;

import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.mvc.api.authority.controller.request.AuthorityUserListRequest;
import kr.co.hulan.aas.mvc.api.authority.model.dto.AuthorityUserDto;
import kr.co.hulan.aas.mvc.dao.mapper.AuthorityUserDao;
import kr.co.hulan.aas.mvc.dao.mapper.G5MemberDao;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AuthorityUserService {

    private Logger logger;

    @Autowired
    G5MemberDao g5MemberDao;

//    @Autowired
//    private AuthorityUserDao authorityUserDao;

//    public DefaultPageResult<AuthorityUserDto> findListPage(AuthorityUserListRequest request) {
//        return DefaultPageResult.<AuthorityUserDto>builder()
//                .currentPage(request.getPage())
//                .pageSize(request.getPageSize())
//                .totalCount(countListByCondition(request.getConditionMap()))
//                .list(findListByCondition(request.getConditionMap()))
//                .build();
//    }
//
//    public List<AuthorityUserDto> findListByCondition(Map<String,Object> conditionMap) {
//        return g5MemberDao.findAuthorityInsertUserListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
//    }
//
//    private Long countListByCondition(Map<String,Object> conditionMap) {
//        return g5MemberDao.countfindAuthorityInsertUserListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
//    }

}
