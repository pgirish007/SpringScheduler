package com.hsbc.fw.portal.scheduler.datasource;

import com.hsbc.fw.portal.scheduler.schedulerframework.DynamicTaskRegistrar;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerService {

    private final DynamicTaskRegistrar taskRegistrar;
    private final DataSourceService dataSourceService;

    public SchedulerService(DynamicTaskRegistrar taskRegistrar, DataSourceService dataSourceService) {
        this.taskRegistrar = taskRegistrar;
        this.dataSourceService = dataSourceService;
    }

    public void configureAndSchedule(String sourceType) {
        // Fetch configuration from the data source (FTP, Splunk, Oracle, etc.)
        dataSourceService.fetchDataSourceConfig(sourceType);

        // After fetching configuration, dynamically register tasks based on the config
        // Use logic to parse and register tasks for the framework
        System.out.println("Configuring tasks from: " + sourceType);
        // Assume that after fetching, configurations are parsed and tasks are created.
        RunnableTask runnableTask = new RunnableTask();
        runnableTask.setTaskName(sourceType);
        taskRegistrar.registerCronTask(runnableTask, "0/30 * * * * ?");
    }

    @Scheduled(cron = "*/30 * * * * *")
    public void scheduledTask() {
        System.out.println("Executing scheduled task...");
    }
}

