package kr.co.hulan.aas.mvc.dao.mapper;

import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceDeviceStateVo;
import kr.co.hulan.aas.mvc.model.dto.WorkDeviceInfoDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface WorkDeviceInfoDao {

    List<WorkDeviceInfoDto> findListByCondition(Map<String,Object> condition);
    Long findListCountByCondition(Map<String,Object> condition);

    WorkDeviceInfoDto findById(int idx);

    List<HiccWorkplaceDeviceStateVo> findDeviceStateByWpId(@Param(value="wpId") String wpId );
}
