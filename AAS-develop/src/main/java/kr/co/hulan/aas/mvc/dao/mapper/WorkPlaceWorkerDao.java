package kr.co.hulan.aas.mvc.dao.mapper;

import kr.co.hulan.aas.mvc.api.monitor.controller.response.AttendantVo;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.AttendantSituationVo;
import kr.co.hulan.aas.mvc.api.monitor4_1.vo.CommuteTypeSummary;
import kr.co.hulan.aas.mvc.api.monitor4_1.vo.CurrentWorkStatusSummary;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.ImosCoopStatVo;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.ImosWorkingWorkerVo;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.ImosCommuteTypeStatVo;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.response.ListWorkerFatiqueResponse;
import kr.co.hulan.aas.mvc.model.dto.G5MemberDto;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceWorkerDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface WorkPlaceWorkerDao {

    List<WorkPlaceWorkerDto> findListByCondition(Map<String,Object> condition);
    Long findListCountByCondition(Map<String,Object> condition);

    List<WorkPlaceWorkerDto> findWorkerFatiqueListByCondition(Map<String,Object> condition);
    // Long findWorkerFatiqueListCountByCondition(Map<String,Object> condition);
    ListWorkerFatiqueResponse findWorkerFatiqueListPerTypeCountByCondition(Map<String,Object> condition);

    List<WorkPlaceWorkerDto> findAttendantListByCondition(Map<String,Object> condition);
    Long findAttendantListCountByCondition(Map<String,Object> condition);

    List<AttendantSituationVo> findAttendantSituationListByCondition(Map<String,Object> condition);
    Long findAttendantSituationListCountByCondition(Map<String,Object> condition);

    List<WorkPlaceWorkerDto> findEducationAttendeeListByCondition(Map<String,Object> condition);
    Long findEducationAttendeeListCountByCondition(Map<String,Object> condition);

    WorkPlaceWorkerDto findById(String wpwId);
    int create(WorkPlaceWorkerDto dto);

    int updateWorkerMbName(G5MemberDto dto);

    List<G5MemberDto> findWorkplaceWorkerForPush(Map<String,Object> condition);

    CommuteTypeSummary findCommuteTypeSummary(String wpId);

    List<AttendantVo> findQrEnterCommuteWorker(String wpId);
    List<AttendantVo> findAppEnterCommuteWorker(String wpId);

    List<AttendantVo> findCurrentEntranceWorkerList(Map<String,Object> condition);


    /* IMOS 근로자 출역관리 컴포넌트 */
    List<ImosCommuteTypeStatVo> findCommuteTypeStatList(Map<String,Object> condition);
    CurrentWorkStatusSummary findCurrentWorkStatus(Map<String,Object> condition);

    List<ImosCoopStatVo> findCoopListByCommuteType(Map<String,Object> condition);
    List<ImosCoopStatVo> findCoopListByCurrentWorking(Map<String,Object> condition);

    List<ImosWorkingWorkerVo> findMonitoringWorkerListByCondition(Map<String,Object> condition);

}
