package kr.co.hulan.aas.mvc.api.jobMgr.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.MemberLevel;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.member.controller.request.CreateWorkerRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="CreateRecruitApplyRequest", description="구직 정보 생성 요청")
public class CreateRecruitApplyRequest {

  @NotBlank(message="현장 아이디는 필수입니다")
  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;

  @NotBlank(message="협력사 아이디는 필수입니다")
  @ApiModelProperty(notes = "협력사 아이디")
  private String coopMbId;

  @NotBlank(message="근로자 아이디는 필수입니다")
  @ApiModelProperty(notes = "근로자 아이디")
  private String mbId;

}
