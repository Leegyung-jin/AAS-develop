package kr.co.hulan.aas.mvc.dao.mapper;

import kr.co.hulan.aas.mvc.api.hicc.vo.main.HiccNationWideStateIndicatorVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.main.HiccWorkplaceForIntegGroupVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccRegionStatVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccStateIndicator2Vo;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccStateIndicatorVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceStateVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceSummaryPerSidoVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceSummaryPerSigunguVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceSummaryVo;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.WorkplaceSupportMonitoringDto;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface WorkPlaceDao {

    List<WorkPlaceDto> findListByCondition(Map<String,Object> condition);
    Long findListCountByCondition(Map<String,Object> condition);

    List<WorkplaceSupportMonitoringDto> findWorkplaceSupportMonitoring(Map<String,Object> condition);

    List<WorkPlaceDto> findSensorSituationListByCondition(Map<String,Object> condition);
    Long findSensorSituationListCountByCondition(Map<String,Object> condition);

    List<WorkPlaceDto> findSafetySituationListByCondition(Map<String,Object> condition);
    List<WorkPlaceDto> findListForCode(Map<String,Object> condition);
    Long findSafetySituationListCountByCondition(Map<String,Object> condition);

    WorkPlaceDto findById(String wpId);

    int create(WorkPlaceDto dto);
    int update(WorkPlaceDto dto);

    List<HiccWorkplaceSummaryVo> findSupportedWorkplaceByAccount(Map<String,Object> condition);
    Integer countSupportedWorkplaceByAccount(Map<String,Object> condition);

    List<HiccWorkplaceSummaryPerSidoVo> findWorkplaceSummaryPerSido(Map<String,Object> condition);

    List<HiccWorkplaceSummaryPerSigunguVo> findWorkplaceSummaryByRegion(Map<String,Object> condition);

    @Deprecated
    HiccWorkplaceStateVo findWorkplaceState(Map<String,Object> condition);

    HiccStateIndicator2Vo findWorkplaceState2(Map<String,Object> condition);

    @Deprecated
    HiccStateIndicatorVo findTotalWorkplaceState(Map<String,Object> condition);

    HiccStateIndicator2Vo findTotalWorkplaceState2(Map<String,Object> condition);

    List<HiccWorkplaceForIntegGroupVo> findWorkplaceListForNationWide(Map<String,Object> condition);

    HiccNationWideStateIndicatorVo findNationWideState(Map<String,Object> condition);

    List<HiccRegionStatVo> findHiccRegionStat(Map<String,Object> condition);
}
