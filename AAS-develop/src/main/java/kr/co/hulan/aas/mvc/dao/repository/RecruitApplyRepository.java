package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.RecruitApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RecruitApplyRepository extends JpaRepository<RecruitApply, Integer> {

  @Modifying
  @Query("delete from RecruitApply RCA where RCA.wpId =:wpId")
  void deleteAllByWpId(String wpId);

  long countByWpIdAndCoopMbIdAndMbIdAndRaStatusNot(String wpId, String coopMbId, String mbId, String raStatus );
}
