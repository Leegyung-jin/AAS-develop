package kr.co.hulan.aas.mvc.dao.mapper;

import kr.co.hulan.aas.mvc.model.dto.WorkerWarnDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface WorkerWarnDao {


    List<WorkerWarnDto> findListByCondition(Map<String,Object> condition);
    Long findListCountByCondition(Map<String,Object> condition);

    WorkerWarnDto findById(int wcIdx);
    int create(WorkerWarnDto dto);
    int update(WorkerWarnDto dto);
}
