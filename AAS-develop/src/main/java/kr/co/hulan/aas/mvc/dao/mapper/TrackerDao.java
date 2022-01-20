package kr.co.hulan.aas.mvc.dao.mapper;

import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.mvc.model.dto.TrackerDto;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackerDao {
  List<TrackerDto> findListByCondition(Map<String,Object> condition);
  Long findListCountByCondition(Map<String,Object> condition);

  TrackerDto findById(String trackerId);
}
