package kr.co.hulan.aas.mvc.api.orderoffice.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.Storage;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.config.properties.HiccBaseConfigProperties;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.file.dto.UploadedFile;
import kr.co.hulan.aas.mvc.api.file.service.FileService;
import kr.co.hulan.aas.mvc.api.orderoffice.controller.request.OrderingOfficeCreateRequest;
import kr.co.hulan.aas.mvc.api.orderoffice.controller.request.OrderingOfficePagingListRequest;
import kr.co.hulan.aas.mvc.api.orderoffice.controller.request.OrderingOfficeUpdateRequest;
import kr.co.hulan.aas.mvc.api.orderoffice.controller.request.OfficeWorkplaceGroupCreateRequest;
import kr.co.hulan.aas.mvc.api.orderoffice.controller.request.OfficeWorkplaceGroupUpdateRequest;
import kr.co.hulan.aas.mvc.api.orderoffice.vo.LinkBtnInfoDto;
import kr.co.hulan.aas.mvc.api.orderoffice.vo.OfficeWorkplaceGroupVo;
import kr.co.hulan.aas.mvc.api.orderoffice.vo.OrderingOfficeListVo;
import kr.co.hulan.aas.mvc.api.orderoffice.vo.OrderingOfficeVo;
import kr.co.hulan.aas.mvc.dao.mapper.HiccInfoDao;
import kr.co.hulan.aas.mvc.dao.mapper.OrderingOfficeDao;
import kr.co.hulan.aas.mvc.dao.repository.HiccInfoRepository;
import kr.co.hulan.aas.mvc.dao.repository.HiccMainBtnRepository;
import kr.co.hulan.aas.mvc.dao.repository.OfficeWorkplaceGrpRepository;
import kr.co.hulan.aas.mvc.dao.repository.OrderingOfficeRepository;
import kr.co.hulan.aas.mvc.model.domain.HiccInfo;
import kr.co.hulan.aas.mvc.model.domain.HiccMainBtn;
import kr.co.hulan.aas.mvc.model.domain.OfficeWorkplaceGrp;
import kr.co.hulan.aas.mvc.model.domain.OrderingOffice;
import kr.co.hulan.aas.mvc.model.dto.HiccMainBtnDto;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderingOfficeMgrService {

  private Logger logger = LoggerFactory.getLogger(OrderingOfficeMgrService.class);

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private OrderingOfficeDao orderingOfficeDao;

  @Autowired
  private OrderingOfficeRepository orderingOfficeRepository;

  @Autowired
  private OfficeWorkplaceGrpRepository officeWorkplaceGrpRepository;

  @Autowired
  private HiccInfoRepository hiccInfoRepository;

  @Autowired
  private FileService fileService;

  @Autowired
  private HiccBaseConfigProperties hiccBaseConfigProperties;

  @Autowired
  private HiccMainBtnRepository hiccMainBtnRepository;

  @Autowired
  private HiccInfoDao hiccInfoDao;

  public DefaultPageResult<OrderingOfficeListVo> findListPage(OrderingOfficePagingListRequest request) {
    return DefaultPageResult.<OrderingOfficeListVo>builder()
        .currentPage(request.getPage())
        .pageSize(request.getPageSize())
        .totalCount(countListByCondition(request.getConditionMap()))
        .list(findListByCondition(request.getConditionMap()))
        .build();
  }

  public List<OrderingOfficeListVo> findListByCondition(Map<String,Object> conditionMap) {
    return orderingOfficeDao.findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
  }

  private Long countListByCondition(Map<String,Object> conditionMap) {
    return orderingOfficeDao.countListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
  }

  public OrderingOfficeVo findInfo(long officeNo){
    OrderingOfficeVo currentVo = orderingOfficeDao.findInfo(officeNo);
    if (currentVo != null ) {
      List<LinkBtnInfoDto> btnList = hiccInfoDao.findHiccMainButtonList(currentVo.getHiccNo())
          .stream().map( dto -> modelMapper.map( dto, LinkBtnInfoDto.class)).collect(Collectors.toList());
      currentVo.setMainBtnList(btnList != null ? btnList : Collections.emptyList());
      return currentVo;
    } else {
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
    }
  }


  @Transactional("transactionManager")
  public Long create(OrderingOfficeCreateRequest request) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }

    OrderingOffice saveEntity = modelMapper.map(request, OrderingOffice.class);
    if( orderingOfficeRepository.existsByBiznum(saveEntity.getBiznum()) ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "이미 존재하는 사업자번호입니다.");
    }
    saveEntity.setCreator(loginUser.getMbId());
    saveEntity.setCreateDate(new Date());
    saveEntity.setUpdater(loginUser.getMbId());
    saveEntity.setUpdateDate(new Date());

    HiccInfo hiccInfo = modelMapper.map(request, HiccInfo.class);
    if( StringUtils.isBlank( hiccInfo.getHiccName())){
      hiccInfo.setHiccName(saveEntity.getOfficeName()+" "+hiccBaseConfigProperties.getTitle());
    }

    UploadedFile iconFile = request.getIconFile();

    boolean existsIconUploadedFile = iconFile != null
        && StringUtils.isNotEmpty(iconFile.getFileName())
        && StringUtils.isNotEmpty(iconFile.getFileOriginalName());
    if( existsIconUploadedFile ){
      fileService.copyTempFile(iconFile.getFileName(), fileService.getOfficeFilePath(saveEntity.getOfficeNo(), true));
      hiccInfo.setIconFileLocation(Storage.LOCAL_STORAGE.getCode());
      hiccInfo.setIconFileName(iconFile.getFileName());
      hiccInfo.setIconFileNameOrg(iconFile.getFileOriginalName());
      hiccInfo.setIconFilePath(fileService.getOfficeFilePath(saveEntity.getOfficeNo(), false));
    }

    UploadedFile bgImgFile = request.getBgImgFile();

    boolean existsbgImgUploadedFile = bgImgFile != null
        && StringUtils.isNotEmpty(bgImgFile.getFileName())
        && StringUtils.isNotEmpty(bgImgFile.getFileOriginalName());
    if( existsbgImgUploadedFile ){
      fileService.copyTempFile(bgImgFile.getFileName(), fileService.getOfficeFilePath(saveEntity.getOfficeNo(), true));
      hiccInfo.setBgImgFileLocation(Storage.LOCAL_STORAGE.getCode());
      hiccInfo.setBgImgFileName(bgImgFile.getFileName());
      hiccInfo.setBgImgFileNameOrg(bgImgFile.getFileOriginalName());
      hiccInfo.setBgImgFilePath(fileService.getOfficeFilePath(saveEntity.getOfficeNo(), false));
    }

    hiccInfo.setCreateDate(new Date());
    hiccInfo.setCreator(loginUser.getMbId());
    hiccInfo.setUpdateDate(new Date());
    hiccInfo.setUpdater(loginUser.getMbId());

    hiccInfoRepository.save(hiccInfo);

    updateMainBtnInfo(hiccInfo.getHiccNo(), request.getMainBtnList(), loginUser.getMbId());

    saveEntity.setHiccNo(hiccInfo.getHiccNo());
    orderingOfficeRepository.save(saveEntity);
    return saveEntity.getOfficeNo();
  }

  @Transactional("transactionManager")
  public void update(OrderingOfficeUpdateRequest request, long officeNo) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }

    OrderingOffice saveEntity =  orderingOfficeRepository.findById(officeNo).orElse(null);
    if( saveEntity == null ){
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), "발주사 정보가 존재하지 않습니다.");
    }

    modelMapper.map(request, saveEntity);
    if( orderingOfficeRepository.existsByBiznumAndOfficeNoNot(saveEntity.getBiznum(), officeNo) ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "이미 존재하는 사업자번호입니다.");
    }
    saveEntity.setUpdater(loginUser.getMbId());
    saveEntity.setUpdateDate(new Date());

    HiccInfo hiccInfo = null;
    if( saveEntity.getHiccNo() != null ){
      hiccInfo = hiccInfoRepository.findById(saveEntity.getHiccNo()).orElse(null);
    }

    if( hiccInfo == null ){
      hiccInfo = modelMapper.map(request, HiccInfo.class);
      hiccInfo.setCreateDate(new Date());
      hiccInfo.setCreator(loginUser.getMbId());
    }
    else {
      modelMapper.map(request, hiccInfo);
    }

    if( StringUtils.isBlank( hiccInfo.getHiccName())){
      hiccInfo.setHiccName(saveEntity.getOfficeName()+" "+hiccBaseConfigProperties.getTitle());
    }

    UploadedFile iconFile = request.getIconFile();

    boolean existsIconUploadedFile = iconFile != null
        && StringUtils.isNotEmpty(iconFile.getFileName())
        && StringUtils.isNotEmpty(iconFile.getFileOriginalName());
    if( existsIconUploadedFile ){
      if( !StringUtils.equals( iconFile.getFileName(), hiccInfo.getIconFileName() )){
        fileService.copyTempFile(iconFile.getFileName(), fileService.getOfficeFilePath(saveEntity.getOfficeNo(), true));
        hiccInfo.setIconFileLocation(Storage.LOCAL_STORAGE.getCode());
        hiccInfo.setIconFileName(iconFile.getFileName());
        hiccInfo.setIconFileNameOrg(iconFile.getFileOriginalName());
        hiccInfo.setIconFilePath(fileService.getOfficeFilePath(saveEntity.getOfficeNo(), false));
      }
    }
    else {
      hiccInfo.setIconFileLocation(null);
      hiccInfo.setIconFileName(null);
      hiccInfo.setIconFileNameOrg(null);
      hiccInfo.setIconFilePath(null);
    }

    UploadedFile bgImgFile = request.getBgImgFile();

    boolean existsbgImgUploadedFile = bgImgFile != null
        && StringUtils.isNotEmpty(bgImgFile.getFileName())
        && StringUtils.isNotEmpty(bgImgFile.getFileOriginalName());
    if( existsbgImgUploadedFile ){
      if( !StringUtils.equals( bgImgFile.getFileName(), hiccInfo.getBgImgFileName() )){
        fileService.copyTempFile(bgImgFile.getFileName(), fileService.getOfficeFilePath(saveEntity.getOfficeNo(), true));
        hiccInfo.setBgImgFileLocation(Storage.LOCAL_STORAGE.getCode());
        hiccInfo.setBgImgFileName(bgImgFile.getFileName());
        hiccInfo.setBgImgFileNameOrg(bgImgFile.getFileOriginalName());
        hiccInfo.setBgImgFilePath(fileService.getOfficeFilePath(saveEntity.getOfficeNo(), false));
      }
    }
    else {
      hiccInfo.setBgImgFileLocation(null);
      hiccInfo.setBgImgFileName(null);
      hiccInfo.setBgImgFileNameOrg(null);
      hiccInfo.setBgImgFilePath(null);
    }

    hiccInfo.setUpdateDate(new Date());
    hiccInfo.setUpdater(loginUser.getMbId());

    hiccInfoRepository.save(hiccInfo);

    updateMainBtnInfo(hiccInfo.getHiccNo(), request.getMainBtnList(), loginUser.getMbId());

    saveEntity.setHiccNo(hiccInfo.getHiccNo());

    orderingOfficeRepository.save(saveEntity);
  }

  @Transactional("transactionManager")
  public void updateMainBtnInfo(long hiccNo, List<LinkBtnInfoDto> btnList, String loginUserId){
    hiccMainBtnRepository.deleteAllByHiccNo(hiccNo);
    if( btnList != null && btnList.size() > 0 ){
      List<HiccMainBtn> saveList = new ArrayList<HiccMainBtn>();
      for(LinkBtnInfoDto btn : btnList  ){
        HiccMainBtn mainBtn = modelMapper.map(btn, HiccMainBtn.class);
        mainBtn.setHiccNo(hiccNo);
        mainBtn.setCreateDate(new Date());
        mainBtn.setCreator(loginUserId);
        mainBtn.setUpdateDate(new Date());
        mainBtn.setUpdater(loginUserId);
        saveList.add(mainBtn);
      }
      hiccMainBtnRepository.saveAll(saveList);
    }
  }

  @Transactional("transactionManager")
  public int delete(long officeNo) {
    if( officeNo == 1){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "삭제할 수 없는 디폴트 발주사입니다.");
    }

    OrderingOffice saveEntity =  orderingOfficeRepository.findById(officeNo).orElse(null);
    if( saveEntity != null ){
      orderingOfficeRepository.delete(saveEntity);
      return 1;
    }
    return 0;
  }

  @Transactional("transactionManager")
  public int deleteMultiple(List<Long> officeNoList) {
    int deleteCnt = 0;
    for (Long officeNo : officeNoList) {
      deleteCnt += delete(officeNo);
    }
    return deleteCnt;
  }

  @Transactional("transactionManager")
  public Long createWorkplaceGroup(OfficeWorkplaceGroupCreateRequest request, long officeNo) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }

    if( !orderingOfficeRepository.existsById(officeNo)){
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), "발주사 정보가 존재하지 않습니다.");
    }

    OfficeWorkplaceGrp saveEntity = modelMapper.map(request, OfficeWorkplaceGrp.class);
    saveEntity.setOfficeNo(officeNo);
    saveEntity.setCreator(loginUser.getMbId());
    saveEntity.setCreateDate(new Date());
    saveEntity.setUpdater(loginUser.getMbId());
    saveEntity.setUpdateDate(new Date());
    officeWorkplaceGrpRepository.save(saveEntity);
    return saveEntity.getOfficeNo();
  }

  @Transactional("transactionManager")
  public void updateWorkplaceGroup(OfficeWorkplaceGroupUpdateRequest request, long officeNo, long wpGrpNo) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }

    if( !orderingOfficeRepository.existsById(officeNo)){
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), "발주사 정보가 존재하지 않습니다.");
    }

    OfficeWorkplaceGrp saveEntity =  officeWorkplaceGrpRepository.findById(wpGrpNo).orElse(null);
    if( saveEntity == null ){
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), "발주사 현장그룹 정보가 존재하지 않습니다.");
    }
    else if( saveEntity.getOfficeNo().longValue() != officeNo ){
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), "해당 발주사의 현장그룹이 아닙니다");
    }
    modelMapper.map(request, saveEntity);
    saveEntity.setUpdater(loginUser.getMbId());
    saveEntity.setUpdateDate(new Date());
    officeWorkplaceGrpRepository.save(saveEntity);
  }


  @Transactional("transactionManager")
  public int deleteWorkplaceGroup(long officeNo, long wpGrpNo) {
    if( wpGrpNo == 1){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "삭제할 수 없는 디폴트 발주사 현장관리 그룹입니다.");
    }
    OfficeWorkplaceGrp saveEntity =  officeWorkplaceGrpRepository.findById(wpGrpNo).orElse(null);
    if( saveEntity != null ){
      if( saveEntity.getOfficeNo().longValue() != officeNo ){
        throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), "해당 발주사의 현장그룹이 아닙니다");
      }
      officeWorkplaceGrpRepository.delete(saveEntity);
      return 1;
    }
    return 0;
  }
}
