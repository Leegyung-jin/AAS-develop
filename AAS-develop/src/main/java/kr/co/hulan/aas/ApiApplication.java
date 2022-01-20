package kr.co.hulan.aas;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableScheduling
public class ApiApplication extends SpringBootServletInitializer {

    private Logger logger = LoggerFactory.getLogger(ApiApplication.class);

    @Autowired
    private Environment environment;

    @PostConstruct
    public void printProfiles(){
        StringBuilder sb = new StringBuilder();
        sb.append("Application is called. Active Profile [");
        sb.append(StringUtils.join(environment.getActiveProfiles(), ","));
        sb.append("]");
        logger.info(sb.toString());
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ApiApplication.class);
    }
}
