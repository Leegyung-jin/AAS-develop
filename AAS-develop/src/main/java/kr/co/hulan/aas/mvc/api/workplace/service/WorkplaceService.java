package kr.co.hulan.aas.mvc.api.workplace.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.EnableCode;
import kr.co.hulan.aas.common.code.MemberLevel;
import kr.co.hulan.aas.common.code.Storage;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.common.utils.GenerateIdUtils;
import kr.co.hulan.aas.common.utils.GpsGridUtils;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.infra.weather.WeatherGridXY;
import kr.co.hulan.aas.infra.weather.request.WeatherGridXYRequest;
import kr.co.hulan.aas.mvc.api.file.dto.UploadedFile;
import kr.co.hulan.aas.mvc.api.file.service.FileService;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.WorkplaceSupportMonitoringDto;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.WorkplaceSupportMonitoringResponse;
import kr.co.hulan.aas.mvc.api.workplace.controller.request.CreateWorkplaceRequest;
import kr.co.hulan.aas.mvc.api.workplace.controller.request.ListWorkplaceRequest;
import kr.co.hulan.aas.mvc.api.workplace.controller.request.UpdateWorkplaceRequest;
import kr.co.hulan.aas.mvc.api.workplace.controller.response.ListWorkplaceResponse;
import kr.co.hulan.aas.mvc.dao.mapper.SensorPolicyInfoDao;
import kr.co.hulan.aas.mvc.dao.mapper.WorkPlaceCoopDao;
import kr.co.hulan.aas.mvc.dao.mapper.WorkPlaceDao;
import kr.co.hulan.aas.mvc.dao.mapper.WorkPlaceMonitorConfigDao;
import kr.co.hulan.aas.mvc.dao.repository.*;
import kr.co.hulan.aas.mvc.model.domain.*;
import kr.co.hulan.aas.mvc.model.dto.SensorPolicyInfoDto;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceCoopDto;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceDto;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceMonitorConfigDto;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class WorkplaceService {

    @Autowired
    WorkPlaceRepository workPlaceRepository;

    @Autowired
    WorkPlaceMonitorConfigRepository workPlaceMonitorConfigRepository;

    @Autowired
    WorkPlaceDao workPlaceDao;

    @Autowired
    WorkPlaceMonitorConfigDao workPlaceMonitorConfigDao;

    @Autowired
    SensorPolicyInfoDao sensorPolicyInfoDao;

    @Autowired
    G5MemberRepository g5MemberRepository;

    @Autowired
    ConCompanyRepository conCompanyRepository;

    @Autowired
    ConstructionSiteRepository constructionSiteRepository;

    @Autowired
    ConstructionSiteManagerRepository constructionSiteManagerRepository;

    @Autowired
    GpsCheckPolicyInfoRepository gpsCheckPolicyInfoRepository;

    @Autowired
    SensorPolicyInfoRepository sensorPolicyInfoRepository;

    @Autowired
    WorkDeviceInfoRepository workDeviceInfoRepository;

    @Autowired
    WorkEquipmentInfoRepository workEquipmentInfoRepository;

    @Autowired
    WorkPlaceCoopRepository workPlaceCoopRepository;

    @Autowired
    WorkPlaceCoopDao workPlaceCoopDao;

    @Autowired
    WorkPlaceWorkerRepository workPlaceWorkerRepository;

    @Autowired
    WorkPlaceAddressRepository workPlaceAddressRepository;

    @Autowired
    AlarmCheckPolicyInfoRepository alarmCheckPolicyInfoRepository;

    @Autowired
    DeviceCheckPolicyInfoRepository deviceCheckPolicyInfoRepository;

    @Autowired
    GpsDeviceInfoRepository gpsDeviceInfoRepository;

    @Autowired
    GpsLocationRepository gpsLocationRepository;

    @Autowired
    WorkGeofenceInfoRepository workGeofenceInfoRepository;

    @Autowired
    SensorDistrictRepository sensorDistrictRepository;

    @Autowired
    RecruitRepository recruitRepository;

    @Autowired
    RecruitApplyRepository recruitApplyRepository;

    @Autowired
    FileService fileService;

    @Autowired
    BuildingRepository buildingRepository;

    @Autowired
    WorkSectionRepository workSectionRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    OrderingOfficeRepository orderingOfficeRepository;

    @Autowired
    @Qualifier("entityManagerFactory")
    EntityManager entityManager;

    @Value(value = "${default.smart_monitor.workplace_image}")
    String defaultWorkspaceImage;

    public ListWorkplaceResponse findListPage(ListWorkplaceRequest request) {
        return new ListWorkplaceResponse(
                findListCountByCondition(request.getConditionMap()),
                findListByCondition(request.getConditionMap())
        );
    }

    public List<WorkPlaceDto> findListByCondition(Map<String,Object> conditionMap) {
        return workPlaceDao.findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }

    private Long findListCountByCondition(Map<String,Object> conditionMap) {
        return workPlaceDao.findListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }

    public WorkplaceSupportMonitoringResponse findAvailableMonitoringList(){
        List<WorkplaceSupportMonitoringDto> workplaceList = workPlaceDao.findWorkplaceSupportMonitoring(
            AuthenticationHelper.addAdditionalConditionByLevel(new HashMap<String,Object>()));
        workplaceList.stream().forEach( dto -> {
            if( dto.isSupportBle() ){
                dto.setViewFileDownloadUrl( dto.getViewMapFileDownloadUrl() );
            }
            else {
                dto.setViewFileDownloadUrl( defaultWorkspaceImage );
            }
        });
        return new WorkplaceSupportMonitoringResponse(workplaceList.size(), workplaceList);
    }

    public WorkplaceSupportMonitoringDto findWorkplaceSupportMonitoringInfo(String wpId){
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("wpId", wpId);
        List<WorkplaceSupportMonitoringDto> workplaceList = workPlaceDao.findWorkplaceSupportMonitoring(
            AuthenticationHelper.addAdditionalConditionByLevel(condition));
        if( workplaceList != null && workplaceList.size() > 0 ){
            WorkplaceSupportMonitoringDto dto = workplaceList.get(0);
            if( dto.isSupportBle() ){
                dto.setViewFileDownloadUrl( dto.getViewMapFileDownloadUrl() );
            }
            else {
                dto.setViewFileDownloadUrl( defaultWorkspaceImage );
            }
            return dto;
        }
        else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }
    }

    public WorkPlaceDto findById(String wpId){
        WorkPlaceDto workPlaceDto = workPlaceDao.findById(wpId);
        if (workPlaceDto != null ) {
            return workPlaceDto;
        } else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }
    }

    @Transactional("transactionManager")
    public void create(CreateWorkplaceRequest request) {
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        if( request.getOfficeNo() != null ){
            OrderingOffice office = orderingOfficeRepository.findById(request.getOfficeNo()).orElse(null);
            if( office == null ){
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 발주사입니다.");
            }
        }

        WorkPlace saveEntity = modelMapper.map(request, WorkPlace.class);

        Optional<ConCompany> companyOp = conCompanyRepository.findById(saveEntity.getCcId());
        if( companyOp.isPresent()){
            saveEntity.setCcName( companyOp.get().getCcName());
        }
        else {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 건설사입니다.");
        }
        ConCompany company = companyOp.get();
        Optional<G5Member> memberOp = g5MemberRepository.findByMbId(saveEntity.getManMbId());
        if( memberOp.isPresent() ){
            G5Member member = memberOp.get();
            if( member.getMbLevel() != MemberLevel.FIELD_MANAGER.getCode() ){
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "선택된 관리자는 현장관리자가 아닙니다.");
            }
            else if( !StringUtils.equals(member.getCcId(), company.getCcId()) ){
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "선택된 현장관리자가 ["+company.getCcName()+"] 소속이 아닙니다.");
            }
            saveEntity.setManMbName( memberOp.get().getName());
        }
        else {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 현장관리자입니다.");
        }

        Optional<G5Member> defaultCoopOp = g5MemberRepository.findByMbId(saveEntity.getDefaultCoopMbId());
        if( defaultCoopOp.isPresent() ){
            if( defaultCoopOp.get().getMbLevel() != MemberLevel.COOPERATIVE_COMPANY.getCode()){
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "선택된 기본 협력사가 올바르지 않습니다.");
            }
        }
        else {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 협력사입니다.");
        }

        long existsCnt = workPlaceRepository.countByManMbId(saveEntity.getManMbId());
        if( existsCnt > 0 ){
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "이미 다른 현장에 할당된 현장관리자입니다.");
        }

        saveEntity.createId();

        if ( request.getViewMapFile() != null ) {
            UploadedFile viewMapFile = request.getViewMapFile();
            if( StringUtils.isNotEmpty(viewMapFile.getFileName())
                && StringUtils.isNotEmpty(viewMapFile.getFileOriginalName()) ){
                fileService.copyTempFile(viewMapFile.getFileName(), fileService.getWorkplaceFilePath(saveEntity.getWpId(), true));
                saveEntity.setViewMapFileLocation(Storage.LOCAL_STORAGE.getCode());
                saveEntity.setViewMapFileName(viewMapFile.getFileName());
                saveEntity.setViewMapFileNameOrg(viewMapFile.getFileOriginalName());
                saveEntity.setViewMapFilePath(fileService.getWorkplaceFilePath(saveEntity.getWpId(), false));
            }
        }

        workPlaceRepository.save(saveEntity);

        WorkPlaceMonitorConfig dto = modelMapper.map( request, WorkPlaceMonitorConfig.class);
        dto.setWpId(saveEntity.getWpId());
        dto.setCreator(loginUser.getMbId());
        dto.setUpdater(loginUser.getMbId());
        checkAndSaveMonitorConfig(saveEntity, dto);

        updateAddress(saveEntity, request.getAddressData());

        createDefaultWorkplaceCoop(saveEntity, defaultCoopOp.get());
        createDefaultConstructionSiteCoop(saveEntity, loginUser );

        List<SensorPolicyInfo> policyList = sensorPolicyInfoRepository.findByWpIdIsNullAndSiTypeNot("default");
        List<SensorPolicyInfo> newPolicyList = new ArrayList<SensorPolicyInfo>();
        for(SensorPolicyInfo defaultPolicy : policyList ){
            SensorPolicyInfo policyDto = new SensorPolicyInfo();
            policyDto.setWpId(saveEntity.getWpId());
            policyDto.setWpName(saveEntity.getWpName());
            policyDto.setMbId(loginUser.getUsername());
            policyDto.setSiType(defaultPolicy.getSiType());
            policyDto.setMinorMax(defaultPolicy.getMinorMax());
            policyDto.setMinorMin(defaultPolicy.getMinorMin());
            policyDto.setScanInterval(defaultPolicy.getScanInterval());
            policyDto.setIdleInterval(defaultPolicy.getIdleInterval());
            policyDto.setReportInterval(defaultPolicy.getReportInterval());
            newPolicyList.add(policyDto);
        }
        sensorPolicyInfoRepository.saveAll(newPolicyList);
    }

    @Transactional("transactionManager")
    public void update(UpdateWorkplaceRequest request, String wpId) {
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        if (!StringUtils.equals(request.getWpId(), wpId)) {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "아이디가 일치하지 않습니다.");
        }

        if( request.getOfficeNo() != null ){
            OrderingOffice office = orderingOfficeRepository.findById(request.getOfficeNo()).orElse(null);
            if( office == null ){
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 발주사입니다.");
            }
        }

        Optional<WorkPlace> duplicated = workPlaceRepository.findById(request.getWpId());
        if (duplicated.isPresent()) {
            WorkPlace saveEntity = duplicated.get();
            if( request.getUninjuryRecordDate() != null && saveEntity.getUninjuryRecordDate() != null ) {
                if( !DateUtils.isSameDay(saveEntity.getUninjuryRecordDate(), request.getUninjuryRecordDate()) ){
                    saveEntity.setUninjuryRecordChange(new Date());
                }
            }
            modelMapper.map(request, saveEntity);

            Optional<G5Member> memberOp = g5MemberRepository.findByMbId(saveEntity.getManMbId());
            if( memberOp.isPresent()){
                G5Member member = memberOp.get();
                if( member.getMbLevel() != MemberLevel.FIELD_MANAGER.getCode() ){
                    throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "선택된 관리자는 현장관리자가 아닙니다.");
                }
                else if( !StringUtils.equals(member.getCcId(), saveEntity.getCcId()) ){
                    throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "선택된 현장관리자가 기본건설사 소속이 아닙니다.");
                }
                saveEntity.setManMbName( memberOp.get().getName());
            }
            else {
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 현장관리자입니다.");
            }

            long existsCnt = workPlaceRepository.countByManMbIdAndWpIdNot(saveEntity.getManMbId(), saveEntity.getWpId());
            if( existsCnt > 0 ){
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "이미 다른 현장에 할당된 현장관리자입니다.");
            }

            Optional<G5Member> defaultCoopOp = g5MemberRepository.findByMbId(saveEntity.getDefaultCoopMbId());
            if( defaultCoopOp.isPresent() ){
                if( defaultCoopOp.get().getMbLevel() != MemberLevel.COOPERATIVE_COMPANY.getCode()){
                    throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "선택된 기본 협력사가 올바르지 않습니다.");
                }
            }
            else {
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 협력사입니다.");
            }

            String originFile = "";

            if( StringUtils.isNotEmpty(saveEntity.getViewMapFileName())
                && StringUtils.isNotEmpty(saveEntity.getViewMapFilePath())
                && saveEntity.getViewMapFileLocation() != null
                && Storage.get(saveEntity.getViewMapFileLocation()) != null ){

                Storage storage = Storage.get(saveEntity.getViewMapFileLocation());
                StringBuilder sb = new StringBuilder();
                sb.append(storage.getPath());
                sb.append(saveEntity.getViewMapFilePath());
                sb.append(saveEntity.getViewMapFileName());
                originFile = sb.toString();
            }

            boolean existsUploadedFile = request.getViewMapFile() != null
                && StringUtils.isNotEmpty(request.getViewMapFile().getFileName())
                && StringUtils.isNotEmpty(request.getViewMapFile().getFileOriginalName());

            boolean needDelete = false;

            if( existsUploadedFile ){
                if( StringUtils.isNotEmpty(originFile) ){
                    if( !StringUtils.equals( saveEntity.getViewMapFileName(),  request.getViewMapFile().getFileName()) ){
                        UploadedFile viewMapFile = request.getViewMapFile();
                        fileService.copyTempFile(viewMapFile.getFileName(), fileService.getWorkplaceFilePath(saveEntity.getWpId(), true));
                        saveEntity.setViewMapFileLocation(Storage.LOCAL_STORAGE.getCode());
                        saveEntity.setViewMapFileName(viewMapFile.getFileName());
                        saveEntity.setViewMapFileNameOrg(viewMapFile.getFileOriginalName());
                        saveEntity.setViewMapFilePath(fileService.getWorkplaceFilePath(saveEntity.getWpId(), false));

                        needDelete = true;
                    }
                    else {
                        saveEntity.setViewMapFileLocation(saveEntity.getViewMapFileLocation());
                        saveEntity.setViewMapFileName(saveEntity.getViewMapFileName());
                        saveEntity.setViewMapFileNameOrg(saveEntity.getViewMapFileNameOrg());
                        saveEntity.setViewMapFilePath(saveEntity.getViewMapFilePath());
                    }
                }
                else {
                    UploadedFile viewMapFile = request.getViewMapFile();
                    fileService.copyTempFile(viewMapFile.getFileName(), fileService.getWorkplaceFilePath(saveEntity.getWpId(), true));
                    saveEntity.setViewMapFileLocation(Storage.LOCAL_STORAGE.getCode());
                    saveEntity.setViewMapFileName(viewMapFile.getFileName());
                    saveEntity.setViewMapFileNameOrg(viewMapFile.getFileOriginalName());
                    saveEntity.setViewMapFilePath(fileService.getWorkplaceFilePath(saveEntity.getWpId(), false));
                }

            }
            else if( StringUtils.isNotEmpty(originFile) ){
                needDelete = true;

                saveEntity.setViewMapFileLocation(null);
                saveEntity.setViewMapFileName(null);
                saveEntity.setViewMapFileNameOrg(null);
                saveEntity.setViewMapFilePath(null);
            }

            workPlaceRepository.save(saveEntity);

            updateAddress(saveEntity, request.getAddressData());

            WorkPlaceMonitorConfig monitorConfig = workPlaceMonitorConfigRepository.findById(saveEntity.getWpId()).orElse(null);
            if( monitorConfig == null ){
                monitorConfig = modelMapper.map(request, WorkPlaceMonitorConfig.class);
                monitorConfig.setWpId(saveEntity.getWpId());
                monitorConfig.setCreator(loginUser.getMbId());
            }
            else {
                modelMapper.map(request, monitorConfig);
            }
            monitorConfig.setUpdater(loginUser.getMbId());
            checkAndSaveMonitorConfig(saveEntity, monitorConfig);

            createDefaultWorkplaceCoop(saveEntity, defaultCoopOp.get());
            createDefaultConstructionSiteCoop(saveEntity, loginUser );

            if( needDelete && StringUtils.isNotEmpty(originFile) ){
                File viewMapFile = new File(originFile);
                if( viewMapFile.exists() && viewMapFile.isFile() ){
                    viewMapFile.delete();
                }
            }
        }
        else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }
    }

    @Transactional("transactionManager")
    public void updateAddress(WorkPlace workplace, String addressData){
        if( StringUtils.isNotBlank(addressData)){
            try{
                Gson gson = new GsonBuilder().create();
                WorkPlaceAddress newAddress = gson.fromJson(addressData, WorkPlaceAddress.class);
                if( newAddress == null || StringUtils.isBlank(newAddress.getAddress())){
                    return;
                }
                newAddress.setWpId(workplace.getWpId());

                WorkPlaceAddress currentAddress = workPlaceAddressRepository.findById(workplace.getWpId()).orElse(null);
                if( currentAddress != null ){
                    modelMapper.map(newAddress, currentAddress);
                }
                else {
                    currentAddress = modelMapper.map(newAddress, WorkPlaceAddress.class);
                }
                currentAddress.setCreateDate(new Date());
                workPlaceAddressRepository.save(currentAddress);
            }
            catch(Exception e){}
        }
    }


    @Transactional("transactionManager")
    public void checkAndSaveMonitorConfig(WorkPlace workplace, WorkPlaceMonitorConfig monitorConfig){
        monitorConfig.prePersist();
        if( monitorConfig.getSupportBle() == EnableCode.ENABLED.getCode()){
            if( StringUtils.isBlank(workplace.getViewMapFileName())){
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "BLE 모니터링 지원은 조감도가 필수입니다.");
            }
        }
        if( monitorConfig.getSupportGps() == EnableCode.ENABLED.getCode()){
            if( gpsCheckPolicyInfoRepository.countByWpId(workplace.getWpId()) < 1 ){
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "GPS 모니터링 지원은 GPS 정책이 필수입니다.");
            }
        }

        if( workplace.getActivationGeofenceLatitude() != null && workplace.getActivationGeofenceLongitude() != null  ){
            WeatherGridXY grid = GpsGridUtils
                .getWeatherGrid(workplace.getActivationGeofenceLatitude(), workplace.getActivationGeofenceLongitude() );
            monitorConfig.setNx(grid.getNx());
            monitorConfig.setNy(grid.getNy());
        }
        else {
            GpsCheckPolicyInfo gpsPolicy = gpsCheckPolicyInfoRepository.findByWpId(workplace.getWpId());
            if( gpsPolicy != null ){
                WeatherGridXY grid = GpsGridUtils.getWeatherGrid(gpsPolicy.getGpsCenterLat(), gpsPolicy.getGpsCenterLong() );
                monitorConfig.setNx(grid.getNx());
                monitorConfig.setNy(grid.getNy());
            }
        }
        workPlaceMonitorConfigRepository.save(monitorConfig);
    }

    @Transactional("transactionManager")
    public void createDefaultWorkplaceCoop(WorkPlace workplace, G5Member defaultCoop){
        WorkPlaceCoop coopOp = workPlaceCoopRepository.findByWpIdAndCoopMbId(workplace.getWpId(), workplace.getDefaultCoopMbId()).orElse(null);
        if( coopOp != null  ){
            coopOp.setCcId(workplace.getCcId());
        }
        else {
            Optional<WorkSection> wsOp = workSectionRepository.findById(defaultCoop.getWorkSectionA());

            coopOp = WorkPlaceCoop.builder()
                .wpcId(GenerateIdUtils.getUuidKey())
                .wpId(workplace.getWpId())
                .wpName(workplace.getWpName())
                .coopMbId(workplace.getDefaultCoopMbId())
                .coopMbName(defaultCoop.getName())
                .ccId(workplace.getCcId())
                .workSectionA(defaultCoop.getWorkSectionA())
                .wpcWork(wsOp.isPresent() ? wsOp.get().getSectionName() : "")
                .build();
        }
        workPlaceCoopRepository.save(coopOp);
    }

    @Transactional("transactionManager")
    public void createDefaultConstructionSiteCoop(WorkPlace workplace, SecurityUser loginUser){

        ConstructionSite con = constructionSiteRepository.findById(ConstructionSiteKey.builder()
            .wpId(workplace.getWpId())
            .ccId(workplace.getCcId())
            .build()).orElse(null);
        if( con == null ){
            con = new ConstructionSite();
            con.setWpId(workplace.getWpId());
            con.setCcId(workplace.getCcId());
            con.setCreateDate(new Date());
            con.setCreator(loginUser.getMbId());

            constructionSiteRepository.save(con);
        }

        ConstructionSiteManager csManager = constructionSiteManagerRepository.findById(ConstructionSiteManagerKey.builder()
            .wpId(workplace.getWpId())
            .mbId(workplace.getManMbId())
            .build()).orElse(null);
        if( csManager == null ){
            csManager = new ConstructionSiteManager();
            csManager.setWpId(workplace.getWpId());
            csManager.setCcId(workplace.getCcId());
            csManager.setMbId(workplace.getManMbId());
            csManager.setCreateDate(new Date());
            csManager.setCreator(loginUser.getMbId());

            constructionSiteManagerRepository.save(csManager);
        }
        else {
            csManager.setCcId(workplace.getCcId());
            constructionSiteManagerRepository.save(csManager);
        }
    }

    @Transactional("transactionManager")
    public int delete(String wpId) {
        alarmCheckPolicyInfoRepository.deleteAllByWpId(wpId);
        deviceCheckPolicyInfoRepository.deleteAllByWpId(wpId);
        gpsCheckPolicyInfoRepository.deleteAllByWpId(wpId);
        gpsDeviceInfoRepository.deleteAllByWpId(wpId);
        gpsLocationRepository.deleteAllByWpId(wpId);
        sensorPolicyInfoRepository.deleteAllByWpId(wpId);
        sensorDistrictRepository.deleteAllByWpId(wpId);
        workDeviceInfoRepository.deleteAllByWpId(wpId);
        workEquipmentInfoRepository.deleteAllByWpId(wpId);
        workPlaceWorkerRepository.deleteAllByWpId(wpId);
        workPlaceCoopRepository.deleteAllByWpId(wpId);
        workGeofenceInfoRepository.deleteAllByWpId(wpId);
        recruitRepository.deleteAllByWpId(wpId);
        recruitApplyRepository.deleteAllByWpId(wpId);
        buildingRepository.deleteAllByWpId(wpId);
        workPlaceMonitorConfigRepository.deleteById(wpId);
        Optional<WorkPlace> workplaceOp = workPlaceRepository.findById(wpId);
        if( workplaceOp.isPresent() ){
            WorkPlace workplace = workplaceOp.get();
            workPlaceRepository.deleteById(wpId);

            if( StringUtils.isNotEmpty(workplace.getViewMapFileName())
                && StringUtils.isNotEmpty(workplace.getViewMapFilePath())
                && workplace.getViewMapFileLocation() != null ){
                Storage storage = Storage.get(workplace.getViewMapFileLocation());
                if( storage != null ){
                    File viewMapFile = new File(storage.getPath()+workplace.getViewMapFilePath()+workplace.getViewMapFileName());
                    if( viewMapFile.exists() && viewMapFile.isFile() ){
                        viewMapFile.delete();
                    }
                }

            }
        }

        return 1;
    }


    @Transactional("transactionManager")
    public int deleteMultiple(List<String> wpIds) {
        int deleteCnt = 0;
        for (String wpId : wpIds) {
            deleteCnt += delete(wpId);
        }
        return deleteCnt;
    }
}
