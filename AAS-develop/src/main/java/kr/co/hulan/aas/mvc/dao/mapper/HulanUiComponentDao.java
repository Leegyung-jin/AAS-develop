package kr.co.hulan.aas.mvc.dao.mapper;

import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.mvc.api.component.vo.HulanUiComponentDetailVo;
import kr.co.hulan.aas.mvc.api.component.vo.HulanUiComponentLevelSelectVo;
import kr.co.hulan.aas.mvc.api.component.vo.HulanUiComponentLevelVo;
import kr.co.hulan.aas.mvc.api.component.vo.HulanUiComponentVo;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.ImosMemberUiComponentVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.HiccMemberUiComponentVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HulanUiComponentDao {

  List<HulanUiComponentVo> findListByCondition(Map<String,Object> condition);
  Long countListByCondition(Map<String,Object> condition);

  HulanUiComponentDetailVo findInfo(String hcmptId);
  List<HulanUiComponentLevelVo> findSupportLevelList(@Param(value = "hcmptId") String hcmptId);
  List<HulanUiComponentLevelSelectVo> findSelectableLevelListByHcmptId(@Param(value = "hcmptId") String hcmptId);


  List<ImosMemberUiComponentVo> findImosMemberComponentListByMember(@Param(value = "wpId") String wpId, @Param(value = "mbId") String mbId, @Param(value = "deployPage") int deployPage);
  ImosMemberUiComponentVo findImosMemberComponentInfoById(long imucNo);
  ImosMemberUiComponentVo findImosMemberComponentInfo(
      @Param(value = "wpId") String wpId,
      @Param(value = "mbId") String mbId,
      @Param(value = "deployPage") int deployPage,
      @Param(value = "posX") int posX,
      @Param(value = "posY") int posY
  );

  List<HiccMemberUiComponentVo> findHiccMemberComponentListByMember(@Param(value = "mbId") String mbId, @Param(value = "deployPage") int deployPage);
  HiccMemberUiComponentVo findHiccMemberComponentInfoById(long hmucNo);
  HiccMemberUiComponentVo findHiccMemberComponentInfo(
      @Param(value = "mbId") String mbId,
      @Param(value = "deployPage") int deployPage,
      @Param(value = "posX") int posX,
      @Param(value = "posY") int posY
  );



}

