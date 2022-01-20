package kr.co.hulan.aas.mvc.api.board.service;

import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.common.utils.JsonUtil;
import kr.co.hulan.aas.common.utils.YamlPropertiesAccessor;
import kr.co.hulan.aas.common.utils.YamlPropertiesProcessor;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.infra.StorageClient;
import kr.co.hulan.aas.infra.storage.DeleteFileCommand;
import kr.co.hulan.aas.mvc.api.board.controller.request.CreateNoticeBoardRequest;
import kr.co.hulan.aas.mvc.api.board.controller.request.ListNoticeBoardRequest;
import kr.co.hulan.aas.mvc.api.board.controller.request.UpdateNoticeBoardRequest;
import kr.co.hulan.aas.mvc.api.board.controller.response.ListNoticeBoardResponse;
import kr.co.hulan.aas.mvc.api.board.dto.AttachedFileDto;
import kr.co.hulan.aas.mvc.api.board.dto.NoticeDto;
import kr.co.hulan.aas.mvc.api.board.dto.UploadAttachedFileDto;
import kr.co.hulan.aas.mvc.api.file.dto.ImageInfo;
import kr.co.hulan.aas.mvc.api.file.dto.UploadedFile;
import kr.co.hulan.aas.mvc.api.file.service.FileService;
import kr.co.hulan.aas.mvc.dao.mapper.G5BoardFileDao;
import kr.co.hulan.aas.mvc.dao.mapper.G5BoardNewDao;
import kr.co.hulan.aas.mvc.dao.mapper.G5MemberDao;
import kr.co.hulan.aas.mvc.dao.mapper.G5WriteNoticeDao;
import kr.co.hulan.aas.mvc.dao.repository.*;
import kr.co.hulan.aas.mvc.model.domain.G5Member;
import kr.co.hulan.aas.mvc.model.domain.G5WriteNotice;
import kr.co.hulan.aas.mvc.model.domain.WorkPlace;
import kr.co.hulan.aas.mvc.model.dto.G5BoardFileDto;
import kr.co.hulan.aas.mvc.model.dto.G5BoardNewDto;
import kr.co.hulan.aas.mvc.model.dto.G5MemberDto;
import kr.co.hulan.aas.mvc.model.dto.G5WriteNoticeDto;
import kr.co.hulan.aas.mvc.service.push.PushData;
import kr.co.hulan.aas.mvc.service.push.PushService;
import kr.co.hulan.aas.mvc.service.push.PushTargetInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NoticeBoardService {

    @Autowired
    private G5MemberRepository g5MemberRepository;

    @Autowired
    private G5MemberDao g5MemberDao;

    @Autowired
    private WorkPlaceRepository workPlaceRepository;

    @Autowired
    private G5WriteNoticeRepository g5WriteNoticeRepository;

    @Autowired
    private G5WriteNoticeDao g5WriteNoticeDao;

    @Autowired
    private G5BoardNewDao g5BoardNewDao;

    @Autowired
    private AttachedFileService attachedFileService;

    @Autowired
    private FileService fileService;

    @Autowired
    private PushService pushService;

    @Autowired
    private G5BoardFileDao g5BoardFileDao;

    @Autowired
    private StorageClient storageClient;

    public ListNoticeBoardResponse findListPage(ListNoticeBoardRequest request) {
        return new ListNoticeBoardResponse(
                findListCountByCondition(request.getConditionMap()),
                findListByCondition(request.getConditionMap())
        );
    }


    public List<NoticeDto> findListByCondition(Map<String,Object> conditionMap) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return g5WriteNoticeDao.findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap))
                .stream().map(g5WriteNoticeDto -> modelMapper.map(g5WriteNoticeDto, NoticeDto.class)).collect(Collectors.toList());
    }

    private Long findListCountByCondition(Map<String,Object> conditionMap) {
        return g5WriteNoticeDao.findListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }

    public NoticeDto findLastNoticeByWpId(String wpId) {
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("wpId", wpId);
        condition.put("startRow", 0);
        condition.put("pageSize", 1);
        List<NoticeDto> noticeList = findListByCondition(condition);
        if( noticeList != null && noticeList.size() > 0 ){
            return noticeList.get(0);
        }
        return null;
    }


    public NoticeDto findById(int wcIdx){

        G5WriteNoticeDto noticeDto = g5WriteNoticeDao.findById(wcIdx);
        if ( noticeDto != null ) {

            g5WriteNoticeDao.increaseHitCount(noticeDto);

            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            NoticeDto dto = modelMapper.map(noticeDto, NoticeDto.class);

            dto.setFileList(attachedFileService.findListByBoardKey("notice", noticeDto.getWrId()));
            return dto;
        } else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }
    }


    @Transactional("mybatisTransactionManager")
    public void create(CreateNoticeBoardRequest request) {
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        Optional<G5Member> memberOp = g5MemberRepository.findByMbId(loginUser.getUsername());
        if( !memberOp.isPresent() ){
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }
        G5Member loginMember = memberOp.get();

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        G5WriteNoticeDto saveDto = modelMapper.map(request, G5WriteNoticeDto.class);

        saveDto.setMbId(loginMember.getMbId());
        saveDto.setWrPassword(loginMember.getMbPassword());
        saveDto.setWrName(loginMember.getName());
        saveDto.setWrIp(AuthenticationHelper.getRemoteIp());

        Optional<WorkPlace> workplaceOp = workPlaceRepository.findById(saveDto.getWr2());
        if( !workplaceOp.isPresent() ){
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }
        WorkPlace workPlace = workplaceOp.get();
        saveDto.setWr3(workPlace.getWpName());
        saveDto.setWrFile(0);

        g5WriteNoticeDao.create(saveDto);

        G5BoardNewDto newDto = new G5BoardNewDto();
        newDto.setBoTable("notice");
        newDto.setWrId(saveDto.getWrId());
        newDto.setWrParent(saveDto.getWrId());
        newDto.setMbId(loginUser.getUsername());
        g5BoardNewDao.create(newDto);

        saveDto.setWrParent( saveDto.getWrId() );
        g5WriteNoticeDao.updateParent(saveDto);

        if( StringUtils.equals(saveDto.getWrNotice(), "1")){
            g5WriteNoticeDao.addNoticeFlag(saveDto.getWrId());
        }

        DeleteFileCommand deleteCommand = new DeleteFileCommand();
        treatUploadedFile(saveDto.getWrId(), request.getWrFiles(), deleteCommand);

        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("wpId", saveDto.getWr2());
        if( !StringUtils.equals(saveDto.getAllWorkplaceWorkerNotice(), "1") ){
            condition.put("attendance", saveDto.getAllWorkplaceWorkerNotice());
        }
        List<G5MemberDto> noticeTargetList = g5MemberDao.findWorkplaceWorkerListForNoticeByCondition(condition);

        //String body = StringUtils.substring(StringEscapeUtils.escapeHtml4(StringEscapeUtils.escapeHtml3(saveDto.getWrContent())), 0, 30);
        String body = StringUtils.substring(saveDto.getWrContent(), 0, 30);
        PushData push = new PushData();
        push.setSubject(saveDto.getWrSubject());
        push.setBody(body);
        push.setCode(StringUtils.equals(saveDto.getWr1(), "1") ? "notice_warn" : "notice");

        Map<String,Object> linkData = new HashMap<String,Object>();
        linkData.put("wr_id", saveDto.getWrId());
        linkData.put("bo_table", "notice");
        try{
            String subData = JsonUtil.toStringJson(linkData);
            push.setSub_data(subData);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        for(G5MemberDto dto : noticeTargetList){
            if( StringUtils.equals(dto.getPushNormal(), "1")){
                push.addTargetInfo(dto.getMbId(), workPlace.getWpId(), dto.getDeviceId(), dto.getAppVersion());
            }
        }
        if( push.targetInfoSize() > 0 ){
            pushService.sendPush(push);
        }
        if( deleteCommand.existsDeleteFiles() ){
            storageClient.requestDeleteFiles(deleteCommand);
        }
    }



    @Transactional("mybatisTransactionManager")
    public void update(UpdateNoticeBoardRequest request, int wrId) {
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        if ( request.getWrId() != wrId) {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "아이디가 일치하지 않습니다.");
        }

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        G5WriteNoticeDto saveDto = modelMapper.map(request, G5WriteNoticeDto.class);

        Optional<G5WriteNotice> duplicated = g5WriteNoticeRepository.findById(wrId);
        if (duplicated.isPresent()) {

            g5WriteNoticeDao.update(saveDto);
        }
        else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }

        if( StringUtils.equals(saveDto.getWrNotice(), "1")){
            g5WriteNoticeDao.addNoticeFlag(saveDto.getWrId());
        }
        else {
            g5WriteNoticeDao.removeNoticeFlag(saveDto.getWrId());
        }

        DeleteFileCommand deleteCommand = new DeleteFileCommand();

        treatUploadedFile(saveDto.getWrId(), request.getWrFiles(), deleteCommand);

        if( deleteCommand.existsDeleteFiles() ){
            storageClient.requestDeleteFiles(deleteCommand);
        }
    }


    @Transactional("transactionManager")
    public int delete(int wrId) {
        DeleteFileCommand deleteCommand = new DeleteFileCommand();
        int deleteCnt = deleteBoard(wrId, deleteCommand);
        if( deleteCommand.existsDeleteFiles() ){
            storageClient.requestDeleteFiles(deleteCommand);
        }
        return deleteCnt;
    }

    @Transactional("transactionManager")
    public int deleteMultiple(List<Integer> wrIds) {
        DeleteFileCommand deleteCommand = new DeleteFileCommand();

        int deleteCnt = 0;
        for (Integer wrId : wrIds) {
            deleteCnt += deleteBoard(wrId, deleteCommand);
        }

        if( deleteCommand.existsDeleteFiles() ){
            storageClient.requestDeleteFiles(deleteCommand);
        }
        return deleteCnt;
    }

    private int deleteBoard(int wrId, DeleteFileCommand deleteCommand ){
        NoticeDto notice = findById(wrId);
        if( notice != null ){
            g5WriteNoticeRepository.deleteById(wrId);
            if( notice.getFileList() != null && notice.getFileList().size() != 0 ){
                for( AttachedFileDto fileDto : notice.getFileList() ){
                    attachedFileService.delete(fileDto.getBoTable(), fileDto.getWrId(), fileDto.getBfNo());
                    deleteCommand.addFilePath(fileService.getNoticeFilePath() + File.separator + fileDto.getBfFile());
                }
            }
            return 1;
        }
        else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }
    }


    private void treatUploadedFile(int wrId, List<UploadAttachedFileDto> wrFiles, DeleteFileCommand command){
        if( wrFiles != null && wrFiles.size() != 0 ){
            for( UploadAttachedFileDto uploadFileDto : wrFiles  ){

                G5BoardFileDto fileDto = new G5BoardFileDto();
                fileDto.setBoTable("notice");
                fileDto.setWrId(wrId);
                fileDto.setBfNo(uploadFileDto.getBfNo());

                G5BoardFileDto currentDto = g5BoardFileDao.findByKey(fileDto);

                if( StringUtils.equals(uploadFileDto.getBfDelete(), "2")){
                    if( StringUtils.isNotBlank(uploadFileDto.getFileName())){
                        fileDto.setBfSource(uploadFileDto.getFileOriginalName());
                        fileDto.setBfFile(uploadFileDto.getFileName());
                        fileDto.setBfDownload(0);
                        fileDto.setBfContent("");
                        ImageInfo imageInfo = fileService.getImageInfo(uploadFileDto.getFileName());
                        if( imageInfo != null ){
                            fileDto.setBfWidth( imageInfo.getWidth());
                            fileDto.setBfHeight( imageInfo.getHeigth());
                            fileDto.setBfType( imageInfo.getType());
                            fileDto.setBfFilesize( (int) imageInfo.getFileSize());
                        }
                        else {
                            fileDto.setBfFilesize( (int) fileService.getTempFileSize(uploadFileDto.getFileName()));
                            fileDto.setBfWidth(0);
                            fileDto.setBfHeight(0);
                            fileDto.setBfType(0);
                            fileDto.setBfFilesize(0);
                        }
                        fileService.moveTempFile(fileDto.getBfFile(), fileService.getNoticeFilePath());
                        attachedFileService.create(fileDto);

                        if( currentDto != null ){
                            command.addFilePath(fileService.getNoticeFilePath() + File.separator + currentDto.getBfFile());
                        }
                    }
                    else {
                        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "업로드 파일명이 존재하지 않습니다.");
                    }
                }
                else if( StringUtils.equals(uploadFileDto.getBfDelete(), "1")){
                    attachedFileService.delete(fileDto.getBoTable(), fileDto.getWrId(), fileDto.getBfNo());
                    if( currentDto != null ){
                        command.addFilePath(fileService.getNoticeFilePath() + File.separator + currentDto.getBfFile());
                    }
                }
            }
            attachedFileService.deleteBlank("notice", wrId);
            g5WriteNoticeDao.updateFileCount(wrId);
        }
    }

}
