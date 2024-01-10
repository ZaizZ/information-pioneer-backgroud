package pioneer.media.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class TaskPoolConfig {
    @Bean("asyncExecutor")
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程池数
        executor.setCorePoolSize(4);
		//最大线程池数
        executor.setMaxPoolSize(20);
         //任务队列的容量
        executor.setQueueCapacity(200);
        // 非核心线程的存活时间
        executor.setKeepAliveSeconds(60);
        // 线程池的前缀名称
        executor.setThreadNamePrefix("my_asyncExecutor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());//调用主线程，让主线程处理
        executor.initialize();
        return executor;
    }
}
