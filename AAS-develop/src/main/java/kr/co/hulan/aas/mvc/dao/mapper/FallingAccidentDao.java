package kr.co.hulan.aas.mvc.dao.mapper;

import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.mvc.model.dto.FallingAccidentDto;
import org.springframework.stereotype.Repository;

@Repository
public interface FallingAccidentDao {

  List<FallingAccidentDto> findListByCondition(Map<String,Object> condition);
  Long findListCountByCondition(Map<String,Object> condition);

  List<FallingAccidentDto> findFallingAccidentRecentListByWpId(String wpId);
  List<FallingAccidentDto> findFallingAccidentListByWpId(String wpId);


  FallingAccidentDto findById(String trackerId);

}
