package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.UiComponent;
import kr.co.hulan.aas.mvc.model.domain.WorkDeviceInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UiComponentRepository extends JpaRepository<UiComponent, String> {

}
