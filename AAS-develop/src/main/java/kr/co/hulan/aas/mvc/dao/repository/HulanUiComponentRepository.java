package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.HulanUiComponent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HulanUiComponentRepository extends
    JpaRepository<HulanUiComponent, String> {

  HulanUiComponent findTopBySiteTypeAndUiTypeAndStatusOrderByCreateDateDesc(
      Integer sideType, Integer uiType, Integer status
  );
}
