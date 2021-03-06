package kr.co.hulan.aas.mvc.api.workplace.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.MemberLevel;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.workplace.controller.request.ConstructionSiteCreateRequest;
import kr.co.hulan.aas.mvc.api.workplace.controller.request.ConstructionSitePagingListRequest;
import kr.co.hulan.aas.mvc.api.workplace.controller.request.ConstructionSiteUpdateRequest;
import kr.co.hulan.aas.mvc.api.workplace.vo.ConstructionSiteManagerVo;
import kr.co.hulan.aas.mvc.api.workplace.vo.ConstrunctionSiteVo;
import kr.co.hulan.aas.mvc.dao.mapper.ConstructionSiteDao;
import kr.co.hulan.aas.mvc.dao.repository.ConCompanyRepository;
import kr.co.hulan.aas.mvc.dao.repository.ConstructionSiteManagerRepository;
import kr.co.hulan.aas.mvc.dao.repository.ConstructionSiteRepository;
import kr.co.hulan.aas.mvc.dao.repository.G5MemberRepository;
import kr.co.hulan.aas.mvc.dao.repository.WorkPlaceRepository;
import kr.co.hulan.aas.mvc.model.domain.ConstructionSite;
import kr.co.hulan.aas.mvc.model.domain.ConstructionSiteKey;
import kr.co.hulan.aas.mvc.model.domain.ConstructionSiteManager;
import kr.co.hulan.aas.mvc.model.domain.ConstructionSiteManagerKey;
import kr.co.hulan.aas.mvc.model.domain.G5Member;
import kr.co.hulan.aas.mvc.model.domain.WorkPlace;
import kr.co.hulan.aas.mvc.model.dto.GasSafeRangeDto;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConstructionSiteService {

  private Logger logger = LoggerFactory.getLogger(ConstructionSiteService.class);

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private ConstructionSiteDao constructionSiteDao;

  @Autowired
  private ConstructionSiteRepository constructionSiteRepository;

  @Autowired
  private ConstructionSiteManagerRepository constructionSiteManagerRepository;

  @Autowired
  private G5MemberRepository g5MemberRepository;

  @Autowired
  private WorkPlaceRepository workPlaceRepository;

  @Autowired
  private ConCompanyRepository conCompanyRepository;

  public DefaultPageResult<ConstrunctionSiteVo> findListPage(
      ConstructionSitePagingListRequest request) {
    return DefaultPageResult.<ConstrunctionSiteVo>builder()
        .currentPage(request.getPage())
        .pageSize(request.getPageSize())
        .totalCount(countListByCondition(request.getConditionMap()))
        .list(findListByCondition(request.getConditionMap()))
        .build();
  }

  public List<ConstrunctionSiteVo> findListByCondition(Map<String,Object> conditionMap) {
    Map<String,Object> condition = AuthenticationHelper.addAdditionalConditionByLevel();
    condition.putAll(conditionMap);
    return constructionSiteDao.findListByCondition(condition);
  }

  private Long countListByCondition(Map<String,Object> conditionMap) {
    Map<String,Object> condition = AuthenticationHelper.addAdditionalConditionByLevel();
    condition.putAll(conditionMap);
    return constructionSiteDao.countListByCondition(condition);
  }

  public ConstrunctionSiteVo findInfo(ConstructionSiteKey key){
    ConstrunctionSiteVo currentVo = constructionSiteDao.findInfo(key);
    if (currentVo != null ) {
      return currentVo;
    } else {
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
    }
  }

  public List<ConstructionSiteManagerVo> findConstructionSiteMangerListByKey(ConstructionSiteKey key){
    return constructionSiteDao.findConstructionSiteMangerListByKey(key);
  }


  @Transactional("transactionManager")
  public void create(ConstructionSiteCreateRequest request) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }
    ConstructionSiteKey key = modelMapper.map(request, ConstructionSiteKey.class);

    conCompanyRepository.findById(key.getCcId()).orElseThrow( () ->
        new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "???????????? ?????? ??????????????????.")
    );
    workPlaceRepository.findById(key.getWpId()).orElseThrow( () ->
        new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "???????????? ?????? ???????????????.")
    );

    if( constructionSiteRepository.findById(key).isPresent() ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "?????? ????????? ??????????????????.");
    }

    ConstructionSite saveEntity = modelMapper.map(request, ConstructionSite.class);
    saveEntity.setCreator(loginUser.getMbId());
    saveEntity.setCreateDate(new Date());

    constructionSiteRepository.save(saveEntity);

    List<ConstructionSiteManager> saveManagerList = new ArrayList<>();
    List<String> managerMbIdList = request.getManagerMbIdList();
    for(String managerMbId : managerMbIdList ){

      G5Member member = g5MemberRepository.findByMbId(managerMbId).orElseThrow( () ->
        new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "["+managerMbId+"]??? ???????????? ?????? ??????????????????.")
      );

      if(MemberLevel.get(member.getMbLevel()) != MemberLevel.FIELD_MANAGER ){
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "["+managerMbId+"]??? ?????? ???????????? ????????????.");
      }
      if( !StringUtils.equals(saveEntity.getCcId(), member.getCcId())){
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "["+managerMbId+"]??? ?????? ???????????? ?????????????????? ????????????.");
      }

      // ????????? ??????????????? ??? ?????? ??????. ?????? ????????? ????????? ?????? ?????? ????????? ??????
      if( constructionSiteManagerRepository.existsByMbId( managerMbId )){
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "["+managerMbId+"]??? ?????? ????????? ????????????????????????.");
      }

      ConstructionSiteManager manager = new ConstructionSiteManager();
      manager.setMbId(managerMbId);
      manager.setWpId(saveEntity.getWpId());
      manager.setCcId(saveEntity.getCcId());
      manager.setCreator(loginUser.getMbId());
      manager.setCreateDate(new Date());
      saveManagerList.add(manager);
    }
    constructionSiteManagerRepository.saveAll(saveManagerList);
  }


  @Transactional("transactionManager")
  public void update(ConstructionSiteKey key, ConstructionSiteUpdateRequest request) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }

    conCompanyRepository.findById(key.getCcId()).orElseThrow( () ->
        new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "???????????? ?????? ??????????????????.")
    );
    WorkPlace workPlace = workPlaceRepository.findById(key.getWpId()).orElseThrow( () ->
        new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "???????????? ?????? ???????????????.")
    );

    ConstructionSite saveEntity = constructionSiteRepository.findById(key).orElseThrow( () ->
      new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "????????? ???????????? ?????? ??????????????????.")
    );

    Map<String, ConstructionSiteManager> managerMap = constructionSiteManagerRepository.findByWpIdAndCcId(saveEntity.getWpId(), saveEntity.getCcId())
        .stream()
        .collect(
            Collectors.toMap(ConstructionSiteManager::getMbId, Function.identity())
    );

    if( StringUtils.equals(workPlace.getCcId(), key.getCcId() ) ){
      Optional existsManMbId = request.getManagerMbIdList().stream()
          .filter( manMbid -> StringUtils.equals(workPlace.getManMbId(), manMbid))
          .findFirst();
      if(!existsManMbId.isPresent()){
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "["+workPlace.getManMbId()+"]??? ????????? ???????????? ????????? ??????????????? ?????????.");
      }
    }

    List<ConstructionSiteManager> saveManagerList = new ArrayList<>();
    List<String> managerMbIdList = request.getManagerMbIdList();
    for(String managerMbId : managerMbIdList ){
      ConstructionSiteManager manager = managerMap.remove(managerMbId);
      if( manager == null ){
        G5Member member = g5MemberRepository.findByMbId(managerMbId).orElseThrow( () ->
            new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "["+managerMbId+"]??? ???????????? ?????? ??????????????????.")
        );

        if(MemberLevel.get(member.getMbLevel()) != MemberLevel.FIELD_MANAGER ){
          throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "["+managerMbId+"]??? ?????? ???????????? ????????????.");
        }
        if( !StringUtils.equals(saveEntity.getCcId(), member.getCcId())){
          throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "["+managerMbId+"]??? ?????? ???????????? ?????????????????? ????????????.");
        }

        // ????????? ??????????????? ??? ?????? ??????. ?????? ????????? ????????? ?????? ?????? ????????? ??????
        if( constructionSiteManagerRepository.existsByMbIdAndWpIdNot( managerMbId, saveEntity.getWpId() )){
          throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "["+managerMbId+"]??? ?????? ????????? ????????????????????????.");
        }

        manager = new ConstructionSiteManager();
        manager.setMbId(managerMbId);
        manager.setWpId(saveEntity.getWpId());
        manager.setCcId(saveEntity.getCcId());
        manager.setCreator(loginUser.getMbId());
        manager.setCreateDate(new Date());
        saveManagerList.add(manager);
      }
    }

    if( saveManagerList.size() != 0 ){
      constructionSiteManagerRepository.saveAll(saveManagerList);
    }
    Collection<ConstructionSiteManager> deleteList = Arrays.asList(managerMap.values().toArray(new ConstructionSiteManager[0]));
    if( deleteList.size() != 0 ){
      constructionSiteManagerRepository.deleteAll(deleteList);
    }
  }


  @Transactional("transactionManager")
  public int delete(ConstructionSiteKey key) {

    WorkPlace workplace = workPlaceRepository.findById(key.getWpId()).orElseThrow( () ->
      new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "???????????? ?????? ???????????????.")
    );

    if( StringUtils.equals( workplace.getCcId(), key.getCcId() ) ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "?????? ??? ????????? ???????????? ?????? ????????? ??? ????????????.");
    }

    ConstructionSite constructionSite = constructionSiteRepository.findById(key).orElse(null);
    if( constructionSite != null ){
        constructionSiteRepository.delete(constructionSite);
        return 1;
    }
    return 0;
  }

  @Transactional("transactionManager")
  public int deleteMultiple(List<String> keyList) {
    int deleteCnt = 0;

    for (String key : keyList) {
      String[] tokens = StringUtils.split(key, "_");
      if( tokens != null && tokens.length == 2 ){
        deleteCnt += delete(ConstructionSiteKey.builder()
            .wpId(tokens[0])
            .ccId(tokens[1])
            .build());
      }
      else {
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "?????? ???????????? ????????????.");
      }
    }
    return deleteCnt;
  }

  @Transactional("transactionManager")
  public void assignManager(String wpId, String ccId, String mbId){
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser(true);

    G5Member member = g5MemberRepository.findByMbId(mbId).orElseThrow( () ->
        new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "???????????? ?????? ??????????????????.")
    );
    MemberLevel level = MemberLevel.get(member.getMbLevel());
    if( level != MemberLevel.FIELD_MANAGER ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "["+mbId+"]??? ?????? ???????????? ????????????.");
    }
    if( !StringUtils.equals(ccId, member.getCcId())){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "?????? ???????????? ?????? ???????????? ?????????????????? ????????????.");
    }

    ConstructionSiteKey key = ConstructionSiteKey.builder()
        .wpId(wpId)
        .ccId(ccId)
        .build();

    workPlaceRepository.findById(key.getWpId()).orElseThrow( () ->
        new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "???????????? ?????? ???????????????.")
    );
    conCompanyRepository.findById(key.getCcId()).orElseThrow( () ->
        new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "???????????? ?????? ??????????????????.")
    );
    constructionSiteRepository.findById(key).orElseThrow( () ->
        new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "?????? ????????? ???????????? ?????? ??????????????????.")
    );

    ConstructionSiteManagerKey managerKey = ConstructionSiteManagerKey.builder()
        .wpId(wpId)
        .mbId(mbId)
        .build();
    ConstructionSiteManager manager = constructionSiteManagerRepository.findById(managerKey).orElse(null);
    if( manager != null ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "?????? ??????????????? ?????? ????????? ??????????????????.");
    };
    manager = modelMapper.map(managerKey, ConstructionSiteManager.class );
    manager.setCcId(ccId);
    manager.setCreateDate(new Date());
    manager.setCreator(loginUser.getMbId());
    constructionSiteManagerRepository.save(manager);

  }


  @Transactional("transactionManager")
  public void dismissManager(String wpId, String ccId, String mbId){
    ConstructionSiteManager manager = constructionSiteManagerRepository.findById(
        ConstructionSiteManagerKey.builder()
            .wpId(wpId)
            .mbId(mbId)
            .build()).orElse(null);
    if( manager != null ){

      WorkPlace workPlace = workPlaceRepository.findById(wpId).orElseThrow( () ->
          new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "???????????? ?????? ???????????????.")
      );
      if( StringUtils.equals(workPlace.getCcId(), ccId ) ){
        if( StringUtils.equals(workPlace.getManMbId(), mbId) ){
          throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "["+workPlace.getManMbId()+"]??? ????????? ???????????? ???????????? ????????? ??? ????????????.");
        }
      }

      if( !StringUtils.equals(ccId, manager.getCcId())){
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "?????? ???????????? ?????? ???????????? ?????????????????? ????????????.");
      }
      constructionSiteManagerRepository.delete(manager);
    }
  }
}
