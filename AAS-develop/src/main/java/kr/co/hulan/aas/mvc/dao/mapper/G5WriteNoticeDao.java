package kr.co.hulan.aas.mvc.dao.mapper;

import kr.co.hulan.aas.mvc.model.dto.G5WriteNoticeDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface G5WriteNoticeDao {
    List<G5WriteNoticeDto> findListByCondition(Map<String,Object> condition);
    Long findListCountByCondition(Map<String,Object> condition);

    G5WriteNoticeDto findById(int wrId);

    int create(G5WriteNoticeDto dto);
    int update(G5WriteNoticeDto dto);
    int updateParent(G5WriteNoticeDto dto);

    void increaseHitCount(G5WriteNoticeDto dto);
    void updateFileCount(int wrId);
    void addNoticeFlag(int wrId);
    void removeNoticeFlag(int wrId);
}
