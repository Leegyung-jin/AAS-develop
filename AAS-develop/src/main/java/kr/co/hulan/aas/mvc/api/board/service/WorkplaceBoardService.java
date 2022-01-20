package kr.co.hulan.aas.mvc.api.board.service;

import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.MemberLevel;
import kr.co.hulan.aas.common.code.WorkplaceBoardCateogry;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.infra.StorageClient;
import kr.co.hulan.aas.infra.storage.DeleteFileCommand;
import kr.co.hulan.aas.mvc.api.board.controller.request.CreateWorkplaceBoardRequest;
import kr.co.hulan.aas.mvc.api.board.controller.request.ListWorkplaceBoardRequest;
import kr.co.hulan.aas.mvc.api.board.controller.request.UpdateActionWorkplaceBoardRequest;
import kr.co.hulan.aas.mvc.api.board.controller.request.UpdateWorkplaceBoardRequest;
import kr.co.hulan.aas.mvc.api.board.controller.response.ListWorkplaceBoardResponse;
import kr.co.hulan.aas.mvc.api.board.dto.AttachedFileDto;
import kr.co.hulan.aas.mvc.api.board.dto.NoticeDto;
import kr.co.hulan.aas.mvc.api.board.dto.UploadAttachedFileDto;
import kr.co.hulan.aas.mvc.api.board.dto.WpBoardDto;
import kr.co.hulan.aas.mvc.api.file.dto.ImageInfo;
import kr.co.hulan.aas.mvc.api.file.service.FileService;
import kr.co.hulan.aas.mvc.api.sensorLog.controller.request.NotifyAlarmRequest;
import kr.co.hulan.aas.mvc.dao.mapper.G5BoardFileDao;
import kr.co.hulan.aas.mvc.dao.mapper.G5BoardNewDao;
import kr.co.hulan.aas.mvc.dao.mapper.G5WriteWpboardDao;
import kr.co.hulan.aas.mvc.dao.mapper.ManagerTokenDao;
import kr.co.hulan.aas.mvc.dao.repository.*;
import kr.co.hulan.aas.mvc.model.domain.*;
import kr.co.hulan.aas.mvc.model.dto.*;
import kr.co.hulan.aas.mvc.service.push.PushData;
import kr.co.hulan.aas.mvc.service.push.PushService;
import kr.co.hulan.aas.mvc.service.push.PushTargetInfo;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WorkplaceBoardService {

    private Logger logger = LoggerFactory.getLogger(WorkplaceBoardService.class);

    @Autowired
    private G5MemberRepository g5MemberRepository;

    @Autowired
    private G5WriteWpboardRepository g5WriteWpboardRepository;

    @Autowired
    private ManagerTokenService managerTokenService;

    @Autowired
    private WorkPlaceRepository workPlaceRepository;

    @Autowired
    private WorkPlaceCoopRepository workPlaceCoopRepository;

    @Autowired
    private G5WriteWpboardDao g5WriteWpboardDao;

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

    @Autowired
    @Qualifier("entityManagerFactory")
    EntityManager entityManager;

    public ListWorkplaceBoardResponse findListPage(ListWorkplaceBoardRequest request) {
        return new ListWorkplaceBoardResponse(
                findListCountByCondition(request.getConditionMap()),
                findListByCondition(request.getConditionMap())
        );
    }


    public List<WpBoardDto> findListByCondition(Map<String,Object> conditionMap) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return g5WriteWpboardDao.findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap))
                .stream().map(wpBoardDto -> modelMapper.map(wpBoardDto, WpBoardDto.class) ).collect(Collectors.toList());
    }

    private Long findListCountByCondition(Map<String,Object> conditionMap) {
        return g5WriteWpboardDao.findListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }


    public WpBoardDto findById(int wrId){
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        G5WriteWpboardDto wpBoardDto = g5WriteWpboardDao.findById(wrId);
        if ( wpBoardDto != null ) {
            g5WriteWpboardDao.increaseHitCount(wpBoardDto);

            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            WpBoardDto wpBoard = modelMapper.map(wpBoardDto, WpBoardDto.class);
            wpBoard.setFileList(attachedFileService.findListByBoardKey("wpboard", wpBoard.getWrId()));
            return wpBoard;
        } else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }
    }


    @Transactional("mybatisTransactionManager")
    public void create(CreateWorkplaceBoardRequest request) {
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

        G5WriteWpboardDto saveDto = modelMapper.map(request, G5WriteWpboardDto.class);

        saveDto.setMbId(loginMember.getMbId());
        saveDto.setWrPassword(loginMember.getMbPassword());
        saveDto.setWrName(loginMember.getName());
        saveDto.setWrIp(AuthenticationHelper.getRemoteIp());
        saveDto.setWr6("미입력");
        saveDto.setWr9(""+loginMember.getMbLevel());

        WorkplaceBoardCateogry category = WorkplaceBoardCateogry.getByName(saveDto.getCaName());
        if( category == null || !category.isRegistableLevel(loginMember.getMbLevel())){
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "등록 권한이 없는 카테고리입니다.");
        }

        Optional<WorkPlace> workplaceOp = workPlaceRepository.findById(saveDto.getWr2());
        if( loginMember.getMbLevel() == MemberLevel.SUPER_ADMIN.getCode() ){
            if( workplaceOp.isPresent()){
                saveDto.setWr3( workplaceOp.get().getWpName());
            }
            else {
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 현장입니다.");
            }

            Optional<WorkPlaceCoop> coopOp = workPlaceCoopRepository.findByWpIdAndCoopMbId(saveDto.getWr2(), saveDto.getWr4());
            if( coopOp.isPresent()){
                saveDto.setWr5( coopOp.get().getCoopMbName());
            }
            else {
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "해당 현장에 폅입되지 않은 협력사 정보입니다.");
            }
        }
        else if( loginMember.getMbLevel() == MemberLevel.FIELD_MANAGER.getCode() ){
            if( workplaceOp.isPresent()){
                if( StringUtils.equals(workplaceOp.get().getManMbId(), loginUser.getUsername()) ){
                    saveDto.setWr3( workplaceOp.get().getWpName());
                }
                else {
                    throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
                }
            }
            else {
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 현장입니다.");
            }

            Optional<WorkPlaceCoop> coopOp = workPlaceCoopRepository.findByWpIdAndCoopMbId(saveDto.getWr2(), saveDto.getWr4());
            if( coopOp.isPresent()){
                saveDto.setWr5( coopOp.get().getCoopMbName());
            }
            else {
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "해당 현장에 폅입되지 않은 협력사 정보입니다.");
            }
        }
        else if( loginMember.getMbLevel() == MemberLevel.COOPERATIVE_COMPANY.getCode() ){
            if( workplaceOp.isPresent()){
                saveDto.setWr3( workplaceOp.get().getWpName() );
            }
            else {
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 현장입니다.");
            }
            Optional<WorkPlaceCoop> coopOp = workPlaceCoopRepository.findByWpIdAndCoopMbId(saveDto.getWr2(), saveDto.getWr4());
            if( coopOp.isPresent()){
                if( StringUtils.equals(coopOp.get().getCoopMbId(), loginUser.getUsername()) ){
                    saveDto.setWr5( coopOp.get().getCoopMbName());
                }
                else {
                    throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
                }
            }
            else {
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "해당 현장에 폅입되지 않은 협력사 정보입니다.");
            }
        }
        else {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        g5WriteWpboardDao.create(saveDto);

        G5BoardNewDto newDto = new G5BoardNewDto();
        newDto.setBoTable("wpboard");
        newDto.setWrId(saveDto.getWrId());
        newDto.setWrParent(saveDto.getWrId());
        newDto.setMbId(loginUser.getUsername());
        g5BoardNewDao.create(newDto);

        saveDto.setWrParent( saveDto.getWrId() );
        g5WriteWpboardDao.updateParent(saveDto);

        DeleteFileCommand deleteCommand = new DeleteFileCommand();
        treatFileUpload(saveDto.getWrId(), request.getWrFiles(), deleteCommand);

        PushData push = new PushData();
        push.setSubject("공사게시판");
        push.setCode("notice");
        push.setSub_data("");
        if( loginMember.getMbLevel() == MemberLevel.SUPER_ADMIN.getCode()){
            push.setBody( String.format("%s 현장대상으로 어드민이 %s 글을 작성했습니다.", saveDto.getWr3(), saveDto.getCaName()));
            Optional<G5Member> targetMemberOp = g5MemberRepository.findByMbId(saveDto.getWr4());
            if( targetMemberOp.isPresent() ){
                G5Member g5Member = targetMemberOp.get();
                if( StringUtils.equals(targetMemberOp.get().getPushNormal(), "1")){
                    push.addTargetInfo(g5Member.getMbId(), workplaceOp.get().getWpId(), g5Member.getDeviceId(), g5Member.getAppVersion() );
                }
            }

            Optional<G5Member> managerOp = g5MemberRepository.findByMbId(workplaceOp.get().getManMbId());
            if( managerOp.isPresent() ){
                G5Member g5Member = targetMemberOp.get();
                if( StringUtils.equals(managerOp.get().getPushNormal(), "1")){
                    List<ManagerTokenDto> tokenList = managerTokenService.findByMbIdAndMtYn(workplaceOp.get().getManMbId(), 1);
                    for(ManagerTokenDto token : tokenList){
                        push.addTargetInfo( workplaceOp.get().getManMbId(), workplaceOp.get().getWpId(), token.getMtToken(), token.getAppVersion() );
                    }
                }
            }
        }
        else if( loginMember.getMbLevel() == MemberLevel.FIELD_MANAGER.getCode()){
            push.setBody( String.format("%s 현장관리자가 %s 글을 작성했습니다.", saveDto.getWr3(), saveDto.getCaName()));

            Optional<G5Member> targetMemberOp = g5MemberRepository.findByMbId(saveDto.getWr4());
            if( targetMemberOp.isPresent() ){
                G5Member g5Member = targetMemberOp.get();
                if( StringUtils.equals(targetMemberOp.get().getPushNormal(), "1")){
                    push.addTargetInfo( g5Member.getMbId(), workplaceOp.get().getWpId(), g5Member.getDeviceId(), g5Member.getAppVersion() );
                }
            }
        }
        else if( loginMember.getMbLevel() == MemberLevel.COOPERATIVE_COMPANY.getCode()){
            push.setBody( String.format("%s에서 %s 요청글을 작성했습니다.", loginMember.getName(), saveDto.getCaName()) );

            Optional<G5Member> managerOp = g5MemberRepository.findByMbId(workplaceOp.get().getManMbId());
            if( managerOp.isPresent() ){
                G5Member g5Member = managerOp.get();
                if( StringUtils.equals(g5Member.getPushNormal(), "1")){
                    List<ManagerTokenDto> tokenList = managerTokenService.findByMbIdAndMtYn(workplaceOp.get().getManMbId(), 1);
                    for(ManagerTokenDto token : tokenList){
                        push.addTargetInfo( workplaceOp.get().getManMbId(), workplaceOp.get().getWpId(), token.getMtToken(), token.getAppVersion() );
                    }
                }
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
    public void update(UpdateWorkplaceBoardRequest request, int wrId) {
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }
        Optional<G5Member> memberOp = g5MemberRepository.findByMbId(loginUser.getUsername());
        if( !memberOp.isPresent()){
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }
        G5Member loginMember = memberOp.get();

        if ( request.getWrId() != wrId) {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "아이디가 일치하지 않습니다.");
        }
        WorkplaceBoardCateogry category = WorkplaceBoardCateogry.getByName(request.getCaName());
        if( category == null || !category.isRegistableLevel(loginMember.getMbLevel())){
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "수정 권한이 없는 카테고리입니다.");
        }

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        G5WriteWpboardDto existsDto = g5WriteWpboardDao.findById(wrId);
        if (existsDto != null ) {
            if( StringUtils.equals(loginUser.getUsername(), existsDto.getMbId()) || loginMember.getMbLevel() == MemberLevel.SUPER_ADMIN.getCode() ){
                modelMapper.map(request, existsDto);
                g5WriteWpboardDao.update(existsDto);
            }
            else {
                throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
            }
        }
        else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }

        Optional<WorkPlace> workplaceOp = workPlaceRepository.findById(existsDto.getWr2());
        if( !workplaceOp.isPresent()){
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 현장입니다.");
        }

        DeleteFileCommand deleteCommand = new DeleteFileCommand();
        treatFileUpload(existsDto.getWrId(), request.getWrFiles(), deleteCommand);

        PushData push = new PushData();
        push.setSubject("공사게시판");
        push.setCode("notice");
        push.setSub_data("");
        if( loginMember.getMbLevel() == MemberLevel.SUPER_ADMIN.getCode()){
            push.setBody( String.format("%s 현장대상으로 어드민이 %s 글을 수정했습니다.", existsDto.getWr3(), existsDto.getCaName()));
            Optional<G5Member> targetMemberOp = g5MemberRepository.findByMbId(existsDto.getWr4());
            if( targetMemberOp.isPresent() ){
                G5Member g5Member = targetMemberOp.get();
                if( StringUtils.equals(targetMemberOp.get().getPushNormal(), "1")){
                    push.addTargetInfo( g5Member.getMbId(), workplaceOp.get().getWpId(), g5Member.getDeviceId(), g5Member.getAppVersion() );
                }
            }
            Optional<G5Member> managerOp = g5MemberRepository.findByMbId(workplaceOp.get().getManMbId());
            if( managerOp.isPresent() ){
                if( StringUtils.equals(managerOp.get().getPushNormal(), "1")){
                    List<ManagerTokenDto> tokenList = managerTokenService.findByMbIdAndMtYn(workplaceOp.get().getManMbId(), 1);
                    for(ManagerTokenDto token : tokenList){
                        push.addTargetInfo( workplaceOp.get().getManMbId(), workplaceOp.get().getWpId(), token.getMtToken() , token.getAppVersion());
                    }
                }
            }
        }
        else if( loginMember.getMbLevel() == MemberLevel.FIELD_MANAGER.getCode()){
            push.setBody( String.format("%s 현장관리자가 %s 글을 수정했습니다.", existsDto.getWr3(), existsDto.getCaName()));
            Optional<G5Member> targetMemberOp = g5MemberRepository.findByMbId(existsDto.getWr4());
            if( targetMemberOp.isPresent() ){
                G5Member g5Member = targetMemberOp.get();
                if( StringUtils.equals(targetMemberOp.get().getPushNormal(), "1")){
                    push.addTargetInfo( g5Member.getMbId(), workplaceOp.get().getWpId(), g5Member.getDeviceId(), g5Member.getAppVersion() );
                }
            }
        }
        else if( loginMember.getMbLevel() == MemberLevel.COOPERATIVE_COMPANY.getCode()){
            push.setBody( String.format("%s에서 %s 요청글을 수정했습니다.", loginMember.getName(), existsDto.getCaName()) );

            Optional<G5Member> managerOp = g5MemberRepository.findByMbId(workplaceOp.get().getManMbId());
            if( managerOp.isPresent() ){
                if( StringUtils.equals(managerOp.get().getPushNormal(), "1")){
                    List<ManagerTokenDto> tokenList = managerTokenService.findByMbIdAndMtYn(workplaceOp.get().getManMbId(), 1);
                    for(ManagerTokenDto token : tokenList){
                        push.addTargetInfo( workplaceOp.get().getManMbId(), workplaceOp.get().getWpId(), token.getMtToken() , token.getAppVersion());
                    }
                }
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
    public void updateAction(UpdateActionWorkplaceBoardRequest request, int wrId) {
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }
        Optional<G5Member> memberOp = g5MemberRepository.findByMbId(loginUser.getUsername());
        if( !memberOp.isPresent()){
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }
        G5Member loginMember = memberOp.get();

        if ( request.getWrId() != wrId) {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "아이디가 일치하지 않습니다.");
        }


        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        G5WriteWpboardDto saveDto = modelMapper.map(request, G5WriteWpboardDto.class );
        G5WriteWpboardDto existsDto = g5WriteWpboardDao.findById(wrId);
        WorkplaceBoardCateogry category = null;
        if (existsDto != null ) {
            category = WorkplaceBoardCateogry.getByName(existsDto.getCaName());
            if( category == null || !category.isRepliableLevel(loginMember.getMbLevel())){
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "처리 권한이 없는 사용자입니다.");
            }

            if( loginMember.getMbLevel() == MemberLevel.SUPER_ADMIN.getCode() ){
                g5WriteWpboardDao.updateAction(saveDto);
            }
            else if( loginMember.getMbLevel() == MemberLevel.FIELD_MANAGER.getCode() ){
                if( StringUtils.equals( loginUser.getWpId(), existsDto.getWr2()) ){
                    g5WriteWpboardDao.updateAction(saveDto);
                }
                else {
                    logger.warn(this.getClass().getSimpleName()+"|NOT_EQUAL_WP_ID|"+loginUser.getWpId()+"|"+existsDto.getWr2());
                }
            }
            else if( loginMember.getMbLevel() == MemberLevel.COOPERATIVE_COMPANY.getCode() && StringUtils.equals( loginUser.getUsername(), existsDto.getWr4())){
                //saveDto.setWr6("결과입력");
                g5WriteWpboardDao.updateAction(saveDto);
            }
            else {
                throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
            }

        }
        else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }

        Optional<WorkPlace> workplaceOp = workPlaceRepository.findById(existsDto.getWr2());
        if( !workplaceOp.isPresent()){
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 현장입니다.");
        }

        DeleteFileCommand deleteCommand = new DeleteFileCommand();
        treatFileUpload(existsDto.getWrId(), request.getWrFiles(), deleteCommand);

        PushData push = new PushData();
        push.setSubject("공사게시판");
        push.setCode("notice");
        push.setSub_data("");
        if( loginMember.getMbLevel() == MemberLevel.SUPER_ADMIN.getCode()){
            if( category == WorkplaceBoardCateogry.WORK_APPROVAL ){
                push.setBody( String.format("%s 현장대상으로 어드민이 %s 작업요청을 %s 했습니다", existsDto.getWr3(), existsDto.getCaName(), saveDto.getWr6()));
            }
            else if( existsDto.getWr12() == null ){
                push.setBody( String.format("%s에서 %s 글에 대한 조치사항을 입력했습니다.", loginMember.getName(), existsDto.getCaName()));
            }
            else {
                push.setBody( String.format("%s에서 %s 글에 대한 조치사항을 수정했습니다.", loginMember.getName(), existsDto.getCaName()));
            }

            Optional<G5Member> targetMemberOp = g5MemberRepository.findByMbId(existsDto.getWr4());
            if( targetMemberOp.isPresent() ){
                G5Member member = targetMemberOp.get();
                if( StringUtils.equals(targetMemberOp.get().getPushNormal(), "1")){
                    push.addTargetInfo(member.getMbId(), workplaceOp.get().getWpId(), member.getDeviceId(), member.getAppVersion() );
                }
            }

            Optional<G5Member> managerOp = g5MemberRepository.findByMbId(workplaceOp.get().getManMbId());
            if( managerOp.isPresent() ){
                if( StringUtils.equals(managerOp.get().getPushNormal(), "1")){
                    List<ManagerTokenDto> tokenList = managerTokenService.findByMbIdAndMtYn(workplaceOp.get().getManMbId(), 1);
                    for(ManagerTokenDto token : tokenList){
                        push.addTargetInfo( workplaceOp.get().getManMbId(), workplaceOp.get().getWpId(), token.getMtToken(), token.getAppVersion() );
                    }
                }
            }
        }
        else if( loginMember.getMbLevel() == MemberLevel.FIELD_MANAGER.getCode()){
            push.setBody( String.format("%s 현장관리자가 %s 작업요청을 %s 했습니다", existsDto.getWr3(), existsDto.getCaName(), saveDto.getWr6()));
            Optional<G5Member> targetMemberOp = g5MemberRepository.findByMbId(existsDto.getWr4());
            if( targetMemberOp.isPresent() ){
                G5Member member = targetMemberOp.get();
                if( StringUtils.equals(targetMemberOp.get().getPushNormal(), "1")){
                    push.addTargetInfo(member.getMbId(), workplaceOp.get().getWpId(), member.getDeviceId(), member.getAppVersion() );
                }
            }
        }
        else if( loginMember.getMbLevel() == MemberLevel.COOPERATIVE_COMPANY.getCode()){
            if( existsDto.getWr12() == null ){
                push.setBody( String.format("%s에서 %s 글에 대한 조치사항을 입력했습니다.", loginMember.getName(), existsDto.getCaName()));
            }
            else {
                push.setBody( String.format("%s에서 %s 글에 대한 조치사항을 수정했습니다.", loginMember.getName(), existsDto.getCaName()));
            }

            Optional<G5Member> managerOp = g5MemberRepository.findByMbId(workplaceOp.get().getManMbId());
            if( managerOp.isPresent() ){
                if( StringUtils.equals(managerOp.get().getPushNormal(), "1")){
                    List<ManagerTokenDto> tokenList = managerTokenService.findByMbIdAndMtYn(workplaceOp.get().getManMbId(), 1);
                    for(ManagerTokenDto token : tokenList){
                        push.addTargetInfo( workplaceOp.get().getManMbId(), workplaceOp.get().getWpId(), token.getMtToken() , token.getAppVersion());
                    }
                }
            }
        }
        if( push.targetInfoSize() > 0 ){
            pushService.sendPush(push);
        }
        if( deleteCommand.existsDeleteFiles() ){
            storageClient.requestDeleteFiles(deleteCommand);
        }
    }

    private void treatFileUpload(int wrId, List<UploadAttachedFileDto> wrFiles, DeleteFileCommand command){
        if( wrFiles != null && wrFiles.size() != 0 ){
            for( UploadAttachedFileDto uploadFileDto : wrFiles  ){

                G5BoardFileDto fileDto = new G5BoardFileDto();
                fileDto.setBoTable("wpboard");
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
                        fileService.moveTempFile(fileDto.getBfFile(), fileService.getWpBoardFilePath());
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
            attachedFileService.deleteBlank("wpboard", wrId);
            g5WriteWpboardDao.updateFileCount(wrId);
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
        WpBoardDto wpboardDto = findById(wrId);
        if( wpboardDto != null ){
            g5WriteWpboardRepository.deleteById(wrId);
            if( wpboardDto.getFileList() != null && wpboardDto.getFileList().size() != 0 ){
                for( AttachedFileDto fileDto : wpboardDto.getFileList() ){
                    attachedFileService.delete(fileDto.getBoTable(), fileDto.getWrId(), fileDto.getBfNo());
                    deleteCommand.addFilePath(fileService.getNoticeFilePath() + File.separator + fileDto.getBfFile());
                }
            }
            return 1;
        }
        return 0;
    }
}
