package kr.co.hulan.aas.mvc.dao.mapper;

import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.response.ImosMagneticOpeningStateResponse;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.ImosMagneticOpeningStateVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MagneticOpeningDao {

  ImosMagneticOpeningStateResponse findCurrentState(
      @Param(value="wpId")  String wpId
  );

  public List<ImosMagneticOpeningStateVo> findDeviceStateList(Map<String,Object> condition);
  public Integer countDeviceStateList(Map<String,Object> condition);



  /*
  List<ImosMagneticOpeningStateVo> findDeviceListByStatus(
      @Param(value="wpId") String wpId,
      @Param(value="status") int status
  );
   */
}
