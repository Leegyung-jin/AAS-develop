package kr.co.hulan.aas.mvc.api.orderoffice.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.MemberLevel;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.orderoffice.controller.request.OfficeWorkplaceGroupCreateRequest;
import kr.co.hulan.aas.mvc.api.orderoffice.controller.request.OfficeWorkplaceGroupPagingListRequest;
import kr.co.hulan.aas.mvc.api.orderoffice.controller.request.OfficeWorkplaceGroupUpdateRequest;
import kr.co.hulan.aas.mvc.api.orderoffice.vo.OfficeWorkplaceGroupVo;
import kr.co.hulan.aas.mvc.dao.mapper.OfficeWorkplaceGroupDao;
import kr.co.hulan.aas.mvc.dao.repository.G5MemberRepository;
import kr.co.hulan.aas.mvc.dao.repository.OfficeWorkplaceGrpRepository;
import kr.co.hulan.aas.mvc.dao.repository.OfficeWorkplaceManagerRepository;
import kr.co.hulan.aas.mvc.dao.repository.OrderingOfficeRepository;
import kr.co.hulan.aas.mvc.model.domain.G5Member;
import kr.co.hulan.aas.mvc.model.domain.OfficeWorkplaceGrp;
import kr.co.hulan.aas.mvc.model.domain.OfficeWorkplaceManager;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// @Service
public class OfficeWorkplaceGroupMgrService {

  /*
  private Logger logger = LoggerFactory.getLogger(OfficeWorkplaceGroupMgrService.class);

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private G5MemberRepository g5MemberRepository;

  @Autowired
  private OfficeWorkplaceGroupDao officeWorkplaceGroupDao;

  @Autowired
  private OrderingOfficeRepository orderingOfficeRepository;

  @Autowired
  private OfficeWorkplaceGrpRepository officeWorkplaceGrpRepository;

  @Autowired
  private OfficeWorkplaceManagerRepository officeWorkplaceManagerRepository;

  public DefaultPageResult<OfficeWorkplaceGroupVo> findListPage(
      OfficeWorkplaceGroupPagingListRequest request) {
    return DefaultPageResult.<OfficeWorkplaceGroupVo>builder()
        .currentPage(request.getPage())
        .pageSize(request.getPageSize())
        .totalCount(countListByCondition(request.getConditionMap()))
        .list(findListByCondition(request.getConditionMap()))
        .build();
  }

  public List<OfficeWorkplaceGroupVo> findListByCondition(Map<String,Object> conditionMap) {
    return officeWorkplaceGroupDao.findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
  }

  private Long countListByCondition(Map<String,Object> conditionMap) {
    return officeWorkplaceGroupDao.countListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
  }

  public OfficeWorkplaceGroupVo findInfo(long officeNo){
    OfficeWorkplaceGroupVo currentVo = officeWorkplaceGroupDao.findInfo(officeNo);
    if (currentVo != null ) {
      return currentVo;
    } else {
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
    }
  }

  @Transactional("transactionManager")
  public Long create(OfficeWorkplaceGroupCreateRequest request) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }

    if( !orderingOfficeRepository.existsById(request.getOfficeNo())){
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), "발주사 정보가 존재하지 않습니다.");
    }

    OfficeWorkplaceGrp saveEntity = modelMapper.map(request, OfficeWorkplaceGrp.class);
    saveEntity.setCreator(loginUser.getMbId());
    saveEntity.setCreateDate(new Date());
    saveEntity.setUpdater(loginUser.getMbId());
    saveEntity.setUpdateDate(new Date());
    officeWorkplaceGrpRepository.saveAndFlush(saveEntity);

    List<OfficeWorkplaceManager> saveManagerList = new ArrayList<>();
    List<String> managerMbIdList = request.getManagerMbIdList();
    for(String managerMbId : managerMbIdList ){

      G5Member member = g5MemberRepository.findByMbId(managerMbId).orElseThrow( () ->
          new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "["+managerMbId+"]는 존재하지 않는 관리자입니다.")
      );
      if(MemberLevel.get(member.getMbLevel()) != MemberLevel.WP_GROUP_MANAGER ){
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "["+managerMbId+"]는 현장그룹 관리자가 아닙니다.");
      }

      OfficeWorkplaceManager manager = new OfficeWorkplaceManager();
      manager.setMbId(managerMbId);
      manager.setWpGrpNo(saveEntity.getWpGrpNo());
      manager.setCreator(loginUser.getMbId());
      manager.setCreateDate(new Date());
      saveManagerList.add(manager);
    }
    officeWorkplaceManagerRepository.saveAll(saveManagerList);

    return saveEntity.getWpGrpNo();
  }

  @Transactional("transactionManager")
  public void update(OfficeWorkplaceGroupUpdateRequest request, long wpGrpNo) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }

    OfficeWorkplaceGrp saveEntity =  officeWorkplaceGrpRepository.findById(wpGrpNo).orElseThrow( () ->
        new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), "발주사 현장그룹 정보가 존재하지 않습니다.")
    );
    modelMapper.map(request, saveEntity);
    saveEntity.setUpdater(loginUser.getMbId());
    saveEntity.setUpdateDate(new Date());
    officeWorkplaceGrpRepository.save(saveEntity);

    Map<String, OfficeWorkplaceManager> managerMap = officeWorkplaceManagerRepository.findByWpGrpNo(saveEntity.getWpGrpNo())
        .stream()
        .collect(
            Collectors.toMap(OfficeWorkplaceManager::getMbId, Function.identity())
        );

    List<OfficeWorkplaceManager> saveManagerList = new ArrayList<>();
    List<String> managerMbIdList = request.getManagerMbIdList();
    for(String managerMbId : managerMbIdList ){
      OfficeWorkplaceManager manager = managerMap.remove(managerMbId);
      if( manager == null ){
        G5Member member = g5MemberRepository.findByMbId(managerMbId).orElseThrow( () ->
            new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "["+managerMbId+"]는 존재하지 않는 관리자입니다.")
        );

        if(MemberLevel.get(member.getMbLevel()) != MemberLevel.WP_GROUP_MANAGER ){
          throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "["+managerMbId+"]는 현장그룹 관리자가 아닙니다.");
        }
        manager = new OfficeWorkplaceManager();
        manager.setMbId(managerMbId);
        manager.setWpGrpNo(saveEntity.getWpGrpNo());
        manager.setCreator(loginUser.getMbId());
        manager.setCreateDate(new Date());
        saveManagerList.add(manager);
      }
    }

    if( saveManagerList.size() != 0 ){
      officeWorkplaceManagerRepository.saveAll(saveManagerList);
    }
    Collection<OfficeWorkplaceManager> deleteList = Arrays.asList(managerMap.values().toArray(new OfficeWorkplaceManager[0]));
    if( deleteList.size() != 0 ){
      officeWorkplaceManagerRepository.deleteAll(deleteList);
    }
  }


  @Transactional("transactionManager")
  public int delete(long wpGrpNo) {
    if( wpGrpNo == 1){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "삭제할 수 없는 디폴트 발주사 현장그룹입니다.");
    }

    OfficeWorkplaceGrp saveEntity =  officeWorkplaceGrpRepository.findById(wpGrpNo).orElse(null);
    if( saveEntity != null ){
      officeWorkplaceGrpRepository.delete(saveEntity);
      return 1;
    }
    return 0;
  }

  @Transactional("transactionManager")
  public int deleteMultiple(List<Long> wpGrpNoList) {
    int deleteCnt = 0;
    for (Long wpGrpNo : wpGrpNoList) {
      deleteCnt += delete(wpGrpNo);
    }
    return deleteCnt;
  }
   */

}
