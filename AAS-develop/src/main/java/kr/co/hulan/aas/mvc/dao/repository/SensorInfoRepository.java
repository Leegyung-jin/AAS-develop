package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.SensorInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorInfoRepository extends JpaRepository<SensorInfo, Integer> {

    long countByWpIdAndSiCode(String wpId, String siCode);
    long countByWpIdAndSiCodeEqualsAndSiIdxNot(String wpId, String siCode, int siIdx);
    long countBySdIdx(int sdIdx);

}
