package kr.co.hulan.aas.mvc.dao.mapper;

import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.mvc.api.bookmark.vo.BleBookmarkVo;
import kr.co.hulan.aas.mvc.api.bookmark.vo.GpsBookmarkVo;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkDao {

  List<BleBookmarkVo> findBleBookmarkList(Map<String,Object> condition);
  List<GpsBookmarkVo> findGpsBookmarkList(Map<String,Object> condition);

}
