package kr.co.hulan.aas.mvc.dao.mapper;

import kr.co.hulan.aas.mvc.model.dto.WorkerWorkPrintDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface WorkerWorkPrintDao {
    List<WorkerWorkPrintDto> findListByCondition(Map<String,Object> condition);
    Long findListCountByCondition(Map<String,Object> condition);

    int create(WorkerWorkPrintDto workerWorkPrintDto);
    int update(WorkerWorkPrintDto workerWorkPrintDto);
    int updateStatus(WorkerWorkPrintDto workerWorkPrintDto);

}
