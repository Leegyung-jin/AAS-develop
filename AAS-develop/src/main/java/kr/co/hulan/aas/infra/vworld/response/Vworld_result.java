package kr.co.hulan.aas.infra.vworld.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by hulan on 2020-04-27.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vworld_result {
  private String crs;
  private Vworld_point point;

  public String getCrs() {
    return crs;
  }

  public void setCrs(String crs) {
    this.crs = crs;
  }

  public Vworld_point getPoint() {
    return point;
  }

  public void setPoint(Vworld_point point) {
    this.point = point;
  }

  public boolean hasValidPoint(){
    return point != null
        && StringUtils.isNotEmpty(point.getX())
        && StringUtils.isNotEmpty(point.getY())
        ;
  }
}
