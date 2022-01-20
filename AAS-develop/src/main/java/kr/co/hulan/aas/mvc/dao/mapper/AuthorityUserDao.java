package kr.co.hulan.aas.mvc.dao.mapper;

import kr.co.hulan.aas.mvc.api.authority.model.dto.AuthorityUserDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface AuthorityUserDao {

    List<AuthorityUserDto> findListByCondition(Map<String, Object> addAdditionalConditionByLevel);
    Integer countListByCondition(Map<String, Object> addAdditionalConditionByLevel);
}
