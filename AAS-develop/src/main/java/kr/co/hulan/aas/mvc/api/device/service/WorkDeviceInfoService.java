package kr.co.hulan.aas.mvc.api.device.service;

import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.DeviceType;
import kr.co.hulan.aas.common.code.EnableCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.device.controller.request.CreateWorkDeviceInfoRequest;
import kr.co.hulan.aas.mvc.api.device.controller.request.ListWorkDeviceInfoRequest;
import kr.co.hulan.aas.mvc.api.device.controller.request.UpdateWorkDeviceInfoRequest;
import kr.co.hulan.aas.mvc.api.device.controller.response.ListWorkDeviceInfoResponse;
import kr.co.hulan.aas.mvc.dao.mapper.WorkDeviceInfoDao;
import kr.co.hulan.aas.mvc.dao.repository.WorkDeviceInfoRepository;
import kr.co.hulan.aas.mvc.dao.repository.WorkEquipmentInfoRepository;
import kr.co.hulan.aas.mvc.dao.repository.WorkPlaceRepository;
import kr.co.hulan.aas.mvc.model.domain.WorkDeviceInfo;
import kr.co.hulan.aas.mvc.model.domain.WorkEquipmentInfo;
import kr.co.hulan.aas.mvc.model.domain.WorkPlace;
import kr.co.hulan.aas.mvc.model.dto.WorkDeviceInfoDto;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class WorkDeviceInfoService {

    @Autowired
    private WorkDeviceInfoDao workDeviceInfoDao;

    @Autowired
    private WorkDeviceInfoRepository workDeviceInfoRepository;

    @Autowired
    private WorkEquipmentInfoRepository workEquipmentInfoRepository;

    @Autowired
    private WorkPlaceRepository workPlaceRepository;

    public ListWorkDeviceInfoResponse findListPage(ListWorkDeviceInfoRequest request) {
        return new ListWorkDeviceInfoResponse(
                findListCountByCondition(request.getConditionMap()),
                findListByCondition(request.getConditionMap())
        );
    }


    public List<WorkDeviceInfoDto> findListByCondition(Map<String, Object> conditionMap) {
        return workDeviceInfoDao.findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }

    private Long findListCountByCondition(Map<String, Object> conditionMap) {
        return workDeviceInfoDao.findListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }

    public WorkDeviceInfoDto findById(int idx) {
        WorkDeviceInfoDto WorkDeviceInfoDto = workDeviceInfoDao.findById(idx);
        if (WorkDeviceInfoDto != null) {
            return WorkDeviceInfoDto;
        } else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }
    }


    @Transactional("transactionManager")
    public int create(CreateWorkDeviceInfoRequest request){
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        Optional<WorkPlace> workPlaceOp = workPlaceRepository.findById(request.getWpId());
        if( !workPlaceOp.isPresent()){
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "???????????? ?????? ???????????????.");
        }

        if( workDeviceInfoRepository.countByWpIdAndDeviceId( request.getWpId(), request.getDeviceId() ) > 0 ){
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "?????? ?????? ????????? ????????? ???????????? ??????????????????.");
        }

        if( DeviceType.get(request.getDeviceType()) == null ){
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "?????????????????? ???????????? ????????????.");
        }

        if(EnableCode.get(request.getUseSensoryTemp()) == EnableCode.ENABLED){
            if( request.getRefDevice() == null ){
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "???????????? ????????? ????????? ????????? ??????????????? ?????????.");
            }
            WorkDeviceInfo refDevice = workDeviceInfoRepository.findById(request.getRefDevice()).orElse(null);
            if( refDevice != null ){
                if( DeviceType.get(refDevice.getDeviceType()) != DeviceType.TEMPERATURE_MOISTURE_SENSOR ){
                    throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "????????? ????????? ????????? ????????? ????????????.");
                }
                else if( !StringUtils.equals(refDevice.getWpId(), request.getWpId())){
                    throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "?????? ????????? ????????? ???????????????.");
                }
            }
            else {
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "???????????? ?????? ????????? ???????????????.");
            }
        }

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        WorkDeviceInfo saveDto = modelMapper.map(request, WorkDeviceInfo.class);
        saveDto.setUpdateDatetime(new Date());
        saveDto.setUpdater( loginUser.getUsername() );
        workDeviceInfoRepository.save(saveDto);
        return saveDto.getIdx();
    }

    @Transactional("transactionManager")
    public void update(UpdateWorkDeviceInfoRequest request, int idx){
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        if ( request.getIdx() != idx ) {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "???????????? ???????????? ????????????.");
        }

        Optional<WorkPlace> workPlaceOp = workPlaceRepository.findById(request.getWpId());
        if( !workPlaceOp.isPresent()){
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "???????????? ?????? ???????????????.");
        }

        if( workDeviceInfoRepository.countByWpIdAndDeviceIdAndIdxNot( request.getWpId(), request.getDeviceId(), idx ) > 0 ){
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "?????? ?????? ????????? ????????? ???????????? ??????????????????.");
        }

        if( DeviceType.get(request.getDeviceType()) == null ){
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "?????????????????? ???????????? ????????????.");
        }

        if(EnableCode.get(request.getUseSensoryTemp()) == EnableCode.ENABLED){
            if( request.getRefDevice() == null ){
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "???????????? ????????? ????????? ????????? ??????????????? ?????????.");
            }
            WorkDeviceInfo refDevice = workDeviceInfoRepository.findById(request.getRefDevice()).orElse(null);
            if( refDevice != null ){
                if( DeviceType.get(refDevice.getDeviceType()) != DeviceType.TEMPERATURE_MOISTURE_SENSOR ){
                    throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "????????? ????????? ????????? ????????? ????????????.");
                }
                else if( !StringUtils.equals(refDevice.getWpId(), request.getWpId())){
                    throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "?????? ????????? ????????? ???????????????.");
                }
            }
            else {
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "???????????? ?????? ????????? ???????????????.");
            }
        }

        Optional<WorkDeviceInfo>  workDeviceInfoOp = workDeviceInfoRepository.findById(idx);
        if( workDeviceInfoOp.isPresent() ){
            WorkDeviceInfo currentInfo = workDeviceInfoOp.get();

            if( !StringUtils.equals(currentInfo.getWpId(), request.getWpId())){
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "????????? ????????? ??? ????????????.");
                /*
                Optional<WorkEquipmentInfo> equipmentInfoOp = workEquipmentInfoRepository.findWorkEquipmentInfoByWpIdAndDeviceId(currentInfo.getWpId(), currentInfo.getDeviceId());
                if( equipmentInfoOp.isPresent() ){
                    WorkEquipmentInfo equipmentInfo = equipmentInfoOp.get();
                    if( StringUtils.isEmpty( equipmentInfo.getMbId() ) ){
                        workEquipmentInfoRepository.deleteById(equipmentInfo.getIdx());
                    }
                    else {
                        equipmentInfo.setDeviceId(null);
                        workEquipmentInfoRepository.save(equipmentInfo);
                    }
                }
                 */
            }
            else if( !StringUtils.equals(currentInfo.getDeviceId(), request.getDeviceId())){
                Optional<WorkEquipmentInfo> equipmentInfoOp = workEquipmentInfoRepository.findWorkEquipmentInfoByWpIdAndDeviceId(currentInfo.getWpId(), currentInfo.getDeviceId());
                if( equipmentInfoOp.isPresent() ){
                    WorkEquipmentInfo equipmentInfo = equipmentInfoOp.get();
                    equipmentInfo.setDeviceId(request.getDeviceId());
                    workEquipmentInfoRepository.save(equipmentInfo);
                }
            }

            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            modelMapper.map(request, currentInfo);

            currentInfo.setUpdateDatetime(new Date());
            currentInfo.setUpdater( loginUser.getUsername() );

            workDeviceInfoRepository.save(currentInfo);
        }
        else {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "???????????? ?????? ?????? ?????????????????????.");
        }
    }

    @Transactional("transactionManager")
    public void delete(int idx){
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }
        Optional<WorkDeviceInfo>  workDeviceInfoOp = workDeviceInfoRepository.findById(idx);
        if( workDeviceInfoOp.isPresent() ){
            WorkDeviceInfo deletedDevice = workDeviceInfoOp.get();
            Optional<WorkEquipmentInfo> equipmentInfoOp = workEquipmentInfoRepository.findWorkEquipmentInfoByWpIdAndDeviceId(deletedDevice.getWpId(), deletedDevice.getDeviceId());
            if( equipmentInfoOp.isPresent() ){
                WorkEquipmentInfo equipmentInfo = equipmentInfoOp.get();
                if( StringUtils.isEmpty( equipmentInfo.getMbId() ) ){
                    workEquipmentInfoRepository.deleteById(equipmentInfo.getIdx());
                }
                else {
                    equipmentInfo.setDeviceId(null);
                    workEquipmentInfoRepository.save(equipmentInfo);
                }

            }
            workDeviceInfoRepository.deleteById(idx);
        }
    }


    @Transactional("transactionManager")
    public void deleteMultiple(List<Integer> idxs ) {
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }
        for (Integer idx : idxs) {
            delete(idx);
        }
    }
}
