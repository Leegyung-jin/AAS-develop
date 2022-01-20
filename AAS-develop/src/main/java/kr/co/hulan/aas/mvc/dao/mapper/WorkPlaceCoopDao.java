package kr.co.hulan.aas.mvc.dao.mapper;

import kr.co.hulan.aas.mvc.api.hicc.vo.coop.CooperativeAttendanceStatVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.coop.CooperativeStateVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.coop.CooperativeVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.coop.SectionStatVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceAttendanceStatVo;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceCoopDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface WorkPlaceCoopDao {
    List<WorkPlaceCoopDto> findSafetySituationListByCondition(Map<String,Object> condition);
    Long findSafetySituationListCountByCondition(Map<String,Object> condition);

    WorkPlaceCoopDto findInfo(String wpcId);

    List<WorkPlaceCoopDto> findEducationNonAttendeeSituationListByCondition(Map<String,Object> condition);
    Long findEducationNonAttendeeSituationListCountByCondition(Map<String,Object> condition);

    WorkPlaceCoopDto findByWpIdAndCoopMbId(Map<String,Object> condition);

    int create(WorkPlaceCoopDto dto);
    int update(WorkPlaceCoopDto dto);

    CooperativeStateVo findCooperativeState(Map<String,Object> condition);
    List<SectionStatVo> findCooperativeSectionStatList(Map<String,Object> condition);

    List<CooperativeAttendanceStatVo> findCooperativeAttendanceStatList(Map<String,Object> condition);
    Long countCooperativeAttendanceStatList(Map<String,Object> condition);

    List<HiccWorkplaceAttendanceStatVo> findWorkplaceAttendanceStatListByCoop(Map<String,Object> condition);
    Long countWorkplaceAttendanceStatListByCoop(Map<String,Object> condition);

    List<CooperativeVo> findHiccCooperativeList(Map<String,Object> condition);

    Long countHiccWorkplaceCoopList(Map<String,Object> condition);

}
