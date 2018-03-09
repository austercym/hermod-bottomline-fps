package com.orwellg.hermod.bottomline.fps;

import com.codahale.metrics.MetricRegistry;
import com.orwellg.hermod.bottomline.fps.spring.context.SpringProfileSettingApplicationContextInitializer;
import com.orwellg.hermod.bottomline.fps.spring.core.env.SpringArchaiusPropertySource;
import com.orwellg.hermod.bottomline.fps.utils.properties.DefaultPropertyValues;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableAsync
public class FpsBootApplication {
	private static final Logger LOG = LogManager.getLogger(FpsBootApplication.class);

	@Bean
	public MetricRegistry metricRegistry(){
		return new MetricRegistry();
	}

	@Bean
	public TaskExecutor taskOutboundRequestExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(20);
		executor.setMaxPoolSize(20);
		executor.setThreadNamePrefix("task_outbound_request_executor_thread");
		executor.initialize();
		return executor;
	}
	@Bean
	public TaskExecutor taskOutboundResponseExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(20);
		executor.setMaxPoolSize(20);
		executor.setThreadNamePrefix("task_outbound_response_executor_thread");
		executor.initialize();
		return executor;
	}

	@Bean
	public TaskExecutor taskInboundResponseExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(20);
		executor.setMaxPoolSize(20);
		executor.setThreadNamePrefix("task_inbound_response_executor_thread");
		executor.initialize();
		return executor;
	}

	@Bean
	public TaskExecutor taskInboundReversalExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(20);
		executor.setMaxPoolSize(20);
		executor.setThreadNamePrefix("task_inbound_reversal_executor_thread");
		executor.initialize();
		return executor;
	}

	@Bean
	public TaskExecutor taskInboundRequestExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(20);
		executor.setMaxPoolSize(20);
		executor.setThreadNamePrefix("task_inbound_request_executor_thread");
		executor.initialize();
		return executor;
	}

    public static void main(String[] args) {
    	try {
			new SpringApplicationBuilder(FpsBootApplication.class).initializers(new SpringProfileSettingApplicationContextInitializer("hermod-bottomline-fps.properties", DefaultPropertyValues.getDefaultValues())).run(args);
		}catch(Exception e){
    		LOG.error("[FPS] Error running connector {}", e.getMessage(), e);
		}
    }

}
