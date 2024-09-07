package com.hsbc.fw.portal.scheduler.schedulerframework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.hsbc.fw.portal.scheduler") // Scans the consumer package for components
@EnableScheduling // Enables scheduling for the application
public class SchedulerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchedulerApplication.class, args);
    }
}