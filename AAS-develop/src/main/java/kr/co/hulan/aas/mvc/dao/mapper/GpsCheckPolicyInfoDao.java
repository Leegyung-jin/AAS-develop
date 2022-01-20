package kr.co.hulan.aas.mvc.dao.mapper;

import kr.co.hulan.aas.mvc.model.dto.GpsCheckPolicyInfoDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface GpsCheckPolicyInfoDao {

    List<GpsCheckPolicyInfoDto> findListByCondition(Map<String,Object> condition);
    Long findListCountByCondition(Map<String,Object> condition);

    GpsCheckPolicyInfoDto findById(int idx);
    GpsCheckPolicyInfoDto findByWpId(String idx);


}
