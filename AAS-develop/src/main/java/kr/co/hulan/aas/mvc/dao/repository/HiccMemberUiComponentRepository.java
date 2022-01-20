package kr.co.hulan.aas.mvc.dao.repository;

import java.util.List;
import kr.co.hulan.aas.mvc.model.domain.HiccMemberUiComponent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HiccMemberUiComponentRepository extends
    JpaRepository<HiccMemberUiComponent, Long> {

  HiccMemberUiComponent findByMbIdAndDeployPageAndPosXAndPosY(
      String mbId,
      int deployPage,
      int posX,
      int posY
  );

  List<HiccMemberUiComponent> findByMbIdAndDeployPage(
      String mbId,
      int deployPage
  );

}
