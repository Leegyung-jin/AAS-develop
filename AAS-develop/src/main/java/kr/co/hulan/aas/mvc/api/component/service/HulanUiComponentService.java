package kr.co.hulan.aas.mvc.api.component.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.EnableCode;
import kr.co.hulan.aas.common.code.Storage;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.component.controller.request.HulanUiComponentCreateRequest;
import kr.co.hulan.aas.mvc.api.component.controller.request.HulanUiComponentPagingListRequest;
import kr.co.hulan.aas.mvc.api.component.controller.request.HulanUiComponentUpdateRequest;
import kr.co.hulan.aas.mvc.api.component.vo.HulanUiComponentDetailVo;
import kr.co.hulan.aas.mvc.api.component.vo.HulanUiComponentLevelSelectVo;
import kr.co.hulan.aas.mvc.api.component.vo.HulanUiComponentLevelVo;
import kr.co.hulan.aas.mvc.api.component.vo.HulanUiComponentVo;
import kr.co.hulan.aas.mvc.api.file.dto.UploadedFile;
import kr.co.hulan.aas.mvc.api.file.service.FileService;
import kr.co.hulan.aas.mvc.dao.mapper.HulanUiComponentDao;
import kr.co.hulan.aas.mvc.dao.repository.HulanUiComponentLevelRepository;
import kr.co.hulan.aas.mvc.dao.repository.HulanUiComponentRepository;
import kr.co.hulan.aas.mvc.model.domain.HulanUiComponent;
import kr.co.hulan.aas.mvc.model.domain.HulanUiComponentLevel;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HulanUiComponentService {


  @Autowired
  ModelMapper modelMapper;

  @Autowired
  private FileService fileService;

  @Autowired
  private HulanUiComponentDao hulanUiComponentDao;

  @Autowired
  private HulanUiComponentRepository hulanUiComponentRepository;

  @Autowired
  private HulanUiComponentLevelRepository hulanUiComponentLevelRepository;

  public DefaultPageResult<HulanUiComponentVo> findListPage(HulanUiComponentPagingListRequest request) {
    return DefaultPageResult.<HulanUiComponentVo>builder()
        .currentPage(request.getPage())
        .pageSize(request.getPageSize())
        .list(findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(request.getConditionMap())))
        .totalCount(countListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(request.getConditionMap())))
        .build();
  }

  public List<HulanUiComponentVo> findListByCondition(Map<String,Object> conditionMap) {
    return hulanUiComponentDao.findListByCondition(conditionMap);
  }

  private Long countListByCondition(Map<String,Object> conditionMap) {
    return hulanUiComponentDao.countListByCondition(conditionMap);
  }


  public HulanUiComponentDetailVo findById(String hcmptId){
    HulanUiComponentDetailVo cmpt = hulanUiComponentDao.findInfo(hcmptId);
    if (cmpt != null ) {
      return cmpt;
    } else {
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
    }
  }

  public List<HulanUiComponentLevelSelectVo> findSelectLevelListByHcmptId(String hcmptId){
    return hulanUiComponentDao.findSelectableLevelListByHcmptId(hcmptId);
  }

  @Transactional("transactionManager")
  public void create(HulanUiComponentCreateRequest request) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }

    HulanUiComponent saveEntity = hulanUiComponentRepository.findById(request.getHcmptId()).orElse(null);
    if( saveEntity != null ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "이미 등록된 컴포넌트 아이디입니다.");
    }

    saveEntity = modelMapper.map(request, HulanUiComponent.class);
    saveEntity.setCreator(loginUser.getUsername());
    saveEntity.setUpdater(loginUser.getUsername());
    saveEntity.setCreateDate(new Date());
    saveEntity.setUpdateDate(new Date());
    hulanUiComponentRepository.save(saveEntity);

    if( request.getMbLevelList() != null && request.getMbLevelList().size() > 0 ){
      List<HulanUiComponentLevel> saveLevelEntityList = new ArrayList<>();
      for( Integer mbLevel : request.getMbLevelList() ){
        HulanUiComponentLevel level = new HulanUiComponentLevel();
        level.setMbLevel(mbLevel);
        level.setHcmptId(request.getHcmptId());
        level.setCreateDate(new Date());
        level.setCreator(loginUser.getMbId());
        saveLevelEntityList.add(level);
      }
      hulanUiComponentLevelRepository.saveAll(saveLevelEntityList);
    }

    UploadedFile representativeFile = request.getRepresentativeFile();

    boolean existsUploadedFile = representativeFile != null
        && StringUtils.isNotEmpty(representativeFile.getFileName())
        && StringUtils.isNotEmpty(representativeFile.getFileOriginalName());
    if( existsUploadedFile ){
      fileService.copyTempFile(representativeFile.getFileName(), fileService.getUiComponentFilePath(saveEntity.getHcmptId(), true));
      saveEntity.setFileLocation(Storage.LOCAL_STORAGE.getCode());
      saveEntity.setFileName(representativeFile.getFileName());
      saveEntity.setFileNameOrg(representativeFile.getFileOriginalName());
      saveEntity.setFilePath(fileService.getUiComponentFilePath(saveEntity.getHcmptId(), false));
      hulanUiComponentRepository.save(saveEntity);
    }
    else {
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "대표 파일은 필수 입니다.");
    }


  }


  @Transactional("transactionManager")
  public void update(HulanUiComponentUpdateRequest request, String cmptId) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }

    HulanUiComponent saveEntity = hulanUiComponentRepository.findById(cmptId).orElse(null);
    if( saveEntity == null ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "등록되지 않은 컴포넌트 아이디입니다.");
    }

    modelMapper.map(request, saveEntity );
    saveEntity.setUpdater(loginUser.getUsername());
    saveEntity.setUpdateDate(new Date());
    hulanUiComponentRepository.save(saveEntity);

    hulanUiComponentLevelRepository.deleteByHcmptId(cmptId);
    if( request.getMbLevelList() != null && request.getMbLevelList().size() > 0 ){
      List<HulanUiComponentLevel> saveLevelEntityList = new ArrayList<>();
      for( Integer mbLevel : request.getMbLevelList() ){
        HulanUiComponentLevel level = new HulanUiComponentLevel();
        level.setMbLevel(mbLevel);
        level.setHcmptId(cmptId);
        level.setCreateDate(new Date());
        level.setCreator(loginUser.getMbId());
        saveLevelEntityList.add(level);
      }
      hulanUiComponentLevelRepository.saveAll(saveLevelEntityList);
    }

    UploadedFile representativeFile = request.getRepresentativeFile();

    boolean existsUploadedFile = representativeFile != null
        && StringUtils.isNotEmpty(representativeFile.getFileName())
        && StringUtils.isNotEmpty(representativeFile.getFileOriginalName());
    if( existsUploadedFile ){
      if( !StringUtils.equals( representativeFile.getFileName(), saveEntity.getFileName() )){
        fileService.copyTempFile(representativeFile.getFileName(), fileService.getUiComponentFilePath(saveEntity.getHcmptId(), true));
        saveEntity.setFileLocation(Storage.LOCAL_STORAGE.getCode());
        saveEntity.setFileName(representativeFile.getFileName());
        saveEntity.setFileNameOrg(representativeFile.getFileOriginalName());
        saveEntity.setFilePath(fileService.getUiComponentFilePath(saveEntity.getHcmptId(), false));
        hulanUiComponentRepository.save(saveEntity);
      }
    }
    else {
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "대표 파일은 필수 입니다.");
    }
  }


  @Transactional("transactionManager")
  public void delete(String cmptId) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }

    HulanUiComponent saveEntity = hulanUiComponentRepository.findById(cmptId).orElse(null);
    if( saveEntity != null ){
      hulanUiComponentRepository.delete(saveEntity);
    }

  }

  @Transactional("transactionManager")
  public void deleteMultiple(List<String> cmptIdList) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }

    for (String cmptId : cmptIdList) {
      delete(cmptId);
    }

  }
}
