package kr.co.hulan.aas.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix="file.repository")
public class FileConfigurationProperties {

    private String download;
    private String path;

    private String tempFilePath;
    private String safeFilePath;
    private String memberFilePath;
    private String boardFilePath;
    private String workplaceFilePath;
    private String buildingFilePath;
    private String cctvFilePath;
    private String uiComponentFilePath;
    private String officeFilePath;
    private String conCompanyFilePath;
    private String imosNoticeFilePath;

}

