package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.ConCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConCompanyRepository extends JpaRepository<ConCompany, String> {

    List<ConCompany> findAllByOrderByCcNameAsc();

    Optional<ConCompany> findByCcNum(String ccNum);

    @Query("SELECT count(CC.ccId) FROM ConCompany CC WHERE CC.ccNum = :ccNum AND CC.ccId <> :ccId ")
    Long getCountByCcNumAndCcIdNot(@Param("ccNum") String ccNum, @Param("ccId") String ccId );

    int deleteByCcId(String ccId);
}
