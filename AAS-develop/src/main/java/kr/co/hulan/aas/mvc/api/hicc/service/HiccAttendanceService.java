package kr.co.hulan.aas.mvc.api.hicc.service;

import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.mvc.api.hicc.vo.AttendanceMonthStatVo;
import kr.co.hulan.aas.mvc.dao.mapper.AttendanceBookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HiccAttendanceService {

  @Autowired
  private AttendanceBookDao attendanceBookDao;

  public List<AttendanceMonthStatVo> findAttendanceMonthStatList(){
    Map<String,Object> condition = AuthenticationHelper.addAdditionalConditionByLevel();
    return attendanceBookDao.findAttendanceMonthStatList(condition);
  }
}
