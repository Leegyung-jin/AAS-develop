package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.WorkSection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkSectionRepository extends JpaRepository<WorkSection, String> {

    long countByParentSectionCd(String parentSectionCd);
}
