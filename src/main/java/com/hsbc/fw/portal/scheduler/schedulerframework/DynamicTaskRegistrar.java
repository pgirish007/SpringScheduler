package com.hsbc.fw.portal.scheduler.schedulerframework;

import com.hsbc.fw.portal.scheduler.schedulerframework.config.SchedulerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DynamicTaskRegistrar implements ApplicationListener<ContextRefreshedEvent> {
    Logger logger =  LoggerFactory.getLogger(DynamicTaskRegistrar.class);

    @Autowired
    private TaskScheduler taskScheduler;

    @Autowired
    private SchedulerConfiguration schedulerConfig;

    @Autowired
    private ApplicationContext applicationContext;  // Get all beans at runtime

    /*
    @PostConstruct
    public void registerTasks() {
        // Fetch all beans of type ScheduledTask (consumer tasks)
        Map<String, ScheduledTask> taskBeans = applicationContext.getBeansOfType(ScheduledTask.class);
        logger.info("Registering tasks");
        schedulerConfig.getTasks().forEach(task -> {
            logger.info("Registering tasks: " + task.getName());
            logger.info("taskBeans tasks and type is: " + taskBeans);
            ScheduledTask scheduledTask = taskBeans.get(task.getName());  // Find the matching task
            if (scheduledTask != null && task.isEnabled()) {
                if (task.getCron() != null) {
                    taskScheduler.schedule(scheduledTask, new CronTrigger(task.getCron()));
                } else if (task.getFixedRate() > 0) {
                    taskScheduler.scheduleAtFixedRate(scheduledTask, task.getFixedRate());
                } else if (task.getFixedDelay() > 0) {
                    taskScheduler.scheduleWithFixedDelay(scheduledTask, task.getFixedDelay());
                }
            }
        });
    }
*/

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // This method will be called after the context is refreshed
        Map<String, ScheduledTask> taskBeans = applicationContext.getBeansOfType(ScheduledTask.class);
        logger.info("taskBeans tasks and type is: " + taskBeans);

        schedulerConfig.getTasks().forEach(task -> {
            logger.info("Registering tasks: " + task.getName());
            ScheduledTask scheduledTask = taskBeans.get(task.getName());  // Find the matching task
            if (scheduledTask != null && task.isEnabled()) {
                if (task.getCron() != null) {
                    taskScheduler.schedule(scheduledTask, new CronTrigger(task.getCron()));
                } else if (task.getFixedRate() > 0) {
                    taskScheduler.scheduleAtFixedRate(scheduledTask, task.getFixedRate());
                } else if (task.getFixedDelay() > 0) {
                    taskScheduler.scheduleWithFixedDelay(scheduledTask, task.getFixedDelay());
                }
            }
        });
    }
}

