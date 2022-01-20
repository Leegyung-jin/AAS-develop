package kr.co.hulan.aas.mvc.api.authority.service;

import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.mvc.api.authority.controller.request.AuthorityListRequest;
import kr.co.hulan.aas.mvc.api.authority.controller.response.AuthorityResponse;
import kr.co.hulan.aas.mvc.api.authority.model.dto.AuthorityDto;
import kr.co.hulan.aas.mvc.api.authority.model.dto.AuthorityUserDto;
import kr.co.hulan.aas.mvc.dao.mapper.AuthorityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AuthorityService {

    @Autowired
    private AuthorityDao authorityDao;

    public DefaultPageResult<AuthorityDto> findListPage(AuthorityListRequest request) {
        return DefaultPageResult.<AuthorityDto>builder()
                .currentPage(request.getPage())
                .pageSize(request.getPageSize())
                .totalCount(countListByCondition(request.getConditionMap()))
                .list(findListByCondition(request.getConditionMap()))
                .build();
    }

//    public AuthorityResponse findListPage(AuthorityListRequest request) {
//        return new AuthorityResponse(
//                countListByCondition(request.getConditionMap()),
//                findListByCondition(request.getConditionMap())
//        );
//    }

    public List<AuthorityDto> findListByCondition(Map<String, Object> conditionMap) {
        return authorityDao.findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }

    public Integer countListByCondition(Map<String, Object> conditionMap) {
        return authorityDao.countListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }

    public AuthorityDto findInfo(String authorityId) {
        AuthorityDto currentVo = authorityDao.findInfo(authorityId);
        List<String> selectedLevelList = authorityDao.findSelectedLevelList(authorityId);
        List<AuthorityUserDto> usedAuthorityUserList = authorityDao.findUsedAuthorityUserList(authorityId);
//        Integer countfindUsedAuthorityUserList = authorityDao.countfindUsedAuthorityUserList(authorityId);

        // el-transfer
        currentVo.setSelectedLevelList(selectedLevelList);
        currentVo.setUsedAuthorityUserList(usedAuthorityUserList);
//        currentVo.countfindUsedAuthorityUserList(usedAuthorityUserList);

        if (currentVo != null ) {
            return currentVo;
        } else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }
    }
}
