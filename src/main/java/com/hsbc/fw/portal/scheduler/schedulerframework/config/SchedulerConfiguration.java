package com.hsbc.fw.portal.scheduler.schedulerframework.config;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class SchedulerConfiguration {

    private List<ScheduledTask> tasks;

    @PostConstruct
    public void loadConfigFromJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            SchedulerConfiguration config = mapper.readValue(new File("tasks-config.json"), SchedulerConfiguration.class);
            this.tasks = config.getTasks();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<ScheduledTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<ScheduledTask> tasks) {
        this.tasks = tasks;
    }

    public static class ScheduledTask {
        private String name;
        private String cron;
        private long fixedRate;
        private long fixedDelay;
        private boolean enabled;
        private String className;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCron() {
            return cron;
        }

        public void setCron(String cron) {
            this.cron = cron;
        }

        public long getFixedRate() {
            return fixedRate;
        }

        public void setFixedRate(long fixedRate) {
            this.fixedRate = fixedRate;
        }

        public long getFixedDelay() {
            return fixedDelay;
        }

        public void setFixedDelay(long fixedDelay) {
            this.fixedDelay = fixedDelay;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        // Getters and Setters for all properties
    }
}