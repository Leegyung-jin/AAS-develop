package kr.co.hulan.aas.mvc.api.main.service;

import kr.co.hulan.aas.mvc.api.main.controller.response.AdminMainResponse;
import kr.co.hulan.aas.mvc.api.member.controller.request.ListWorkerRequest;
import kr.co.hulan.aas.mvc.api.member.controller.response.ListWorkerResponse;
import kr.co.hulan.aas.mvc.api.member.service.WorkerService;
import kr.co.hulan.aas.mvc.dao.mapper.G5BoardNewDao;
import kr.co.hulan.aas.mvc.model.dto.G5BoardNewDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MainService {

    @Autowired
    private WorkerService workerService;

    @Autowired
    private G5BoardNewDao g5BoardNewDao;

    @Autowired
    ModelMapper modelMapper;


    public AdminMainResponse findMainData() {
        return new AdminMainResponse(
                findRecentRegistWorkerList(),
                findRecentBoardList()
        );
    }

    public ListWorkerResponse findRecentRegistWorkerList() {
        ListWorkerRequest req = new ListWorkerRequest();
        req.setPage(1);
        req.setPageSize(10);
        return workerService.findWorkerListPage(req);

    }

    public List<G5BoardNewDto> findRecentBoardList() {
        return g5BoardNewDao.findList();
    }
}
