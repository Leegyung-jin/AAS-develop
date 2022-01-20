package kr.co.hulan.aas.mvc.dao.repository;

import java.util.List;
import kr.co.hulan.aas.mvc.model.domain.nvr.NetworkVideoRecoder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NetworkVideoRecoderRepository extends JpaRepository<NetworkVideoRecoder, Long> {

  boolean existsByIpAndPort(String ip, int port);
  boolean existsByIpAndPortAndNvrNoNot(String ip, int port, long nvrNo);

}
