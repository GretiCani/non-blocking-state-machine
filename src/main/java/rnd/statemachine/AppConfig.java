package rnd.statemachine;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@EnableAsync
public class AppConfig implements AsyncConfigurer {

	@Bean(name = "threadPoolTaskExecutor")
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(2);
		executor.setQueueCapacity(20);
		executor.setThreadNamePrefix("StateMachine-");
		executor.initialize();
		return executor;
	}

	/**
	 * Catches unhandled exceptions in @Async methods
	 */
	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new AsyncUncaughtExceptionHandler(){
			@Override
			public void handleUncaughtException(Throwable ex, Method method, Object... params) {
				log.error("Exception in the async method {}.{}() {} {}", method.getDeclaringClass().getSimpleName(), method.getName(), ex.getClass().getSimpleName(), ex.getMessage());		
			}
		};
	}
}
