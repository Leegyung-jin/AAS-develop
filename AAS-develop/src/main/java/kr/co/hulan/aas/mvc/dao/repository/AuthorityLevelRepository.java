package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.AuthorityLevel;
import kr.co.hulan.aas.mvc.model.domain.AuthorityLevelKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityLevelRepository extends JpaRepository<AuthorityLevel, AuthorityLevelKey> {

}
