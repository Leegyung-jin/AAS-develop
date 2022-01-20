package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.ImageAnalyticInfo;
import kr.co.hulan.aas.mvc.model.domain.ImageAnalyticInfoKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageAnalyticInfoRepository extends JpaRepository<ImageAnalyticInfo, ImageAnalyticInfoKey>  {

}
