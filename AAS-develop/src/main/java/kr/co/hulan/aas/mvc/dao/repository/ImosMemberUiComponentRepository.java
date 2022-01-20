package kr.co.hulan.aas.mvc.dao.repository;


import java.util.List;
import kr.co.hulan.aas.mvc.model.domain.ImosMemberUiComponent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImosMemberUiComponentRepository extends
    JpaRepository<ImosMemberUiComponent, String> {

  ImosMemberUiComponent findByWpIdAndMbIdAndDeployPageAndPosXAndPosY(
      String wpId,
      String mbId,
      int deployPage,
      int posX,
      int posY
  );

  List<ImosMemberUiComponent> findByWpIdAndMbIdAndDeployPage(
      String wpId,
      String mbId,
      int deployPage
  );
}
