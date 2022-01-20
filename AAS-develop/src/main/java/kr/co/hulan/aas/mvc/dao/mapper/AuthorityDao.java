package kr.co.hulan.aas.mvc.dao.mapper;

import kr.co.hulan.aas.mvc.api.authority.model.dto.AuthorityDto;
import kr.co.hulan.aas.mvc.api.authority.model.dto.AuthorityUserDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface AuthorityDao {

    // Search
    List<AuthorityDto> findListByCondition(Map<String, Object> condition);
    Integer countListByCondition(Map<String, Object> addAdditionalConditionByLevel);

    // Detail
    AuthorityDto findInfo(String authorityId);
    List<String> findSelectedLevelList(String authorityId);                     // 권한을 가진 등륵 목록
    List<AuthorityUserDto> findUsedAuthorityUserList(String authorityId);       // 권한을 사용중인 사용자 목록
    Integer countfindUsedAuthorityUserList(String addAdditionalConditionByLevel);  // 권한을 사용중인 사용자 수

}
