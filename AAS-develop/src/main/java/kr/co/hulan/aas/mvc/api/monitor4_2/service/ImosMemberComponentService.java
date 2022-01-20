package kr.co.hulan.aas.mvc.api.monitor4_2.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.DeviceType;
import kr.co.hulan.aas.common.code.EnableCode;
import kr.co.hulan.aas.common.code.HulanComponentSiteType;
import kr.co.hulan.aas.common.code.HulanComponentUiType;
import kr.co.hulan.aas.common.code.UiComponentCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.common.utils.Matrix;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.component.vo.HulanUiComponentVo;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.response.ImosUiComponentResponse;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.ImosMemberUiComponentVo;
import kr.co.hulan.aas.mvc.dao.mapper.HulanUiComponentDao;
import kr.co.hulan.aas.mvc.dao.repository.EntrGateWorkplaceRepository;
import kr.co.hulan.aas.mvc.dao.repository.HulanUiComponentLevelRepository;
import kr.co.hulan.aas.mvc.dao.repository.HulanUiComponentRepository;
import kr.co.hulan.aas.mvc.dao.repository.ImosMemberUiComponentRepository;
import kr.co.hulan.aas.mvc.dao.repository.WorkDeviceInfoRepository;
import kr.co.hulan.aas.mvc.dao.repository.WorkPlaceCctvRepository;
import kr.co.hulan.aas.mvc.model.domain.EntrGateWorkplace;
import kr.co.hulan.aas.mvc.model.domain.HulanUiComponent;
import kr.co.hulan.aas.mvc.model.domain.HulanUiComponentLevelKey;
import kr.co.hulan.aas.mvc.model.domain.ImosMemberUiComponent;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceUiComponentDto;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ImosMemberComponentService {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private HulanUiComponentDao hulanUiComponentDao;

  @Autowired
  private HulanUiComponentRepository hulanUiComponentRepository;

  @Autowired
  private HulanUiComponentLevelRepository hulanUiComponentLevelRepository;

  @Autowired
  private ImosMemberUiComponentRepository imosMemberUiComponentRepository;

  @Autowired
  private EntrGateWorkplaceRepository entrGateWorkplaceRepository;

  @Autowired
  private WorkPlaceCctvRepository workPlaceCctvRepository;

  @Autowired
  private WorkDeviceInfoRepository workDeviceInfoRepository;

  public List<ImosMemberUiComponentVo> findListByWpId(String wpId, int deployPage) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser(true);
    return hulanUiComponentDao.findImosMemberComponentListByMember(wpId, loginUser.getMbId(), deployPage);
  }


  public ImosUiComponentResponse findImosUiComponent(String wpId, Integer deployPage, Integer posX, Integer posY){
    SecurityUser user = AuthenticationHelper.getSecurityUser(true);

    ImosMemberUiComponentVo currentVo = hulanUiComponentDao
        .findImosMemberComponentInfo(wpId, user.getMbId(), deployPage, posX, posY );

    List<ImosMemberUiComponentVo> currentList = hulanUiComponentDao
        .findImosMemberComponentListByMember(wpId, user.getMbId(), deployPage);
    Matrix matrix = new Matrix();

    if( currentList != null && currentList.size() > 0 ){
      for( ImosMemberUiComponentVo deployedVo : currentList ){
        if( currentVo == null || !StringUtils.equals(deployedVo.getHcmptId(), currentVo.getHcmptId()) ){
          matrix.setData(deployedVo.getPosX(), deployedVo.getPosY(), deployedVo.getWidth(), deployedVo.getHeight());
        }
      }
    }

    Map<String,Object> condition = new HashMap<String,Object>();
    condition.put("supportedLevel", user.getMbLevel());
    condition.put("status", EnableCode.ENABLED.getCode());
    condition.put("siteType", HulanComponentSiteType.IMOS.getCode());
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
    return ImosUiComponentResponse.builder()
        .uiComponent(currentVo)
        .supportedUiComponents(supportList)
        .build();
  }


  @Transactional("transactionManager")
  public void assignUiComponent(String wpId, Integer deployPage, Integer posX, Integer posY, String hcmptId, String customData){
    SecurityUser user = AuthenticationHelper.getSecurityUser(true);
    ImosMemberUiComponent currentEntity = imosMemberUiComponentRepository.findByWpIdAndMbIdAndDeployPageAndPosXAndPosY(
        wpId, user.getMbId(), deployPage, posX, posY
    );

    if( deployPage == 1 && posX < 4 && posY < 3 && !( posX == 1 && posY == 1 ) ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "메인 화면 영역은 교체가 불가능합니다.");
    }

    HulanUiComponent comp = hulanUiComponentRepository.findById(hcmptId).orElse(null);
    if( comp == null || !comp.isUsable() || HulanComponentSiteType.get(comp.getSiteType()) != HulanComponentSiteType.IMOS ){
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), "지원하지 않는 컴포넌트입니다.");
    }
    else if( currentEntity != null && StringUtils.equals(currentEntity.getHcmptId(), hcmptId)){
      if( StringUtils.equals(customData, currentEntity.getCustomData())){
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "이미 해당 컴포넌트가 지정되어 있습니다.");
      }
      currentEntity.setCustomData(customData);
      imosMemberUiComponentRepository.save(currentEntity);
      return;
    }

    if( !hulanUiComponentLevelRepository.existsById(HulanUiComponentLevelKey.builder()
        .hcmptId(comp.getHcmptId())
        .mbLevel(user.getMbLevel())
        .build()) ){
      String mlevName = StringUtils.defaultIfBlank(user.getMbLevelName(), "UNKNOWN");
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "["+mlevName+"] 계정은 지원하지 않는 컴포넌트입니다.");
    }

    UiComponentCode compCode = UiComponentCode.get(comp.getHcmptId());
    if( compCode == UiComponentCode.CCTV ){
      long cctvCount = workPlaceCctvRepository.countByWpId(wpId);
      if( cctvCount < 1 ){
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "해당 현장에 할당된 CCTV 가 존재하지 않습니다.");
      }
    }
    else if( compCode == UiComponentCode.ENTER_GATE ){
      EntrGateWorkplace wgate = entrGateWorkplaceRepository.findById(wpId).orElse(null);
      if( wgate == null || EnableCode.get(wgate.getStatus()) != EnableCode.ENABLED){
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "해당 현장에서는 지원되지 않는 컴포넌트입니다");
      }
    }
    else if( compCode == UiComponentCode.TILT_SENSOR ){
      if( workDeviceInfoRepository.countByWpIdAndDeviceType(wpId, DeviceType.TILT_SENSOR.getCode()) < 1 ){
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "해당 현장에 할당된 기울기 센서가 존재하지 않습니다.");
      }
    }

    List<ImosMemberUiComponent> currentList = imosMemberUiComponentRepository.findByWpIdAndMbIdAndDeployPage(wpId, user.getMbId(), deployPage);
    if( currentList != null && currentList.size() > 0 ){
      for( ImosMemberUiComponent deployed : currentList ){
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
      currentEntity = new ImosMemberUiComponent();
      currentEntity.setWpId(wpId);
      currentEntity.setMbId(user.getMbId());
      currentEntity.setDeployPage(deployPage);
      currentEntity.setPosX(posX);
      currentEntity.setPosY(posY);
    }
    currentEntity.setHcmptId(hcmptId);
    currentEntity.setCreateDate(new Date());
    imosMemberUiComponentRepository.save(currentEntity);
  }

  @Transactional("transactionManager")
  public void unassignUiComponent(String wpId, Integer deployPage, Integer posX, Integer posY){
    SecurityUser user = AuthenticationHelper.getSecurityUser(true);
    ImosMemberUiComponent currentEntity = imosMemberUiComponentRepository.findByWpIdAndMbIdAndDeployPageAndPosXAndPosY(
        wpId, user.getMbId(), deployPage, posX, posY
    );
    if( currentEntity != null ){
      imosMemberUiComponentRepository.delete(currentEntity);
    }
  }

  @Transactional("transactionManager")
  public void createDefaultUiComponent(String wpId, Integer deployPage){
    HulanUiComponent defaultMain = hulanUiComponentRepository.findTopBySiteTypeAndUiTypeAndStatusOrderByCreateDateDesc(
        HulanComponentSiteType.IMOS.getCode(), HulanComponentUiType.MAIN_UI.getCode(), EnableCode.ENABLED
            .getCode());
    if( defaultMain != null ){
      assignUiComponent(wpId, deployPage, 1, 1, defaultMain.getHcmptId(), null);
    }
  }
}
