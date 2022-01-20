package kr.co.hulan.aas.mvc.dao.mapper;

import kr.co.hulan.aas.mvc.model.dto.G5WriteNoticeDto;
import kr.co.hulan.aas.mvc.model.dto.G5WriteWpboardDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface G5WriteWpboardDao {
    List<G5WriteWpboardDto> findListByCondition(Map<String,Object> condition);
    Long findListCountByCondition(Map<String,Object> condition);

    G5WriteWpboardDto findById(int wrId);

    int create(G5WriteWpboardDto dto);
    int update(G5WriteWpboardDto dto);
    int updateParent(G5WriteWpboardDto dto);

    void increaseHitCount(G5WriteWpboardDto dto);
    void updateFileCount(int wrId);
    void updateAction(G5WriteWpboardDto dto);
    
}
