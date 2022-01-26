package kr.co.hulan.aas.mvc.dao.mapper;

import kr.co.hulan.aas.mvc.api.authority.model.dto.AuthorityManagerDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AuthorityManagerDao {

//    List<AuthorityManagerDto> findListByCondition(Map<String, Object> condition);
//    Integer countListByCondition(Map<String, Object> condition);

    AuthorityManagerDto findInfo(String mbId);
    List<String> findSelectedAuthorityList(String mbId);
}
