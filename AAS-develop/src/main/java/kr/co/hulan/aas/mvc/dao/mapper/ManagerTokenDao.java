package kr.co.hulan.aas.mvc.dao.mapper;

import kr.co.hulan.aas.mvc.model.domain.ManagerToken;
import kr.co.hulan.aas.mvc.model.dto.ManagerTokenDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ManagerTokenDao {

    //String mbId, int mtYn
    List<ManagerTokenDto> findListByCondition(Map<String,Object> condition);

}
