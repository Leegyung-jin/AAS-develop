package kr.co.hulan.aas.mvc.api.component.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.Storage;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.component.controller.request.CreateUiComponentRequest;
import kr.co.hulan.aas.mvc.api.component.controller.request.ListUiComponentRequest;
import kr.co.hulan.aas.mvc.api.component.controller.request.UpdateUiComponentRequest;
import kr.co.hulan.aas.mvc.api.component.controller.response.ListUiComponentResponse;
import kr.co.hulan.aas.mvc.api.file.dto.UploadedFile;
import kr.co.hulan.aas.mvc.api.file.service.FileService;
import kr.co.hulan.aas.mvc.api.tracker.controller.request.CreateTrackerRequest;
import kr.co.hulan.aas.mvc.api.tracker.controller.request.ListTrackerRequest;
import kr.co.hulan.aas.mvc.api.tracker.controller.request.UpdateTrackerRequest;
import kr.co.hulan.aas.mvc.api.tracker.controller.response.ListTrackerResponse;
import kr.co.hulan.aas.mvc.dao.mapper.UiComponentDao;
import kr.co.hulan.aas.mvc.dao.repository.UiComponentRepository;
import kr.co.hulan.aas.mvc.dao.repository.WorkPlaceUiComponentRepository;
import kr.co.hulan.aas.mvc.model.domain.Tracker;
import kr.co.hulan.aas.mvc.model.domain.TrackerAssign;
import kr.co.hulan.aas.mvc.model.domain.UiComponent;
import kr.co.hulan.aas.mvc.model.domain.WorkPlace;
import kr.co.hulan.aas.mvc.model.dto.TrackerDto;
import kr.co.hulan.aas.mvc.model.dto.UiComponentDto;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UiComponentService {

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  private FileService fileService;

  @Autowired
  private UiComponentDao uiComponentDao;

  @Autowired
  private UiComponentRepository uiComponentRepository;

  @Autowired
  private WorkPlaceUiComponentRepository workPlaceUiComponentRepository;


  public ListUiComponentResponse findListPage(ListUiComponentRequest request) {
    return new ListUiComponentResponse(
        findListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(request.getConditionMap())),
        findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(request.getConditionMap()))
    );
  }

  public List<UiComponentDto> findListByCondition(Map<String,Object> conditionMap) {
    return uiComponentDao.findListByCondition(conditionMap);
  }

  private Long findListCountByCondition(Map<String,Object> conditionMap) {
    return uiComponentDao.findListCountByCondition(conditionMap);
  }


  public UiComponentDto findById(String cmptId){
    UiComponentDto cmpt = uiComponentDao.findById(cmptId);
    if (cmpt != null ) {
      return cmpt;
    } else {
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
    }
  }

  @Transactional("transactionManager")
  public void create(CreateUiComponentRequest request) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }

    UiComponent saveEntity = uiComponentRepository.findById(request.getCmptId()).orElse(null);
    if( saveEntity != null ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "이미 등록된 컴포넌트 아이디입니다.");
    }

    saveEntity = modelMapper.map(request, UiComponent.class);
    saveEntity.setCreator(loginUser.getUsername());
    saveEntity.setUpdater(loginUser.getUsername());
    saveEntity.setCreateDate(new Date());
    saveEntity.setUpdateDate(new Date());
    uiComponentRepository.save(saveEntity);

    UploadedFile representativeFile = request.getRepresentativeFile();

    boolean existsUploadedFile = representativeFile != null
        && StringUtils.isNotEmpty(representativeFile.getFileName())
        && StringUtils.isNotEmpty(representativeFile.getFileOriginalName());
    if( existsUploadedFile ){
      fileService.copyTempFile(representativeFile.getFileName(), fileService.getUiComponentFilePath(saveEntity.getCmptId(), true));
      saveEntity.setFileLocation(Storage.LOCAL_STORAGE.getCode());
      saveEntity.setFileName(representativeFile.getFileName());
      saveEntity.setFileNameOrg(representativeFile.getFileOriginalName());
      saveEntity.setFilePath(fileService.getUiComponentFilePath(saveEntity.getCmptId(), false));
      uiComponentRepository.save(saveEntity);
    }
    else {
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "대표 파일은 필수 입니다.");
    }


  }


  @Transactional("transactionManager")
  public void update(UpdateUiComponentRequest request, String cmptId) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }

    UiComponent saveEntity = uiComponentRepository.findById(cmptId).orElse(null);
    if( saveEntity == null ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "등록되지 않은 컴포넌트 아이디입니다.");
    }

    modelMapper.map(request, saveEntity );
    saveEntity.setUpdater(loginUser.getUsername());
    saveEntity.setUpdateDate(new Date());
    uiComponentRepository.save(saveEntity);

    UploadedFile representativeFile = request.getRepresentativeFile();

    boolean existsUploadedFile = representativeFile != null
        && StringUtils.isNotEmpty(representativeFile.getFileName())
        && StringUtils.isNotEmpty(representativeFile.getFileOriginalName());
    if( existsUploadedFile ){
      if( !StringUtils.equals( representativeFile.getFileName(), saveEntity.getFileName() )){
        fileService.copyTempFile(representativeFile.getFileName(), fileService.getUiComponentFilePath(saveEntity.getCmptId(), true));
        saveEntity.setFileLocation(Storage.LOCAL_STORAGE.getCode());
        saveEntity.setFileName(representativeFile.getFileName());
        saveEntity.setFileNameOrg(representativeFile.getFileOriginalName());
        saveEntity.setFilePath(fileService.getUiComponentFilePath(saveEntity.getCmptId(), false));
        uiComponentRepository.save(saveEntity);
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

    UiComponent saveEntity = uiComponentRepository.findById(cmptId).orElse(null);
    if( saveEntity != null ){
      workPlaceUiComponentRepository.deleteAllByCmptId(cmptId);
      uiComponentRepository.delete(saveEntity);
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
