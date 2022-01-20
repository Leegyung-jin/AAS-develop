package kr.co.hulan.aas.mvc.dao.mapper;

import kr.co.hulan.aas.mvc.api.member.dto.ConstructionCompanyDto;
import kr.co.hulan.aas.mvc.model.dto.ConCompanyDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ConCompanyDao {

    List<ConstructionCompanyDto> findListByCondition(Map<String,Object> condition);
    List<ConstructionCompanyDto> findListForCode(Map<String,Object> condition);
    Long findListCountByCondition(Map<String,Object> condition);

    ConCompanyDto findInfo(String ccId);

}
