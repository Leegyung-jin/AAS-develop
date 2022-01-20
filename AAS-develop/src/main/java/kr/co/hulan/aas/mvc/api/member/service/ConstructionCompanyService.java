package kr.co.hulan.aas.mvc.api.member.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.Storage;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.common.utils.GenerateIdUtils;
import kr.co.hulan.aas.config.properties.HiccBaseConfigProperties;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.file.dto.UploadedFile;
import kr.co.hulan.aas.mvc.api.file.service.FileService;
import kr.co.hulan.aas.mvc.api.member.controller.request.CreateConstructionCompanyRequest;
import kr.co.hulan.aas.mvc.api.member.controller.request.UpdateConstructionCompanyRequest;
import kr.co.hulan.aas.mvc.api.member.dto.WorkerDto;
import kr.co.hulan.aas.mvc.api.orderoffice.vo.LinkBtnInfoDto;
import kr.co.hulan.aas.mvc.dao.mapper.ConCompanyDao;
import kr.co.hulan.aas.mvc.dao.mapper.HiccInfoDao;
import kr.co.hulan.aas.mvc.dao.repository.ConCompanyRepository;
import kr.co.hulan.aas.mvc.api.member.dto.ConstructionCompanyDto;
import kr.co.hulan.aas.mvc.api.member.controller.request.ListConstructionCompanyRequest;
import kr.co.hulan.aas.mvc.api.member.controller.response.ListConstructionCompanyResponse;
import kr.co.hulan.aas.mvc.dao.repository.HiccInfoRepository;
import kr.co.hulan.aas.mvc.dao.repository.HiccMainBtnRepository;
import kr.co.hulan.aas.mvc.model.domain.ConCompany;
import kr.co.hulan.aas.mvc.model.domain.G5Member;
import kr.co.hulan.aas.mvc.model.domain.HiccInfo;
import kr.co.hulan.aas.mvc.model.domain.HiccMainBtn;
import kr.co.hulan.aas.mvc.model.dto.ConCompanyDto;
import kr.co.hulan.aas.mvc.model.dto.HiccMainBtnDto;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ConstructionCompanyService {

    @Autowired
    ConCompanyRepository conCompanyRepository;

    @Autowired
    ConCompanyDao conCompanyDao;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    @Qualifier("entityManagerFactory")
    EntityManager entityManager;

    @Autowired
    FileService fileService;

    @Autowired
    private HiccInfoRepository hiccInfoRepository;

    @Autowired
    private HiccBaseConfigProperties hiccBaseConfigProperties;

    @Autowired
    private HiccMainBtnRepository hiccMainBtnRepository;

    @Autowired
    private HiccInfoDao hiccInfoDao;

    public ListConstructionCompanyResponse findListPage(ListConstructionCompanyRequest request) {
        return new ListConstructionCompanyResponse(
                findListCountByCondition(request.getConditionMap()),
                findListByCondition(request.getConditionMap())
        );
    }

    public List<ConstructionCompanyDto> findListByCondition(Map<String,Object> conditionMap) {
        return conCompanyDao.findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }

    private Long findListCountByCondition(Map<String,Object> conditionMap) {
        return conCompanyDao.findListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }

    public ConCompanyDto findConCompanyByCcId(String ccId) {
        ConCompanyDto member = conCompanyDao.findInfo(ccId);
        if (member != null) {
            List<LinkBtnInfoDto> btnList = hiccInfoDao.findHiccMainButtonList(member.getHiccNo())
                .stream().map( dto -> modelMapper.map( dto, LinkBtnInfoDto.class)).collect(
                    Collectors.toList());
            member.setMainBtnList(btnList != null ? btnList : Collections.emptyList());
            return member;
        } else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }
    }


    @Transactional("transactionManager")
    public ConCompanyDto create(CreateConstructionCompanyRequest request) {
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        ConCompany saveEntity = modelMapper.map(request, ConCompany.class);

        Optional<ConCompany> duplicatedCcnum = conCompanyRepository.findByCcNum(saveEntity.getCcNum());
        if (duplicatedCcnum.isPresent()) {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "이미 등록된 사업자번호가 존재합니다.");
        }
        saveEntity.setCcId(GenerateIdUtils.getUuidKey());

        HiccInfo hiccInfo = modelMapper.map(request, HiccInfo.class);
        if( StringUtils.isBlank( hiccInfo.getHiccName())){
            hiccInfo.setHiccName(saveEntity.getCcName()+" "+hiccBaseConfigProperties.getTitle());
        }

        UploadedFile iconFile = request.getIconFile();

        boolean existsIconUploadedFile = iconFile != null
            && StringUtils.isNotEmpty(iconFile.getFileName())
            && StringUtils.isNotEmpty(iconFile.getFileOriginalName());
        if( existsIconUploadedFile ){
            fileService.copyTempFile(iconFile.getFileName(), fileService.getConCompanyFilePath(saveEntity.getCcId(), true));
            hiccInfo.setIconFileLocation(Storage.LOCAL_STORAGE.getCode());
            hiccInfo.setIconFileName(iconFile.getFileName());
            hiccInfo.setIconFileNameOrg(iconFile.getFileOriginalName());
            hiccInfo.setIconFilePath(fileService.getConCompanyFilePath(saveEntity.getCcId(), false));
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
            fileService.copyTempFile(bgImgFile.getFileName(), fileService.getConCompanyFilePath(saveEntity.getCcId(), true));
            hiccInfo.setBgImgFileLocation(Storage.LOCAL_STORAGE.getCode());
            hiccInfo.setBgImgFileName(bgImgFile.getFileName());
            hiccInfo.setBgImgFileNameOrg(bgImgFile.getFileOriginalName());
            hiccInfo.setBgImgFilePath(fileService.getConCompanyFilePath(saveEntity.getCcId(), false));
        }
        else {
            hiccInfo.setBgImgFileLocation(null);
            hiccInfo.setBgImgFileName(null);
            hiccInfo.setBgImgFileNameOrg(null);
            hiccInfo.setBgImgFilePath(null);
        }
        saveEntity.setCcDatetime(new Date());

        hiccInfo.setCreateDate(new Date());
        hiccInfo.setCreator(loginUser.getMbId());
        hiccInfo.setUpdateDate(new Date());
        hiccInfo.setUpdater(loginUser.getMbId());

        hiccInfoRepository.save(hiccInfo);

        updateMainBtnInfo(hiccInfo.getHiccNo(), request.getMainBtnList(), loginUser.getMbId());

        saveEntity.setHiccNo(hiccInfo.getHiccNo());
        conCompanyRepository.save(saveEntity);
        return modelMapper.map(saveEntity, ConCompanyDto.class);
    }


    @Transactional("transactionManager")
    public void update(UpdateConstructionCompanyRequest request, String ccId) {
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        if (!StringUtils.equals(request.getCcId(), ccId)) {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "아이디가 일치하지 않습니다.");
        }

        ConCompany saveEntity = conCompanyRepository.findById(request.getCcId()).orElse(null);
        if ( saveEntity == null ) {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }

        modelMapper.map(request, saveEntity);
        long ccNumCnt = conCompanyRepository.getCountByCcNumAndCcIdNot(saveEntity.getCcNum(), saveEntity.getCcId());
        if (ccNumCnt > 0) {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "이미 등록된 사업자번호가 존재합니다.");
        }

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
            hiccInfo.setHiccName(saveEntity.getCcName()+" "+hiccBaseConfigProperties.getTitle());
        }

        UploadedFile iconFile = request.getIconFile();

        boolean existsIconUploadedFile = iconFile != null
            && StringUtils.isNotEmpty(iconFile.getFileName())
            && StringUtils.isNotEmpty(iconFile.getFileOriginalName());
        if( existsIconUploadedFile ){
            if( !StringUtils.equals( iconFile.getFileName(), hiccInfo.getIconFileName() )){
                fileService.copyTempFile(iconFile.getFileName(), fileService.getConCompanyFilePath(saveEntity.getCcId(), true));
                hiccInfo.setIconFileLocation(Storage.LOCAL_STORAGE.getCode());
                hiccInfo.setIconFileName(iconFile.getFileName());
                hiccInfo.setIconFileNameOrg(iconFile.getFileOriginalName());
                hiccInfo.setIconFilePath(fileService.getConCompanyFilePath(saveEntity.getCcId(), false));
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
                fileService.copyTempFile(bgImgFile.getFileName(), fileService.getConCompanyFilePath(saveEntity.getCcId(), true));
                hiccInfo.setBgImgFileLocation(Storage.LOCAL_STORAGE.getCode());
                hiccInfo.setBgImgFileName(bgImgFile.getFileName());
                hiccInfo.setBgImgFileNameOrg(bgImgFile.getFileOriginalName());
                hiccInfo.setBgImgFilePath(fileService.getConCompanyFilePath(saveEntity.getCcId(), false));
            }
        }
        else {
            hiccInfo.setBgImgFileLocation(null);
            hiccInfo.setBgImgFileName(null);
            hiccInfo.setBgImgFileNameOrg(null);
            hiccInfo.setBgImgFilePath(null);
        }
        saveEntity.setCcDatetime(new Date());

        hiccInfo.setUpdateDate(new Date());
        hiccInfo.setUpdater(loginUser.getMbId());

        hiccInfoRepository.save(hiccInfo);

        updateMainBtnInfo(hiccInfo.getHiccNo(), request.getMainBtnList(), loginUser.getMbId());

        saveEntity.setHiccNo(hiccInfo.getHiccNo());

        conCompanyRepository.save(saveEntity);
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
    public int delete(String ccId) {
        Optional<ConCompany> existWorker = conCompanyRepository.findById(ccId);
        if (existWorker.isPresent()) {
            return conCompanyRepository.deleteByCcId(ccId);
        }
        else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }



    }

    @Transactional("transactionManager")
    public int deleteConCompanyList(List<String> ccIds) {
        int deleteCnt = 0;
        for (String ccId : ccIds) {
            deleteCnt += conCompanyRepository.deleteByCcId(ccId);
        }
        return deleteCnt;
    }
}
