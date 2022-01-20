package kr.co.hulan.aas.infra.vworld.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by hulan on 2020-04-27.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vworld_structure {
  private String level0;
  private String level1;
  private String level2;
  private String level3;
  private String level4L;
  private String level4LC;
  private String level4A;
  private String level4AC;
  private String level5;
  private String detail;

  public String getLevel0() {
    return level0;
  }

  public void setLevel0(String level0) {
    this.level0 = level0;
  }

  public String getLevel1() {
    return level1;
  }

  public void setLevel1(String level1) {
    this.level1 = level1;
  }

  public String getLevel2() {
    return level2;
  }

  public void setLevel2(String level2) {
    this.level2 = level2;
  }

  public String getLevel3() {
    return level3;
  }

  public void setLevel3(String level3) {
    this.level3 = level3;
  }

  public String getLevel4L() {
    return level4L;
  }

  public void setLevel4L(String level4L) {
    this.level4L = level4L;
  }

  public String getLevel4LC() {
    return level4LC;
  }

  public void setLevel4LC(String level4LC) {
    this.level4LC = level4LC;
  }

  public String getLevel4A() {
    return level4A;
  }

  public void setLevel4A(String level4A) {
    this.level4A = level4A;
  }

  public String getLevel4AC() {
    return level4AC;
  }

  public void setLevel4AC(String level4AC) {
    this.level4AC = level4AC;
  }

  public String getLevel5() {
    return level5;
  }

  public void setLevel5(String level5) {
    this.level5 = level5;
  }

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }
}
