package kr.co.hulan.aas.mvc.api.member.service;

import kr.co.hulan.aas.mvc.dao.mapper.HulanSequenceDao;
import kr.co.hulan.aas.mvc.model.dto.HulanSequenceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MbKeyGenerateService {

    @Autowired
    HulanSequenceDao hulanSequenceDao;

    @Transactional("mybatisTransactionManager")
    public String generateKey(){
        HulanSequenceDto dto = new HulanSequenceDto();
        dto.setSeqName("mb_key");
        hulanSequenceDao.update(dto);
        long nextVal = dto.getNextVal();
        dto = hulanSequenceDao.findById("mb_key");
        dto.setNextVal(nextVal);
        return dto.generateMemberKey();
    }
}
