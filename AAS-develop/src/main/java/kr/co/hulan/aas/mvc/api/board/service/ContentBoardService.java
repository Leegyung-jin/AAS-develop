package kr.co.hulan.aas.mvc.api.board.service;

import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.board.controller.request.CreateContentBoardRequest;
import kr.co.hulan.aas.mvc.api.board.controller.request.ListContentBoardRequest;
import kr.co.hulan.aas.mvc.api.board.controller.request.UpdateContentBoardRequest;
import kr.co.hulan.aas.mvc.api.board.controller.response.ListContentBoardResponse;
import kr.co.hulan.aas.mvc.dao.mapper.G5ContentDao;
import kr.co.hulan.aas.mvc.dao.repository.G5ContentRepository;
import kr.co.hulan.aas.mvc.model.domain.G5Content;
import kr.co.hulan.aas.mvc.model.dto.G5ContentDto;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ContentBoardService {

    @Autowired
    private G5ContentRepository g5ContentRepository;

    @Autowired
    private G5ContentDao g5ContentDao;

    @Autowired
    @Qualifier("entityManagerFactory")
    EntityManager entityManager;

    public ListContentBoardResponse findListPage(ListContentBoardRequest request) {
        return new ListContentBoardResponse(
                findListCountByCondition(request.getConditionMap()),
                findListByCondition(request.getConditionMap())
        );
    }


    public List<G5ContentDto> findListByCondition(Map<String,Object> conditionMap) {
        return g5ContentDao.findListByCondition(conditionMap);
    }

    private Long findListCountByCondition(Map<String,Object> conditionMap) {
        return g5ContentDao.findListCountByCondition(conditionMap);
    }


    public G5ContentDto findById(String coId){
        G5ContentDto noticeDto = g5ContentDao.findById(coId);
        if ( noticeDto != null ) {
            return noticeDto;
        } else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }
    }


    @Transactional("mybatisTransactionManager")
    public void create(CreateContentBoardRequest request) {
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        G5ContentDto saveDto = modelMapper.map(request, G5ContentDto.class);
        g5ContentDao.create(saveDto);

    }


    @Transactional("mybatisTransactionManager")
    public void update(UpdateContentBoardRequest request, String coId) {
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        if ( !StringUtils.equals(request.getCoId(), coId) ){
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "아이디가 일치하지 않습니다.");
        }

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        G5ContentDto saveDto = modelMapper.map(request, G5ContentDto.class);

        Optional<G5Content> duplicated = g5ContentRepository.findById(coId);
        if (duplicated.isPresent()) {
            g5ContentDao.update(saveDto);
        }
        else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }
    }


    @Transactional("transactionManager")
    public int delete(String coId) {
        g5ContentRepository.deleteById(coId);
        return 1;
    }

    @Transactional("transactionManager")
    public int deleteMultiple(List<String> coIds) {
        int deleteCnt = 0;
        for (String coId : coIds) {
            deleteCnt += delete(coId);
        }
        return deleteCnt;
    }
}
