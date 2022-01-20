package kr.co.hulan.aas.mvc.api.board.service;

import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.board.controller.request.CreateNoticeBoardRequest;
import kr.co.hulan.aas.mvc.api.board.dto.AttachedFileDto;
import kr.co.hulan.aas.mvc.dao.mapper.G5BoardFileDao;
import kr.co.hulan.aas.mvc.dao.repository.G5BoardFileRepository;
import kr.co.hulan.aas.mvc.model.dto.G5BoardFileDto;
import kr.co.hulan.aas.mvc.model.dto.G5WriteNoticeDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AttachedFileService {

    @Autowired
    private G5BoardFileRepository g5BoardFileRepository;

    @Autowired
    private G5BoardFileDao g5BoardFileDao;


    public List<AttachedFileDto> findListByBoardKey(String boTable, int wrId) {
        Map<String,Object> conditionMap = new HashMap<String,Object>();
        conditionMap.put("boTable", boTable);
        conditionMap.put("wrId", wrId);
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return g5BoardFileDao.findListByCondition(conditionMap)
                .stream().map(g5BoardFileDto -> modelMapper.map(g5BoardFileDto, AttachedFileDto.class)).collect(Collectors.toList());
    }

    public Long findListCountByCondition(String boTable, int wrId) {
        Map<String,Object> conditionMap = new HashMap<String,Object>();
        conditionMap.put("boTable", boTable);
        conditionMap.put("wrId", wrId);
        return g5BoardFileDao.findListCountByCondition(conditionMap);
    }

    @Transactional("mybatisTransactionManager")
    public void create(G5BoardFileDto dto) {
        g5BoardFileDao.create(dto);
    }

    @Transactional("mybatisTransactionManager")
    public void deleteBlank(String boTable, int wrId) {
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("boTable", boTable);
        condition.put("wrId", wrId);
        g5BoardFileDao.deleteBlank(condition);
    }



    @Transactional("mybatisTransactionManager")
    public void delete(String boTable, int wrId, int bfNo) {
        G5BoardFileDto dto = new G5BoardFileDto(boTable, wrId, bfNo);
        g5BoardFileDao.delete(dto);


    }
}
