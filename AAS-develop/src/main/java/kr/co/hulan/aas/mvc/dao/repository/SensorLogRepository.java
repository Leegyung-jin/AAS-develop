package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.SensorLogInout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorLogRepository extends JpaRepository<SensorLogInout, Integer> {


}
