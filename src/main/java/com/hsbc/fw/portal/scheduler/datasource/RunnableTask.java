package com.hsbc.fw.portal.scheduler.datasource;

import com.hsbc.fw.portal.scheduler.schedulerframework.ScheduledTask;
import org.springframework.stereotype.Component;

@Component
public class RunnableTask implements ScheduledTask {

    private String taskName;

    // Constructor for taskName (or it can be injected dynamically)
    public RunnableTask(String taskName) {
        this.taskName = taskName;
    }

    // Default constructor for Spring
    public RunnableTask() {}

    @Override
    public void run() {
        // Here you can add your task-specific logic
        System.out.println("Executing task: " + taskName);

        // Any business logic, for example, interacting with other Spring services.
    }

    // Setter for task name to dynamically set taskName if needed
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * @param command
     */
    @Override
    public void executeCommand(String command) {

    }

}
