package kr.co.hulan.aas.mvc.dao.mapper;

import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.mvc.model.dto.UiComponentDto;
import org.springframework.stereotype.Repository;

@Repository
public interface UiComponentDao {
  List<UiComponentDto> findListByCondition(Map<String,Object> condition);
  Long findListCountByCondition(Map<String,Object> condition);

  UiComponentDto findById(String cmptId);
}
