package kr.co.hulan.aas.config.http;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ExecutorServiceConfiguration {

    private static int DEFAULT_EXECUTOR_THREAD_POOL_COUNT = Runtime.getRuntime().availableProcessors();

    @Bean(destroyMethod = "shutdownNow")
    public ExecutorService fixedExecutorService() {
        return Executors.newFixedThreadPool(DEFAULT_EXECUTOR_THREAD_POOL_COUNT);
    }
}
