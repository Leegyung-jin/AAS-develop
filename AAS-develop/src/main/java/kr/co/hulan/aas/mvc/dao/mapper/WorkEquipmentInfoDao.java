package kr.co.hulan.aas.mvc.dao.mapper;

import kr.co.hulan.aas.mvc.api.monitor4_1.vo.EquipmentSituationVo;
import kr.co.hulan.aas.mvc.api.monitor4_1.vo.OpeEquipmentVo;
import kr.co.hulan.aas.mvc.model.dto.WorkEquipmentInfoDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface WorkEquipmentInfoDao {

    List<WorkEquipmentInfoDto> findListByCondition(Map<String,Object> condition);
    Long findListCountByCondition(Map<String,Object> condition);

    WorkEquipmentInfoDto findById(int idx);

    List<EquipmentSituationVo> findOpeEquipmentStatus(Map<String,Object> condition);
    List<OpeEquipmentVo> findOpeEquipmentList(Map<String,Object> condition);
    List<OpeEquipmentVo> findRegEquipmentList(Map<String,Object> condition);

}
