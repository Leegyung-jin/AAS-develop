package kr.co.hulan.aas.mvc.api.member.service;

import kr.co.hulan.aas.mvc.dao.mapper.VisitDao;
import kr.co.hulan.aas.mvc.api.member.dto.VisitDto;
import kr.co.hulan.aas.mvc.api.member.controller.request.ListVisitRequest;
import kr.co.hulan.aas.mvc.api.member.controller.response.ListVisitResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class VisitService {


    @Autowired
    VisitDao visitDao;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    @Qualifier("entityManagerFactory")
    EntityManager entityManager;

    public ListVisitResponse findListPage(ListVisitRequest request) {
        return new ListVisitResponse(
                findListCountByCondition(request),
                findListByCondition(request)
        );
    }


    private List<VisitDto> findListByCondition(ListVisitRequest request) {
        return visitDao.findListByCondition(request.getConditionMap());
    }

    private Long findListCountByCondition(ListVisitRequest request) {
        return visitDao.findListCountByCondition(request.getConditionMap());
    }

}
