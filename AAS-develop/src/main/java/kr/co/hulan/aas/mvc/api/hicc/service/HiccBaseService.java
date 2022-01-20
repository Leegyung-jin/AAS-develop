package kr.co.hulan.aas.mvc.api.hicc.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.MemberLevel;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.config.properties.HiccBaseConfigProperties;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.hicc.controller.response.HiccBaseMainResponse;
import kr.co.hulan.aas.mvc.api.hicc.vo.HiccBaseInfoVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.HiccMemberUiComponentVo;
import kr.co.hulan.aas.mvc.api.orderoffice.vo.LinkBtnInfoDto;
import kr.co.hulan.aas.mvc.api.orderoffice.vo.OrderingOfficeVo;
import kr.co.hulan.aas.mvc.dao.mapper.ConCompanyDao;
import kr.co.hulan.aas.mvc.dao.mapper.HiccInfoDao;
import kr.co.hulan.aas.mvc.dao.mapper.HulanUiComponentDao;
import kr.co.hulan.aas.mvc.dao.mapper.OrderingOfficeDao;
import kr.co.hulan.aas.mvc.model.dto.ConCompanyDto;
import kr.co.hulan.aas.mvc.model.dto.HiccInfoDto;
import kr.co.hulan.aas.mvc.model.dto.HiccMainBtnDto;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HiccBaseService {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private HiccBaseConfigProperties hiccBaseConfigProperties;

  @Autowired
  private OrderingOfficeDao orderingOfficeDao;

  @Autowired
  private ConCompanyDao conCompanyDao;

  @Autowired
  private HulanUiComponentDao hulanUiComponentDao;

  @Autowired
  private HiccUiComponentService hiccUiComponentService;

  @Autowired
  private HiccWorkplaceService hiccWorkplaceService;

  @Autowired
  private HiccInfoDao hiccInfoDao;


  public HiccBaseMainResponse findBaseMain(int deployPage){
    SecurityUser user = AuthenticationHelper.getSecurityUser(true);

    Map<String,Object> condition = AuthenticationHelper.addAdditionalConditionByLevel();

    HiccInfoDto hiccInfo = hiccInfoDao.findHiccInfoByLoginUser(condition);
    HiccBaseInfoVo baseInfo = modelMapper.map(hiccBaseConfigProperties, HiccBaseInfoVo.class);
    if( hiccInfo != null ){
      modelMapper.map(hiccInfo, baseInfo);
    }
    List<LinkBtnInfoDto> btnList = hiccInfoDao.findHiccMainButtonList(hiccInfo.getHiccNo())
        .stream().map( dto -> modelMapper.map( dto, LinkBtnInfoDto.class)).collect(Collectors.toList());
    baseInfo.setBtnList( btnList != null ? btnList : Collections.emptyList());

    List<HiccMemberUiComponentVo> uiComponentList = hulanUiComponentDao
        .findHiccMemberComponentListByMember(user.getMbId(), deployPage);
    if( deployPage == 1 && ( uiComponentList == null || uiComponentList.size() == 0 ) ){
      hiccUiComponentService.createDefaultUiComponent(deployPage);
      uiComponentList = hulanUiComponentDao
          .findHiccMemberComponentListByMember(user.getMbId(), deployPage);
    }

    return HiccBaseMainResponse.builder()
        .baseInfo(baseInfo)
        .uiComponentList(uiComponentList)
        .build();
  }
}
