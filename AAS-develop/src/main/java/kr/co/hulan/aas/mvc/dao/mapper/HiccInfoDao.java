package kr.co.hulan.aas.mvc.dao.mapper;

import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.mvc.api.hicc.vo.main.HiccIntegGroupVo;
import kr.co.hulan.aas.mvc.model.dto.HiccInfoDto;
import kr.co.hulan.aas.mvc.model.dto.HiccMainBtnDto;
import org.springframework.stereotype.Repository;

@Repository
public interface HiccInfoDao {

  public HiccInfoDto findHiccInfoByLoginUser(Map<String,Object> condition);

  public HiccInfoDto findHiccInfo(long hiccNo);

  public List<HiccIntegGroupVo> HiccIntegGroupStatList(Map<String,Object> condition);

  public List<HiccMainBtnDto> findHiccMainButtonList(long hiccNo);
}
