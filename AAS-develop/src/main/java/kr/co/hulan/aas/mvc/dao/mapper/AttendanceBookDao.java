package kr.co.hulan.aas.mvc.dao.mapper;

import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.mvc.api.hicc.vo.AttendanceDayStatVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.AttendanceMonthStatVo;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceBookDao {

  List<AttendanceMonthStatVo> findAttendanceMonthStatList(Map<String,Object> condition);

  List<AttendanceDayStatVo> findDailyAttendanceStat(Map<String,Object> condition);
}
