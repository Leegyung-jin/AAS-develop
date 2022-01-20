package kr.co.hulan.aas.mvc.api.hicc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.EnableCode;
import kr.co.hulan.aas.common.code.HulanComponentUiType;
import kr.co.hulan.aas.common.code.HulanComponentSiteType;
import kr.co.hulan.aas.common.code.MemberLevel;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.common.utils.Matrix;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.hicc.controller.response.HiccUiComponentResponse;
import kr.co.hulan.aas.mvc.api.hicc.vo.HiccMemberUiComponentVo;
import kr.co.hulan.aas.mvc.api.component.vo.HulanUiComponentVo;
import kr.co.hulan.aas.mvc.dao.mapper.HulanUiComponentDao;
import kr.co.hulan.aas.mvc.dao.repository.HiccMemberUiComponentRepository;
import kr.co.hulan.aas.mvc.dao.repository.HulanUiComponentLevelRepository;
import kr.co.hulan.aas.mvc.dao.repository.HulanUiComponentRepository;
import kr.co.hulan.aas.mvc.model.domain.HiccMemberUiComponent;
import kr.co.hulan.aas.mvc.model.domain.HulanUiComponent;
import kr.co.hulan.aas.mvc.model.domain.HulanUiComponentLevelKey;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HiccUiComponentService {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private HulanUiComponentDao hulanUiComponentDao;

  @Autowired
  private HulanUiComponentRepository hulanUiComponentRepository;

  @Autowired
  private HulanUiComponentLevelRepository hulanUiComponentLevelRepository;

  @Autowired
  private HiccMemberUiComponentRepository hiccMemberUiComponentRepository;

  public HiccUiComponentResponse findHiccUiComponent(Integer deployPage, Integer posX, Integer posY){
    SecurityUser user = AuthenticationHelper.getSecurityUser(true);

    /*
    if( deployPage == 1 && posX < 4 && posY < 3 && !( posX == 1 && posY == 1 ) ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "메인 화면은 교체가 불가능합니다.");
    }
     */

    HiccMemberUiComponentVo currentVo = hulanUiComponentDao
        .findHiccMemberComponentInfo(user.getMbId(), deployPage, posX, posY );

    List<HiccMemberUiComponentVo> currentList = hulanUiComponentDao
        .findHiccMemberComponentListByMember(user.getMbId(), deployPage);
    Matrix matrix = new Matrix();

    if( currentList != null && currentList.size() > 0 ){
      for( HiccMemberUiComponentVo deployedVo : currentList ){
        if( currentVo == null || !StringUtils.equals(deployedVo.getHcmptId(), currentVo.getHcmptId()) ){
          matrix.setData(deployedVo.getPosX(), deployedVo.getPosY(), deployedVo.getWidth(), deployedVo.getHeight());
        }
      }
    }

    Map<String,Object> condition = new HashMap<String,Object>();
    condition.put("supportedLevel", user.getMbLevel());
    condition.put("status", EnableCode.ENABLED.getCode());
    condition.put("siteType", HulanComponentSiteType.HICC.getCode());
    condition.put("uiType", deployPage == 1 && posX == 1 && posY == 1 ?
        HulanComponentUiType.MAIN_UI.getCode() :
        HulanComponentUiType.COMPONENT_UI.getCode()
    );
    List<HulanUiComponentVo>  enabledList = hulanUiComponentDao.findListByCondition(condition);

    List<HulanUiComponentVo> supportList = new ArrayList<>();
    if( enabledList != null && enabledList.size() > 0 ){
      for( HulanUiComponentVo enabledVo : enabledList ){
        if( matrix.addEnable(posX, posY, enabledVo.getWidth(), enabledVo.getHeight())
            && ( currentVo == null || !StringUtils.equals(enabledVo.getHcmptId(), currentVo.getHcmptId() ) )
        ){
          supportList.add(enabledVo);
        }
      }
    }
    return HiccUiComponentResponse.builder()
        .uiComponent(currentVo)
        .supportedUiComponents(supportList)
        .build();
  }


  @Transactional("transactionManager")
  public void assignUiComponent(Integer deployPage, Integer posX, Integer posY, String hcmptId, String customData){
    SecurityUser user = AuthenticationHelper.getSecurityUser(true);
    HiccMemberUiComponent currentEntity = hiccMemberUiComponentRepository.findByMbIdAndDeployPageAndPosXAndPosY(
        user.getMbId(), deployPage, posX, posY
    );

    if( deployPage == 1 && posX < 4 && !( posX == 1 && posY == 1 ) ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "메인 화면 영역은 교체가 불가능합니다.");
    }

    HulanUiComponent comp = hulanUiComponentRepository.findById(hcmptId).orElse(null);
    if( comp == null || !comp.isUsable() || HulanComponentSiteType.get(comp.getSiteType()) != HulanComponentSiteType.HICC ){
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), "지원하지 않는 컴포넌트입니다.");
    }
    else if( currentEntity != null && StringUtils.equals(currentEntity.getHcmptId(), hcmptId)){
      if( StringUtils.equals(customData, currentEntity.getCustomData())){
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "이미 해당 컴포넌트가 지정되어 있습니다.");
      }
      currentEntity.setCustomData(customData);
      hiccMemberUiComponentRepository.save(currentEntity);
      return;
    }

    if( !hulanUiComponentLevelRepository.existsById(HulanUiComponentLevelKey.builder()
        .hcmptId(comp.getHcmptId())
        .mbLevel(user.getMbLevel())
        .build()) ){
      String mlevName = StringUtils.defaultIfBlank(user.getMbLevelName(), "UNKNOWN");
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "["+mlevName+"] 계정은 지원하지 않는 컴포넌트입니다.");
    }

    List<HiccMemberUiComponent> currentList = hiccMemberUiComponentRepository.findByMbIdAndDeployPage(user.getMbId(), deployPage);
    if( currentList != null && currentList.size() > 0 ){
      for( HiccMemberUiComponent deployed : currentList ){
        if( currentEntity == null || !StringUtils.equals(currentEntity.getHcmptId(), deployed.getHcmptId()) ){
          HulanUiComponent uiComp = deployed.getUiComponent();
          Matrix matrix = new Matrix();
          matrix.setData(deployed.getPosX(), deployed.getPosY(), uiComp.getWidth(), uiComp.getHeight());
          if( !matrix.addEnable(posX, posY, comp.getWidth(), comp.getHeight()) ){
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "해당 영역에 ["+uiComp.getHcmptName()+"] 컴포넌트가 이미 지정되어 있습니다.");
          }
        }
      }
    }

    if( currentEntity == null ){
      currentEntity = new HiccMemberUiComponent();
      currentEntity.setMbId(user.getMbId());
      currentEntity.setDeployPage(deployPage);
      currentEntity.setPosX(posX);
      currentEntity.setPosY(posY);
    }
    currentEntity.setHcmptId(hcmptId);
    currentEntity.setCustomData(customData);
    currentEntity.setCreateDate(new Date());
    currentEntity.setCreator(user.getMbId());
    hiccMemberUiComponentRepository.save(currentEntity);
  }

  @Transactional("transactionManager")
  public void unassignUiComponent(Integer deployPage, Integer posX, Integer posY){
    SecurityUser user = AuthenticationHelper.getSecurityUser(true);

    if( deployPage == 1 && posX == 1 && posY == 1 ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "메인 화면은 교체가 불가능합니다.");
    }

    HiccMemberUiComponent currentEntity = hiccMemberUiComponentRepository.findByMbIdAndDeployPageAndPosXAndPosY(
        user.getMbId(), deployPage, posX, posY
    );
    if( currentEntity != null ){
      hiccMemberUiComponentRepository.delete(currentEntity);
    }
  }

  @Transactional("transactionManager")
  public void createDefaultUiComponent(Integer deployPage){
    HulanUiComponent defaultMain = hulanUiComponentRepository.findTopBySiteTypeAndUiTypeAndStatusOrderByCreateDateDesc(
        HulanComponentSiteType.HICC.getCode(), HulanComponentUiType.MAIN_UI.getCode(), EnableCode.ENABLED
        .getCode());
    if( defaultMain != null ){
      assignUiComponent(deployPage, 1, 1, defaultMain.getHcmptId(), null);
    }
  }
}
