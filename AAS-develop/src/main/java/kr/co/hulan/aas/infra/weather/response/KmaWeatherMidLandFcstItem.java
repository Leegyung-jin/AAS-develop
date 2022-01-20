package kr.co.hulan.aas.infra.weather.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="KmaWeatherMidLandFcstItem", description="날씨 예보정보 (중기육상예보조회)")
public class KmaWeatherMidLandFcstItem {

  private String regId;
  private String rnSt3Am;
  private String rnSt3Pm;
  private String rnSt4Am;
  private String rnSt4Pm;
  private String rnSt5Am;
  private String rnSt5Pm;
  private String rnSt6Am;
  private String rnSt6Pm;
  private String rnSt7Am;
  private String rnSt7Pm;
  private String rnSt8;
  private String rnSt9;
  private String rnSt10;

  /*
  - 하늘상태 3단계(맑음, 구름많음, 흐림) 와 현상에 따른 비, 눈, 비/눈, 소나기를 종합하여 사용
  - 맑음
  - 구름많음, 구름많고 비, 구름많고 눈, 구름많고 비/눈, 구름많고 소나기
  - 흐림, 흐리고 비, 흐리고 눈, 흐리고 비/눈, 흐리고 소나기
  - 소나기
  */
  private String wf3Am;
  private String wf3Pm;
  private String wf4Am;
  private String wf4Pm;
  private String wf5Am;
  private String wf5Pm;
  private String wf6Am;
  private String wf6Pm;
  private String wf7Am;
  private String wf7Pm;
  private String wf8;
  private String wf9;
  private String wf10;


  public String getRnStAm(int dayAfter){
    switch (dayAfter){
      case 3: return getRnSt3Am();
      case 4: return getRnSt4Am();
      case 5: return getRnSt5Am();
      case 6: return getRnSt6Am();
      case 7: return getRnSt7Am();
      case 8: return getRnSt8();
      case 9: return getRnSt9();
      case 10: return getRnSt10();
      default: return null;
    }
  }

  public String getRnStPm(int dayAfter){
    switch (dayAfter){
      case 3: return getRnSt3Pm();
      case 4: return getRnSt4Pm();
      case 5: return getRnSt5Pm();
      case 6: return getRnSt6Pm();
      case 7: return getRnSt7Pm();
      case 8: return getRnSt8();
      case 9: return getRnSt9();
      case 10: return getRnSt10();
      default: return null;
    }
  }

  public String getWfAm(int dayAfter){
    switch (dayAfter){
      case 3: return getWf3Am();
      case 4: return getWf4Am();
      case 5: return getWf5Am();
      case 6: return getWf6Am();
      case 7: return getWf7Am();
      case 8: return getWf8();
      case 9: return getWf9();
      case 10: return getWf10();
      default: return null;
    }
  }

  public String getWfPm(int dayAfter){
    switch (dayAfter){
      case 3: return getWf3Pm();
      case 4: return getWf4Pm();
      case 5: return getWf5Pm();
      case 6: return getWf6Pm();
      case 7: return getWf7Pm();
      case 8: return getWf8();
      case 9: return getWf9();
      case 10: return getWf10();
      default: return null;
    }
  }


}
