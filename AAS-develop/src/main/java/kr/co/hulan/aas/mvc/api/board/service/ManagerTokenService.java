package kr.co.hulan.aas.mvc.api.board.service;

import kr.co.hulan.aas.mvc.dao.mapper.ManagerTokenDao;
import kr.co.hulan.aas.mvc.model.dto.ManagerTokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ManagerTokenService {

    @Autowired
    private ManagerTokenDao managerTokenDao;

    public List<ManagerTokenDto> findByMbIdAndMtYn(String mbId, int mtYn) {
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("mbId", mbId);
        condition.put("mtYn", mtYn);
        return managerTokenDao.findListByCondition(condition);
    }
}
