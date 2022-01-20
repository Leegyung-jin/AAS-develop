package kr.co.hulan.aas.mvc.dao.repository;

import java.util.List;
import kr.co.hulan.aas.mvc.model.domain.nvr.NetworkVideoRecoderChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NetworkVideoRecoderChannelRepository extends JpaRepository<NetworkVideoRecoderChannel, String> {

  List<NetworkVideoRecoderChannel> findByNvrNo(long nvrNo);

  NetworkVideoRecoderChannel findFirstByNvrNoAndUid(long nvrNo, int uid);
}
