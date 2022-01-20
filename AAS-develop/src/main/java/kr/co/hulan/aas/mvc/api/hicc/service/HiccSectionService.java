package kr.co.hulan.aas.mvc.api.hicc.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import kr.co.hulan.aas.mvc.api.hicc.vo.HiccSectionVo;
import kr.co.hulan.aas.mvc.dao.mapper.WorkSectionDao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HiccSectionService {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private WorkSectionDao workSectionDao;

  public List<HiccSectionVo>  findSectionList(Map<String,Object> condition){
    return workSectionDao.findListByCondition(condition).stream().map(section -> { return
        modelMapper.map( section, HiccSectionVo.class);
    } ).collect(Collectors.toList());
  }
}
