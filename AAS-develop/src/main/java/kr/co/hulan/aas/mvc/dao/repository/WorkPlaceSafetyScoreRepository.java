package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.WorkPlaceSafetyScore;
import kr.co.hulan.aas.mvc.model.domain.WorkPlaceSafetyScoreKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkPlaceSafetyScoreRepository extends JpaRepository<WorkPlaceSafetyScore, WorkPlaceSafetyScoreKey>  {

}
