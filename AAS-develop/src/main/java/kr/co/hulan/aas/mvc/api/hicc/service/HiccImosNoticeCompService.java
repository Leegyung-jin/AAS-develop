package kr.co.hulan.aas.mvc.api.hicc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.EnableCode;
import kr.co.hulan.aas.common.code.Storage;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.infra.broker.KafkaEventProducer;
import kr.co.hulan.aas.infra.broker.vo.DefaultKafkaEventType;
import kr.co.hulan.aas.infra.broker.vo.ImosNoticeEventDto;
import kr.co.hulan.aas.mvc.api.file.dto.UploadedFile;
import kr.co.hulan.aas.mvc.api.file.service.FileService;
import kr.co.hulan.aas.mvc.api.hicc.controller.request.HiccImosNoticeCreateRequest;
import kr.co.hulan.aas.mvc.api.hicc.controller.request.HiccImosNoticePagingRequest;
import kr.co.hulan.aas.mvc.api.hicc.controller.request.HiccImosNoticeUpdateRequest;
import kr.co.hulan.aas.mvc.api.hicc.vo.ImosNoticeVo;
import kr.co.hulan.aas.mvc.dao.mapper.ImosNoticeDao;
import kr.co.hulan.aas.mvc.dao.repository.ImosNoticeFileRepository;
import kr.co.hulan.aas.mvc.dao.repository.ImosNoticeRepository;
import kr.co.hulan.aas.mvc.dao.repository.ImosNoticeWorkplaceRepository;
import kr.co.hulan.aas.mvc.dao.repository.WorkPlaceRepository;
import kr.co.hulan.aas.mvc.model.domain.imosNotice.ImosNotice;
import kr.co.hulan.aas.mvc.model.domain.imosNotice.ImosNoticeFile;
import kr.co.hulan.aas.mvc.model.domain.imosNotice.ImosNoticeWorkplace;
import kr.co.hulan.aas.mvc.model.dto.ImosNoticeDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HiccImosNoticeCompService {

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  private ImosNoticeDao imosNoticeDao;

  @Autowired
  private FileService fileService;

  @Autowired
  private WorkPlaceRepository workPlaceRepository;

  @Autowired
  private ImosNoticeRepository imosNoticeRepository;

  @Autowired
  private ImosNoticeWorkplaceRepository imosNoticeWorkplaceRepository;

  @Autowired
  private ImosNoticeFileRepository imosNoticeFileRepository;

  @Autowired
  private KafkaEventProducer kafkaEventProducer;

  public DefaultPageResult<ImosNoticeDto> findListPage(HiccImosNoticePagingRequest request) {
    return DefaultPageResult.<ImosNoticeDto>builder()
        .pageSize(request.getPageSize())
        .currentPage(request.getPage())
        .totalCount(findListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(request.getConditionMap())))
        .list(findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(request.getConditionMap())))
        .build();
  }

  public List<ImosNoticeDto> findListByCondition(Map<String,Object> conditionMap) {
    return imosNoticeDao.findListByCondition(conditionMap);
  }

  private Long findListCountByCondition(Map<String,Object> conditionMap) {
    return imosNoticeDao.countListByCondition(conditionMap);
  }

  public ImosNoticeVo findDetailInfo(long imntNo){
    ImosNoticeVo dto = imosNoticeDao.findDetailInfo(imntNo);
    if (dto != null ) {
      return dto;
    } else {
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
    }
  }

  @Transactional("transactionManager")
  public long create(HiccImosNoticeCreateRequest request) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser(true);

    ImosNotice saveEntity = modelMapper.map(request, ImosNotice.class);
    saveEntity.setCreator(loginUser.getUsername());
    saveEntity.setUpdater(loginUser.getUsername());
    saveEntity.setCreateDate(new Date());
    saveEntity.setUpdateDate(new Date());

    imosNoticeRepository.save(saveEntity);

    if( EnableCode.get(saveEntity.getNotiAllFlag()) == EnableCode.DISABLED ){
      List<ImosNoticeWorkplace> wpList = new ArrayList<ImosNoticeWorkplace>();
      List<String> wpIdList = request.getWorkplaceList();
      if( wpIdList == null || wpIdList.size() == 0 ){
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "현장을 선택하셔야 합니다.");
      }

      for( String wpId : wpIdList ){
        if( !workPlaceRepository.existsById(wpId) ){
          throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "선택된 현장 중 존재하지 않는 현장이 있습니다.");
        }
        ImosNoticeWorkplace noticeWorkplace = new ImosNoticeWorkplace();
        noticeWorkplace.setWpId(wpId);
        noticeWorkplace.setImntNo(saveEntity.getImntNo());
        noticeWorkplace.setCreateDate(new Date());
        noticeWorkplace.setCreator(loginUser.getUsername());
        wpList.add(noticeWorkplace);
      }
      imosNoticeWorkplaceRepository.saveAll(wpList);
    }

    List<UploadedFile> uploadFileList = request.getUploadFileList();
    if( uploadFileList != null && uploadFileList.size() > 0 ){
      List<ImosNoticeFile> noticeFileList = new ArrayList<ImosNoticeFile>();
      for(UploadedFile file : uploadFileList ){
        fileService.copyTempFile(file.getFileName(), fileService.getImosNoticeFilePath(saveEntity.getImntNo(), true));
        ImosNoticeFile noticeFile = new ImosNoticeFile();
        noticeFile.setImntNo(saveEntity.getImntNo());
        noticeFile.setFileName(file.getFileName());
        noticeFile.setFileNameOrg(file.getFileOriginalName());
        noticeFile.setFilePath(fileService.getImosNoticeFilePath(saveEntity.getImntNo(), false));
        noticeFile.setFileLocation(Storage.LOCAL_STORAGE.getCode());
        noticeFile.setCreateDate(new Date());
        noticeFile.setCreator(loginUser.getUsername());
        noticeFileList.add(noticeFile);
      }
      imosNoticeFileRepository.saveAll(noticeFileList);
    }
    ImosNoticeEventDto eventDto = modelMapper.map(saveEntity, ImosNoticeEventDto.class);
    eventDto.setWorkplaceList(request.getWorkplaceList());
    kafkaEventProducer.sendEventMessage(DefaultKafkaEventType.CREATE, eventDto);
    return saveEntity.getImntNo();
  }

  @Transactional("transactionManager")
  public void update(long imntNo, HiccImosNoticeUpdateRequest request) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser(true);

    ImosNotice saveEntity = imosNoticeRepository.findById(imntNo).orElse(null);
    if( saveEntity == null ){
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
    }
    modelMapper.map(request, saveEntity);
    saveEntity.setUpdater(loginUser.getUsername());
    saveEntity.setUpdateDate(new Date());

    imosNoticeRepository.save(saveEntity);

    List<UploadedFile> uploadFileList = request.getUploadFileList();
    if( uploadFileList != null && uploadFileList.size() > 0 ){
      List<ImosNoticeFile> noticeFileList = new ArrayList<ImosNoticeFile>();
      for(UploadedFile file : uploadFileList ){
        fileService.copyTempFile(file.getFileName(), fileService.getImosNoticeFilePath(saveEntity.getImntNo(), true));
        ImosNoticeFile noticeFile = new ImosNoticeFile();
        noticeFile.setImntNo(saveEntity.getImntNo());
        noticeFile.setFileName(file.getFileName());
        noticeFile.setFileNameOrg(file.getFileOriginalName());
        noticeFile.setFilePath(fileService.getImosNoticeFilePath(saveEntity.getImntNo(), false));
        noticeFile.setFileLocation(Storage.LOCAL_STORAGE.getCode());
        noticeFile.setCreateDate(new Date());
        noticeFile.setCreator(loginUser.getUsername());
        noticeFileList.add(noticeFile);
      }
      imosNoticeFileRepository.saveAll(noticeFileList);
    }

    ImosNoticeEventDto eventDto = modelMapper.map(saveEntity, ImosNoticeEventDto.class);
    if( EnableCode.get(saveEntity.getNotiAllFlag()) == EnableCode.DISABLED ){
      List<String> list = imosNoticeWorkplaceRepository.findByImntNo(saveEntity.getImntNo())
          .stream().map( notiWp -> notiWp.getWpId() ).collect(Collectors.toList());
      eventDto.setWorkplaceList(list);
    }
    kafkaEventProducer.sendEventMessage(DefaultKafkaEventType.UPDATE, eventDto);
  }

  @Transactional("transactionManager")
  public void delete(long imntNo) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser(true);

    ImosNotice saveEntity = imosNoticeRepository.findById(imntNo).orElse(null);
    if( saveEntity != null ){
      imosNoticeRepository.delete(saveEntity);
    }

  }

  @Transactional("transactionManager")
  public void deleteMultiple(List<Long> imntNoList) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser(true);
    for (Long imntNo : imntNoList) {
      delete(imntNo);
    }
  }

  @Transactional("transactionManager")
  public void deleteFile(long imntNo, long fileNo) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser(true);
    ImosNoticeFile file = imosNoticeFileRepository.findById(fileNo).orElse(null);
    if( file != null ){
      if( file.getImntNo() != imntNo ){
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "지정된 파일은 해당 공지사항의 첨부파일이 아닙니다.");
      }

      imosNoticeFileRepository.delete(file);
      //fileService.deleteFile();
    }
  }



}
