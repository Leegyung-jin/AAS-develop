package kr.co.hulan.aas.mvc.api.bookmark.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.EnableCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.bookmark.controller.request.BleBookmarkOnOffRequest;
import kr.co.hulan.aas.mvc.api.bookmark.controller.request.GpsBookmarkOnOffRequest;
import kr.co.hulan.aas.mvc.api.bookmark.service.BookmarkService;
import kr.co.hulan.aas.mvc.api.bookmark.vo.BleBookmarkVo;
import kr.co.hulan.aas.mvc.api.bookmark.vo.GpsBookmarkVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookmark")
@Api(tags = "현장 북마크 정보 관리")
public class BookmarkController {

  @Autowired
  private BookmarkService bookmarkService;

  @ApiOperation(value = "BLE 북마크 리스트", notes = "BLE 북마크 리스트 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping( value="ble/{wpId}", produces = MediaType.APPLICATION_JSON_VALUE )
  public DefaultHttpRes<List<BleBookmarkVo>> bleBookmarkList(
      @PathVariable(name = "wpId") String wpId
  ) {
    return new DefaultHttpRes<List<BleBookmarkVo>>(BaseCode.SUCCESS, bookmarkService.findBleBookmarkList(wpId));
  }

  @ApiOperation(value = "BLE 북마크 ON ", notes = "BLE 북마크 ON 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "buildingNo", value = "건물 번호", required = true, dataType = "long", paramType = "path"),
      @ApiImplicitParam(name = "floor", value = "층 번호", required = true, dataType = "int", paramType = "path")
  })
  @PostMapping( value="ble/{wpId}/mark/{buildingNo}_{floor}", produces = MediaType.APPLICATION_JSON_VALUE )
  public DefaultHttpRes bleBookmarkOn(
      @PathVariable(name = "wpId") String wpId,
      @PathVariable(name = "buildingNo") long buildingNo,
      @PathVariable(name = "floor") int floor
  ) {
    bookmarkService.updateBleBookmarkOnOff(wpId, buildingNo, floor, true);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  @ApiOperation(value = "BLE 북마크 OFF ", notes = "BLE 북마크 OFF 제공한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "buildingNo", value = "건물 번호", required = true, dataType = "long", paramType = "path"),
      @ApiImplicitParam(name = "floor", value = "층 번호", required = true, dataType = "int", paramType = "path")
  })
  @PostMapping( value="ble/{wpId}/unmark/{buildingNo}_{floor}", produces = MediaType.APPLICATION_JSON_VALUE )
  public DefaultHttpRes bleBookmarkOff(
      @PathVariable(name = "wpId") String wpId,
      @PathVariable(name = "buildingNo") long buildingNo,
      @PathVariable(name = "floor") int floor
  ) {
    bookmarkService.updateBleBookmarkOnOff(wpId, buildingNo, floor, false);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }


  @ApiOperation(value = "GPS(fence) 북마크 리스트", notes = "GPS(fence) 북마크 리스트 제공한다.", consumes="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping( value="gps/{wpId}", produces = MediaType.APPLICATION_JSON_VALUE )
  public DefaultHttpRes<List<GpsBookmarkVo>> gpsBookmarkList(
      @PathVariable(name = "wpId") String wpId
  ) {
    return new DefaultHttpRes<List<GpsBookmarkVo>>(BaseCode.SUCCESS, bookmarkService.findGpsBookmarkList(wpId));
  }

  @ApiOperation(value = "GPS(fence) 북마크 ON ", notes = "GPS(fence) 북마크 ON 제공한다.", consumes="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "wpSeq", value = "fence 순번", required = true, dataType = "int", paramType = "path")
  })
  @PostMapping( value="gps/{wpId}/mark/{wpSeq}", produces = MediaType.APPLICATION_JSON_VALUE )
  public DefaultHttpRes gpsBookmarkOn(
      @PathVariable(name = "wpId") String wpId,
      @PathVariable(name = "wpSeq") int wpSeq
  ) {

    bookmarkService.updateGpsBookmarkOnOff(wpId, wpSeq, true);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }

  @ApiOperation(value = "GPS(fence) 북마크 OFF ", notes = "GPS(fence) 북마크 OFF 제공한다.", consumes="application/json;charset=UTF-8")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wpId", value = "현장 아이디", required = true, dataType = "string", paramType = "path"),
      @ApiImplicitParam(name = "wpSeq", value = "fence 순번", required = true, dataType = "int", paramType = "path")
  })
  @PostMapping( value="gps/{wpId}/unmark/{wpSeq}", produces = MediaType.APPLICATION_JSON_VALUE )
  public DefaultHttpRes gpsBookmarkOff(
      @PathVariable(name = "wpId") String wpId,
      @PathVariable(name = "wpSeq") int wpSeq
  ) {

    bookmarkService.updateGpsBookmarkOnOff(wpId, wpSeq, false);
    return new DefaultHttpRes(BaseCode.SUCCESS);
  }
}
