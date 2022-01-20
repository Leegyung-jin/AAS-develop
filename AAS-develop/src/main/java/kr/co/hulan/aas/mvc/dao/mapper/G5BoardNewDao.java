package kr.co.hulan.aas.mvc.dao.mapper;

import kr.co.hulan.aas.mvc.model.dto.G5BoardNewDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface G5BoardNewDao {

    List<G5BoardNewDto> findList();
    int create(G5BoardNewDto dto);

}
