package kr.co.hulan.aas.mvc.api.monitor4_2.service;

import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.mvc.api.hicc.controller.request.HiccImosNoticePagingRequest;
import kr.co.hulan.aas.mvc.api.hicc.vo.ImosNoticeVo;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.request.ImosNoticePagingRequest;
import kr.co.hulan.aas.mvc.dao.mapper.ImosNoticeDao;
import kr.co.hulan.aas.mvc.model.dto.ImosNoticeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImosNoticeCompService {

  @Autowired
  private ImosNoticeDao imosNoticeDao;

  public DefaultPageResult<ImosNoticeDto> findListPage(ImosNoticePagingRequest request) {
    return DefaultPageResult.<ImosNoticeDto>builder()
        .pageSize(request.getPageSize())
        .currentPage(request.getPage())
        .totalCount(findListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(request.getConditionMap())))
        .list(findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(request.getConditionMap())))
        .build();
  }

  public List<ImosNoticeDto> findListByCondition(Map<String,Object> conditionMap) {
    return imosNoticeDao.findListByCondition(conditionMap);
  }

  private Long findListCountByCondition(Map<String,Object> conditionMap) {
    return imosNoticeDao.countListByCondition(conditionMap);
  }

  public ImosNoticeVo findDetailInfo(long imntNo){
    ImosNoticeVo dto = imosNoticeDao.findDetailInfo(imntNo);
    if (dto != null ) {
      return dto;
    } else {
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
    }
  }


}
