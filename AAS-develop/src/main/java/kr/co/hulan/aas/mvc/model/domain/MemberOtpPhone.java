package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "member_otp_phone")
@IdClass(MemberOtpPhoneKey.class)
public class MemberOtpPhone {

  @Id
  @Basic
  @Column(name = "mb_id", columnDefinition="varchar(20)")
  private String mbId;
  @Id
  @Basic
  @Column(name = "phone_no", columnDefinition="varchar(20)")
  private String phoneNo;
  @Basic
  @Column(name = "uuid", columnDefinition="varchar(64)")
  private String uuid;
  @Basic
  @Column(name = "create_date", columnDefinition="datetime")
  private java.util.Date createDate;
  @Basic
  @Column(name = "creator", columnDefinition="varchar(20)")
  private String creator;

}
