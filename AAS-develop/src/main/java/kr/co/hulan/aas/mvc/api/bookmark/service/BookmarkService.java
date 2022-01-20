package kr.co.hulan.aas.mvc.api.bookmark.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.EnableCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.bookmark.controller.request.BleBookmarkOnOffRequest;
import kr.co.hulan.aas.mvc.api.bookmark.controller.request.GpsBookmarkOnOffRequest;
import kr.co.hulan.aas.mvc.api.bookmark.vo.BleBookmarkVo;
import kr.co.hulan.aas.mvc.api.bookmark.vo.GpsBookmarkVo;
import kr.co.hulan.aas.mvc.dao.mapper.BookmarkDao;
import kr.co.hulan.aas.mvc.dao.repository.BookmarkBleRepository;
import kr.co.hulan.aas.mvc.dao.repository.BookmarkGpsRepository;
import kr.co.hulan.aas.mvc.dao.repository.BuildingFloorRepository;
import kr.co.hulan.aas.mvc.dao.repository.WorkGeofenceGroupRepository;
import kr.co.hulan.aas.mvc.model.domain.BookmarkBle;
import kr.co.hulan.aas.mvc.model.domain.BookmarkBleKey;
import kr.co.hulan.aas.mvc.model.domain.BookmarkGps;
import kr.co.hulan.aas.mvc.model.domain.BookmarkGpsKey;
import kr.co.hulan.aas.mvc.model.domain.BuildingFloor;
import kr.co.hulan.aas.mvc.model.domain.BuildingFloorCompositeKey;
import kr.co.hulan.aas.mvc.model.domain.WorkGeofenceGroup;
import kr.co.hulan.aas.mvc.model.domain.WorkGeofenceGroupKey;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookmarkService {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private BookmarkDao bookmarkDao;

  @Autowired
  private BookmarkBleRepository bookmarkBleRepository;

  @Autowired
  private BookmarkGpsRepository bookmarkGpsRepository;

  @Autowired
  private BuildingFloorRepository buildingFloorRepository;

  @Autowired
  private WorkGeofenceGroupRepository workGeofenceGroupRepository;

  public List<BleBookmarkVo> findBleBookmarkList(String wpId){
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser(true);
    Map<String,Object> condition = new HashMap<String,Object>();
    condition.put("wpId", wpId);
    condition.put("mbId", loginUser.getMbId());
    return bookmarkDao.findBleBookmarkList(condition);
  }

  @Transactional("transactionManager")
  public void updateBleBookmarkOnOff(String wpId, long buildingNo, int floor, boolean mark ){
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser(true);

    BuildingFloor buildingFloor = buildingFloorRepository.findById(BuildingFloorCompositeKey.builder()
        .buildingNo(buildingNo)
        .floor(floor)
        .build()).orElse(null);
    if( buildingFloor != null ){
      BookmarkBleKey key = BookmarkBleKey.builder()
          .wpId(wpId)
          .mbId(loginUser.getMbId())
          .buildingNo(buildingNo)
          .floor(floor)
          .build();
      BookmarkBle bookmarkBle = bookmarkBleRepository.findById(key).orElse(null);
      if( bookmarkBle == null && mark ){

        if( bookmarkBleRepository.countByMbIdAndWpId(loginUser.getMbId(), wpId) >= 10 ){
          throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "북마크는 10개까지만 가능합니다.");
        }
        bookmarkBle = new BookmarkBle();
        bookmarkBle.setMbId(loginUser.getMbId());
        bookmarkBle.setWpId(wpId);
        bookmarkBle.setBuildingNo(buildingNo);
        bookmarkBle.setFloor(floor);

        bookmarkBleRepository.save(bookmarkBle);
      }
      else if( bookmarkBle != null && !mark ){
        bookmarkBleRepository.delete(bookmarkBle);
      }

    }
    else {
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "해당 층 정보가 존재하지 않습니다.");
    }
  }


  public List<GpsBookmarkVo> findGpsBookmarkList(String wpId){
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser(true);
    Map<String,Object> condition = new HashMap<String,Object>();
    condition.put("wpId", wpId);
    condition.put("mbId", loginUser.getMbId());
    return bookmarkDao.findGpsBookmarkList(condition);
  }

  @Transactional("transactionManager")
  public void updateGpsBookmarkOnOff(String wpId, int wpSeq, boolean mark){
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser(true);
    WorkGeofenceGroup fence = workGeofenceGroupRepository.findById(WorkGeofenceGroupKey.builder()
        .wpId(wpId)
        .wpSeq(wpSeq)
        .build()).orElse(null);
    if( fence != null ){
      BookmarkGps bookmarkGps = bookmarkGpsRepository.findById(BookmarkGpsKey.builder()
          .wpId(wpId)
          .mbId(loginUser.getMbId())
          .wpSeq(wpSeq)
          .build()).orElse(null);
      if( bookmarkGps == null && mark ){

        if( bookmarkGpsRepository.countByMbIdAndWpId(loginUser.getMbId(), wpId) >= 10 ){
          throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "북마크는 10개까지만 가능합니다.");
        }

        bookmarkGps = new BookmarkGps();
        bookmarkGps.setWpId(wpId);
        bookmarkGps.setWpSeq(wpSeq);
        bookmarkGps.setMbId(loginUser.getMbId());
        bookmarkGpsRepository.save(bookmarkGps);
      }
      else if( bookmarkGps != null && !mark ){
        bookmarkGpsRepository.delete(bookmarkGps);
      }
    }
    else {
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "펜스 정보가 존재하지 않습니다.");
    }
  }

}
