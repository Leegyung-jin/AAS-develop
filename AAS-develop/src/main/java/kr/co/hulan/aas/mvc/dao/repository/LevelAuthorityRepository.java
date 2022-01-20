package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.AuthorityLevel;
import kr.co.hulan.aas.mvc.model.domain.AuthorityLevelKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelAuthorityRepository extends JpaRepository<AuthorityLevel, AuthorityLevelKey> {

    @Modifying
    @Query("delete from AuthorityLevel AULV where AULV.mbLevel =:mbLevel")
    int deleteByMbLevel(Integer mbLevel);

}
