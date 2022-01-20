package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.MagneticOpenRecent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MagneticOpenRecentRepository extends
    JpaRepository<MagneticOpenRecent, String> {

}
