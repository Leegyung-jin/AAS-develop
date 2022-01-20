package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.OrderingOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderingOfficeRepository extends JpaRepository<OrderingOffice, Long> {


  boolean existsByBiznum(String biznum);

  boolean existsByBiznumAndOfficeNoNot(String biznum, long officeNo);
}
