package kr.co.hulan.aas.mvc.dao.mapper;

import kr.co.hulan.aas.mvc.model.dto.HulanSequenceDto;
import org.springframework.stereotype.Repository;

@Repository
public interface HulanSequenceDao {

    public HulanSequenceDto findById(String seqName);
    public int update(HulanSequenceDto dto);
}
