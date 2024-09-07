package com.hsbc.fw.portal.scheduler.schedulerframework;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TaskRegistry {

    private Map<String, Runnable> taskMap = new HashMap<>();

    public void registerTask(String taskName, Runnable taskLogic) {
        taskMap.put(taskName, taskLogic);
    }

    public Runnable getTask(String taskName) {
        return taskMap.get(taskName);
    }
}
