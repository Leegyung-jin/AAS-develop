package kr.co.hulan.aas.mvc.dao.mapper;

import kr.co.hulan.aas.mvc.model.dto.CoopWorkerDto;
import org.springframework.stereotype.Repository;

@Repository
public interface CoopWorkerDao {
    CoopWorkerDto findById(String workerMbId);
    int create(CoopWorkerDto coopWorkerDto);
    int delete(String workerMbId);
}
