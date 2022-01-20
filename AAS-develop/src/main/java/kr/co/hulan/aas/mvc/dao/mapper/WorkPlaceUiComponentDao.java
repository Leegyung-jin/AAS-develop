package kr.co.hulan.aas.mvc.dao.mapper;

import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.mvc.model.domain.WorkPlaceUiComponentCompositeKey;
import kr.co.hulan.aas.mvc.model.dto.UiComponentDto;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceUiComponentDto;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkPlaceUiComponentDao {

  List<WorkPlaceUiComponentDto> findListByCondition(Map<String,Object> condition);

  WorkPlaceUiComponentDto findById(WorkPlaceUiComponentCompositeKey key);

  List<UiComponentDto> findSupportedListById(WorkPlaceUiComponentCompositeKey key);

}
