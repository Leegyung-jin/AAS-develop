package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.AuthorityLevel;
import kr.co.hulan.aas.mvc.model.domain.AuthorityLevelKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface AuthorityLevelRepository extends JpaRepository<AuthorityLevel, AuthorityLevelKey> {

    @Transactional
    @Modifying
    @Query("delete from AuthorityLevel AULV where AULV.authorityId =:authorityId")
    void deleteByAuthorityId(String authorityId);
}
