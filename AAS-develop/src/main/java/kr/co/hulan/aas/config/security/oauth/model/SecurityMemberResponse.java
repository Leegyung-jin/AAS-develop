package kr.co.hulan.aas.config.security.oauth.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import lombok.Data;

@Data
public class SecurityMemberResponse implements Serializable {

  @JsonIgnoreProperties
  @ApiModelProperty(notes = "회원 일련번호")
  private Long mbNo;
  @ApiModelProperty(notes = "회원 아이디")
  private String mbId;
  @ApiModelProperty(notes = "회원 성명")
  private String mbName;
  @ApiModelProperty(notes = "등급")
  private int mbLevel;
  @ApiModelProperty(notes = "최종접속시간")
  private java.util.Date latestLogin;
  @ApiModelProperty(notes = "가입일")
  private java.util.Date registDate;
  @ApiModelProperty(notes = "패스워드 갱신일")
  private java.util.Date pwdChangeDate;

  @ApiModelProperty(notes = "건설사 아이디. (과거 현장관리자)")
  private String ccId;
  @ApiModelProperty(notes = "건설사명. (과거 현장관리자)")
  private String ccName;
  @ApiModelProperty(notes = "현장아이디. (과거 현장관리자)")
  private String wpId;
  @ApiModelProperty(notes = "현장명. (과거 현장관리자)")
  private String wpName;

  @ApiModelProperty(notes = "협력사 아이디. (협력사)")
  private String coopMbId;
  @ApiModelProperty(notes = "협력사명. (협력사)")
  private String coopMbName;


  @ApiModelProperty(notes = "건설사번호. (현장관리자, 건설사 관리자)")
  private String loginCcId;
  @ApiModelProperty(notes = "건설사명. (현장관리자, 건설사 관리자) ")
  private String loginCcName;
  @ApiModelProperty(notes = "발주사번호. (발주사 관리자, 발주사 현장그룹 매니저)")
  private Long loginOfficeNo;
  @ApiModelProperty(notes = "발주사명. (발주사 관리자, 발주사 현장그룹 매니저)")
  private String loginOfficeName;

  @ApiModelProperty(notes = "해당 현장의 GPS monitoring 사용 유부. 0:N, 1:Y")
  private Integer useGpsMonitor;

  @ApiModelProperty(notes = "해당 현장의 BLE monitoring 사용 유부. 0:N, 1:Y")
  private Integer useBleMonitor;

  @JsonIgnoreProperties
  private boolean needChgPwdNoti;


}
