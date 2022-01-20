package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.ManagerToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManagerTokenRepository extends JpaRepository<ManagerToken, Integer> {

    List<ManagerToken> findByMbIdAndMtYn(String mbId, int mtYn);
}
