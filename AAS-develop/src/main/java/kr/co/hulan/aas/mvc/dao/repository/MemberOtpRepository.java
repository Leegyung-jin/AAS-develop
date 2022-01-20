package kr.co.hulan.aas.mvc.dao.repository;

import java.util.Date;
import kr.co.hulan.aas.mvc.model.domain.MemberOtp;
import kr.co.hulan.aas.mvc.model.domain.MemberOtpKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberOtpRepository extends JpaRepository<MemberOtp, MemberOtpKey> {

  MemberOtp findTopByMbIdAndCreateDateAfterAndOtpNum(String mbId, Date checkDate, int otpNum);

  long countByMbIdAndCreateDateAfter(String mbId, Date checkDate);
}
