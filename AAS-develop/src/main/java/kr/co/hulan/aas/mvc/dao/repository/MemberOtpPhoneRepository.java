package kr.co.hulan.aas.mvc.dao.repository;

import java.util.List;
import kr.co.hulan.aas.mvc.model.domain.MemberOtpPhone;
import kr.co.hulan.aas.mvc.model.domain.MemberOtpPhoneKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberOtpPhoneRepository extends JpaRepository<MemberOtpPhone, MemberOtpPhoneKey> {

  boolean existsByMbIdAndPhoneNo(String mbId, String phoneNo);
}
