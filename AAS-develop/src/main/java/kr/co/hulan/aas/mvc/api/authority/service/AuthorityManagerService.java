package kr.co.hulan.aas.mvc.api.authority.service;

import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.mvc.api.authority.controller.request.AuthorityManagerListRequest;
import kr.co.hulan.aas.mvc.api.authority.controller.request.AuthorityUserListRequest;
import kr.co.hulan.aas.mvc.api.authority.model.dto.AuthorityDto;
import kr.co.hulan.aas.mvc.api.authority.model.dto.AuthorityManagerDto;
import kr.co.hulan.aas.mvc.api.authority.model.dto.AuthorityUserDto;
import kr.co.hulan.aas.mvc.dao.mapper.AuthorityManagerDao;
import kr.co.hulan.aas.mvc.dao.mapper.G5MemberDao;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AuthorityManagerService {

    private Logger logger;

    @Autowired
    AuthorityManagerDao authorityManagerDao;

//    public DefaultPageResult<AuthorityManagerDto> findListPage(AuthorityManagerListRequest request) {
////        Integer cnt = countListByCondition(request.getConditionMap());
//        return DefaultPageResult.<AuthorityManagerDto>builder()
////                .currentPage(request.getPage())
////                .pageSize(request.getPageSize())
//                .totalCount(countListByCondition(request.getConditionMap()))
//                .list(findListByCondition(request.getConditionMap()))
//                .build();
//    }
//
//    public List<AuthorityManagerDto> findListByCondition(Map<String,Object> conditionMap) {
//        return authorityManagerDao.findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
//    }
//
//    private Integer countListByCondition(Map<String,Object> conditionMap) {
//        return authorityManagerDao.countListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
//    }
    public AuthorityManagerDto findInfo(String mbId) {
        AuthorityManagerDto currentVo = authorityManagerDao.findInfo(mbId);
        List<String> selectedAuthorityList = authorityManagerDao.findSelectedAuthorityList(mbId);
//        List<AuthorityUserDto> usedAuthorityUserList = authorityManagerDao.findUsedAuthorityUserList(authorityId);

        // el-transfer
        currentVo.setSelectedAuthorityList(selectedAuthorityList);

        if (currentVo != null ) {
            return currentVo;
        } else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }
    }
}