package kr.co.hulan.aas.mvc.api.hicc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.hicc.controller.response.HiccAttendanceMonthStatResponse;
import kr.co.hulan.aas.mvc.api.hicc.service.HiccAttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hicc/v1/attendance")
@Api(tags = "Hulan Integrated Control Center 출역 현황 API")
public class HiccAttendanceController {

  @Autowired
  private HiccAttendanceService attendanceService;

  @ApiOperation(value = "월간 출역 현황 조회", notes = "월간 출역 현황 제공한다.", produces="application/json;charset=UTF-8")
  @GetMapping(value="/month", produces="application/json;charset=UTF-8")
  public DefaultHttpRes<HiccAttendanceMonthStatResponse> attendanceMonthStat(){
    return new DefaultHttpRes<HiccAttendanceMonthStatResponse>(
        BaseCode.SUCCESS, HiccAttendanceMonthStatResponse.builder()
        .list(attendanceService.findAttendanceMonthStatList())
        .build());
  }

}
