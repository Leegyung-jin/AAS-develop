package kr.co.hulan.aas.mvc.api.device.service;

import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.EnableCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.device.controller.request.CreateWorkEquipmentInfoRequest;
import kr.co.hulan.aas.mvc.api.device.controller.request.ListWorkEquipmentInfoRequest;
import kr.co.hulan.aas.mvc.api.device.controller.request.UpdateWorkEquipmentInfoRequest;
import kr.co.hulan.aas.mvc.api.device.controller.response.ListWorkEquipmentInfoResponse;
import kr.co.hulan.aas.mvc.dao.mapper.WorkEquipmentInfoDao;
import kr.co.hulan.aas.mvc.dao.repository.*;
import kr.co.hulan.aas.mvc.model.domain.WorkDeviceInfo;
import kr.co.hulan.aas.mvc.model.domain.WorkEquipmentInfo;
import kr.co.hulan.aas.mvc.model.domain.WorkPlace;
import kr.co.hulan.aas.mvc.model.dto.WorkDeviceInfoDto;
import kr.co.hulan.aas.mvc.model.dto.WorkEquipmentInfoDto;
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
public class WorkEquipmentInfoService {

    @Autowired
    private WorkEquipmentInfoDao workEquipmentInfoDao;

    @Autowired
    private WorkEquipmentInfoRepository workEquipmentInfoRepository;

    @Autowired
    private WorkDeviceInfoRepository workDeviceInfoRepository;

    @Autowired
    private WorkPlaceRepository workPlaceRepository;

    @Autowired
    private WorkPlaceCoopRepository workPlaceCoopRepository;

    @Autowired
    private WorkPlaceWorkerRepository workPlaceWorkerRepository;

    public ListWorkEquipmentInfoResponse findListPage(ListWorkEquipmentInfoRequest request) {
        return new ListWorkEquipmentInfoResponse(
                findListCountByCondition(request.getConditionMap()),
                findListByCondition(request.getConditionMap())
        );
    }

    public List<WorkEquipmentInfoDto> findListByCondition(Map<String, Object> conditionMap) {
        return workEquipmentInfoDao.findListByCondition(
            AuthenticationHelper.addAdditionalConditionByLevel(conditionMap)
        );
    }

    private Long findListCountByCondition(Map<String, Object> conditionMap) {
        return workEquipmentInfoDao.findListCountByCondition(
            AuthenticationHelper.addAdditionalConditionByLevel(conditionMap)
        );
    }

    public WorkEquipmentInfoDto findById(int idx) {
        WorkEquipmentInfoDto WorkEquipmentInfoDto = workEquipmentInfoDao.findById(idx);
        if (WorkEquipmentInfoDto != null) {
            return WorkEquipmentInfoDto;
        } else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }
    }


    @Transactional("transactionManager")
    public int create(CreateWorkEquipmentInfoRequest request){
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        Optional<WorkPlace> workPlaceOp = workPlaceRepository.findById(request.getWpId());
        if( !workPlaceOp.isPresent()){
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "???????????? ?????? ???????????????.");
        }

        if( workPlaceCoopRepository.countByWpIdAndCoopMbId(request.getWpId(), request.getCoopMbId()) < 1 ){
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "?????? ????????? ?????? ???????????? ????????????.");
        }

        if(StringUtils.isNotEmpty( request.getMbId() ) || StringUtils.isNotEmpty( request.getDeviceId() )){
            if(StringUtils.isNotEmpty( request.getMbId() )){
                if( workPlaceWorkerRepository.countByWpIdAndCoopMbIdAndWorkerMbId( request.getWpId(), request.getCoopMbId(), request.getMbId()) < 1 ){
                    throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "?????? ????????? ???????????? ????????? ???????????? ????????????.");
                }

                if( workEquipmentInfoRepository.countByWpIdAndMbId( request.getWpId(), request.getMbId()) > 0 ){
                    throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "?????? ????????? ????????? ??????????????????.");
                }
            }

            if( StringUtils.isNotEmpty( request.getDeviceId() )){
                if( workDeviceInfoRepository.countByWpIdAndDeviceId(request.getWpId(), request.getDeviceId()) < 1 ){
                    throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "?????? ????????? ???????????? ?????? ?????????????????????.");
                }

                if( workEquipmentInfoRepository.countByWpIdAndDeviceId( request.getWpId(), request.getDeviceId() ) > 0 ){
                    throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "?????? ????????? ????????? ?????????????????????.");
                }
            }
        }
        else {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "????????? ?????? ??????????????? ??????????????? ?????????.");
        }

        /*
        if( workEquipmentInfoRepository.countByEquipmentNo( request.getEquipmentNo() ) > 0 ){
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "?????? ????????? ??????????????? ???????????????.");
        }
         */

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        WorkEquipmentInfo saveDto = modelMapper.map(request, WorkEquipmentInfo.class);

        EnableCode opeType = EnableCode.get(saveDto.getOpeType());
        if( opeType == EnableCode.DISABLED ){
            saveDto.setOpeStart(null);
            saveDto.setOpeEnd(null);
        }
        saveDto.setUpdDatetime(new Date());
        saveDto.setUpdater( loginUser.getUsername() );
        workEquipmentInfoRepository.save(saveDto);
        return saveDto.getIdx();
    }

    @Transactional("transactionManager")
    public void update(UpdateWorkEquipmentInfoRequest request, int idx){
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        if ( request.getIdx() != idx ) {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "???????????? ???????????? ????????????.");
        }


        /*
        if( workEquipmentInfoRepository.countByEquipmentNoAndIdxNot( request.getEquipmentNo(), idx ) > 0 ){
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "?????? ????????? ??????????????? ???????????????.");
        }
         */

        Optional<WorkEquipmentInfo>  WorkEquipmentInfoOp = workEquipmentInfoRepository.findById(idx);
        if( WorkEquipmentInfoOp.isPresent() ){
            WorkEquipmentInfo currentInfo = WorkEquipmentInfoOp.get();

            if( !StringUtils.equals(currentInfo.getWpId(), request.getWpId())){
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "????????? ????????? ??? ????????????.");
            }

            Optional<WorkPlace> workPlaceOp = workPlaceRepository.findById(request.getWpId());
            if( !workPlaceOp.isPresent()){
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "???????????? ?????? ???????????????.");
            }

            if( workPlaceCoopRepository.countByWpIdAndCoopMbId(request.getWpId(), request.getCoopMbId()) < 1 ){
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "?????? ????????? ?????? ???????????? ????????????.");
            }

            if(StringUtils.isNotEmpty( request.getMbId() ) || StringUtils.isNotEmpty( request.getDeviceId() )){
                if(StringUtils.isNotEmpty( request.getMbId() )){
                    if( workPlaceWorkerRepository.countByWpIdAndCoopMbIdAndWorkerMbId( request.getWpId(), request.getCoopMbId(), request.getMbId()) < 1 ){
                        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "?????? ????????? ???????????? ????????? ???????????? ????????????.");
                    }

                    if( workEquipmentInfoRepository.countByWpIdAndMbIdAndIdxNot( request.getWpId(), request.getMbId(), request.getIdx() ) > 0 ){
                        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "?????? ????????? ????????? ??????????????????.");
                    }
                }

                if( StringUtils.isNotEmpty( request.getDeviceId() )){
                    if( workDeviceInfoRepository.countByWpIdAndDeviceId(request.getWpId(), request.getDeviceId()) < 1 ){
                        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "?????? ????????? ???????????? ?????? ?????????????????????.");
                    }

                    if( workEquipmentInfoRepository.countByWpIdAndDeviceIdAndIdxNot( request.getWpId(), request.getDeviceId(), idx ) > 0 ){
                        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "?????? ????????? ????????? ?????????????????????.");
                    }

                }
            }
            else {
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "????????? ?????? ??????????????? ??????????????? ?????????.");
            }


            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            modelMapper.map(request, currentInfo);

            EnableCode opeType = EnableCode.get(currentInfo.getOpeType());
            if( opeType == EnableCode.DISABLED ){
                currentInfo.setOpeStart(null);
                currentInfo.setOpeEnd(null);
            }
            currentInfo.setUpdDatetime(new Date());
            currentInfo.setUpdater( loginUser.getUsername() );

            workEquipmentInfoRepository.save(currentInfo);
        }
        else {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "???????????? ?????? ?????? ???????????????.");
        }
    }

    @Transactional("transactionManager")
    public void delete(int idx){
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }
        workEquipmentInfoRepository.deleteById(idx);
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
