package kr.co.hulan.aas.mvc.dao.mapper;

import kr.co.hulan.aas.mvc.model.dto.G5ContentDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface G5ContentDao {
    List<G5ContentDto> findListByCondition(Map<String,Object> condition);
    Long findListCountByCondition(Map<String,Object> condition);

    G5ContentDto findById(String coId);

    int create(G5ContentDto dto);
    int update(G5ContentDto dto);
}
