package kr.co.hulan.aas.mvc.dao.mapper;

import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.mvc.model.dto.NetworkVideoRecoderChannelDto;
import kr.co.hulan.aas.mvc.model.dto.NetworkVideoRecoderDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface NetworkVideoRecoderDao {

  List<NetworkVideoRecoderDto> findListByCondition(Map<String,Object> condition);
  Long countListByCondition(Map<String,Object> condition);

  NetworkVideoRecoderDto findById(long nvrNo);

  List<NetworkVideoRecoderChannelDto> findChannelList(long nvrNo);

  /*
  IMOS IntelliVix Component
   */
  List<NetworkVideoRecoderChannelDto> findChannelListByWpId(@Param(value = "wpId") String wpId);


}
