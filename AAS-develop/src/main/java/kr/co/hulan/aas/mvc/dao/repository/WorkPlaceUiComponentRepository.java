package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.WorkPlaceUiComponent;
import kr.co.hulan.aas.mvc.model.domain.WorkPlaceUiComponentCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkPlaceUiComponentRepository extends JpaRepository<WorkPlaceUiComponent, WorkPlaceUiComponentCompositeKey> {

  long deleteAllByCmptId(String cmptId);
}
