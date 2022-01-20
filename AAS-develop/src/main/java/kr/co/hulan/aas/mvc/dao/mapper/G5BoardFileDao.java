package kr.co.hulan.aas.mvc.dao.mapper;


import kr.co.hulan.aas.mvc.model.dto.G5BoardFileDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface G5BoardFileDao {
    List<G5BoardFileDto> findListByCondition(Map<String,Object> condition);
    Long findListCountByCondition(Map<String,Object> condition);

    G5BoardFileDto findByKey(G5BoardFileDto dto);

    int create(G5BoardFileDto dto);
    int delete(G5BoardFileDto dto);
    int deleteBlank(Map<String,Object> condition);


}
