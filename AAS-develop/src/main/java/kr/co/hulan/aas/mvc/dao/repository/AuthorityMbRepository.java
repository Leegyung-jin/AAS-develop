package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.AuthorityMb;
import kr.co.hulan.aas.mvc.model.domain.AuthorityMbKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface AuthorityMbRepository extends JpaRepository<AuthorityMb, AuthorityMbKey> {

    @Transactional
    @Modifying
    @Query("delete from AuthorityMb AUMB where AUMB.mbId NOT IN(:mbId) and AUMB.authorityId =:authorityId")
    int deleteByMbId(String mbId, String authorityId);
}
