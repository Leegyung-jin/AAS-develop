package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "member_otp")
@IdClass(MemberOtpKey.class)
public class MemberOtp {

  @Id
  @Basic
  @Column(name = "mb_id", columnDefinition="varchar(20)")
  private String mbId;
  @Id
  @Basic
  @Column(name = "create_date", columnDefinition="datetime")
  private java.util.Date createDate;
  @Basic
  @Column(name = "otp_num", columnDefinition="int")
  private Integer otpNum;
  @Basic
  @Column(name = "otp_usage", columnDefinition="int")
  private Integer otpUsage;
  @Basic
  @Column(name = "validate_date", columnDefinition="datetime")
  private java.util.Date validateDate;


}
