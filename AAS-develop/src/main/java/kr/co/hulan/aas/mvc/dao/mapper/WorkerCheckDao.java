package kr.co.hulan.aas.mvc.dao.mapper;

import kr.co.hulan.aas.mvc.api.monitor4_1.vo.DetectedSensorWorkerVo;
import kr.co.hulan.aas.mvc.model.dto.G5MemberDto;
import kr.co.hulan.aas.mvc.model.dto.WorkerCheckDto;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface WorkerCheckDao {

    List<WorkerCheckDto> findListByCondition(Map<String,Object> condition);
    Long findListCountByCondition(Map<String,Object> condition);

    WorkerCheckDto findById(int wcIdx);
    int create(WorkerCheckDto dto);
    int update(WorkerCheckDto dto);

    int updateWorkerMbName(G5MemberDto dto);

    List<Map<String, Object>> selectWorkCheckSensorTrace(String wpId);
    List<Map<String, Object>> selectDangerAreaWorkerSensorTrace(String wpId);

    List<DetectedSensorWorkerVo> findCheckWorkerList(Map<String,Object> condition);
    List<DetectedSensorWorkerVo> findDangerAreaWorkerList(Map<String,Object> condition);


}
