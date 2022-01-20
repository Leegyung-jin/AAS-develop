package kr.co.hulan.aas.mvc.service.push;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import kr.co.hulan.aas.mvc.model.dto.G5MemberDto;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true, value = { "token", "appVersion"})
@Data
public class PushTargetInfo {

    @JsonIgnoreProperties
    private String token;

    private String wp_id;
    private String mb_id;

    @JsonIgnoreProperties
    private String appVersion;

    public PushTargetInfo(){}

    public PushTargetInfo(G5MemberDto memberDto){
        this.mb_id = memberDto.getMbId();
        this.wp_id = memberDto.getWorkPlace();
        this.token = memberDto.getDeviceId();
        this.appVersion = memberDto.getAppVersion();
    }

    public PushTargetInfo(String mb_id, String wp_id, String token, String appVersion){
        this.mb_id = mb_id;
        this.wp_id = wp_id;
        this.token = token;
        this.appVersion = appVersion;
    }

    @JsonIgnore
    public Integer getNotiType(){
        return StringUtils.isNotEmpty(appVersion)
                && StringUtils.compare(appVersion, "3.2.6") > 0
                ? 1 : 0 ;
    }
}

