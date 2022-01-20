package kr.co.hulan.aas.mvc.api.openapi.service;

import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.infra.airkorea.station.AirkoreaStationClient;
import kr.co.hulan.aas.infra.airkorea.station.dto.NearStationBody;
import kr.co.hulan.aas.infra.airkorea.station.dto.NearStationItem;
import kr.co.hulan.aas.infra.airkorea.station.dto.NearStationRequest;
import kr.co.hulan.aas.infra.airkorea.station.dto.NearStationResponseMessage;
import kr.co.hulan.aas.infra.airkorea.station.dto.TmCoordinateBody;
import kr.co.hulan.aas.infra.airkorea.station.dto.TmCoordinateItem;
import kr.co.hulan.aas.infra.airkorea.station.dto.TmCoordinateRequest;
import kr.co.hulan.aas.infra.airkorea.station.dto.TmCoordinateResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AirkoreaService {

  @Autowired
  private AirkoreaStationClient stationClient;

  public List<NearStationItem> findNearStationList(String sigunguName, String dongName){
    String umdName = sigunguName + " "+dongName;
    TmCoordinateResponseMessage response = stationClient.getTMStdrCrdnt(TmCoordinateRequest.builder()
        .numOfRows(10)
        .pageNo(1)
        .umdName(umdName)
        .build());
    if( response != null && response.isSuccess()){
      TmCoordinateBody body = response.getResponseBody();
      if( body.getItems() != null && body.getItems().size() > 0 ){
        TmCoordinateItem firstItem = body.getItems().get(0);

        NearStationResponseMessage nearResponse = stationClient.getCtprvnRltmMesureDnsty(NearStationRequest.builder()
            .tmX(firstItem.getDecimalTmX())
            .tmY(firstItem.getDecimalTmY())
            .build());
        if( nearResponse != null && nearResponse.isSuccess()){
          NearStationBody nbody = nearResponse.getResponseBody();
          if( nbody.getItems() != null && nbody.getItems().size() > 0 ){
            return nbody.getItems();
          }
        }
        throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), "근접 측정소 조회에 실패하였습니다.");
      }
    }
    throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), "주소 좌표 조회에 실패하였습니다.");

  }
}
