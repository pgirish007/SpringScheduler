package com.hsbc.fw.portal.scheduler.schedulerframework.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class SchedulerConfig {
    Logger logger =  LoggerFactory.getLogger(SchedulerConfig.class);

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        logger.info("Creating ThreadPoolTaskScheduler");
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10);  // Configure the number of concurrent threads
        scheduler.setThreadNamePrefix("scheduler-thread-");
        scheduler.initialize();
        return scheduler;
    }

}
