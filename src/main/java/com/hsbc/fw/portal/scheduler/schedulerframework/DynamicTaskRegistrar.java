package com.hsbc.fw.portal.scheduler.schedulerframework;
import com.hsbc.fw.portal.scheduler.schedulerframework.config.SchedulerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class DynamicTaskRegistrar {

    private static final Logger logger = LoggerFactory.getLogger(DynamicTaskRegistrar.class);

    @Autowired
    private TaskScheduler taskScheduler;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private SchedulerConfiguration schedulerConfig;

    @PostConstruct
    public void registerTasks() {
        // Fetch all beans of type ScheduledTask (consumer tasks)
        Map<String, ScheduledTask> taskBeans = applicationContext.getBeansOfType(ScheduledTask.class);

        schedulerConfig.getTasks().forEach(task -> {
            ScheduledTask scheduledTask = taskBeans.get(task.getName());
            if (scheduledTask != null && task.isEnabled()) {
                task.getCommands().forEach(command -> {
                    if (task.getStartDateTime() != null) {
                        // Handle one-time execution at a specific date-time
                        scheduleAtExactDateTime(task, scheduledTask, command);
                    } else {
                        // Handle repeating tasks (cron, fixedRate, etc.)
                        processTaskAsync(task, scheduledTask, command);
                    }
                });
            }
        });
    }

    @Async  // Run task registration in parallel
    public void processTaskAsync(SchedulerConfiguration.ScheduledTask task, ScheduledTask scheduledTask, String command) {
        logger.info("Processing task asynchronously: {} with command: {}", task.getName(), command);

        ReentrantLock lock = new ReentrantLock();  // Task-level locking

        if (task.getCron() != null) {
            taskScheduler.schedule(() -> runTaskWithCommand(scheduledTask, lock, command), new CronTrigger(task.getCron()));
        } else if (task.getFixedRate() > 0) {
            taskScheduler.scheduleAtFixedRate(() -> runTaskWithCommand(scheduledTask, lock, command), task.getFixedRate());
        } else if (task.getFixedDelay() > 0) {
            taskScheduler.scheduleWithFixedDelay(() -> runTaskWithCommand(scheduledTask, lock, command), task.getFixedDelay());
        }
    }

    // Schedule a task to run at a specific date and time, only if the system time matches
    private void scheduleAtExactDateTime(SchedulerConfiguration.ScheduledTask task, ScheduledTask scheduledTask, String command) {
        LocalDateTime startDateTime = LocalDateTime.parse(task.getStartDateTime());
        Date startDate = Date.from(startDateTime.atZone(ZoneId.systemDefault()).toInstant());

        if (new Date().before(startDate)) {  // Only schedule the task if the date/time is in the future
            taskScheduler.schedule(() -> {
                runTaskWithCommand(scheduledTask, new ReentrantLock(), command);
            }, startDate);
        } else {
            logger.info("The startDateTime {} for task {} has already passed. Skipping execution.", task.getStartDateTime(), task.getName());
        }
    }

    private void runTaskWithCommand(ScheduledTask scheduledTask, ReentrantLock lock, String command) {
        if (lock.tryLock()) {
            try {
                scheduledTask.executeCommand(command);  // Pass the command to the task
            } finally {
                lock.unlock();  // Always release the lock after execution
            }
        } else {
            logger.info("Task {} with command {} is already running. Skipping execution.", scheduledTask.getClass().getSimpleName(), command);
        }
    }

    // Register cron tasks dynamically
    public ScheduledFuture<?> registerCronTask(Runnable task, String cronExpression) {
        return taskScheduler.schedule(task, new CronTrigger(cronExpression));
    }
}


