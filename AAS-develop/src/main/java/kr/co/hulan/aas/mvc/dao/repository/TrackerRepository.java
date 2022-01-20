package kr.co.hulan.aas.mvc.dao.repository;


import kr.co.hulan.aas.mvc.model.domain.Tracker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackerRepository extends JpaRepository<Tracker, String> {

}
