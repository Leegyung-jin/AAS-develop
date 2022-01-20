package kr.co.hulan.aas.mvc.api.monitor4_2.controller.response;

import io.swagger.annotations.ApiModel;
import java.util.List;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.nvr.WorkplaceNvrMonitorConfigVo;
import kr.co.hulan.aas.mvc.model.dto.NetworkVideoRecoderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ImosIntelliVixMainResponse", description="IMOS 지능형 CCTV 메인 정보 응답")
public class ImosIntelliVixMainResponse {

  private WorkplaceNvrMonitorConfigVo config;

  private List<NetworkVideoRecoderDto> nvrList;
}
