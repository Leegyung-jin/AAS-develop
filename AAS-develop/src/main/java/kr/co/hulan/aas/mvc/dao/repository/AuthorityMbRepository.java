package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.AuthorityMb;
import kr.co.hulan.aas.mvc.model.domain.AuthorityMbKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorityMbRepository extends JpaRepository<AuthorityMb, AuthorityMbKey> {

//    List<AuthorityMb> findauthorityIdAndmbLevel(String authorityId, Integer mbLevel);
//    @Modifying
//    @Query("delete from AuthorityMb AUMB where AUMB.MbId =:mbId")
//    int deleteByMbId(String mbId);
}
