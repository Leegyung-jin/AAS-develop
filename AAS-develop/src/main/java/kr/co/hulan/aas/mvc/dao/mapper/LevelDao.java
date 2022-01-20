package kr.co.hulan.aas.mvc.dao.mapper;

import kr.co.hulan.aas.mvc.api.level.model.dto.LevelDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface LevelDao {

    // Search
    List<LevelDto> findListByCondition(Map<String, Object> condition);
    Integer countListByCondition(Map<String, Object> addAdditionalConditionByLevel);

    // Search-Detail
    LevelDto findInfo(Integer mbLevel);
    List<String> findSelectedAuthorityList(Integer mbLevel);
}
